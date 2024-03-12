package com.advancedjava.advancedjava.service;

import java.util.List;
import java.util.Map;

import com.advancedjava.advancedjava.entity.Employee;

public interface EmployeeService {
    Employee addEmployee(Employee employee);

    List<Employee> getAllEmployees();

    List<Map<String, Object>> getFilteredEmployeeList(String filter);

    Map<String, Object> getEmployeeDetails(String employeeId);

}

