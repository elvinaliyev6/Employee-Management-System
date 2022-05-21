package com.company.service;

import com.company.dao.EmployeeRepository;
import com.company.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

    public Employee getEmployee(long id){
        Optional<Employee> optional= repo.findById(id);

        Employee employee=null;
        if(optional.isPresent()){
            employee=optional.get();
        }else{
            throw new RuntimeException("Employee not found for id :: "+id);
        }
        return employee;
    }
}
