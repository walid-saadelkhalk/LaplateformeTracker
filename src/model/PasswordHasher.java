package src.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class PasswordHasher {
    /* 
     * Class to hash a password with SHA-256 algorithm
     */

    // Function to test the hashing of a password
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter password:");
        String password = scanner.nextLine();

        String hashedPassword = hashPassword(password);

        System.out.println("Original password: " + password);
        System.out.println("Hashed password: " + hashedPassword);
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