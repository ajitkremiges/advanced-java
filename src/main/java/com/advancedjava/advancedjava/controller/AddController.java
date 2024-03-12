package com.advancedjava.advancedjava.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping; 
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.advancedjava.advancedjava.entity.Employee;
import com.advancedjava.advancedjava.service.EmployeeService;

@RestController
@RequestMapping("/myhr/employee")
public class AddController {

    private static final Logger logger = LoggerFactory.getLogger(AddController.class);

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/add")
    public ResponseEntity<?> addEmployee(@RequestBody Employee employee) {
        logger.debug("Received request to add employees");
        try {
            Employee savedEmployee = employeeService.addEmployee(employee);
            return ResponseEntity.ok().body(savedEmployee);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("status: error, message: " + e.getMessage());
        
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("status: error, message: " + e.getMessage());
        
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("status: error, message: An unexpected error occurred");
        }
    }

    @GetMapping("/list")
    public List<Object[]> getEmployeeList() {
        logger.debug("Received request to list employees");
        List<Object[]> employeeList = new ArrayList<>();
        List<Employee> employees = employeeService.getAllEmployees();

        for (Employee employee : employees) {
            Object[] employeeData = new Object[2];
            employeeData[0] = employee.getEmpId();
            employeeData[1] = employee.getFirstName();
            employeeList.add(employeeData);
        }

        return employeeList;
    }

}