package src.model;

import java.io.Console;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdminRepository {

    // Méthode pour créer un compte étudiant
    public static void createAccount(Scanner scanner) {
        System.out.println("Enter first name:");
        String firstName = scanner.nextLine();
        
        System.out.println("Enter last name:");
        String lastName = scanner.nextLine();

        int age = -1;
        while (age < 0) {
            System.out.println("Enter age (must be a positive integer):");
            if (scanner.hasNextInt()) {
                age = scanner.nextInt();
                if (age < 0) {
                    System.out.println("Age must be a positive integer.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid age.");
                scanner.next(); // Consume invalid input
            }
        }
        scanner.nextLine(); // Consume newline

        System.out.println("Enter student ID:");
        String studentId = scanner.nextLine();
        
        int gradebookId = -1;
        while (gradebookId < 0) {
            System.out.println("Enter gradebook ID (must be a positive integer):");
            if (scanner.hasNextInt()) {
                gradebookId = scanner.nextInt();
                if (gradebookId < 0) {
                    System.out.println("Gradebook ID must be a positive integer.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid gradebook ID.");
                scanner.next(); // Consume invalid input
            }
        }
        scanner.nextLine(); // Consume newline

        String email;
        while (true) {
            System.out.println("Enter email (must end with @harvard.com):");
            email = scanner.nextLine();
            if (email.endsWith("@harvard.com")) {
                break;
            } else {
                System.out.println("Invalid email. Please ensure it ends with @harvard.com.");
            }
        }

        String password;
        while (true) {
            password = readPassword("Enter password (minimum 8 characters, at least one special character, one uppercase letter, one lowercase letter and one number):");
            if (validatePassword(password)) {
                break;
            } else {
                System.out.println("Invalid password. Please ensure it meets the criteria.");
            }
        }

        // Chiffrement ou hachage des données
        String encryptedFirstName = Crypto.encrypt(firstName);
        String encryptedLastName = Crypto.encrypt(lastName);
        String encryptedEmail = Crypto.encrypt(email);
        String hashedPassword = hashPassword(password);

        String insertStudentSql = "INSERT INTO Student (First_name, Last_name, Age, ID_student, ID_gradebook, Mail, Password) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String insertGradeBookSql = "INSERT INTO Grade_book (ID_student) VALUES (?)";

        try (Connection connection = Database.getConnection();
             PreparedStatement insertStudentStmt = connection.prepareStatement(insertStudentSql);
             PreparedStatement insertGradeBookStmt = connection.prepareStatement(insertGradeBookSql)) {

            // Insert into Student table
            insertStudentStmt.setString(1, encryptedFirstName);
            insertStudentStmt.setString(2, encryptedLastName);
            insertStudentStmt.setInt(3, age);
            insertStudentStmt.setString(4, studentId);
            insertStudentStmt.setInt(5, gradebookId);
            insertStudentStmt.setString(6, encryptedEmail);
            insertStudentStmt.setString(7, hashedPassword);

            int studentRowsAffected = insertStudentStmt.executeUpdate();

            // Insert into Grade_book table
            insertGradeBookStmt.setString(1, studentId);

            int gradeBookRowsAffected = insertGradeBookStmt.executeUpdate();

            if (studentRowsAffected > 0 && gradeBookRowsAffected > 0) {
                System.out.println("Student account and grade book entry created successfully!");
            } else {
                System.out.println("Failed to create student account or grade book entry.");
            }

        } catch (SQLException e) {
            System.out.println("An error occurred while creating the student account or grade book entry.");
            e.printStackTrace();
        }
    }

    public static void updateStudent(Scanner scanner) {
        System.out.println("Enter student ID to update:");
        int studentId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        System.out.println("Enter new first name:");
        String firstName = scanner.nextLine();
        
        System.out.println("Enter new last name:");
        String lastName = scanner.nextLine();

        int age = -1;
        while (age < 0) {
            System.out.println("Enter new age (must be a positive integer):");
            if (scanner.hasNextInt()) {
                age = scanner.nextInt();
                if (age < 0) {
                    System.out.println("Age must be a positive integer.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid age.");
                scanner.next(); // Consume invalid input
            }
        }
        scanner.nextLine(); // Consume newline

        String email;
        while (true) {
            System.out.println("Enter new email (must end with @harvard.com):");
            email = scanner.nextLine();
            if (email.endsWith("@harvard.com")) {
                break;
            } else {
                System.out.println("Invalid email. Please ensure it ends with @harvard.com.");
            }
        }

        String password;
        while (true) {
            password = readPassword("Enter new password (minimum 8 characters, at least one special character, and one uppercase letter):");
            if (validatePassword(password)) {
                break;
            } else {
                System.out.println("Invalid password. Please ensure it meets the criteria.");
            }
        }

        // Chiffrement ou hachage des données
        String encryptedFirstName = Crypto.encrypt(firstName);
        String encryptedLastName = Crypto.encrypt(lastName);
        String encryptedEmail = Crypto.encrypt(email);
        String hashedPassword = hashPassword(password);

        String sql = "UPDATE Student SET First_name = ?, Last_name = ?, Age = ?, Mail = ?, Password = ? WHERE ID = ?";
        try (Connection connection = Database.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, encryptedFirstName);
            stmt.setString(2, encryptedLastName);
            stmt.setInt(3, age);
            stmt.setString(4, encryptedEmail);
            stmt.setString(5, hashedPassword);
            stmt.setInt(6, studentId);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Student updated successfully!");
            } else {
                System.out.println("Failed to update student.");
            }
        } catch (SQLException e) {
            System.out.println("An error occurred while updating the student.");
            e.printStackTrace();
        }
    }

    private static String readPassword(String prompt) {
        Console console = System.console();
        if (console == null) {
            Scanner scanner = new Scanner(System.in);
            System.out.println(prompt);
            return scanner.nextLine();
        }
        char[] passwordArray = console.readPassword(prompt);
        return new String(passwordArray);
    }

    private static boolean validatePassword(String password) {
        if (password.length() < 8) {
            return false;
        }
        Pattern lowerCasePattern = Pattern.compile("[a-z]");
        Pattern upperCasePattern = Pattern.compile("[A-Z]");
        Pattern digitPattern = Pattern.compile("[0-9]");
        Pattern specialCharPattern = Pattern.compile("[^a-zA-Z0-9 ]");

        Matcher hasLowerCase = lowerCasePattern.matcher(password);
        Matcher hasUpperCase = upperCasePattern.matcher(password);
        Matcher hasDigit = digitPattern.matcher(password);
        Matcher hasSpecialChar = specialCharPattern.matcher(password);
        return hasLowerCase.find() && hasUpperCase.find() && hasDigit.find() && hasSpecialChar.find();
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

    // Méthode pour supprimer un étudiant
    public static void deleteStudent(Scanner scanner) {
        System.out.println("Enter student ID to delete:");
        int studentId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

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
    public static void getAllStudents(Scanner scanner) {
        String sql = "SELECT * FROM Student";
        try (Connection connection = Database.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("ID");
                String encryptedFirstName = rs.getString("First_name");
                String encryptedLastName = rs.getString("Last_name");
                int age = rs.getInt("Age");
                String encryptedEmail = rs.getString("Mail");
                String encryptedPassword = rs.getString("Password");

                // Déchiffrer les champs nécessaires
                String firstName = Crypto.decrypt(encryptedFirstName);
                String lastName = Crypto.decrypt(encryptedLastName);
                String email = Crypto.decrypt(encryptedEmail);
                String password = Crypto.decrypt(encryptedPassword);

                System.out.println("ID: " + id + "\nName: " + firstName + " " + lastName + "\nAge: " + age + "\nEmail: " + email + "\nPassword: " + password + "\n");
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
    
            boolean found = false; // Flag to check if any results were found
    
            while (rs.next()) {
                found = true; // Set flag to true if we find any results
                int id = rs.getInt("ID");
                String encryptedFirstName = rs.getString("First_name");
                String encryptedLastName = rs.getString("Last_name");
                int age = rs.getInt("Age");
                String encryptedEmail = rs.getString("Mail");
                String encryptedPassword = rs.getString("Password");
    
                // Déchiffrer les champs nécessaires
                String firstName = Crypto.decrypt(encryptedFirstName);
                String lastName = Crypto.decrypt(encryptedLastName);
                String email = Crypto.decrypt(encryptedEmail);
                String password = Crypto.decrypt(encryptedPassword);

                System.out.println("ID: " + id + "\nFirstname: " + firstName + "\nLastname: " + lastName + "\nAge: " + age + "\nEmail: " + email + "\nPassword: " + password);
            }
    
            if (!found) {
                System.out.println("Student not found.");
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Invalid input for numeric fields.");
        }
    }
    
    public static void addGrade(Scanner scanner) {
        System.out.println("Enter student ID:");
        String studentId = scanner.nextLine(); // Assume ID_student is varchar

        // Verify the student exists in the Grade_book
        if (!studentExists(studentId)) {
            System.out.println("Student ID not found in the grade book.");
            return;
        }

        System.out.println("Choose subject to add grade:");
        System.out.println("1. Math");
        System.out.println("2. Physics");
        System.out.println("3. English");
        int subjectChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline after integer input

        String subject = "";
        switch (subjectChoice) {
            case 1:
                subject = "Math_grades";
                break;
            case 2:
                subject = "Physics_grades";
                break;
            case 3:
                subject = "English_grades";
                break;
            default:
                System.out.println("Invalid subject choice.");
                return;
        }

        System.out.println("Enter grade:");
        float grade = scanner.nextFloat();
        scanner.nextLine(); // Consume newline after float input

        String updateSql = "UPDATE Grade_book SET " + subject + " = ? WHERE ID_student = ?";
        try (Connection connection = Database.getConnection();
             PreparedStatement updateStmt = connection.prepareStatement(updateSql)) {

            updateStmt.setFloat(1, grade);
            updateStmt.setString(2, studentId);

            int rowsAffected = updateStmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Grade added successfully!");
                // Calculate and update average grade
                updateAverageGrade(connection, studentId);
            } else {
                System.out.println("Failed to add grade. Student ID might be incorrect.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void updateAverageGrade(Connection connection, String studentId) throws SQLException {
        String selectSql = "SELECT Math_grades, Physics_grades, English_grades FROM Grade_book WHERE ID_student = ?";
        String updateAverageSql = "UPDATE Grade_book SET average = ? WHERE ID_student = ?";

        try (PreparedStatement selectStmt = connection.prepareStatement(selectSql);
             PreparedStatement updateAverageStmt = connection.prepareStatement(updateAverageSql)) {

            selectStmt.setString(1, studentId);
            ResultSet rs = selectStmt.executeQuery();

            float mathGrade = 0;
            float physicsGrade = 0;
            float englishGrade = 0;
            int count = 0;

            if (rs.next()) {
                if (rs.getFloat("Math_grades") != 0) {
                    mathGrade = rs.getFloat("Math_grades");
                    count++;
                }
                if (rs.getFloat("Physics_grades") != 0) {
                    physicsGrade = rs.getFloat("Physics_grades");
                    count++;
                }
                if (rs.getFloat("English_grades") != 0) {
                    englishGrade = rs.getFloat("English_grades");
                    count++;
                }
            }

            if (count == 0) {
                System.out.println("No grades found for this student.");
                return;
            }

            float average = (mathGrade + physicsGrade + englishGrade) / count;
            updateAverageStmt.setFloat(1, average);
            updateAverageStmt.setString(2, studentId);

            int rowsAffected = updateAverageStmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Average grade updated successfully!");
            } else {
                System.out.println("Failed to update average grade.");
            }

        }
    }

    private static boolean studentExists(String studentId) {
        String query = "SELECT 1 FROM Grade_book WHERE ID_student = ?";
        try (Connection connection = Database.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, studentId);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
