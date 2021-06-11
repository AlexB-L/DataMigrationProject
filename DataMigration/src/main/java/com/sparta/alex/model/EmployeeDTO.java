package com.sparta.alex.model;

import java.sql.Date;

public class EmployeeDTO {

    private int ID;
    private String namePrefix;
    private String firstName;
    private String middleInitial;
    private String lastName;
    private String gender;
    private String eMail;
    private Date dateOfBirth;
    private Date dateOfJoining;
    private int salary;

    public EmployeeDTO (String[] string) {
        this.ID = toInt(string[0]);
        this.namePrefix = string[1];
        this.firstName = string[2];
        this.middleInitial = string[3];
        this.lastName = string[4];
        this.gender = string[5];
        this.eMail = string[6];
        this.dateOfBirth = toSQLDate(string[7]);
        this.dateOfJoining = toSQLDate(string[8]);
        this.salary = toInt(string[9]);
    }

    private Date toSQLDate (String date) {
        if (date.matches("[0-9]/[0-9]{2}/[0-9]{4}")) {
            date = "0" + date;
        }
        if (date.matches("[0-9]{2}/[0-9]/[0-9]{4}")) {
            date = date.substring(0, 3) + "0" + date.substring(3);
        }
        if (date.matches("[0-9]/[0-9]/[0-9]{4}")) {
            date = "0" + date.substring(0, 2) + "0" + date.substring(2);
        }
        String[] values = date.split("/");
        date = values[2] + "-" + values[0] + "-" + values[1];
        Date dateSQL = Date.valueOf(date);
        return dateSQL;
    }

    private int toInt (String number) {
        int num = Integer.parseInt(number);
        return num;
    }

    public int getID() {
        return ID;
    }

    public String getNamePrefix() {
        return namePrefix;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleInitial() {
        return middleInitial;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

    public String geteMail() {
        return eMail;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public Date getDateOfJoining() {
        return dateOfJoining;
    }

    public int getSalary() {
        return salary;
    }
}
