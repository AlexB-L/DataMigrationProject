package com.sparta.alex;

import com.sparta.alex.controller.ReadAndInsertCSV;
import com.sparta.alex.model.EmployeeDAO;
import com.sparta.alex.util.Printer;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class PerformanceTest {

    String inputFileLarge ="resources/employeeRecordsLarge.csv";
    String inputFile ="resources/employees.csv";

    @Test
    public void averagePerformanceTestEmployees () {
        List<Double> timeTaken = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            double start = System.nanoTime();
            EmployeeDAO.createTableEmployees();
            ReadAndInsertCSV.readAndInsertCSV(inputFile, 250);
            double end = System.nanoTime();
            double time = (end - start) / 1000000000.0;
            timeTaken.add(time);
            Printer.print("Insert has finished");
            Printer.print("This took " + String.format("%.2f", time) + " seconds" + "\n");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        double sum = 0;
        for (double time : timeTaken) {
            sum = sum + time;
        }
        double average = sum / timeTaken.size(); //3.57s 3.72s 3.69s
        Printer.print("The average time take is " + String.format("%.2f", average) + " seconds");
    }

    @Test
    public void averagePerformanceTestEmployeeRecordsLarge () {
        List<Double> timeTaken = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            double start = System.nanoTime();
            EmployeeDAO.createTableEmployees();
            ReadAndInsertCSV.readAndInsertCSV(inputFileLarge, 1500);
            double end = System.nanoTime();
            double time = (end - start) / 1000000000.0;
            timeTaken.add(time);
            Printer.print("Insert has finished");
            Printer.print("This took " + String.format("%.2f", time) + " seconds" + "\n");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        double sum = 0;
        for (double time : timeTaken) {
            sum = sum + time;
        }
        double average = sum / timeTaken.size(); //19.94s 20.18s 20.47s
        Printer.print("The average time take is " + String.format("%.2f", average) + " seconds");
    }
}
