package com.advancedjava.advancedjava.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.advancedjava.advancedjava.entity.Employee;
import com.advancedjava.advancedjava.repo.EmployeeRepository;
import com.advancedjava.advancedjava.service.EmployeeService;


@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @SuppressWarnings("null")
    @Override
    public Employee addEmployee(Employee employee) {
        
        return employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

}

