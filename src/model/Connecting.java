package src.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.io.Console;

public class Connecting {

    public static boolean StudentConnection(Scanner scanner) {
        Console console = System.console();
        if (console == null) {
            System.out.println("No console available");
            return false;
        }

        System.out.println("Enter your mail: ");
        String mail = scanner.nextLine();

        System.out.print("Enter your password: ");
        String password = readPasswordWithAsterisks(console);

        return authenticateUser(mail, password, "Student");
        
    }

    public static boolean AdminConnection(Scanner scanner) {
        Console console = System.console();
        if (console == null) {
            System.out.println("No console available");
            return false;
        }

        System.out.println("Enter your mail: ");
        String mail = scanner.nextLine();

        System.out.print("Enter your password: ");
        String password = readPasswordWithAsterisks(console);

        return authenticateUser(mail, password, "Admin");
    }

    private static boolean authenticateUser(String mail, String password, String userType) {
        Connection connection = null;
        PreparedStatement stmt = null;

        try {
            connection = Database.getConnection();
            String sql = "SELECT * FROM " + userType + " WHERE Mail = ? AND Password = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, mail);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                System.out.println("Connection successful!");
                return true;
            } else {
                System.out.println("Invalid mail or password.");
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

    private static String readPasswordWithAsterisks(Console console) {
        char[] passwordArray = console.readPassword();
        StringBuilder password = new StringBuilder();
        for (char ch : passwordArray) {
            password.append(ch);
            System.out.print("*");
        }
        System.out.println();
        return password.toString();
    }
}