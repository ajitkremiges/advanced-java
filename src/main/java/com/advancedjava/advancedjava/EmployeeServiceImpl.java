package com.advancedjava.advancedjava;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

}

