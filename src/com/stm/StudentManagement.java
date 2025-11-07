package com.stm;

import java.sql.*;
import java.util.Scanner;

public class StudentManagement {

    private final Scanner sc = new Scanner(System.in);

    // --- Add Student with manual ID ---
    private void addStudent() {
        System.out.println("\n================= Add New Student =================");

        System.out.print("Enter ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Department: ");
        String dept = sc.nextLine();

        System.out.print("Enter Marks: ");
        int marks = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Email: ");
        String email = sc.nextLine();

        String query = "INSERT INTO students (id, name, department, marks, email) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setString(3, dept);
            ps.setInt(4, marks);
            ps.setString(5, email);
            ps.executeUpdate();

            System.out.println("✔ Student added successfully!");
        } catch (SQLException e) {
            System.out.println("✖ Error adding student!");
            e.printStackTrace();
        }
    }

    // --- View Students ---
    private void viewStudents() {
        String query = "SELECT * FROM students";
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            System.out.println("\n============================== Student List ===============================");
            System.out.printf("%-5s | %-20s | %-15s | %-7s | %-25s%n",
                    "ID", "Name", "Department", "Marks", "Email");
            System.out.println("----------------------------------------------------------------------------");

            boolean alternate = false;
            while (rs.next()) {
                String prefix = alternate ? "▶ " : "▪ ";
                System.out.printf(prefix + "%-5d | %-20s | %-15s | %-7d | %-25s%n",
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("department"),
                        rs.getInt("marks"),
                        rs.getString("email"));
                alternate = !alternate;
            }

        } catch (SQLException e) {
            System.out.println("✖ Error fetching students!");
            e.printStackTrace();
        }
    }

    // --- Delete Student ---
    private void deleteStudent() {
        System.out.println("\n================= Delete Student =================");

        System.out.print("Enter Student ID to delete: ");
        int id = sc.nextInt();
        sc.nextLine();

        String query = "DELETE FROM students WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            System.out.println(rows > 0 ? "✔ Student deleted!" : "✖ ID not found!");

        } catch (SQLException e) {
            System.out.println("✖ Error deleting student!");
            e.printStackTrace();
        }
    }

    // --- Update Student ---
    private void updateStudent() {
        System.out.println("\n================= Update Student =================");

        System.out.print("Enter Student ID to update: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter new Name: ");
        String name = sc.nextLine();

        System.out.print("Enter new Department: ");
        String dept = sc.nextLine();

        System.out.print("Enter new Marks: ");
        int marks = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter new Email: ");
        String email = sc.nextLine();

        try (Connection conn = DBConnection.getConnection()) {

            String selectQuery = "SELECT * FROM students WHERE id=?";
            PreparedStatement selectStmt = conn.prepareStatement(selectQuery);
            selectStmt.setInt(1, id);
            ResultSet rs = selectStmt.executeQuery();

            if (!rs.next()) {
                System.out.println("✖ ID not found!");
                return;
            }

            if (name.isEmpty()) name = rs.getString("name");
            if (dept.isEmpty()) dept = rs.getString("department");
            if (marks == -1) marks = rs.getInt("marks");
            if (email.isEmpty()) email = rs.getString("email");

            String updateQuery = "UPDATE students SET name=?, department=?, marks=?, email=? WHERE id=?";
            PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
            updateStmt.setString(1, name);
            updateStmt.setString(2, dept);
            updateStmt.setInt(3, marks);
            updateStmt.setString(4, email);
            updateStmt.setInt(5, id);

            int rows = updateStmt.executeUpdate();
            System.out.println(rows > 0 ? "✔ Student updated successfully!" : "✖ Update failed!");

        } catch (SQLException e) {
            System.out.println("✖ Error updating student!");
            e.printStackTrace();
        }
    }

    // --- Display Menu ---
    private void displayMenu() {
        int choice;
        do {
            System.out.println("\n================= Student Management System =================");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> addStudent();
                case 2 -> viewStudents();
                case 3 -> updateStudent();
                case 4 -> deleteStudent();
                case 5 -> System.out.println("Exiting...");
                default -> System.out.println("✖ Invalid choice!");
            }

        } while (choice != 5);
    }

    public static void main(String[] args) {
        new StudentManagement().displayMenu();
    }
}
