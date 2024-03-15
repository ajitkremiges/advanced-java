package com.advancedjava.advancedjava.serviceimpl;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Row;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import com.advancedjava.advancedjava.entity.Employee;
import com.advancedjava.advancedjava.entity.EmployeeShadow;
import com.advancedjava.advancedjava.repo.EmployeeRepository;
import com.advancedjava.advancedjava.repo.EmployeeShadowRepository;
import com.advancedjava.advancedjava.service.EmployeeService;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;


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

    @Override
    public String getAllEmployeesAsXml() {
        List<Employee> employees = getAllEmployees();
        StringBuilder xmlBuilder = new StringBuilder();
        xmlBuilder.append("<employees>");
        for (Employee employee : employees) {
            xmlBuilder.append("<employee>");
            xmlBuilder.append("<id>").append(employee.getId()).append("</id>");
            xmlBuilder.append("<name>").append(employee.getFirstName()).append("</name>");
   
            xmlBuilder.append("</employee>");
        }
        xmlBuilder.append("</employees>");
        return xmlBuilder.toString();
    }


    @Override
    public byte[] getAllEmployeesAsXlsx() throws IOException {
        List<Employee> employees = getAllEmployees();
        
        if (employees == null || employees.isEmpty()) {
            throw new IOException("No employees found");
        }

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Employees");
            int rowNum = 0;
            Row headerRow = sheet.createRow(rowNum++);
            headerRow.createCell(0).setCellValue("ID");
            headerRow.createCell(1).setCellValue("First Name");
            for (Employee employee : employees) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(employee.getId());
                row.createCell(1).setCellValue(employee.getFirstName());
            }
            workbook.write(outputStream);
            return outputStream.toByteArray();
        }
    }
    
    @Override
    public byte[] getAllEmployeesAsPdf() throws IOException {
        List<Employee> employees = employeeRepository.findAll(); // Fetching employees from the database
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, outputStream);
            document.open();
            for (Employee employee : employees) {
                document.add(new Paragraph("Employee ID: " + employee.getId()));
                document.add(new Paragraph("Employee Name: " + employee.getFirstName()));
                document.add(new Paragraph("\n"));
            }
            document.close();
            return outputStream.toByteArray();
        } catch (DocumentException e) {
            throw new IOException("Error generating PDF", e);
        }
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
    
