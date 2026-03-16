package com.example.employee_metrics.service;

import com.example.employee_metrics.model.Employee;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class ReportingLineService {
    //Assumption: Max 5 levels of management allowed in reporting line (excluding CEO)
    //Considering CEO not as manager, so max 4 managers allowed in reporting line
    //If we consider CEO as manager, then max 5 managers allowed in reporting line
    private static final int MAX_MANAGERS_ALLOWED = 4;

    public void checkReportingLines(Map<Integer, Employee> employees) {

        for (Employee emp : employees.values()) {

            int managers = countManagers(emp, employees);

            if (managers > MAX_MANAGERS_ALLOWED) {

                System.out.println("Employee "
                        + emp.getName()
                        + " has too long reporting line by "
                        + (managers - MAX_MANAGERS_ALLOWED));
            }
        }
    }

    private int countManagers(Employee emp, Map<Integer, Employee> employees) {

        int count = 0;
        Integer managerId = emp.getManagerId();

        Set<Integer> visited = new HashSet<>();

        while (managerId != null) {

            if (visited.contains(managerId)) {
                System.out.println("Cycle detected in reporting line for employee " + emp.getName());
                break;
            }

            visited.add(managerId);

            Employee manager = employees.get(managerId);

            if (manager == null) {
                System.out.println("Manager with ID " + managerId + " not found for employee " + emp.getName());
                break;
            }

            if (manager.getManagerId() == null) {
                // stop when CEO is reached
                break;
            }

            count++;
            managerId = manager.getManagerId();
        }

        return count;
    }
}