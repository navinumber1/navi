package com.gec.activiti.junit;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipInputStream;


public class expression02Process {
    ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();//默认读取activiti.cfg.xml 不用改文件名;

    @Test //1.部署流程定义图 ,相当于业务的"模板" ,类
    public void testDeployProcess() {
        System.out.println(engine);
        //获取业务对象
        Deployment deployment = engine.getRepositoryService()
                .createDeployment()
                .name("监听分配代办人")
                .addClasspathResource("icon/expressionProcess02.bpmn")
                .addClasspathResource("icon/expressionProcess02.png")
                .deploy();
        System.out.println("部署的id" + deployment.getId());
        System.out.println("部署名称:" + deployment.getName());
    }

    @Test //2. 启动流程实例,相当于某一次的流程的操作 ,对象
    public void testStartProcess() {
        String key="expression02Process";
        ProcessInstance pi = engine.getRuntimeService().startProcessInstanceByKey(key);
        System.out.println("流程实例id:" + pi.getProcessInstanceId());
        System.out.println("流程实例定义的id: " + pi.getProcessDefinitionId());
    }


    @Test //4.流程任务结束(流程推进)
    public void testFinishTask() {
        String taskId="2004";
        engine.getTaskService().complete(taskId);
        System.out.println("任务完成!");
    }


}
