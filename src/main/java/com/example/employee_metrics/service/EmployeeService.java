package com.example.employee_metrics.service;

import com.example.employee_metrics.model.Employee;
import com.example.employee_metrics.util.CsvFileReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service

public class EmployeeService {
    @Autowired
    private CsvFileReader csvFileReader;

    @Autowired
    private SalaryAnalyzerService salaryAnalyzerService;

    @Autowired
    private ReportingLineService reportingLineService;

    public void analyzeEmployees(String filePath) {

        Map<Integer, Employee> employees =
                csvFileReader.readEmployees(filePath);

        salaryAnalyzerService.analyzeSalaries(employees);

        reportingLineService.checkReportingLines(employees);
    }
}
