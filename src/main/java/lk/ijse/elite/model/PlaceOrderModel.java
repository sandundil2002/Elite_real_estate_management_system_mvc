package lk.ijse.elite.model;

import javafx.scene.control.Alert;
import lk.ijse.elite.dto.PaymentDto;
import lk.ijse.elite.dto.PaymentdetailDto;
import lk.ijse.elite.util.SQLUtil;
import lk.ijse.elite.util.TransactionUtil;
import java.sql.SQLException;
import static lk.ijse.elite.model.PaymentDetailModel.savePaymentDetail;
import static lk.ijse.elite.model.PaymentModel.savePayment;

public class PlaceOrderModel {
    public static boolean isUpdated(PaymentDto paymentDto, PaymentdetailDto paymentdetailDto) throws SQLException, ClassNotFoundException {
        try{
            TransactionUtil.startTransaction();
            boolean isPaymentSaved = savePayment(paymentDto);
            if(isPaymentSaved){
                boolean isPaymentDetailSaved = savePaymentDetail(paymentdetailDto);
                if (isPaymentDetailSaved){
                    boolean isPropertyUpdated = updateProperty(paymentdetailDto.getProperty_id());
                    if (isPropertyUpdated){
                        return true;
                    }
                }
            }
            TransactionUtil.rollBack();
            return false;
        } catch (SQLException e){
            TransactionUtil.rollBack();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } finally {
            TransactionUtil.endTransaction();
        }
        return false;
    }

    public static boolean updateProperty(String propertyId) throws SQLException, ClassNotFoundException {
        return SQLUtil.sql("UPDATE property SET Status=? WHERE property_id=?", "Not Available", propertyId);
    }
}

