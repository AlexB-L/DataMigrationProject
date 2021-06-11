package com.sparta.alex.model;

import com.sparta.alex.view.Main;
import com.sparta.alex.controller.ThreadCreator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.FileReader;
import java.sql.*;
import java.util.HashMap;
import java.util.Properties;

public class EmployeeDAO {

    public static final Logger logger = LogManager.getLogger(Main.class);

    private static final String URL = "jdbc:mysql://localhost:3306/employeedb?serverTimezone=GMT";
    private static Connection connection;
    private static Properties properties = new Properties();
    private static final String dropTableEmployees = "DROP TABLE IF EXISTS employees;";
    private static final String createTableEmployees =  "CREATE TABLE employees (empID int, namePrefix varchar(10), firstName varchar(20), middleInitial char(1), lastName varchar(20), gender char(1), eMail varchar(40), dateOfBirth date, dateOfJoining date, salary int, PRIMARY KEY (empID));";
    private static final String readDataIntoTableEmployees = "INSERT INTO employees (empID, namePrefix, firstName, middleInitial, lastName, gender, eMail, dateOfBirth, dateOfJoining, salary) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

    public static Connection connectionToDatabase() {
        try {
            properties.load(new FileReader("resources/login.properties"));
            connection = DriverManager.getConnection(URL, properties.getProperty("username"), properties.getProperty("password"));
        } catch (SQLException e) {
            logger.error(e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return connection;
    }

    public static void createTableEmployees() {
        try {
            Statement statement = connectionToDatabase().createStatement();
            statement.execute(dropTableEmployees);
            System.out.println("Table has been dropped");
            statement.execute(createTableEmployees);
            System.out.println("Table has been created");
        } catch (SQLException throwables) {
            logger.error(throwables.getMessage());
        }
    }

    public void insertIntoDB (HashMap<Integer, EmployeeDTO> employeeDTOHashMap) {
        try {
            PreparedStatement preparedStatement = connectionToDatabase().prepareStatement(readDataIntoTableEmployees);
                employeeDTOHashMap.forEach((key, employeeDTO) -> {
                    try {
                        preparedStatement.setInt(1, employeeDTO.getID());
                        preparedStatement.setString(2, employeeDTO.getNamePrefix());
                        preparedStatement.setString(3, employeeDTO.getFirstName());
                        preparedStatement.setString(4, employeeDTO.getMiddleInitial());
                        preparedStatement.setString(5, employeeDTO.getLastName());
                        preparedStatement.setString(6, employeeDTO.getGender());
                        preparedStatement.setString(7, employeeDTO.geteMail());
                        preparedStatement.setDate(8, employeeDTO.getDateOfBirth());
                        preparedStatement.setDate(9, employeeDTO.getDateOfJoining());
                        preparedStatement.setInt(10, employeeDTO.getSalary());
                        preparedStatement.addBatch();
                    } catch (SQLException throwables) {
                        logger.error(throwables.getMessage());
                    }
                });
                preparedStatement.executeBatch();
            } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        //Printer.print("Insert finished by " + Thread.currentThread().getName());
        ThreadCreator.intThreadRunning--;
    }
}
