package lk.ijse.elite.model;

import lk.ijse.elite.dto.PaymentDto;
import lk.ijse.elite.utill.SQLUtill;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentModel {
    public List<PaymentDto> getAllPayments() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtill.sql("SELECT * FROM payment");
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
        return SQLUtill.sql("INSERT INTO payment VALUES (?,?,?,?,?,?)",dto.getPayment_id(),dto.getCustomer_id(),dto.getProperty_id(),dto.getDate(),dto.getMethod(),dto.getPrice());
    }
}
