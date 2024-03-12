package com.advancedjava.advancedjava.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.advancedjava.advancedjava.entity.Employee;
import com.advancedjava.advancedjava.service.EmployeeService;

import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
public class EmployeeController {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);    

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/myhr/employee/list-filter")
    public List<Map<String, Object>> getFilteredEmployeeList(@RequestParam(value = "filter", required = false) String filter) {
        logger.debug("Received request to filter employees");
        return employeeService.getFilteredEmployeeList(filter);
    }
    
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/myhr/employee/get/{employeeId}")
    public Map<String, Object> getEmployeeDetails(@PathVariable String employeeId) {
        logger.debug("Received request to get employee");
        return employeeService.getEmployeeDetails(employeeId);
    }

    @PutMapping("/myhr/employee/update/{empId}")
    public ResponseEntity<?> updateEmployeeDetails(@PathVariable String empId, @RequestBody Employee updateRequest) {
        logger.debug("Received request to update employee");
        boolean isUpdated = employeeService.updateEmployeeDetails(empId, updateRequest);
        if (isUpdated) {
            return ResponseEntity.ok("Employee details updated successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found or unable to update details.");
        }
    }

    @DeleteMapping("/myhr/employee/delete/{employeeId}")
    public ResponseEntity<String> deleteEmployee(@PathVariable String employeeId) {
        logger.debug("Received request to delete employee");
        boolean deleted = employeeService.deleteEmployee(employeeId);
        if (deleted) {
            return ResponseEntity.ok("Employee with ID " + employeeId + " has been deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee with ID " + employeeId + " not found.");
        }
    }
    
    /*@PutMapping("/myhr/employee/update/{empId}")
    public ResponseEntity<String> updateEmployee(@PathVariable String employeeId, @RequestBody Employee updateRequest) {
        boolean success = employeeService.updateEmployeeDetailsWithShadow(employeeId, updateRequest);
        if (success) {
            return ResponseEntity.ok("Employee with ID " + employeeId + " has been updated successfully.");
        } else {
            return ResponseEntity.badRequest().body("Failed to update employee with ID " + employeeId);
        }
    }

    @DeleteMapping("/myhr/employee/delete/{employeeId}")
    public ResponseEntity<String> deleteEmployee(@PathVariable String employeeId) {
        boolean success = employeeService.deleteEmployeeWithShadow(employeeId);
        if (success) {
            return ResponseEntity.ok("Employee with ID " + employeeId + " has been deleted successfully.");
        } else {
            return ResponseEntity.badRequest().body("Failed to delete employee with ID " + employeeId);
        }
    }*/
}               
  
    
    
      
    
    



