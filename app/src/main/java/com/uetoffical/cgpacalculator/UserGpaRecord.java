package com.uetoffical.cgpacalculator;
public class UserGpaRecord {
    public String name;
    public String roll;
    public double cgpa;

    public UserGpaRecord() {
        // Default constructor required for Firebase
    }

    public UserGpaRecord(String name, String roll, double cgpa) {
        this.name = name;
        this.roll = roll;
        this.cgpa = cgpa;
    }

    public String getName() {
        return name;
    }

    public String getRoll() {
        return roll;
    }

    public double getCgpa() {
        return cgpa;
    }
}
