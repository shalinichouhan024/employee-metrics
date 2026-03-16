package com.example.employee_metrics.util;

import com.example.employee_metrics.model.Employee;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class CsvFileReaderTest {

    @Test
    void testCsvFileReadSuccessfully() {

        CsvFileReader reader = new CsvFileReader();

        Map<Integer, Employee> employees =
                reader.readEmployees("src/main/resources/employees.csv");

        assertNotNull(employees);
        assertFalse(employees.isEmpty());
    }

    @Test
    void testEmployeesLoaded() {

        CsvFileReader reader = new CsvFileReader();

        Map<Integer, Employee> employees =
                reader.readEmployees("src/main/resources/employees.csv");

        // should contain multiple employees
        assertTrue(employees.size() >= 1);
    }

    @Test
    void testCeoHasNoManager() {

        CsvFileReader reader = new CsvFileReader();

        Map<Integer, Employee> employees =
                reader.readEmployees("src/main/resources/employees.csv");

        // find employee with no manager
        boolean ceoFound = employees.values()
                .stream()
                .anyMatch(emp -> emp.getManagerId() == null);

        assertTrue(ceoFound);
    }

    @Test
    void testManagerRelationshipExists() {

        CsvFileReader reader = new CsvFileReader();

        Map<Integer, Employee> employees =
                reader.readEmployees("src/main/resources/employees.csv");

        // ensure at least one employee has a manager
        boolean managerRelationshipExists = employees.values()
                .stream()
                .anyMatch(emp -> emp.getManagerId() != null);

        assertTrue(managerRelationshipExists);
    }
}