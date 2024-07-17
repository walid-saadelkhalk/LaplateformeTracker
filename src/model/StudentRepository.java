package src.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StudentRepository {

    public float getMathGrade(String studentId) {
        return getGrade(studentId, "Math_grades");
    }

    public float getPhysicsGrade(String studentId) {
        return getGrade(studentId, "Physics_grades");
    }

    public float getEnglishGrade(String studentId) {
        return getGrade(studentId, "English_grades");
    }

    public void getGradebook(String studentId) {
        String sql = "SELECT Math_grades, Physics_grades, English_grades, average FROM Grade_book WHERE ID_student = ?";

        try (Connection connection = Database.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, studentId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                float mathGrade = rs.getFloat("Math_grades");
                float physicsGrade = rs.getFloat("Physics_grades");
                float englishGrade = rs.getFloat("English_grades");
                float average = rs.getFloat("average");

                System.out.println("Math grade: " + mathGrade);
                System.out.println("Physics grade: " + physicsGrade);
                System.out.println("English grade: " + englishGrade);
                System.out.println("Average grade: " + average);
            } else {
                System.out.println("No grades found for student ID: " + studentId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void handleMathGrades(Scanner scanner) {
        System.out.println("Enter student ID:");
        String studentId = scanner.nextLine();
        float mathGrade = getMathGrade(studentId);
        System.out.println("Maths grades: " + (mathGrade == -1 ? "No grade found." : mathGrade));
    }

    public void handlePhysicsGrades(Scanner scanner) {
        System.out.println("Enter student ID:");
        String studentId = scanner.nextLine();
        float physicsGrade = getPhysicsGrade(studentId);
        System.out.println("Physics grades: " + (physicsGrade == -1 ? "No grade found." : physicsGrade));
    }

    public void handleEnglishGrades(Scanner scanner) {
        System.out.println("Enter student ID:");
        String studentId = scanner.nextLine();
        float englishGrade = getEnglishGrade(studentId);
        System.out.println("English grades: " + (englishGrade == -1 ? "No grade found." : englishGrade));
    }

    public void handleGradebook(Scanner scanner) {
        System.out.println("Enter student ID:");
        String studentId = scanner.nextLine();
        getGradebook(studentId);
    }

    public List<String> getAllGradebooks() {
        List<String> gradebooks = new ArrayList<>();
        String sql = "SELECT * FROM Grade_book";

        try (Connection connection = Database.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String studentId = rs.getString("ID_student");
                String mathGrade = rs.getString("Math_grades");
                String physicsGrade = rs.getString("Physics_grades");
                String englishGrade = rs.getString("English_grades");
                String average = rs.getString("average");

                String gradebook = String.format(
                        "ID_student: %s\nMath grade: %s\nPhysics grade: %s\nEnglish grade: %s\nAverage grade: %s",
                        studentId, mathGrade, physicsGrade, englishGrade, average);
                gradebooks.add(gradebook);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return gradebooks;
    }


    private static float getGrade(String studentId, String subject) {
        String sql = "SELECT " + subject + " FROM Grade_book WHERE ID_student = ?";

        try (Connection connection = Database.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, studentId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                float grade = rs.getFloat(subject);
                System.out.println(subject + " for student ID " + studentId + ": " + grade); // Debugging output
                return grade;
            } else {
                System.out.println("No grade found for student ID: " + studentId + " in subject: " + subject);
                return -1;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
