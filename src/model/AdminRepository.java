package src.model;

import java.io.Console;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AdminRepository {

    public static void createAccount(Scanner scanner) {
        System.out.println("Enter first name:");
        String firstName = scanner.nextLine();
        
        System.out.println("Enter last name:");
        String lastName = scanner.nextLine();

        int age = getPositiveIntInput(scanner, "Enter age (must be a positive integer):");

        System.out.println("Enter student ID:");
        String studentId = scanner.nextLine();
        
        int gradebookId = getPositiveIntInput(scanner, "Enter gradebook ID (must be a positive integer):");

        String email = getEmailInput(scanner, "Enter email (must end with @harvard.com):");

        String password = getPasswordInput("Enter password (minimum 8 characters, at least one special character, and one uppercase letter):");

        String insertStudentSql = "INSERT INTO Student (First_name, Last_name, Age, ID_student, ID_gradebook, Mail, Password) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String insertGradeBookSql = "INSERT INTO Grade_book (ID_student) VALUES (?)";

        try (Connection connection = Database.getConnection();
             PreparedStatement insertStudentStmt = connection.prepareStatement(insertStudentSql);
             PreparedStatement insertGradeBookStmt = connection.prepareStatement(insertGradeBookSql)) {

            insertStudentStmt.setString(1, firstName);
            insertStudentStmt.setString(2, lastName);
            insertStudentStmt.setInt(3, age);
            insertStudentStmt.setString(4, studentId);
            insertStudentStmt.setInt(5, gradebookId);
            insertStudentStmt.setString(6, email);
            insertStudentStmt.setString(7, password);

            int studentRowsAffected = insertStudentStmt.executeUpdate();

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
        int studentId = getPositiveIntInput(scanner, "Enter student ID:");

        System.out.println("Enter new first name:");
        String firstName = scanner.nextLine();
        
        System.out.println("Enter new last name:");
        String lastName = scanner.nextLine();

        int age = getPositiveIntInput(scanner, "Enter new age (must be a positive integer):");

        String email = getEmailInput(scanner, "Enter new email (must end with @harvard.com):");

        String password = getPasswordInput("Enter new password (minimum 8 characters, at least one special character, and one uppercase letter):");

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
            System.out.println("An error occurred while updating the student.");
            e.printStackTrace();
        }
    }

    public static void deleteStudent(Scanner scanner) {
        System.out.println("Enter student ID to delete:");
        int studentId = getPositiveIntInput(scanner, "Enter student ID:");

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

    public static void getAllStudents(Scanner scanner) {
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
                System.out.println("ID: " + id + "\nName: " + firstName + " " + lastName + "\nAge: " + age + "\nEmail: " + email + "\nPassword: " + password + "\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<String> searchStudent(Scanner scanner) {
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
                return new ArrayList<>();
        }

        System.out.println("Enter search value:");
        String searchValue = scanner.nextLine();

        List<String> students = new ArrayList<>();

        try (Connection connection = Database.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            if ("id".equals(searchBy) || "age".equals(searchBy)) {
                int intValue = Integer.parseInt(searchValue);
                stmt.setInt(1, intValue);
            } else {
                stmt.setString(1, searchValue);
            }

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("ID");
                String firstName = rs.getString("First_name");
                String lastName = rs.getString("Last_name");
                int age = rs.getInt("Age");
                String email = rs.getString("Mail");
                String password = rs.getString("Password");

                String studentInfo = "ID: " + id + "\nFirst name: " + firstName + "\nLast name: " + lastName +
                        "\nAge: " + age + "\nEmail: " + email + "\nPassword: " + password;
                students.add(studentInfo);
            }

            if (students.isEmpty()) {
                System.out.println("No students found matching the criteria.");
            }

        } catch (SQLException e) {
            System.out.println("An error occurred while searching for students.");
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Invalid input for numeric fields.");
        }

        return students;
    }

    public static void addGrade(Scanner scanner) {
        System.out.println("Enter student ID:");
        String studentId = scanner.nextLine();
    
        if (!studentExists(studentId)) {
            System.out.println("Student ID not found in the grade book.");
            return;
        }
    
        System.out.println("Choose subject to add grade:");
        System.out.println("1. Math");
        System.out.println("2. Physics");
        System.out.println("3. English");
        int subjectChoice = scanner.nextInt();
        scanner.nextLine();
    
        String subjectColumn = "";
        switch (subjectChoice) {
            case 1:
                subjectColumn = "Math_grades";
                break;
            case 2:
                subjectColumn = "Physics_grades";
                break;
            case 3:
                subjectColumn = "English_grades";
                break;
            default:
                System.out.println("Invalid subject choice.");
                return;
        }
    
        System.out.println("Enter grade:");
        float newGrade = scanner.nextFloat();
        scanner.nextLine();
    
        // Step 1: Retrieve the existing grade from the database
        String selectSql = "SELECT " + subjectColumn + " FROM Grade_book WHERE ID_student = ?";
        String updateSql = "UPDATE Grade_book SET " + subjectColumn + " = ? WHERE ID_student = ?";
        
        try (Connection connection = Database.getConnection();
             PreparedStatement selectStmt = connection.prepareStatement(selectSql);
             PreparedStatement updateStmt = connection.prepareStatement(updateSql)) {
    
            selectStmt.setString(1, studentId);
            ResultSet rs = selectStmt.executeQuery();
    
            float existingGrade = 0;
            if (rs.next()) {
                existingGrade = rs.getFloat(subjectColumn);
            }
    
            // Step 2: Calculate new average if needed (you need to implement this logic)
            // Note: Implement logic to handle average calculation if necessary
    
            // Step 3: Update the existing grade with the new grade
            float updatedGrade = existingGrade + newGrade;
    
            updateStmt.setFloat(1, updatedGrade);
            updateStmt.setString(2, studentId);
    
            int rowsAffected = updateStmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Grade added successfully!");
                // Update average grade if necessary
                // updateAverageGrade(connection, studentId);
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

    private static int getPositiveIntInput(Scanner scanner, String prompt) {
        int value = -1;
        while (value < 0) {
            System.out.println(prompt);
            if (scanner.hasNextInt()) {
                value = scanner.nextInt();
                if (value < 0) {
                    System.out.println("Value must be a positive integer.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid positive integer.");
                scanner.next();
            }
        }
        scanner.nextLine();
        return value;
    }

    private static String getEmailInput(Scanner scanner, String prompt) {
        String email;
        while (true) {
            System.out.println(prompt);
            email = scanner.nextLine();
            if (email.endsWith("@harvard.com")) {
                break;
            } else {
                System.out.println("Invalid email. Please ensure it ends with @harvard.com.");
            }
        }
        return email;
    }

    private static String getPasswordInput(String prompt) {
        String password;
        while (true) {
            password = readPassword(prompt);
            if (validatePassword(password)) {
                break;
            } else {
                System.out.println("Invalid password. Please ensure it meets the criteria.");
            }
        }
        return password;
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
        Pattern specialCharPattern = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Pattern upperCasePattern = Pattern.compile("[A-Z ]");
        Matcher hasSpecial = specialCharPattern.matcher(password);
        Matcher hasUpperCase = upperCasePattern.matcher(password);
        return hasSpecial.find() && hasUpperCase.find();
    }


    public static void exportStudentsToHTML() {
        String htmlFilePath = "students.html";

        String sql = "SELECT * FROM Student";

        try (Connection connection = Database.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql);
             FileWriter htmlWriter = new FileWriter(htmlFilePath)) {

            htmlWriter.write("<html>\n<head>\n<title>Students List</title>\n</head>\n<body>\n");
            htmlWriter.write("<h1>Students List</h1>\n<table border=\"1\">\n");
            htmlWriter.write("<tr><th>ID</th><th>First Name</th><th>Last Name</th><th>Age</th><th>Email</th><th>Password</th></tr>\n");

            while (rs.next()) {
                int id = rs.getInt("ID");
                String firstName = rs.getString("First_name");
                String lastName = rs.getString("Last_name");
                int age = rs.getInt("Age");
                String email = rs.getString("Mail");
                String password = rs.getString("Password");

                htmlWriter.write("<tr><td>" + id + "</td><td>" + firstName + "</td><td>" + lastName + "</td><td>" + age + "</td><td>" + email + "</td><td>" + password + "</td></tr>\n");
            }

            htmlWriter.write("</table>\n</body>\n</html>");

            System.out.println("HTML file exported successfully to: " + htmlFilePath);

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void exportStudentsToCSV() {
        String csvFilePath = "students.csv";

        String sql = "SELECT * FROM Student";

        try (Connection connection = Database.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql);
             FileWriter csvWriter = new FileWriter(csvFilePath)) {

            // Write CSV header
            csvWriter.append("ID,First_name,Last_name,Age,Email,Password\n");

            // Write CSV rows
            while (rs.next()) {
                int id = rs.getInt("ID");
                String firstName = rs.getString("First_name");
                String lastName = rs.getString("Last_name");
                int age = rs.getInt("Age");
                String email = rs.getString("Mail");
                String password = rs.getString("Password");

                csvWriter.append(String.join(",", String.valueOf(id), firstName, lastName,
                        String.valueOf(age), email, password));
                csvWriter.append("\n");
            }

            System.out.println("CSV file exported successfully to: " + csvFilePath);

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }


    
}
