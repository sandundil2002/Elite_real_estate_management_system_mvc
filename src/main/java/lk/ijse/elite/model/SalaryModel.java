package lk.ijse.elite.model;

import lk.ijse.elite.db.DbConnection;
import lk.ijse.elite.dto.SalaryDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SalaryModel {
    public boolean saveSalary(SalaryDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String query = "INSERT INTO salary VALUES (?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(query);

        pstm.setString(1, dto.getSalary_id());
        pstm.setString(2, dto.getEmployee_id());
        pstm.setString(3, dto.getDate());
        pstm.setString(4, dto.getAmount());

        return pstm.executeUpdate() > 0;
    }
}
