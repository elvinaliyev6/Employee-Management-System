package com.company.controller;

import com.company.model.Employee;
import com.company.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class EmployeeController {

    @Autowired
    EmployeeService service;

    @GetMapping("/")
    public String viewHomePage(Model model){
        model.addAttribute("listEmployees",service.findAll());
        return "index";
    }

    @GetMapping("/showNewEmployeeForm")
    public String showNewEmployeeForm(Model model){
        Employee employee=new Employee();
        model.addAttribute("employee",employee);
        return "new_employee";
    }

    @PostMapping("saveEmployee")
    public String formSuccess(@ModelAttribute("employee") Employee employee, @Valid Employee emp, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return "update_employee";
        }else{

            try{
                service.saveEmployee(employee);
            }catch (Exception e){
                e.printStackTrace();
            }
            return "redirect:/";
        }

    }

    @GetMapping("/showFormUpdate/{id}")
    public String showFormUpdate(@PathVariable(value = "id") long id,Model model){
        Employee employee=service.getEmployee(id);
        model.addAttribute("employee",employee);
        return "update_employee";
    }

}
