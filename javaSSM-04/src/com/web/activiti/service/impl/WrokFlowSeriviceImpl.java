package com.web.activiti.service.impl;

import com.web.activiti.mapper.BaoxiaobillMapper;
import com.web.activiti.mapper.LeavebillMapper;
import com.web.activiti.pojo.ActiveUser;
import com.web.activiti.pojo.Baoxiaobill;
import com.web.activiti.pojo.Employee;
import com.web.activiti.pojo.Leavebill;
import com.web.activiti.service.WrokFlowSerivice;
import com.web.activiti.utils.Constants;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.InputStream;
import java.util.*;
import java.util.zip.ZipInputStream;

@Service
public class WrokFlowSeriviceImpl implements WrokFlowSerivice {

    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private HistoryService historyService;
   @Autowired
   private BaoxiaobillMapper baoxiaobillMapper;

    @Override
    public void deployProcess(String processName, InputStream resourceFile) {
        ZipInputStream zipInputStream = new ZipInputStream(resourceFile);
        Deployment deploy = this.repositoryService.createDeployment().name(processName).addZipInputStream(zipInputStream).deploy();
    }

    @Override
    public List<Deployment> findAllDeployment() {
        return repositoryService.createDeploymentQuery().list();
    }

    @Override
    public List<ProcessDefinition> findAllProcessDefinitions() {
        return repositoryService.createProcessDefinitionQuery().list();
    }

    @Override
    public void saveStartLeave(Baoxiaobill baoxiaobilll, Employee employee) {
        System.out.println("添加业务和员工数据....");
        //1.保存请假单到数据库中
        baoxiaobilll.setCreatdate(new Date());
        baoxiaobilll.setState(1);
        baoxiaobilll.setUserId(Math.toIntExact(employee.getId()));
        baoxiaobillMapper.insertSelective(baoxiaobilll);

        //2.启动访问流程实例
        String key = Constants.LEAVEBILL_KEY;
        Map<String, Object> map = new HashMap<>();
        map.put("userId", employee.getName());
        String bussiness_key = Constants.LEAVEBILL_KEY + "." + baoxiaobilll.getId(); //leaveProcess +id
        runtimeService.startProcessInstanceByKey(key, bussiness_key, map);

    }

    @Override
    public List<Task> findByUserTask(String assignee) {
        return taskService.createTaskQuery().taskAssignee(assignee).list();
    }

    @Override
    public Baoxiaobill finBillByTask(String taskId) {
        System.out.println("拿数据到页面..");
        //1.从task找打processintace
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
        String business_key = pi.getBusinessKey();
        System.out.println(business_key);
        String id = "";
        if (StringUtils.isNotBlank(business_key)) {
            id = business_key.split("\\.")[1];
        }
        System.out.println(id);
        return baoxiaobillMapper.selectByPrimaryKey(Integer.valueOf(id));
    }

    @Override
    public List<Comment> findCommentList(String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        //processid 流程实例id
        List<Comment> commentList = taskService.getProcessInstanceComments(task.getProcessInstanceId());
        return commentList;
    }

    @Override
    public void saveSubmitTask(String taskId, Integer id, String comment, String outcome, String name) {
        //1.加批注
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processinstanceId = task.getProcessInstanceId();
        //当前任务的审核人信息
        Authentication.setAuthenticatedUserId(name);
        taskService.addComment(taskId, processinstanceId, comment);
        //2.推进流程
        if (outcome != null && !"默认提交".equals(outcome)) {
            Map<String, Object> map = new HashMap<>();
            map.put("message", outcome);
            taskService.complete(taskId, map);
        } else {
            taskService.complete(taskId);
        }
        //3.如果流程实例结束,修改业务表的状态
        ProcessInstance pi = runtimeService.createProcessInstanceQuery()
                .processInstanceId(task.getProcessInstanceId()).singleResult();
        if (pi == null) {
            Baoxiaobill baoxiaobill = baoxiaobillMapper.selectByPrimaryKey(id);
            baoxiaobill.setState(2);
            baoxiaobillMapper.updateByPrimaryKey(baoxiaobill);
        }
    }

    @Override
    public InputStream findImageInpuStream(String deploymentId, String imageName) {
        return repositoryService.getResourceAsStream(deploymentId, imageName);
    }

    @Override
    public void deleteDeployment(String deploymentId, Boolean message) {
        repositoryService.deleteDeployment(deploymentId, message);
    }

    @Override
    public ProcessDefinition findProcessDefinitionByTaskId(String taskId) {
        //1.使用任务ID,查询任务对象
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        //2.获取流程定义ID
        String processDefinitionId = task.getProcessDefinitionId();
        System.out.println(processDefinitionId);
        ProcessDefinition pd = repositoryService
                .createProcessDefinitionQuery()  //创建流程定义查询对象,对应表act_re_procdef
                .processDefinitionId(processDefinitionId).singleResult();
        return pd;
    }

    /**
     * 活动节点的四个坐标参数的计算
     *
     * @param taskId
     * @return
     */
    @Override
    public Map<String, Object> findCoordingByTask(String taskId) {
        //存放坐标
        HashMap<String, Object> map = new HashMap<>();
        //使用任务ID,查询任务对象
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        //获取流程定义id
        String processDefinitionId = task.getProcessDefinitionId();
        //获取流程定义的实体对象(对应.bpmn文件)
        ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(processDefinitionId);
        //流程实例ID
        String processInstanceId = task.getProcessInstanceId();
        //使用流程实例ID,查询正在执行的执行对象表,获取当前活动对应的流程对象
        ProcessInstance pi = runtimeService.createProcessInstanceQuery()//创建流程实例查询
                .processInstanceId(processInstanceId).singleResult();
        //获取当前活动的ID
        String activityId = pi.getActivityId();
        //获取当前活动对象
        ActivityImpl activity = processDefinitionEntity.findActivity(activityId);
        //存放坐标
        map.put("x", activity.getX());
        map.put("y", activity.getY());
        map.put("width", activity.getWidth());
        map.put("height", activity.getHeight());
        return map;
    }

    /**
     * 获取当前活动任务的封装信息,才得到每个活动任务的连线信息
     *
     * @param taskId
     * @return
     */
    @Override
    public List<String> findOutComeListByTaskId(String taskId) {
        //返回存放连线的名称集合
        List<String> list = new ArrayList<>();
        //1.使用任务id,查询任务对象
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        //2.获取流程定义的id
        String processDefinitionId = task.getProcessDefinitionId();
        //3.查询ProcessDefinitionEntity对象
        ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(processDefinitionId);
        //使用任务对象Task获取流程实例ID
        String processInstanceId = task.getProcessInstanceId();
        ProcessInstance pi = runtimeService.createProcessInstanceQuery()
                .processInstanceId(processInstanceId).singleResult();
        //获取当前的活动id
        String activityId = pi.getActivityId();
        //4.获取当前的活动
        ActivityImpl activity = processDefinitionEntity.findActivity(activityId);
        //5.获取当前活动完成之后连线的名称
        List<PvmTransition> pvmList = activity.getOutgoingTransitions();
        if (pvmList != null && pvmList.size() > 0) {
            for (PvmTransition pvm : pvmList) {
                String name = (String) pvm.getProperty("name");
                if (StringUtils.isNotBlank(name)) {
                    list.add(name);
                } else {
                    list.add("默认提交");
                }
            }
        }
        return list;
    }


    @Override
    public Task findTaskByBussinessKey(String bussiness_key) {
        return taskService.createTaskQuery().processInstanceBusinessKey(bussiness_key).singleResult();
    }

}
