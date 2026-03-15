package com.example.employee_metrics.service;
import com.example.employee_metrics.model.Employee;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ReportingLineService {
    public void checkReportingLines(Map<Integer, Employee> employees) {

        for (Employee emp : employees.values()) {

            int managers = countManagers(emp, employees);

            if (managers > 4) {

                System.out.println("Employee "
                        + emp.getName()
                        + " has too long reporting line by "
                        + (managers - 4));
            }
        }
    }

    private int countManagers(Employee emp, Map<Integer, Employee> employees) {

        int count = 0;

        Integer managerId = emp.getManagerId();

        while (managerId != null) {

            count++;

            Employee manager = employees.get(managerId);
            managerId = manager.getManagerId();
        }

        return count;
    }
}
