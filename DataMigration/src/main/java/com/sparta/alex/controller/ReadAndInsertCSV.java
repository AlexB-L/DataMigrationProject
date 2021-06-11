package com.sparta.alex.controller;

import com.sparta.alex.util.Printer;
import com.sparta.alex.model.EmployeeDAO;
import com.sparta.alex.model.EmployeeDTO;
import com.sparta.alex.view.Main;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class ReadAndInsertCSV {

    public static final Logger logger = LogManager.getLogger(Main.class);

    public static void readAndInsertCSV(String inputFile, int fileSize) {
        int duplicateCount = 0;
        try (BufferedReader csv = new BufferedReader(new FileReader(inputFile))) {
            csv.readLine();
            String nextLine;
            HashMap<Integer, EmployeeDTO> employeeDTOHashMap = new HashMap<>();
            HashMap<Integer, EmployeeDTO> duplicateChecker = new HashMap<>();
            HashMap<Integer, EmployeeDTO> duplicateRows = new HashMap<>();
            while ((nextLine = csv.readLine()) != null) {
                String[] nextLineSplit = nextLine.split(",");
                EmployeeDTO employeeDTO = new EmployeeDTO(nextLineSplit);
                if (!duplicateChecker.containsKey(employeeDTO.getID())) {
                    employeeDTOHashMap.put(employeeDTO.getID(), employeeDTO);
                    duplicateChecker.put(employeeDTO.getID(), employeeDTO);
                    if (employeeDTOHashMap.size() == fileSize) {
                        ThreadCreator.intThreadRunning++;
                        HashMap<Integer, EmployeeDTO> transferHashMap = new HashMap<>(employeeDTOHashMap);
                        ThreadCreator.threadCreator(transferHashMap);
                        employeeDTOHashMap.clear();
                    }
                }
                else {
                    duplicateRows.put(employeeDTO.getID(), employeeDTO);
                    duplicateCount++;
                }
            }
            while (ThreadCreator.intThreadRunning > 0) {
                Thread.onSpinWait();
            }
            EmployeeDAO dao = new EmployeeDAO();
            dao.insertIntoDB(employeeDTOHashMap);
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        Printer.print("Number of duplicate rows: " + duplicateCount);
    }
}
