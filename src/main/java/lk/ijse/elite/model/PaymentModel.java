package lk.ijse.elite.model;

import lk.ijse.elite.db.DbConnection;
import lk.ijse.elite.dto.PaymentDto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentModel {
    public List<PaymentDto> getAllPayments() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT * FROM payment";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<PaymentDto> paymentList = new ArrayList<>();

        while (resultSet.next()) {
            paymentList.add(new PaymentDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6)
            ));
        }
        return paymentList;
    }

    public static boolean savePayment(PaymentDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO payment VALUES (?,?,?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getPayment_id());
        pstm.setString(2, dto.getCustomer_id());
        pstm.setString(3, dto.getProperty_id());
        pstm.setString(4, dto.getDate());
        pstm.setString(5, dto.getMethod());
        pstm.setString(6, dto.getPrice());

        return pstm.executeUpdate() > 0;
    }
}
