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
        PreparedStatement stmt = null;
        try (Connection connection = Database.getConnection()) {
            stmt = connection.prepareStatement(sql);

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
        String studentId = scanner.nextLine();

        // Delete from Grade_book first
        String deleteGradeBookSql = "DELETE FROM Grade_book WHERE ID_student = ?";
        String deleteStudentSql = "DELETE FROM Student WHERE ID_student = ?";
        try (Connection connection = Database.getConnection();
             PreparedStatement stmtGradeBook = connection.prepareStatement(deleteGradeBookSql);
             PreparedStatement stmtStudent = connection.prepareStatement(deleteStudentSql)) {

            stmtGradeBook.setString(1, studentId);
            int rowsAffectedGradeBook = stmtGradeBook.executeUpdate();
            System.out.println("Rows deleted from Grade_book: " + rowsAffectedGradeBook);
            // Then delete from Student
            stmtStudent.setString(1, studentId);
            int rowsAffectedStudent = stmtStudent.executeUpdate();
            System.out.println("Rows deleted from Student: " + rowsAffectedStudent);
            if (rowsAffectedStudent > 0) {
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
            System.out.println("Student not found.");
            return;
        }

        System.out.println("Select subject (1: Math, 2: Physics, 3: English):");
        int subject = scanner.nextInt();
        scanner.nextLine(); // consume newline

        String subjectColumn;
        switch (subject) {
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
                System.out.println("Invalid choice.");
                return;
        }

        System.out.println("Enter grade to add (1 to 10):");
        int grade = scanner.nextInt();
        scanner.nextLine();

        if (grade < 1 || grade > 10) {
            System.out.println("Invalid grade. Please enter a grade between 1 and 10.");
            return;
        }

        String sql = "UPDATE Grade_book SET " + subjectColumn + " = ? WHERE ID_student = ?";
        try (Connection connection = Database.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, grade);
            stmt.setString(2, studentId);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Grade added successfully!");
            } else {
                System.out.println("Failed to add grade.");
            }

            updateAverageGrade(studentId);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateAverageGrade(String studentId) {
        String sqlSelect = "SELECT Math_grades, Physics_grades, English_grades FROM Grade_book WHERE ID_student = ?";
        String sqlUpdate = "UPDATE Grade_book SET average = ? WHERE ID_student = ?";
        String sqlCheckAverage = "SELECT COUNT(*) FROM classroom WHERE Average_classes = ?";
        String sqlInsertAverage = "INSERT INTO classroom (Average_classes) VALUES (?)";
    
        try (Connection connection = Database.getConnection();
             PreparedStatement selectStmt = connection.prepareStatement(sqlSelect);
             PreparedStatement updateStmt = connection.prepareStatement(sqlUpdate);
             PreparedStatement checkAverageStmt = connection.prepareStatement(sqlCheckAverage);
             PreparedStatement insertAverageStmt = connection.prepareStatement(sqlInsertAverage)) {
    
            selectStmt.setString(1, studentId);
            ResultSet rs = selectStmt.executeQuery();
    
            if (rs.next()) {
                int mathGrade = rs.getInt("Math_grades");
                int physicsGrade = rs.getInt("Physics_grades");
                int englishGrade = rs.getInt("English_grades");
    
                // Calculate the average, considering only non-zero grades
                int total = 0;
                int count = 0;
    
                if (mathGrade > 0) {
                    total += mathGrade;
                    count++;
                }
                if (physicsGrade > 0) {
                    total += physicsGrade;
                    count++;
                }
                if (englishGrade > 0) {
                    total += englishGrade;
                    count++;
                }
    
                if (count > 0) {
                    double average = (double) total / count;
    
                    // Check if the average exists in the classroom table
                    checkAverageStmt.setDouble(1, average);
                    ResultSet rsCheck = checkAverageStmt.executeQuery();
                    if (rsCheck.next() && rsCheck.getInt(1) == 0) {
                        // Average does not exist, insert it
                        insertAverageStmt.setDouble(1, average);
                        insertAverageStmt.executeUpdate();
                    }
    
                    // Update the average in the Grade_book table
                    updateStmt.setDouble(1, average);
                    updateStmt.setString(2, studentId);
    
                    updateStmt.executeUpdate();
                }
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    


    public static boolean studentExists(String studentId) {
        String sql = "SELECT COUNT(*) FROM Grade_book WHERE ID_student = ?";
        try (Connection connection = Database.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, studentId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static int getPositiveIntInput(Scanner scanner, String prompt) {
        int input;
        while (true) {
            System.out.println(prompt);
            if (scanner.hasNextInt()) {
                input = scanner.nextInt();
                scanner.nextLine();
                if (input > 0) {
                    break;
                } else {
                    System.out.println("Please enter a positive integer.");
                }
            } else {
                scanner.nextLine();
                System.out.println("Invalid input. Please enter a positive integer.");
            }
        }
        return input;
    }

    private static String getEmailInput(Scanner scanner, String prompt) {
        String input;
        while (true) {
            System.out.println(prompt);
            input = scanner.nextLine();
            if (input.endsWith("@harvard.com")) {
                break;
            } else {
                System.out.println("Invalid email. Please enter an email ending with @harvard.com.");
            }
        }
        return input;
    }

    private static String getPasswordInput(String prompt) {
        Console console = System.console();
        if (console == null) {
            System.out.println("Console not available. Using Scanner for password input.");
            Scanner scanner = new Scanner(System.in);
            return getPasswordFromScanner(scanner, prompt);
        }

        String input;
        while (true) {
            char[] passwordArray = console.readPassword(prompt);
            input = new String(passwordArray);
            if (validatePassword(input)) {
                break;
            } else {
                System.out.println("Invalid password. Please enter a password with a minimum of 8 characters, at least one special character, and one uppercase letter.");
            }
        }
        return input;
    }

    private static String getPasswordFromScanner(Scanner scanner, String prompt) {
        String input;
        while (true) {
            System.out.println(prompt);
            input = scanner.nextLine();
            if (validatePassword(input)) {
                break;
            } else {
                System.out.println("Invalid password. Please enter a password with a minimum of 8 characters, at least one special character, and one uppercase letter.");
            }
        }
        return input;
    }

    private static boolean validatePassword(String password) {
        return password.length() >= 8 &&
                password.matches(".*[!@#$%^&*()].*") &&
                password.matches(".*[A-Z].*");
    }




    public static void exportStudentsToHTML() {
        String htmlFilePath = "export/students.html";

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
        String csvFilePath = "export/students.csv";

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
