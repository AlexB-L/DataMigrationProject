package com.sparta.alex.view;

import com.sparta.alex.util.Printer;
import com.sparta.alex.controller.ReadAndInsertCSV;
import com.sparta.alex.model.EmployeeDAO;

public class Starter {


    public static void start() {

        String inputFileLarge ="resources/employeeRecordsLarge.csv";
        String inputFile ="resources/employees.csv";

        double start = System.nanoTime();
        EmployeeDAO.createTableEmployees();
        ReadAndInsertCSV.readAndInsertCSV(inputFile, 250);
        double end = System.nanoTime();
        double time = (end - start) / 1000000000.0;
        Printer.print("Insert has finished");
        Printer.print("This took " + String.format("%.2f", time) + " seconds");

    }
}
