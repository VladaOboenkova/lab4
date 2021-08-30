package project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private final static String URL = "jdbc:postgresql://localhost:5432/travel_agency";
    private final static String USERNAME = "postgres";
    private final static String PASSWORD = "postgress";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Error while connecting to DB");
        }
        return null;
    }

    public static Connection getConnection(int isolationLevel) {
        Connection connection = getConnection();
        if (connection != null) {
            try {
                connection.setTransactionIsolation(isolationLevel);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

}
