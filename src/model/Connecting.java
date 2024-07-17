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
                    return true;
                } else {
                    System.out.println("Invalid mail or password.");
                    return false;
                }
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

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your mail:");
        String mail = scanner.nextLine();
        System.out.println("Enter your password:");
        String password = scanner.nextLine();
        
        String hashedPassword = hashPassword(password);
        String encryptedMail = Crypto.encrypt(mail);  // Encrypt the email for the test
        System.out.println("Testing with:");
        System.out.println("Mail: " + mail);
        System.out.println("Encrypted Mail: " + encryptedMail);
        System.out.println("Password: " + password);
        System.out.println("Hashed password: " + hashedPassword);

        boolean isAuthenticated = authenticateUser(mail, password, "Student");
        System.out.println("Authenticated: " + isAuthenticated);
    }
}
