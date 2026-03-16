package com.example.employee_metrics.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SalaryAnalyzerServiceTest {

    @Test
    void managerSalaryWithinRange() {

        double avgSalary = 50000;
        double managerSalary = 65000;

        double minAllowed = avgSalary * 1.2;
        double maxAllowed = avgSalary * 1.5;

        assertTrue(managerSalary >= minAllowed && managerSalary <= maxAllowed);
    }

    @Test
    void managerSalaryTooLow() {

        double avgSalary = 50000;
        double managerSalary = 55000;

        double minAllowed = avgSalary * 1.2;

        assertTrue(managerSalary < minAllowed);
    }

    @Test
    void managerSalaryTooHigh() {

        double avgSalary = 50000;
        double managerSalary = 80000;

        double maxAllowed = avgSalary * 1.5;

        assertTrue(managerSalary > maxAllowed);
    }

    @Test
    void managerSalaryExactlyTwentyPercent() {

        double avgSalary = 50000;
        double managerSalary = 60000;

        assertEquals(avgSalary * 1.2, managerSalary);
    }

    @Test
    void managerSalaryExactlyFiftyPercent() {

        double avgSalary = 50000;
        double managerSalary = 75000;

        assertEquals(avgSalary * 1.5, managerSalary);
    }
}