package com.example.employee_metrics.service;
import com.example.employee_metrics.model.Employee;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SalaryAnalyzerService {
    public void analyzeSalaries(Map<Integer, Employee> employees) {

        for (Employee manager : employees.values()) {

            List<Employee> subs = manager.getSubordinates();

            if (subs.isEmpty())
                continue;

            double avgSalary = subs.stream()
                    .mapToDouble(Employee::getSalary)
                    .average()
                    .orElse(0);

            double minAllowed = avgSalary * 1.20;
            double maxAllowed = avgSalary * 1.50;

            double managerSalary = manager.getSalary();

            if (managerSalary < minAllowed) {

                System.out.println("Manager " + manager.getName()
                        + " earns LESS than allowed by "
                        + (minAllowed - managerSalary));
            }

            if (managerSalary > maxAllowed) {

                System.out.println("Manager " + manager.getName()
                        + " earns MORE than allowed by "
                        + (managerSalary - maxAllowed));
            }
        }
    }
}
