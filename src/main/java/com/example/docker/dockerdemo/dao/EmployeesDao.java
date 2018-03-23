package com.example.docker.dockerdemo.dao;

import com.example.docker.dockerdemo.entity.EmployeesDO;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface EmployeesDao extends ElasticsearchRepository<EmployeesDO,Integer> {

    List<EmployeesDO> findByFirstName(String firstName, Sort sort);

    EmployeesDO findByEmpNo(Integer id);

}
