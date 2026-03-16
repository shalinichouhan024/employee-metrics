package com.example.employee_metrics.service;

import com.example.employee_metrics.model.Employee;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ReportingLineServiceTest {

    private final ReportingLineService service = new ReportingLineService();
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    void testCheckReportingLines_CycleDetected() {
        Map<Integer, Employee> employees = new HashMap<>();
        // 1 reports to 2, 2 reports to 1
        employees.put(1, new Employee(1, "Emp1", "", 1000, 2));
        employees.put(2, new Employee(2, "Emp2", "", 1000, 1));

        service.checkReportingLines(employees);

        String output = outContent.toString();
        assertTrue(output.contains("Cycle detected in reporting line for employee Emp1")
                        || output.contains("Cycle detected in reporting line for employee Emp2"),
                "Expected cycle detection message but got: " + output);
    }

    @Test
    void testCheckReportingLines_ManagerNotFound() {
        Map<Integer, Employee> employees = new HashMap<>();
        // 1 reports to 2, but 2 is missing
        employees.put(1, new Employee(1, "Emp1", "", 1000, 2));

        service.checkReportingLines(employees);

        String output = outContent.toString();
        assertTrue(output.contains("Manager with ID 2 not found for employee Emp1"),
                "Expected manager not found message but got: " + output);
    }

    @Test
    void testCheckReportingLines_ValidChain_TooLong() {
        Map<Integer, Employee> employees = new HashMap<>();
        // 5 managers in total (including CEO). 4 managers between Emp and CEO.
        // This should be OK.
        employees.put(1, new Employee(1, "CEO", "", 10000, null));
        employees.put(2, new Employee(2, "M1", "", 8000, 1));
        employees.put(3, new Employee(3, "M2", "", 6000, 2));
        employees.put(4, new Employee(4, "M3", "", 4000, 3));
        employees.put(5, new Employee(5, "M4", "", 3000, 4));
        employees.put(6, new Employee(6, "Emp", "", 2000, 5));

        service.checkReportingLines(employees);

        String output = outContent.toString();
        assertFalse(output.contains("Employee Emp has too long reporting line"),
                "Expected NO too long reporting line message but got: " + output);
    }

    @Test
    void testCheckReportingLines_ValidChain_ReallyTooLong() {
        Map<Integer, Employee> employees = new HashMap<>();
        // 6 managers in total. 5 managers between Emp and CEO. Too long!
        employees.put(1, new Employee(1, "CEO", "", 10000, null));
        employees.put(2, new Employee(2, "M1", "", 8000, 1));
        employees.put(3, new Employee(3, "M2", "", 6000, 2));
        employees.put(4, new Employee(4, "M3", "", 4000, 3));
        employees.put(5, new Employee(5, "M4", "", 3000, 4));
        employees.put(6, new Employee(6, "M5", "", 2000, 5));
        employees.put(7, new Employee(7, "Emp", "", 1000, 6));

        service.checkReportingLines(employees);

        String output = outContent.toString();
        assertTrue(output.contains("Employee Emp has too long reporting line by 1"),
                "Expected too long reporting line message but got: " + output);
    }
}