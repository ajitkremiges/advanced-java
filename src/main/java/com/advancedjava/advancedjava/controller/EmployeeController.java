package com.advancedjava.advancedjava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.advancedjava.advancedjava.service.EmployeeService;
import java.util.List;
import java.util.Map;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/myhr/employee/list-filter")
    public List<Map<String, Object>> getFilteredEmployeeList(@RequestParam(value = "filter", required = false) String filter) {
        return employeeService.getFilteredEmployeeList(filter);
    }
    
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/myhr/employee/get/{employeeId}")
    public Map<String, Object> getEmployeeDetails(@PathVariable String employeeId) {
        return employeeService.getEmployeeDetails(employeeId);
    }
}               
  
    
    
      
    
    



