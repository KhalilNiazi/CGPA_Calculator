package com.uetoffical.cgpacalculator;

public class UserGpaRecord {
    private final String name, roll, department, semester;
    private final double cgpa;
    private final int credits;

    public UserGpaRecord(String name, String roll, String department,
                         String semester, double cgpa, int credits) {
        this.name = name;
        this.roll = roll;
        this.department = department;
        this.semester = semester;
        this.cgpa = cgpa;
        this.credits = credits;
    }

    public String getName() { return name; }
    public String getRoll() { return roll; }
    public String getDepartment() { return department; }
    public String getSemester() { return semester; }
    public double getCgpa() { return cgpa; }
    public int getCredits() { return credits; }
}
