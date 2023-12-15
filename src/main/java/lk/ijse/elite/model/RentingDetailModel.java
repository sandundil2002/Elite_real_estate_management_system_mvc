package lk.ijse.elite.model;

import lk.ijse.elite.db.DbConnection;
import lk.ijse.elite.dto.RentingDetailDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RentingDetailModel {

    public static boolean saveRentingDetail(RentingDetailDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO renting_details VALUES (?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getRentId());
        pstm.setString(2, dto.getPropertyId());
        pstm.setString(3, dto.getDescription());

        return pstm.executeUpdate() > 0;
    }
}
