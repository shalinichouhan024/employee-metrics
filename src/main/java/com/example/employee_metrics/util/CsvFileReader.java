package com.example.employee_metrics.util;
import com.example.employee_metrics.model.Employee;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CsvFileReader {

    public Map<Integer, Employee> readEmployees(String filePath) {
        Map<Integer, Employee> employees = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // skip header

            while ((line = br.readLine()) != null) {
                String[] data = parseCsvLine(line);

                if (data.length < 4) {
                    System.out.println("Invalid CSV line (less than 4 columns): " + java.util.Arrays.toString(data));
                    continue;
                }

                try {
                    int id = Integer.parseInt(data[0]);
                    String firstName = data[1];
                    String lastName = data[2];
                    double salary = Double.parseDouble(data[3]);

                    Integer managerId = data.length > 4 && !data[4].isEmpty()
                            ? Integer.parseInt(data[4])
                            : null;

                    Employee emp = new Employee(id, firstName, lastName, salary, managerId);
                    employees.put(id, emp);
                } catch (NumberFormatException e) {
                    System.out.println("Error parsing numeric values in line: " + java.util.Arrays.toString(data));
                }
            }

            // Build manager-subordinate relationship
            for (Employee emp : employees.values()) {
                if (emp.getManagerId() != null) {
                    Employee manager = employees.get(emp.getManagerId());
                    if (manager != null) {
                        manager.addSubordinate(emp);
                    } else {
                        System.out.println("Manager with ID " + emp.getManagerId() + " not found for employee " + emp.getName());
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("Error reading CSV file: " + filePath);
            e.printStackTrace();
        }

        return employees;
    }

    private String[] parseCsvLine(String line) {
        List<String> tokens = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        boolean inQuotes = false;
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                tokens.add(sb.toString().trim()); // Trim whitespace? Maybe not needed, but safe.
                sb.setLength(0);
            } else {
                sb.append(c);
            }
        }
        tokens.add(sb.toString().trim());
        return tokens.toArray(new String[0]);
    }
}