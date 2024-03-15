package com.advancedjava.advancedjava.controller;
import org.springframework.http.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.advancedjava.advancedjava.entity.Employee;
import com.advancedjava.advancedjava.service.EmployeeService;
import java.io.IOException;
import java.util.List;
import java.util.Map;


import org.springframework.web.bind.annotation.PutMapping;


@RestController
public class EmployeeController {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);    
    
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/myhr/employee/list-filter")
    public List<Map<String, Object>> getFilteredEmployeeList(@RequestParam(value = "filter", required = false) String filter) {
        logger.debug("Received request to filter employees with filter: {}", filter);
        return employeeService.getFilteredEmployeeList(filter);
    }
    
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/myhr/employee/get/{employeeId}")
    public Map<String, Object> getEmployeeDetails(@PathVariable String employeeId) {
        logger.debug("Received request to get employee");
        return employeeService.getEmployeeDetails(employeeId);
    }

    @PutMapping("/myhr/employee/update/{empId}")
    public ResponseEntity<?> updateEmployeeDetails(@PathVariable String empId, @RequestBody Employee updateRequest) {
        logger.debug("Received request to update employee");
        boolean isUpdated = employeeService.updateEmployeeDetails(empId, updateRequest);
        if (isUpdated) {
            return ResponseEntity.ok("Employee details updated successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found or unable to update details.");
        }
    }

    @DeleteMapping("/myhr/employee/delete/{employeeId}")
    public ResponseEntity<String> deleteEmployee(@PathVariable String employeeId) {
        logger.debug("Received request to delete employee");
        boolean deleted = employeeService.deleteEmployee(employeeId);
        if (deleted) {
            return ResponseEntity.ok("Employee with ID " + employeeId + " has been deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee with ID " + employeeId + " not found.");
        }
    }

   
    @SuppressWarnings("null")
    @GetMapping(value = "/myhr/employee/list-xml", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<?> getEmployeeListXml(@RequestParam(value = "type", required = false) String type) {
        String xmlData = employeeService.getAllEmployeesAsXml();
        MediaType mediaType = MediaType.APPLICATION_XML;
        if (mediaType != null) {
            return ResponseEntity.ok().contentType(mediaType).body(xmlData);
        } else {
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_XML).body(xmlData);
        }
    }

    @GetMapping("/myhr/employee/list-xlsx")
    public ResponseEntity<byte[]> getAllEmployeesAsXlsx() {
        try {
            byte[] excelData = employeeService.getAllEmployeesAsXlsx();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("filename", "employees.xlsx");
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(excelData);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    
    @SuppressWarnings("null")
    @GetMapping(value = "/myhr/employee/list-pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> getEmployeeListPdf() {
        try {
            MediaType mediaType = MediaType.APPLICATION_PDF;
            byte[] pdfData = employeeService.getAllEmployeesAsPdf();
            if (pdfData != null) {
                return ResponseEntity.ok()
                        .contentType(mediaType)
                        .body(pdfData);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Error generating PDF file".getBytes());
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error generating PDF file".getBytes());
        }
    }
    
    
}    
    

    

    
    
      
    
    



