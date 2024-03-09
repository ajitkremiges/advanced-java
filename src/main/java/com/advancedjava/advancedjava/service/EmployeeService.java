package com.advancedjava.advancedjava.service;

import java.util.List;

import com.advancedjava.advancedjava.entity.Employee;

public interface EmployeeService {
    Employee addEmployee(Employee employee);

    List<Employee> getAllEmployees();
}

