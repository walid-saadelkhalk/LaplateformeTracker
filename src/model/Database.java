package src.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Class to connect to the database
public class Database {

    /* 
     * Class to connect to the database
     */
    private static final String URL = "jdbc:mysql://localhost:3306/Harvard";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "dominique59";

    // Function to get the connection
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC driver not found.", e);
        }
    }
}
