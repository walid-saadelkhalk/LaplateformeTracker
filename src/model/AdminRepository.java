package src.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class AdminRepository {

    // Méthode pour créer un compte étudiant
    public static void createAccount(Scanner scanner) {
        
        System.out.println("Enter first name:");
        String firstName = scanner.nextLine();
        
        System.out.println("Enter last name:");
        String lastName = scanner.nextLine();
        
        System.out.println("Enter age:");
        int age = scanner.nextInt();
        scanner.nextLine(); 
        
        System.out.println("Enter email:");
        String email = scanner.nextLine();
        
        System.out.println("Enter password:");
        String password = scanner.nextLine();
        
<<<<<<< HEAD
        scanner.close();
        
=======
>>>>>>> dev
        Connection connection = null;
        PreparedStatement stmt = null;
        
        try {
            connection = Database.getConnection();
            String sql = "INSERT INTO Student (First_name, Last_name, Age, ID_student, ID_gradebook, Mail, Password) VALUES (?, ?, ?, ?, ?, ?, ?)";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setInt(3, age);
            stmt.setString(4, generateStudentId(firstName, lastName)); 
            stmt.setInt(5, 1); 
            stmt.setString(6, email);
            stmt.setString(7, password);
            
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Student registered successfully!");
            } else {
                System.out.println("Failed to register student.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Méthode pour mettre à jour un étudiant
    public static void updateStudent(Scanner scanner) {
        
        System.out.println("Enter student ID to update:");
        int studentId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        System.out.println("Enter new first name:");
        String firstName = scanner.nextLine();
        
        System.out.println("Enter new last name:");
        String lastName = scanner.nextLine();
        
        System.out.println("Enter new age:");
        int age = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        System.out.println("Enter new email:");
        String email = scanner.nextLine();
        
        System.out.println("Enter new password:");
        String password = scanner.nextLine();
<<<<<<< HEAD
        
        scanner.close();
=======
>>>>>>> dev

        String sql = "UPDATE Student SET First_name = ?, Last_name = ?, Age = ?, Mail = ?, Password = ? WHERE ID = ?";
        try (Connection connection = Database.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setInt(3, age);
            stmt.setString(4, email);
            stmt.setString(5, password);
            stmt.setInt(6, studentId);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Student updated successfully!");
            } else {
                System.out.println("Failed to update student.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour supprimer un étudiant
    public static void deleteStudent(Scanner scanner) {
        
        
        System.out.println("Enter student ID to delete:");
        int studentId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
<<<<<<< HEAD
        
        scanner.close();
=======
>>>>>>> dev

        String sql = "DELETE FROM Student WHERE ID = ?";
        try (Connection connection = Database.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, studentId);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Student deleted successfully!");
            } else {
                System.out.println("Failed to delete student.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour récupérer tous les étudiants
<<<<<<< HEAD
    public static void getAllStudents() {
=======
    public static void getAllStudents(Scanner scanner) {
>>>>>>> dev
        String sql = "SELECT * FROM Student";
        try (Connection connection = Database.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("ID");
                String firstName = rs.getString("First_name");
                String lastName = rs.getString("Last_name");
                int age = rs.getInt("Age");
                String email = rs.getString("Mail");
                String password = rs.getString("Password");
<<<<<<< HEAD
                System.out.println("ID: " + id + ", Name: " + firstName + " " + lastName + ", Age: " + age + ", Email: " + email + ", Password: " + password);
=======
                System.out.println("ID: " + id + "\nName: " + firstName + " " + lastName + "\nAge: " + age + "\nEmail: " + email + "\nPassword: " + password + "\n");
>>>>>>> dev
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void searchStudent(Scanner scanner) {
        
    
        System.out.println("Enter search criteria (id, firstname, lastname, age):");
        String searchBy = scanner.nextLine().toLowerCase();
    
        String sql = "";
        switch (searchBy) {
            case "id":
                sql = "SELECT * FROM Student WHERE ID = ?";
                break;
            case "firstname":
                sql = "SELECT * FROM Student WHERE First_name = ?";
                break;
            case "lastname":
                sql = "SELECT * FROM Student WHERE Last_name = ?";
                break;
            case "age":
                sql = "SELECT * FROM Student WHERE Age = ?";
                break;
            default:
                System.out.println("Invalid search criteria.");
                scanner.close();
                return;
        }
    
        System.out.println("Enter search value:");
        String searchValue = scanner.nextLine();
    
        try (Connection connection = Database.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
    
            // Bind the search value based on the search criteria
            if ("id".equals(searchBy) || "age".equals(searchBy)) {
                int intValue = Integer.parseInt(searchValue);
                stmt.setInt(1, intValue);
            } else {
                stmt.setString(1, searchValue);
            }
    
            ResultSet rs = stmt.executeQuery();
    
            while (rs.next()) {
                int id = rs.getInt("ID");
                String firstName = rs.getString("First_name");
                String lastName = rs.getString("Last_name");
                int age = rs.getInt("Age");
                String email = rs.getString("Mail");
                String password = rs.getString("Password");
<<<<<<< HEAD
                System.out.println("ID: " + id + ", Name: " + firstName + " " + lastName + ", Age: " + age + ", Email: " + email + ", Password: " + password);
=======
                System.out.println("ID: " + id + "\nFirstame: " + firstName + "\nLastname: " + lastName + "\nAge: " + age + "\nEmail: " + email + "\nPassword: " + password);
>>>>>>> dev
            }
    
            if (!rs.isBeforeFirst()) {
                System.out.println("Student not found.");
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Invalid input for numeric fields.");
        } finally {
            scanner.close();
        }
    }
    


        // Méthode pour générer un ID étudiant
    private static String generateStudentId(String firstName, String lastName) {
        return firstName.substring(0, 1).toUpperCase() + lastName.toLowerCase();
    }
}
