package com.example.employee_metrics.util;
import com.example.employee_metrics.model.Employee;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

@Component

public class CsvFileReader {
    public Map<Integer, Employee> readEmployees(String filePath) {

        Map<Integer, Employee> employees = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            String line;
            br.readLine(); // skip header

            while ((line = br.readLine()) != null) {

                String[] data = line.split(",");

                int id = Integer.parseInt(data[0]);
                String firstName = data[1];
                String lastName = data[2];
                double salary = Double.parseDouble(data[3]);

                Integer managerId = data.length > 4 && !data[4].isEmpty()
                        ? Integer.parseInt(data[4])
                        : null;

                Employee emp = new Employee(id, firstName, lastName, salary, managerId);

                employees.put(id, emp);
            }

            // Build manager-subordinate relationship
            for (Employee emp : employees.values()) {

                if (emp.getManagerId() != null) {

                    Employee manager = employees.get(emp.getManagerId());

                    if (manager != null) {
                        manager.addSubordinate(emp);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return employees;
    }
}
