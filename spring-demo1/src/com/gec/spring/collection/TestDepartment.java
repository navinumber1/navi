package com.gec.spring.collection;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class TestDepartment {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("config/collections.xml");
        Department dept = (Department) context.getBean("dept");
        System.out.println(dept.getName());
        //List输出
        List<Employee> empList = dept.getEmpList();
        for (Employee employee : empList) {
            System.out.println(employee.getId()+","+employee.getName());
        }
        System.out.println("----------------");
        //Set输出
        Set<Employee> empSet = dept.getEmpSet();
        for (Employee employee : empSet) {
            System.out.println(employee.getId()+","+employee.getName());
        }
        System.out.println("----------------");
        Set<Map.Entry<String, Employee>> set = dept.getEmpMap().entrySet();
        for (Map.Entry<String, Employee> entry : set) {
            System.out.println(entry.getKey()+","+entry.getValue());
        }
        System.out.println("----------------");
        Properties properties = dept.getProperties();
        Set<Map.Entry<Object, Object>> entries = properties.entrySet();
        for (Map.Entry<Object, Object> entry : entries) {
            System.out.println(entry.getKey()+"::"+entry.getValue());
        }
    }
}
