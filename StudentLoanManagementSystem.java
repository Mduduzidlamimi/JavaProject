
package student_loan_apll_system;

import java.sql.*;
import java.util.Scanner;

public class StudentLoanManagementSystem {
  private static final String URL = "jdbc:mysql://localhost:3306/student_Loan_Apll_System";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    
    private Connection connection;
    private Scanner scanner;
    
    public StudentLoanManagementSystem() {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            scanner = new Scanner(System.in);
        } catch (SQLException e) {
        }
    }
    
    public void displayMenu() {
        while (true) {
            System.out.println("\n=== Student Loan Management System ===");
            System.out.println("1. Add New Student");
            System.out.println("2. Submit Loan Application");
            System.out.println("3. Add Cosigner");
            System.out.println("4. View Student Applications");
            System.out.println("5. Update Application Status");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            switch (choice) {
                case 1 -> addStudent();
                case 2 -> submitLoanApplication();
                case 3 -> addCosigner();
                case 4 -> viewStudentApplications();
                case 5 -> updateApplicationStatus();
                case 6 -> {
                    exit(); return;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }
    
    private void addStudent() {
        try {
            System.out.println("\n=== Add New Student ===");
            System.out.print("stud_ID ");
            String Stud_ID = scanner.nextLine();
            System.out.print("First Name: ");
            String firstName = scanner.nextLine();
            System.out.print("Last Name: ");
             String Date_Of_Birth = scanner.nextLine();
            String lastName = scanner.nextLine();
            System.out.print("National Id ");
            String NationalID = scanner.nextLine();
            System.out.print("phone ");
            String Phone = scanner.nextLine();
            String Address = scanner.nextLine();
            String EnrolmentStatus = scanner.nextLine();
            String Program = scanner.nextLine();
            String Credits = scanner.nextLine();
            
            String sql = "INSERT INTO Student (Stud_ID,First_Name, LName,Date_Of_Birth,National_id,Phone,Address,EnrolmentStatus,Program,Credits) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, Stud_ID);
            stmt.setString(2, firstName);
            stmt.setString(3, lastName);
            stmt.setString(4, Date_Of_Birth);
            stmt.setString(5, NationalID);
            stmt.setString(6, Phone);
            stmt.setString(7, Address);
            stmt.setString(8, EnrolmentStatus);
              stmt.setString(9, Program);
                stmt.setString(10, Credits);
            
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Student added successfully!");
            }
        } catch (SQLException e) {
            System.out.println("Error adding student: " + e.getMessage());
        }
    }
    
    private void submitLoanApplication() {
        try {
            System.out.println("\n=== Submit Loan Application ===");
            System.out.print("Student ID: ");
            int studentId = scanner.nextInt();
            System.out.print("Loan Amount: ");
            double loanAmount = scanner.nextDouble();
            scanner.nextLine(); // Consume newline
            System.out.print("Loan Purpose: ");
            String purpose = scanner.nextLine();
            
            String sql = "INSERT INTO LoanApplication (StudentID, LoanAmount, LoanPurpose, ApplicationStatus) VALUES (?, ?, ?, 'Pending')";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, studentId);
            stmt.setDouble(2, loanAmount);
            stmt.setString(3, purpose);
            
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Loan application submitted successfully!");
            }
        } catch (SQLException e) {
            System.out.println("Error submitting application: " + e.getMessage());
        }
    }
    
    private void addCosigner() {
        try {
            System.out.println("\n=== Add Cosigner ===");
            System.out.print("Application ID: ");
            int applicationId = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            System.out.print("First Name: ");
            String firstName = scanner.nextLine();
            System.out.print("Last Name: ");
            String lastName = scanner.nextLine();
            System.out.print("SSN: ");
            String ssn = scanner.nextLine();
            
            String sql = "INSERT INTO Cosigner (ApplicationID, FirstName, LastName, SSN) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, applicationId);
            stmt.setString(2, firstName);
            stmt.setString(3, lastName);
            stmt.setString(4, ssn);
            
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Cosigner added successfully!");
            }
        } catch (SQLException e) {
            System.out.println("Error adding cosigner: " + e.getMessage());
        }
    }
    
    private void viewStudentApplications() {
        try {
            System.out.println("\n=== View Student Applications ===");
            System.out.print("Enter Student ID: ");
            int studentId = scanner.nextInt();
            
            String sql = "SELECT a.ApplicationID, a.LoanAmount, a.ApplicationStatus, a.SubmissionDate " +
                        "FROM LoanApplication a " +
                        "WHERE a.StudentID = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, studentId);
            
            ResultSet rs = stmt.executeQuery();
            System.out.println("\nApplication ID | Loan Amount | Status | Submission Date");
            System.out.println("------------------------------------------------");
            while (rs.next()) {
                System.out.printf("%13d | %11.2f | %6s | %s%n",
                    rs.getInt("ApplicationID"),
                    rs.getDouble("LoanAmount"),
                    rs.getString("ApplicationStatus"),
                    rs.getDate("SubmissionDate"));
            }
        } catch (SQLException e) {
            System.out.println("Error viewing applications: " + e.getMessage());
        }
    }
    
    private void updateApplicationStatus() {
        try {
            System.out.println("\n=== Update Application Status ===");
            System.out.print("Application ID: ");
            int applicationId = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            System.out.print("New Status (Pending/Approved/Rejected): ");
            String status = scanner.nextLine();
            
            String sql = "UPDATE LoanApplication SET ApplicationStatus = ? WHERE ApplicationID = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, status);
            stmt.setInt(2, applicationId);
            
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Application status updated successfully!");
            }
        } catch (SQLException e) {
            System.out.println("Error updating status: " + e.getMessage());
        }
    }
    
    private void exit() {
        try {
            if (connection != null) {
                connection.close();
            }
            scanner.close();
            System.out.println("Goodbye!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        StudentLoanManagementSystem system = new StudentLoanManagementSystem();
        system.displayMenu();
    }
}

