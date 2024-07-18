package src.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StudentRepository {


    // geter to get the grade of the student
    public float getMathGrade(String studentId) {
        return getGrade(studentId, "Math_grades");
    }

    public float getPhysicsGrade(String studentId) {
        return getGrade(studentId, "Physics_grades");
    }

    public float getEnglishGrade(String studentId) {
        return getGrade(studentId, "English_grades");
    }

    // geter to get the gradebook of the student
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

    // handle the grades of the student
    public void handleMathGrades(Scanner scanner) {
        System.out.println("Enter student ID:");
        String studentId = scanner.nextLine();
        float mathGrade = getMathGrade(studentId);
        System.out.println("Maths grades: " + (mathGrade == -1 ? "No grade found." : mathGrade));
    }

    // handle the grades of the student
    public void handlePhysicsGrades(Scanner scanner) {
        System.out.println("Enter student ID:");
        String studentId = scanner.nextLine();
        float physicsGrade = getPhysicsGrade(studentId);
        System.out.println("Physics grades: " + (physicsGrade == -1 ? "No grade found." : physicsGrade));
    }

    // handle the grades of the student
    public void handleEnglishGrades(Scanner scanner) {
        System.out.println("Enter student ID:");
        String studentId = scanner.nextLine();
        float englishGrade = getEnglishGrade(studentId);
        System.out.println("English grades: " + (englishGrade == -1 ? "No grade found." : englishGrade));
    }

    // handle the gradebook of the student
    public void handleGradebook(Scanner scanner) {
        System.out.println("Enter student ID:");
        String studentId = scanner.nextLine();
        getGradebook(studentId);
    }


    // get the grade of the student
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
