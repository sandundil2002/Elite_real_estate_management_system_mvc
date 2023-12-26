package lk.ijse.elite.model;

import lk.ijse.elite.dto.PaymentDto;
import lk.ijse.elite.util.SQLUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentModel {
    public List<PaymentDto> getAllPayments() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.sql("SELECT * FROM payment");
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

    public static boolean savePayment(PaymentDto dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.sql("INSERT INTO payment VALUES (?,?,?,?,?,?)",dto.getPayment_id(),dto.getCustomer_id(),dto.getProperty_id(),dto.getDate(),dto.getMethod(),dto.getPrice());
    }

    public String generatePaymentId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.sql("SELECT Payment_id FROM payment ORDER BY Payment_id DESC LIMIT 1");
        if (resultSet.next()) {
            String id =  resultSet.getString(1);
            String numericPart = id.replaceAll("\\D", "");
            int newPaymentId = Integer.parseInt(numericPart) + 1;
            return String.format("Pay%03d", newPaymentId);
        } else {
            return "Pay001";
        }
    }
}
