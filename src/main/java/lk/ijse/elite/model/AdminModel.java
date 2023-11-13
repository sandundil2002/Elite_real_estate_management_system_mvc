package lk.ijse.elite.model;

import lk.ijse.elite.db.DbConnection;

import java.sql.*;

public class AdminModel {
    public boolean loginAdmin(String id ,String pw) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM admin WHERE Admin_id =? AND password = ?";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, id);
        statement.setString(2, pw);

        ResultSet resultSet = statement.executeQuery();
        return resultSet.next();
    }
}

