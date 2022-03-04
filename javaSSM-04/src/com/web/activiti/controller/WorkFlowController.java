package com.web.activiti.controller;

import com.web.activiti.pojo.ActiveUser;
import com.web.activiti.pojo.Baoxiaobill;
import com.web.activiti.service.WrokFlowSerivice;
import com.web.activiti.utils.Constants;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

@Controller
public class WorkFlowController {

    @Autowired
    private WrokFlowSerivice wrokFlowSerivice;

    /**
     * 上传数据库并刷新到流程部署表
     * 部署流程定义图 ,相当于业务的"模板" ,类
     *
     * @param processName
     * @param processFile
     * @return
     */
    @RequestMapping("/deployProcess")
    public String deployProcess(String processName, MultipartFile processFile) {
        try {
            wrokFlowSerivice.deployProcess(processName, processFile.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/processDefinitionList";
    }

    /**
     * 查询流程部署信息  act_re_deployment
     * 看戏流程定义信息   act_re_procdef
     *
     * @return
     */
    @RequestMapping("/processDefinitionList")
    public ModelAndView processDefinitionList() {
        ModelAndView mv = new ModelAndView();
        List<Deployment> deployment = wrokFlowSerivice.findAllDeployment();
        //查询流程部署信息  act_re_deployment    流程部署表
        mv.addObject("depList", deployment);
        //查询流程定义信息  act_re_procdef   流程定义表，保存流程定义的信息
        List<ProcessDefinition> processDefinitions = wrokFlowSerivice.findAllProcessDefinitions();
        mv.addObject("pdList", processDefinitions);
        mv.setViewName("workflow_list");
        return mv;
    }

    /**
     * 保存请假单到数据库中
     * 启动访问流程实例
     *
     * @param baoxiaobill
     * @param session
     * @return
     */

    @RequestMapping("/saveStartLeave")
    public String saveStartLeave(Baoxiaobill baoxiaobill, HttpSession session) {
        ActiveUser activeUser = (ActiveUser) session.getAttribute("activeUser");
        wrokFlowSerivice.saveStartLeave(baoxiaobill, activeUser.getEmployees());
        return "redirect:/myTaskList";
    }

    /**
     * 查看当前代办事务
     *
     * @param session
     * @return
     */
    @RequestMapping("/myTaskList")
    public ModelAndView myTaskList(HttpSession session) {
        ModelAndView mv = new ModelAndView();
        ActiveUser activeUser = (ActiveUser) session.getAttribute("activeUser");
        List<Task> taskList = wrokFlowSerivice.findByUserTask(activeUser.getEmployees().getName());
        mv.addObject("taskList", taskList);
        mv.setViewName("workflow_task");
        return mv;
    }

    /**
     * 从Task查询出请假单的业务表
     * 查询出当前流程的所有批注信息
     *
     * @param taskId
     * @return
     */
    @RequestMapping("viewTaskForm")
    public ModelAndView viewTaskForm(String taskId) {
        //1.从Task查询出请假单的业务表
        Baoxiaobill baoxiaobill = wrokFlowSerivice.finBillByTask(taskId);
        //2.查询出当前流程的所有批注信息
        List<Comment> commentList = wrokFlowSerivice.findCommentList(taskId);
        //3.获取当前活动任务的封装信息,才得到每个活动任务的连线信息
        List<String> outComeListByTaskId = wrokFlowSerivice.findOutComeListByTaskId(taskId);
        ModelAndView mv = new ModelAndView();
        mv.addObject("bill", baoxiaobill);
        mv.addObject("commentList", commentList);
        mv.addObject("taskId", taskId);
        mv.addObject("outcomeList", outComeListByTaskId);
        mv.setViewName("approve_baoxiao");
        return mv;
    }

    /**
     * 提交申请单,任务推进
     *
     * @param taskId
     * @param id
     * @param comment
     * @param session
     * @return
     */
    @RequestMapping("/submitTask")
    public String submitTask(String taskId, Integer id, String comment, String outcome, HttpSession session) {
        ActiveUser activeUser = (ActiveUser) SecurityUtils.getSubject().getPrincipal();
        //ActiveUser activeUser = (ActiveUser) session.getAttribute("activeUser");

        //提交申请单,做任务推进(结束)
        wrokFlowSerivice.saveSubmitTask(taskId, id, comment, outcome, activeUser.getUsername());
        return "redirect:/myTaskList";
    }

    /**
     * 查询当前流程定义图
     */
    @RequestMapping("/viewCurrentImage")
    public String viewCurrentImage(String taskId, ModelMap model) {
        //查看流程图
        //1.获取任务ID,获取任务对象,使用任务对象获取流程定义ID,查询流程定义对象
        ProcessDefinition pd = wrokFlowSerivice.findProcessDefinitionByTaskId(taskId);
        model.addAttribute("deploymentId", pd.getDeploymentId());
        model.addAttribute("imageName", pd.getDiagramResourceName());
        //2.查看当前获动,获取的当前活动对应的坐标x,y,width,height 将4个值存放到Map<String ,Object>
        Map<String, Object> map = wrokFlowSerivice.findCoordingByTask(taskId);
        model.addAttribute("acs", map);
        return "viewimage";
    }


    /**
     * 查看流程定义图
     */
    @RequestMapping("/viewImage")
    public String viewImage(String deploymentId, String imageName, HttpServletResponse response) throws IOException {
        InputStream in = wrokFlowSerivice.findImageInpuStream(deploymentId, imageName);
        OutputStream outputStream = response.getOutputStream();
        int length = 0;
        while ((length = in.read()) != -1) {
            outputStream.write(length);
        }
        outputStream.close();
        in.close();
        return null;
    }

    /**
     * 删除流程定义 true 级联强制删除，把正在运行的流程实例也删除
     */
    @RequestMapping("/delDeployment")
    public String delDeployment(String deploymentId) {
        wrokFlowSerivice.deleteDeployment(deploymentId, true);
        return "redirect:/processDefinitionList";
    }

    @RequestMapping("/viewCurrentImageByBill")
    public String viewCurrentImageByBill(ModelMap model, Integer billId) {
        System.out.println("1111");
        String bussiness_key = Constants.LEAVEBILL_KEY + "." + billId;
        Task task = wrokFlowSerivice.findTaskByBussinessKey(bussiness_key);
        System.out.println("task.getId() = " + task.getId());
        System.out.println("task = " + task);
        /*一.查看流程图*/
        //1.获取任务ID,获取任务对象,使用任务对象获取流程定义ID,查询流程定义对象
        ProcessDefinition pd = wrokFlowSerivice.findProcessDefinitionByTaskId(task.getId());
        model.addAttribute("deploymentId", pd.getDeploymentId());
        model.addAttribute("imageName", pd.getDiagramResourceName());
        /*二.查看当前活动,获取活动对应的坐标x,y,width,height,将4个值存放到Map<String ,Object>*/
        Map<String, Object> map = wrokFlowSerivice.findCoordingByTask(task.getId());
        model.addAttribute("acs", map);
        return "viewimage";
    }


}
