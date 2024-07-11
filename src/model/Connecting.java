package src.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Connecting {
    public static boolean StudentConnection(Scanner scanner){
        System.out.println("Enter your mail: ");
        String mail = scanner.nextLine();

        System.out.println("Enter your password: ");
        String password = scanner.nextLine();

        Connection connection = null;
        PreparedStatement stmt = null;

        try {
            connection = Database.getConnection();
            String sql = "SELECT * FROM Student WHERE Mail = ? AND Password = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, mail);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                System.out.println("Connection successful!");
                return true; 
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false; 
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

    public static boolean AdminConnection(Scanner scanner){
        System.out.println("Enter your mail: ");
        String mail = scanner.nextLine();

        System.out.println("Enter your password: ");
        String password = scanner.nextLine();

        Connection connection = null;
        PreparedStatement stmt = null;

        try {
            connection = Database.getConnection();
            String sql = "SELECT * FROM Admin WHERE Mail = ? AND Password = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, mail);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                System.out.println("Connection successful!");
                return true; 
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false; 
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
}
