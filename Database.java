
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static Connection connection = null;

    public static Connection getConnection() {
        if (connection == null) {
            try {
               
                String url = "jdbc:mysql://localhost:3306/Harvard";
                String user = "root";
                String password = "dominique59";
                
                
                
                connection = DriverManager.getConnection(url, user, password);
                System.out.println("Connection established successfully.");
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Failed to establish connection.");
            }
        }
        return connection;
    }
}


