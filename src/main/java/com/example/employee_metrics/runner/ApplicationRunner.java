package com.example.employee_metrics.runner;
import com.example.employee_metrics.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component

public class ApplicationRunner implements CommandLineRunner{
    @Autowired
    private EmployeeService employeeService;

    public ApplicationRunner(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public void run(String... args) {

        String filePath = "src/main/resources/employees.csv";

        employeeService.analyzeEmployees(filePath);
    }
}
