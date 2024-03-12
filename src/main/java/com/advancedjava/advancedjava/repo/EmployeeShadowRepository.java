package com.advancedjava.advancedjava.repo;
import com.advancedjava.advancedjava.entity.Employee;
import com.advancedjava.advancedjava.entity.EmployeeShadow;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeShadowRepository extends JpaRepository<EmployeeShadow, Long> {

    Optional<Employee> findByEmpId(String employeeId);
}
