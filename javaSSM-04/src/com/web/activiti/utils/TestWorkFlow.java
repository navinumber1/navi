package com.web.activiti.utils;


import com.web.activiti.mapper.EmployeeMapper;
import com.web.activiti.mapper.SysPermissionMapperCustom;
import com.web.activiti.pojo.*;
import com.web.activiti.service.EmployeeService;
import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.zip.ZipInputStream;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext-*.xml", "classpath:spring/springmvc.xml"})
public class TestWorkFlow {
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private FormService formService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private SysPermissionMapperCustom sysPermissionMapperCustom;
    @Autowired
    private EmployeeService employeeService;


    @Test//1.部署流程
    public void testDeployDB() throws FileNotFoundException {

        InputStream inputstream = new FileInputStream("E:/bpmnicon/baoxiaoprocess.zip");
        ZipInputStream zipInputstream = new ZipInputStream(inputstream);
        Deployment deployment = repositoryService.createDeployment()
                .name("报销测试02")
                .addZipInputStream(zipInputstream)
                .deploy();
        System.out.println("deployment id: " + deployment.getId());
        System.out.println("deployment name: " + deployment.getName());
    }

    @Test//2.启动流程实例
    public void testStartProcess() {
        String key = "baoxiao";
        Map<String, Object> map = new HashMap<>();
        map.put("userId", "lisi");
        ProcessInstance pi = runtimeService.startProcessInstanceByKey(key, map);
        System.out.println("流程实例ID:" + pi.getId());
        System.out.println("流程定义ID:" + pi.getProcessDefinitionId());
    }

    @Test//3.查询当前任务人的待办事务
    public void testFindTaskByAssignee() {
        String name = "jack";
        List<Task> list = taskService.createTaskQuery().taskAssignee(name).list();
        for (Task task : list) {
            System.out.println("任务id:" + task.getId());
            System.out.println("任务所属的流程实例id:" + task.getProcessInstanceId());
            System.out.println("任务创建的时间： " + task.getCreateTime());
        }
    }

    @Test//4. 流程的推进
    public void testTaskFinish() {
        String taskId = "605";
        taskService.complete(taskId);
        System.out.println("任务已经完成");
    }

    @Test//6. 查看流程定义图
    public void viewPic() throws IOException {
        String deploymentId = "1";
        String resourceName = "diagram/HelloworldProcess.png";
        InputStream in = repositoryService.getResourceAsStream(deploymentId, resourceName);
        File targetFile = new File("D:/" + resourceName);
        //FileUtils.copyInputStreamToFile(in, targetFile );
        System.out.println("图片已经保存");
    }

    @Test
    public void test() {
        List<SysRole> roleAndPermissionList = sysPermissionMapperCustom.findRoleAndPermissionList();
        for (SysRole sysRole : roleAndPermissionList) {
            System.out.println(sysRole.getName());
            for (SysPermission sysPermission : sysRole.getPermissionList()) {
                System.out.println(sysPermission.getName());
            }
        }
    }

    @Test
    public void test1() {
        String password = "123";
        String salt = new SecureRandomNumberGenerator().nextBytes().toHex(); //生成盐值
        String ciphertext = new Md5Hash(password, salt, 3).toString(); //生成的密文，使用md5算法对明文与盐值的组合进行了三次加密
        System.out.println(salt);
        System.out.println(ciphertext);
        String s = UUID.randomUUID().toString();
        System.out.println(s);
    }

    @Test
    public void test2() {
        SysRole zhang1 = sysPermissionMapperCustom.findRoleAndPermissionListByUserId("zhang");
        System.out.println(zhang1);
    }
}

