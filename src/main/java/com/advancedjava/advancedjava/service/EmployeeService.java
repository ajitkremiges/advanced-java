package com.advancedjava.advancedjava.service;

import java.util.List;
import java.util.Map;

import com.advancedjava.advancedjava.entity.Employee;

public interface EmployeeService {
    Employee addEmployee(Employee employee);

    List<Employee> getAllEmployees();

    List<Map<String, Object>> getFilteredEmployeeList(String filter);

    Map<String, Object> getEmployeeDetails(String employeeId);

    boolean updateEmployeeDetails(String employeeId, Employee updateRequest);

    boolean deleteEmployee(String employeeId);

    boolean copyEmployeeToShadow(String employeeId);

    boolean updateEmployeeDetailsWithShadow(String employeeId, Employee updateRequest);
    
    }
    


