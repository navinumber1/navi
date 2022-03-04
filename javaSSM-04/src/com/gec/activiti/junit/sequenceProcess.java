package com.gec.activiti.junit;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class sequenceProcess {
    ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();//默认读取activiti.cfg.xml 不用改文件名;

    @Test //1.部署流程定义图 ,相当于业务的"模板" ,类
    public void testDeployProcess() {
        System.out.println(engine);
        //获取业务对象
        Deployment deployment = engine.getRepositoryService()
                .createDeployment()
                .name("分支连线")
                .addClasspathResource("icon/sequenceProcess.bpmn")
                .addClasspathResource("icon/sequenceProcess.png")
                .deploy();
        System.out.println("部署的id" + deployment.getId());
        System.out.println("部署名称:" + deployment.getName());
    }

    @Test //2. 启动流程实例,相当于某一次的流程的操作 ,对象
    public void testStartProcess() {
        String key="sequenceProcess";
        ProcessInstance pi = engine.getRuntimeService().startProcessInstanceByKey(key);
        System.out.println("流程实例id:" + pi.getId());//流程实例id:1001
        System.out.println("流程实例id:" + pi.getProcessInstanceId());
        System.out.println("流程实例定义的id: " + pi.getProcessDefinitionId());
    }


    @Test //4.流程任务结束(流程推进)
    public void testFinishTask() {
        String taskId="1103";
        Map<String, Object> map = new HashMap<>();
        //存放流程变量
        map.put("money",200);
        engine.getTaskService().complete(taskId);
        System.out.println("任务完成!");
    }

}
