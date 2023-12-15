package lk.ijse.elite.model;

import lk.ijse.elite.db.DbConnection;
import lk.ijse.elite.dto.MaintainDto;
import lk.ijse.elite.dto.RentingDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaintainModel {
    public static Connection connection;

    static {
        try {
            connection = DbConnection.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean addMaintain(MaintainDto maintainDto) throws SQLException {
        String sql = "INSERT INTO maintain VALUES (?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, maintainDto.getMaintain_id());
        statement.setString(2, maintainDto.getRent_id());
        statement.setString(3, maintainDto.getDate());
        statement.setString(4, maintainDto.getStatus());

        boolean isAdded = statement.executeUpdate() > 0;
        return isAdded;
    }

    public List<MaintainDto> loadAllMaintenance() throws SQLException {
        String sql = "SELECT * FROM maintain";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<MaintainDto> maintainList = new ArrayList<>();

        while (resultSet.next()) {
            maintainList.add(new MaintainDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            ));
        }
        return maintainList;
    }

    public boolean updateMaintainComplete(String maintainId) throws SQLException {
        String sql = "UPDATE maintain SET Status = ? WHERE Maintain_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, "Completed");
        statement.setString(2, maintainId);

        return statement.executeUpdate() > 0;
    }

    public boolean updateMaintainCansel(String maintainId) throws SQLException {
        String sql = "UPDATE maintain SET Status = ? WHERE Maintain_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, "Canseled");
        statement.setString(2, maintainId);

        return statement.executeUpdate() > 0;
    }
}
