package lk.ijse.elite.model;

import lk.ijse.elite.db.DbConnection;
import lk.ijse.elite.dto.AdminDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class AdminModel {
    public static Connection connection;

    static {
        try {
            connection = DbConnection.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<AdminDto> loadAllAdmin() throws SQLException {
        String sql = "SELECT * FROM admin";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<AdminDto> adminList = new ArrayList<>();

        while (resultSet.next()) {
            adminList.add(new AdminDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(5),
                    resultSet.getString(4),
                    resultSet.getString(3),
                    resultSet.getString(6)
            ));
        }
        return adminList;
    }

    public static AdminDto searchAdmin(String string) throws SQLException {
        String sql = "SELECT * FROM admin WHERE Admin_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, string);

        ResultSet resultSet = statement.executeQuery();
        AdminDto adminDto = null;

        if (resultSet.next()) {
            adminDto = new AdminDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(5),
                    resultSet.getString(4),
                    resultSet.getString(3),
                    resultSet.getString(6)
            );
        }
        return adminDto;
    }

    public boolean searchAdminPassword(String string) throws SQLException {
        String sql = "SELECT * FROM admin WHERE password = ?";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, string);

        ResultSet resultSet = statement.executeQuery();
        return resultSet.next();
    }

    public boolean searchAdminUserId(String string) throws SQLException {
        String sql = "SELECT * FROM admin WHERE Admin_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, string);

        ResultSet resultSet = statement.executeQuery();
        return resultSet.next();
    }

    public boolean registerAdmin(AdminDto dto) throws SQLException {
        String sql = "insert into admin values (?,?,?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, dto.getAdmin_id());
        statement.setString(2, dto.getName());
        statement.setString(3, dto.getAddress());
        statement.setString(4, dto.getMobile());
        statement.setString(5, dto.getPassword());
        statement.setString(6, dto.getEmail());

        boolean isSaved = statement.executeUpdate() > 0;
        return isSaved;
    }
}

