package lk.ijse.elite.model;

import lk.ijse.elite.db.DbConnection;
import lk.ijse.elite.dto.PropertyDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PropertyModel {
    public static Connection connection;

    static {
        try {
            connection = DbConnection.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<PropertyDto> loadAllProperty() throws SQLException {
        String sql = "SELECT * FROM property";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<PropertyDto> proList = new ArrayList<>();

        while (resultSet.next()) {
            proList.add(new PropertyDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(5),
                    resultSet.getString(4),
                    resultSet.getString(3),
                    resultSet.getString(6),
                    resultSet.getString(7)
            ));
        }
        return proList;
    }

    public boolean saveProperty(PropertyDto dto) throws SQLException {
        String sql = "insert into property values (?,?,?,?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, dto.getPropertyId());
        statement.setString(2, dto.getAgentId());
        statement.setString(3, dto.getPrice());
        statement.setString(4, dto.getAddress());
        statement.setString(5, dto.getType());
        statement.setString(6, dto.getPerches());
        statement.setString(7, dto.getStatus());

        boolean isSaved = statement.executeUpdate() > 0;
        return isSaved;
    }

    public boolean updateProperty(PropertyDto dto) throws SQLException {
        String sql = "UPDATE property SET Agent_id = ?, price = ?, address = ?, type = ?,perches = ? WHERE Property_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, dto.getAgentId());
        statement.setString(1, dto.getPrice());
        statement.setString(2, dto.getAddress());
        statement.setString(3, dto.getType());
        statement.setString(4, dto.getPerches());
        statement.setString(5, dto.getPropertyId());

        return statement.executeUpdate() > 0;
    }

    public static PropertyDto searchProperty(String id) throws SQLException {
        String sql = "SELECT * FROM property WHERE Property_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, id);

        ResultSet resultSet = statement.executeQuery();
        PropertyDto dto = null;

        if (resultSet.next()){
            String pro_id = resultSet.getString(1);
            String a_id = resultSet.getString(2);
            String price = resultSet.getString(3);
            String address = resultSet.getString(4);
            String type = resultSet.getString(5);
            String perches = resultSet.getString(6);
            String status = resultSet.getString(7);

            dto = new PropertyDto(pro_id,a_id,price, address, type, perches, status);
        }
        return dto;
    }

    public boolean deleteProperty(String id) throws SQLException {
        String sql = "DELETE FROM property WHERE Property_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, id);
        return statement.executeUpdate() > 0;
    }
}
