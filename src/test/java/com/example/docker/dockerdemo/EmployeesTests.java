package com.example.docker.dockerdemo;

import com.example.docker.dockerdemo.dao.EmployeesDao;
import com.example.docker.dockerdemo.entity.EmployeesDO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DockerDemoApplication.class)
public class EmployeesTests {

    @Autowired
    EmployeesDao employeesRepository;

    @Test
    public void Test() {
        EmployeesDO employeesDO = new EmployeesDO();
        //employeesDO.setEmpNo(123);
        employeesDO.setFirstName("Tom1");
        employeesDO.setLastName("jery1");
        employeesDO.setGender("M1");

        employeesRepository.save(employeesDO);
    }

    @Test
    public void queryTest(){
        Iterable<EmployeesDO> list = employeesRepository.findAll();
        list.forEach(employeesDO -> {
            System.out.println(employeesDO.getFirstName());
        });
        EmployeesDO employeesDO = employeesRepository.findByEmpNo(123);
        System.out.println(employeesDO.getFirstName());
    }



}
