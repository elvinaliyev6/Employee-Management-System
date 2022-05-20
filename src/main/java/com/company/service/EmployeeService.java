package com.company.service;

import com.company.dao.EmployeeRepository;
import com.company.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EmployeeService {

    @Autowired
    EmployeeRepository repo;

    public List<Employee> findAll(){
        return  repo.findAll();
    }

    public void saveEmployee(Employee employee){
        repo.save(employee);
    }
}
