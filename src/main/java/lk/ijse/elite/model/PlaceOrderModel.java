package lk.ijse.elite.model;

import javafx.scene.control.Alert;
import lk.ijse.elite.db.DbConnection;
import lk.ijse.elite.dto.PaymentDto;
import lk.ijse.elite.dto.PaymentdetailDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static lk.ijse.elite.model.PaymentDetailModel.savePaymentDetail;
import static lk.ijse.elite.model.PaymentModel.savePayment;

public class PlaceOrderModel {
    public static boolean isUpdated(PaymentDto paymentDto, PaymentdetailDto paymentdetailDto) throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);
            boolean isPaymentSaved = savePayment(paymentDto);
            if (isPaymentSaved) {
                boolean isPaymentDetailSaved = savePaymentDetail(paymentdetailDto);
                if (isPaymentDetailSaved) {
                    boolean isPropertyUpdated = updateProperty(paymentdetailDto.getProperty_id());
                    if (isPropertyUpdated) {
                        connection.commit();
                        return true;
                    }
                }
            }
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public static boolean updateProperty(String propertyId) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE property SET Perches = ? WHERE Property_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, "Not Available");
        statement.setString(2, propertyId);

        return statement.executeUpdate() > 0;
    }
}

