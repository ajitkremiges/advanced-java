package com.advancedjava.advancedjava.serviceimpl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
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
            // Handle empty result set
            return null;
        } catch (DataAccessException e) {
            // Handle other data access exceptions
            return null;
        }
    }
    

}

