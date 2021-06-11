package com.sparta.alex.controller;

import com.sparta.alex.model.EmployeeDAO;
import com.sparta.alex.model.EmployeeDTO;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class ReadCSV {



    public static void readCSV (String inputFile, int fileSize) {
        try (BufferedReader csv = new BufferedReader(new FileReader(inputFile))) {
            csv.readLine();
            String nextLine;
            HashMap<Integer, EmployeeDTO> employeeDTOHashMap = new HashMap<>();
            HashMap<Integer, EmployeeDTO> duplicateChecker = new HashMap<>();
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
            }
            while (ThreadCreator.intThreadRunning > 0) {
                Thread.onSpinWait();
            }
            EmployeeDAO employeeDAO = new EmployeeDAO();
            employeeDAO.insertIntoDB(employeeDTOHashMap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
