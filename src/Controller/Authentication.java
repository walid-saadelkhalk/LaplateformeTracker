package src.Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import src.Modele.Database;

public class Authentication {
    
    public static void create_account() {
        Scanner scanner = new Scanner(System.in);
        
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
        
        
        scanner.close();
        
       
        Connection connection = null;
        PreparedStatement stmt = null;
        
        try {
            connection = Database.getConnection();
            String sql = "INSERT INTO Student (First_name, Last_name, Age, ID_student, ID_gradebook, Mail, Password) VALUES (?, ?, ?, ?, ?, ?, ?)";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setInt(3, age);
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
    
    
    private static String generateStudentId(String firstName, String lastName) {
        return firstName.substring(0, 1).toUpperCase() + lastName.toLowerCase();
    }
    
    public static void main(String[] args) {
        create_account();
    }
}
