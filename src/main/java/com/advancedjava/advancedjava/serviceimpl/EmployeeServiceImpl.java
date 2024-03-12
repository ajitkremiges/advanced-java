package com.advancedjava.advancedjava.serviceimpl;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.advancedjava.advancedjava.entity.Employee;
import com.advancedjava.advancedjava.entity.EmployeeShadow;
import com.advancedjava.advancedjava.repo.EmployeeRepository;
import com.advancedjava.advancedjava.repo.EmployeeShadowRepository;
import com.advancedjava.advancedjava.service.EmployeeService;


@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeShadowRepository employeeShadowRepository;

    @SuppressWarnings("null")
    @Override
    public Employee addEmployee(Employee employee) {
        
        return employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

   @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Map<String, Object>> getFilteredEmployeeList(String filter) {
        String sql = "SELECT empid, fname FROM employee";
        if (filter != null && !filter.isEmpty()) {
            sql += " WHERE LOWER(fname) LIKE '%' || LOWER(?) || '%'";
            return jdbcTemplate.queryForList(sql, filter);
        } else {
            return jdbcTemplate.queryForList(sql);
        }
    }  
    
    public EmployeeServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Map<String, Object> getEmployeeDetails(String employeeId) {
        String sql = "SELECT e.empid, e.fname, e.dob, e.doj, e.salary, r.rankdesc, d.deptname, s.fname AS supervisor " +
                     "FROM employee e " +
                     "INNER JOIN ranks r ON e.rankid = r.id " +
                     "INNER JOIN departments d ON e.deptid = d.id " +
                     "LEFT JOIN employee s ON CAST(e.reportsto AS VARCHAR) = s.empid " +
                     "WHERE e.empid = ?";
        
        try {
            return jdbcTemplate.queryForMap(sql, employeeId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
            return null;
        }
    }


    /*@Override
    public boolean updateEmployeeDetails(String employeeId, Employee updateRequest) {
        Optional<Employee> optionalEmployee = employeeRepository.findByEmpId(employeeId);
        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            employee.setFirstName(updateRequest.getFirstName());
            employee.setDateOfBirth(updateRequest.getDateOfBirth());
            employee.setDateOfJoining(updateRequest.getDateOfJoining());
            employee.setSalary(updateRequest.getSalary());
            employeeRepository.save(employee);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteEmployee(String employeeId) {
        String sql = "DELETE FROM employee WHERE empid = ?";
        int affectedRows = jdbcTemplate.update(sql, employeeId);
        return affectedRows > 0;
    }*/

    @Override
    public boolean updateEmployeeDetails(String employeeId, Employee updateRequest) {

        boolean copiedToShadow = copyEmployeeToShadow(employeeId);

        if (!copiedToShadow) {
            return false; 
        }

  
        String updateSql = "UPDATE employee SET fname = ?, dob = ?, doj = ?, salary = ? WHERE empid = ?";
        try {
            jdbcTemplate.update(updateSql, updateRequest.getFirstName(), updateRequest.getDateOfBirth(),
                    updateRequest.getDateOfJoining(), updateRequest.getSalary(), employeeId);
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }

    @Override
    public boolean deleteEmployee(String employeeId) {

        boolean copiedToShadow = copyEmployeeToShadow(employeeId);

        if (!copiedToShadow) {
            return false; 
        }


        String deleteSql = "DELETE FROM employee WHERE empid = ?";
        try {
            jdbcTemplate.update(deleteSql, employeeId);
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }

    @Override
    public boolean copyEmployeeToShadow(String employeeId) {

        String insertSql = "INSERT INTO employee_shadow SELECT * FROM employee WHERE empid = ?";
        try {
            int affectedRows = jdbcTemplate.update(insertSql, employeeId);
            return affectedRows > 0;
        } catch (DataAccessException e) {
            return false;
        }
    }

    @Override
    public boolean updateEmployeeDetailsWithShadow(String employeeId, Employee updateRequest) {
        // Find the employee by ID
        Optional<Employee> optionalEmployee = employeeRepository.findByEmpId(employeeId);
        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            
            EmployeeShadow employeeShadow = new EmployeeShadow(null, employeeId, employeeId, employeeId, null, null, null, null, null);
            employeeShadow.setEmpId(employee.getEmpId());
            employeeShadow.setFirstName(employee.getFirstName());

            employeeShadowRepository.save(employeeShadow);
            
            employee.setFirstName(updateRequest.getFirstName());
            employeeRepository.save(employee);
            
            return true;
        }
        return false;
    }
}
    
