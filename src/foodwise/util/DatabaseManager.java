package foodwise.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

    private static final String URL=
        "jdbc:mysql://localhost:3306/foodwise?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

    private static final String USER= "root";

    private static final String PASSWORD = "YOUR_MYSQL_PASSWORD";

    private DatabaseManager() {
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static boolean testConnection() {

        try 
        (Connection connection=getConnection()) {

            return connection != null && !connection.isClosed();

        } catch (SQLException e) {

            System.out.println("database connection failed:"
             + e.getMessage());

            return false;
        }
    }
}

