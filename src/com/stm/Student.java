package com.stm;

public class Student {
    private int id;
    private String name;
    private String department;
    private int marks;
    private String email;

    public Student(String name, String department, int marks, String email) {
        this.name = name;
        this.department = department;
        this.marks = marks;
        this.email = email;
    }

    public Student(int id, String name, String department, int marks, String email) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.marks = marks;
        this.email = email;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public int getMarks() { return marks; }
    public String getEmail() { return email; }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setDepartment(String department) { this.department = department; }
    public void setMarks(int marks) { this.marks = marks; }
    public void setEmail(String email) { this.email = email; }
}
