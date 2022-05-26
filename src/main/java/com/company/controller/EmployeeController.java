package com.company.controller;

import com.company.model.Employee;
import com.company.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class EmployeeController {

    @Autowired
    EmployeeService service;

    @GetMapping("/")
    public String viewHomePage(Model model) {
        return findPaginated(1, "firstName", "asc", model);
    }

    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo, @RequestParam("sortField") String sortField, @RequestParam("sortDir") String sortDir, Model model) {
        int pageSize = 5;

        Page<Employee> page = service.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<Employee> listEmployees = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listEmployees", listEmployees);
        return "index";
    }

    @GetMapping("/showNewEmployeeForm")
    public String showNewEmployeeForm(Model model) {
        Employee employee = new Employee();
        model.addAttribute("employee", employee);
        return "new_employee";
    }

    @PostMapping("saveEmployee")
    public String formSuccess(@ModelAttribute("employee") Employee employee, @Valid Employee emp, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "update_employee";
        } else {

            try {
                service.saveEmployee(employee);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "redirect:/";
        }

    }

    @GetMapping("/showFormUpdate/{id}")
    public String showFormUpdate(@PathVariable(value = "id") long id, Model model) {
        Employee employee = service.getEmployeeById(id);
        model.addAttribute("employee", employee);
        return "update_employee";
    }

    @GetMapping("/deleteEmployee/{id}")
    public String deleteEmployee(@PathVariable(value = "id") long id) {
        service.deleteEmployeeById(id);
        return "redirect:/";
    }


}
