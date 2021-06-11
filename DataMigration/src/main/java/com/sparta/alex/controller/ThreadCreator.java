package com.sparta.alex.controller;

import com.sparta.alex.model.EmployeeDAO;
import com.sparta.alex.model.EmployeeDTO;

import java.util.HashMap;

public class ThreadCreator {

    public static int intThreadRunning = 0;

    public static void threadCreator (HashMap<Integer, EmployeeDTO> employeeDTOHashMap) {
        String lock = "lock";
        Runnable run = () -> {
            EmployeeDAO dao = new EmployeeDAO();
                dao.insertIntoDB(employeeDTOHashMap);
        };
        Thread thread = new Thread(run);
        thread.start();
    }
}
