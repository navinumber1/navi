package com.web.activiti.service;

import com.web.activiti.pojo.Baoxiaobill;
import com.web.activiti.pojo.Employee;
import com.web.activiti.pojo.Leavebill;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * 工作流流程业务接口
 */
public interface WrokFlowSerivice {
    void deployProcess(String processName, InputStream resourceFile);

    List<Deployment> findAllDeployment();

    List<ProcessDefinition> findAllProcessDefinitions();

    void saveStartLeave(Baoxiaobill baoxiaobill, Employee employee);

    List<Task> findByUserTask(String  assignee);

    Baoxiaobill finBillByTask(String taskId);

    List<Comment> findCommentList(String taskId);

    void saveSubmitTask(String taskId, Integer id, String comment,String outcome, String name);

    InputStream findImageInpuStream(String deploymentId,String imageName);

    void deleteDeployment(String deploymentId, Boolean message);

    ProcessDefinition findProcessDefinitionByTaskId(String taskId);

    Map<String, Object> findCoordingByTask(String taskId);

    List<String> findOutComeListByTaskId(String taskId);

    Task findTaskByBussinessKey(String bussiness_key);

}
