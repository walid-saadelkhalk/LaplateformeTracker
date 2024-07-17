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
            String sql = "SELECT * FROM " + userType + " WHERE Mail = ?";
            if ("Student".equalsIgnoreCase(userType)) {
                sql = "SELECT * FROM " + userType + " WHERE Mail = ? AND Password = ?";
            }
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, mail);

            if ("Student".equalsIgnoreCase(userType)) {
                String encryptedMail = Crypto.encrypt(mail);
                String encryptedPassword = Crypto.encrypt(password);
                stmt.setString(1, encryptedMail);
                stmt.setString(2, encryptedPassword);
            }

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                if ("Admin".equalsIgnoreCase(userType)) {
                    String storedHashedPassword = rs.getString("Password");
                    String hashedPassword = hashPassword(password);
                    if (!hashedPassword.equals(storedHashedPassword)) {
                        System.out.println("Invalid mail or password.");
                        return false;
                    }
                }
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

    // Méthode pour hacher un mot de passe en utilisant SHA-256
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
