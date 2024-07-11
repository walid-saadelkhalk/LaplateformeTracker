package src.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StudentRepository {

    // Select student by ID
    public void selectStudentById(int studentId) {
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

    // Get all students
    public void getStudents() {
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

    // Sort students by a specified column
    // public void sortStudents(String column) {
    //     String sql = "SELECT * FROM Student ORDER BY " + column;
    //     try (Connection connection = Database.getConnection();
    //          Statement stmt = connection.createStatement();
    //          ResultSet rs = stmt.executeQuery(sql)) {

    //         while (rs.next()) {
    //             int id = rs.getInt("ID");
    //             String firstName = rs.getString("First_name");
    //             String lastName = rs.getString("Last_name");
    //             int age = rs.getInt("Age");
    //             String email = rs.getString("Mail");
    //             String password = rs.getString("Password");
    //             System.out.println("ID: " + id + ", Name: " + firstName + " " + lastName + ", Age: " + age + ", Email: " + email + ", Password: " + password);
    //         }
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //     }
    // }
}
