package src.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.io.Console;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Connecting {
    /*
     * Function to connect as a student
     * Function to connect as an admin
     * Function to authenticate a user
     */

    // Function to connect as a student
    public static User StudentConnection(Scanner scanner) {
        Console console = System.console();
        if (console == null) {
            System.out.println("No console available");
            return null;
        }

        System.out.println("Enter your mail: ");
        String mail = scanner.nextLine();

        System.out.print("Enter your password: ");
        String password = readPasswordWithAsterisks(console);

        return authenticateUser(mail, password, "Student");
    }
 
    // Function to connect as an admin
    public static User AdminConnection(Scanner scanner) {
        Console console = System.console();
        if (console == null) {
            System.out.println("No console available");
            return null;
        }

        System.out.println("Enter your mail: ");
        String mail = scanner.nextLine();

        System.out.print("Enter your password: ");
        String password = readPasswordWithAsterisks(console);

        return authenticateUser(mail, password, "Admin");
    }

    // Function to authenticate a user
    private static User authenticateUser(String mail, String password, String userType) {
        Connection connection = null;
        PreparedStatement stmt = null;

        try {
            System.out.println("Authenticating user: " + mail + " as " + userType);  // Debugging line
            connection = Database.getConnection();
            String sql = "SELECT * FROM " + userType + " WHERE Mail = ?";
            stmt = connection.prepareStatement(sql);
            String encryptedMail = Crypto.encrypt(mail);  // Encrypt the email before querying
            stmt.setString(1, encryptedMail);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String storedHashedPassword = rs.getString("Password");
                String hashedPassword = hashPassword(password);
                System.out.println("Stored hashed password: " + storedHashedPassword);  // Debugging line
                System.out.println("Entered hashed password: " + hashedPassword);  // Debugging line

                if (hashedPassword.equals(storedHashedPassword)) {
                    System.out.println("Connection successful!");
                    String firstName = Crypto.decrypt(rs.getString("First_name"));
                    String lastName = Crypto.decrypt(rs.getString("Last_name"));
                    return new User(firstName, lastName, mail);
                } else {
                    System.out.println("Invalid mail or password.");
                    return null;
                }
            } else {
                System.out.println("Invalid mail or password.");
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            // Close the connection and statement
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

    // Function to read a password with asterisks
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

    // Function to hash a password
    private static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
}
