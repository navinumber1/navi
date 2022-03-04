package com.gec.activiti.junit;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;


public class TestHelloProcess {
    ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();//默认读取activiti.cfg.xml 不用改文件名;

    @Test //1.部署流程定义图 ,相当于业务的"模板" ,类
    public void testDeployProcess() {
        //获取业务对象
        Deployment deployment = engine.getRepositoryService()
                .createDeployment()
                .name("hello入门程序")
                .addClasspathResource("icon/icon.bpmn")
                .addClasspathResource("icon/icon.png")
                .deploy();
        System.out.println("部署的id" + deployment.getId());
        System.out.println("部署名称:" + deployment.getName());
    }

    @Test //2. 启动流程实例,相当于某一次的流程的操作 ,对象
    public void testStartProcess() {
        ProcessInstance pi = engine.getRuntimeService().startProcessInstanceByKey("HelloProcess");
        System.out.println("流程实例id:" + pi.getId());
        System.out.println("流程实例id:" + pi.getProcessInstanceId());
        System.out.println("流程实例定义的id: " + pi.getProcessDefinitionId());

    }

    @Test  //3.根据代办人查看代办事务  //desc count 方法
    public void testFindTask() {
        List<Task> list = engine.getTaskService()
                .createTaskQuery()
                .taskAssignee("张三")
                .list();
        for (Task task : list) {
            //ProcessInstanceId()流程实例id
            //ProcessDefinitionId() 流程定义id
            System.out.println(task.getId());
            System.out.println(task.getAssignee());
            System.out.println(task.getName());
            System.out.println("任务所属的流程实例id:" + task.getProcessInstanceId());
            System.out.println("任务所属的流程定义id:" + task.getProcessDefinitionId());

        }
    }

    @Test //4.流程任务结束(流程推进)
    public void testFinishTask() {
        engine.getTaskService().complete("502");
        System.out.println("任务完成!");
    }

//    @Test  //5.查看流程定义图
//    public void testViewPic() throws IOException {
//        String deploymentid = "201";
//        String resourceName = "icon/diagram.png";
//        InputStream inputStream = engine.getRepositoryService().getResourceAsStream(deploymentid, resourceName);
//        File targetFile = new File("C:/Users/徐沛鹏/Desktop/bpmn图片/" + resourceName);
//        FileUtils.copyInputStreamToFile(inputStream, targetFile);
//        System.out.println("成功");
//    }

    @Test //6.判断流程实例是否结束,查询出流程实例后,以非空判断
    public void testPiExist() {
        String id = "101";
        ProcessInstance pi = engine.getRuntimeService()
                        .createProcessInstanceQuery()
                        .processInstanceId(id).singleResult();
        if (pi!=null){
            System.out.println("流程正在运行....");
        }else {
            System.out.println("流程已经结束!");
        }
    }

    @Test //7. 删除流程定义
    public void testRemoveProcess() {

        String deploymentId = "3601";
        //第二参数 ： true 级联强制删除，把正在运行的流程实例也删除
        engine.getRepositoryService().deleteDeployment(deploymentId, true);
        System.out.println("删除成功");
    }

    @Test
    public void setVarbles(){
        TaskService taskService = engine.getTaskService();
        taskService.setVariable("13","人员信息表",new Date());
        System.out.println("完成");
    }
}
