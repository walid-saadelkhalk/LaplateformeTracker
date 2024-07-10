package src.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AdminRepository {

    public void createAccount(String firstName, String lastName, int age, String email, String password) {
        String sql = "INSERT INTO Student (First_name, Last_name, Age, ID_student, ID_gradebook, Mail, Password) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = Database.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
                
    
            
            String studentId = generateStudentId(firstName, lastName);
    
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setInt(3, age);
            stmt.setString(4, studentId);
            stmt.setInt(5, 1); 
            stmt.setString(6, email);
            stmt.setString(7, password);
    
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Student inserted successfully!");
            } else {
                System.out.println("Failed to insert student.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update student information
    public void updateStudent(int studentId, String firstName, String lastName, int age, String email, String password) {
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

    // Delete a student
    public void deleteStudent(int studentId) {
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

    // Get all students
    public void getAllStudents() {
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
                System.out.println("ID: " + id + ", Name: " + firstName + " " + lastName + ", Age: " + age + ", Email: " + email + ", Password: " + password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Search for a student by ID
    public void searchStudentById(int studentId) {
        String sql = "SELECT * FROM Student WHERE ID = ?";
        try (Connection connection = Database.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("ID");
                String firstName = rs.getString("First_name");
                String lastName = rs.getString("Last_name");
                int age = rs.getInt("Age");
                String email = rs.getString("Mail");
                String password = rs.getString("Password");
                System.out.println("ID: " + id + ", Name: " + firstName + " " + lastName + ", Age: " + age + ", Email: " + email + ", Password: " + password);
            } else {
                System.out.println("Student not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Generate student ID (example method)
    private String generateStudentId(String firstName, String lastName) {
        return firstName.substring(0, 1).toUpperCase() + lastName.toLowerCase();
    }
}
