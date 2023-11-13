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
    public List<PropertyDto> loadAllProperty() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM property";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<PropertyDto> proList = new ArrayList<>();

        while (resultSet.next()) {
            proList.add(new PropertyDto(
                    resultSet.getString(1),
                    resultSet.getString(4),
                    resultSet.getString(3),
                    resultSet.getString(2)
            ));
        }
        return proList;
    }

    public boolean saveProperty(PropertyDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "insert into property values (?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, dto.getPropertyId());
        statement.setString(2, dto.getPrice());
        statement.setString(3, dto.getAddress());
        statement.setString(4, dto.getType());

        boolean isSaved = statement.executeUpdate() > 0;
        return isSaved;
    }

    public boolean updateProperty(PropertyDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE property SET price = ?, address = ?, type = ? WHERE Property_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, dto.getPrice());
        statement.setString(2, dto.getAddress());
        statement.setString(3, dto.getType());
        statement.setString(4, dto.getPropertyId());

        return statement.executeUpdate() > 0;
    }

    public PropertyDto searchProperty(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM property WHERE Property_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, id);

        ResultSet resultSet = statement.executeQuery();
        PropertyDto dto = null;

        if (resultSet.next()){
            String pro_id = resultSet.getString(1);
            String price = resultSet.getString(2);
            String address = resultSet.getString(3);
            String type = resultSet.getString(4);

            dto = new PropertyDto(pro_id, price, address, type);
        }
        return dto;
    }

    public boolean deleteProperty(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM property WHERE Property_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, id);
        return statement.executeUpdate() > 0;
    }
}
