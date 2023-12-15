package lk.ijse.elite.model;

import lk.ijse.elite.db.DbConnection;
import lk.ijse.elite.dto.PaymentdetailDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PaymentDetailModel {
    public static boolean savePaymentDetail(PaymentdetailDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO payment_details VALUES (?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getProperty_id());
        pstm.setString(2, dto.getPayment_id());
        pstm.setString(3, dto.getMethod());

        return pstm.executeUpdate() > 0;
    }
}
