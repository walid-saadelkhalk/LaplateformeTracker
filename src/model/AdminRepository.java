package src.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class AdminRepository {

    // Méthode pour créer un compte étudiant
    public static void createAccount(Scanner scanner) {
        System.out.println("Enter first name:");
        String firstName = scanner.nextLine();
        
        System.out.println("Enter last name:");
        String lastName = scanner.nextLine();
        
        System.out.println("Enter age:");
        int age = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        System.out.println("Enter student ID:");
        String studentId = scanner.nextLine();
        
        System.out.println("Enter gradebook ID:");
        int gradebookId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        System.out.println("Enter email:");
        String email = scanner.nextLine();
        
        System.out.println("Enter password:");
        String password = scanner.nextLine();

        String insertStudentSql = "INSERT INTO Student (First_name, Last_name, Age, ID_student, ID_gradebook, Mail, Password) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String insertGradeBookSql = "INSERT INTO Grade_book (ID_student) VALUES (?)";

        try (Connection connection = Database.getConnection();
             PreparedStatement insertStudentStmt = connection.prepareStatement(insertStudentSql);
             PreparedStatement insertGradeBookStmt = connection.prepareStatement(insertGradeBookSql)) {

            // Insert into Student table
            insertStudentStmt.setString(1, firstName);
            insertStudentStmt.setString(2, lastName);
            insertStudentStmt.setInt(3, age);
            insertStudentStmt.setString(4, studentId);
            insertStudentStmt.setInt(5, gradebookId);
            insertStudentStmt.setString(6, email);
            insertStudentStmt.setString(7, password);

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
            e.printStackTrace();
        }
    }

    // Méthode pour mettre à jour un étudiant
    public static void updateStudent(Scanner scanner) {
        
        System.out.println("Enter student ID to update:");
        int studentId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        System.out.println("Enter new first name:");
        String firstName = scanner.nextLine();
        
        System.out.println("Enter new last name:");
        String lastName = scanner.nextLine();
        
        System.out.println("Enter new age:");
        int age = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        System.out.println("Enter new email:");
        String email = scanner.nextLine();
        
        System.out.println("Enter new password:");
        String password = scanner.nextLine();

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
            e.printStackTrace();
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
    
            while (rs.next()) {
                int id = rs.getInt("ID");
                String firstName = rs.getString("First_name");
                String lastName = rs.getString("Last_name");
                int age = rs.getInt("Age");
                String email = rs.getString("Mail");
                String password = rs.getString("Password");
                System.out.println("ID: " + id + "\nFirstame: " + firstName + "\nLastname: " + lastName + "\nAge: " + age + "\nEmail: " + email + "\nPassword: " + password);
            }
    
            if (!rs.isBeforeFirst()) {
                System.out.println("Student not found.");
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Invalid input for numeric fields.");
        } 
        // finally {
        // //     scanner.close();
        // // }
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
