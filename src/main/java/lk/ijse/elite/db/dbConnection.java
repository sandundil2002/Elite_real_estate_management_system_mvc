package lk.ijse.elite.db;

import java.sql.*;
public class dbConnection {
    private static dbConnection dbConnection;
    private Connection connection;

    private dbConnection() throws SQLException {
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/Elite_Real_Estate_Management_System",
                "root",
                "1234"
        );
    }

    public static dbConnection getInstance() throws SQLException {
        return (null == dbConnection) ? dbConnection = new dbConnection() : dbConnection;
    }

    public Connection getConnection() {
        return connection;
    }

}
