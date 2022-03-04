package com.gec.activiti.junit;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.junit.Test;

public class TestJunit {
    @Test
    public void test1(){
        ProcessEngineConfiguration configuration = ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();
        configuration.setJdbcDriver("com.mysql.cj.jdbc.Driver");
        configuration.setJdbcUrl("jdbc:mysql://localhost:3306/activiti01?useUnicode=true&characterEncoding=UTF-8");
        configuration.setJdbcUsername("root");
        configuration.setJdbcPassword("123456");

        //如果当前数据库已经有23张表，就不创建，否则创建
        configuration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);

        //运行，创建表，并且返回流程引擎
        ProcessEngine processEngine = configuration.buildProcessEngine();

        System.out.println("创建流程数据库： " + processEngine);
    }

    @Test
    public void testSpring(){
        ProcessEngineConfiguration configuration =ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml");
        //运行，创建表，并且返回流程引擎
        ProcessEngine processEngine = configuration.buildProcessEngine();

        System.out.println("创建流程数据库： " + processEngine);
    }
}
