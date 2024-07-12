package src.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentRepository {

    public static float getMathGrade(String studentId) {
        return getGrade(studentId, "Math_grades");
    }

    public static float getPhysicsGrade(String studentId) {
        return getGrade(studentId, "Physics_grades");
    }

    public static float getEnglishGrade(String studentId) {
        return getGrade(studentId, "English_grades");
    }

    public static void getGradebook(String studentId) {
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

    private static float getGrade(String studentId, String subject) {
        String sql = "SELECT " + subject + " FROM Grade_book WHERE ID_student = ?";
        
        try (Connection connection = Database.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, studentId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getFloat(subject);
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
