import java.sql.Connection;
import java.sql.SQLException;
import src.Modele.Database;

public class Main {
    public static void main(String[] args) {
        
        Connection connection = Database.getConnection();

        
        if (connection != null) {
            System.out.println("Connected to the database!");
        } else {
            System.out.println("Failed to connect to the database.");
        }

       
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
