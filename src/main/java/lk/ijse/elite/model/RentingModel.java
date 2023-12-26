package lk.ijse.elite.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import lk.ijse.elite.dto.*;
import lk.ijse.elite.util.SQLUtil;
import lk.ijse.elite.util.TransactionUtil;
import java.sql.ResultSet;
import java.sql.SQLException;

import static lk.ijse.elite.model.PlaceOrderModel.updateProperty;
import static lk.ijse.elite.model.RentingDetailModel.saveRentingDetail;

public class RentingModel {
    public static boolean isUpdated(RentDto rentDto, RentingDetailDto rentDetailDto, PaymentDto paymentDto, PaymentdetailDto paymentdetailDto) throws SQLException {
        try {
            TransactionUtil.startTransaction();
            boolean isPaymentSaved = PaymentModel.savePayment(paymentDto);
            if(isPaymentSaved){
                boolean isPaymentDetailSaved = PaymentDetailModel.savePaymentDetail(paymentdetailDto);
                if(isPaymentDetailSaved){
                    boolean isRentSaved = saveRent(rentDto);
                    if(isRentSaved){
                        boolean isRentDetailSaved = saveRentingDetail(rentDetailDto);
                        if(isRentDetailSaved){
                            boolean isPropertyUpdated = updateProperty(rentDto.getPropertyId());
                            if(isPropertyUpdated){
                                return true;
                            }
                        }
                    }
                }
            }
            TransactionUtil.rollBack();
            return false;
        } catch (ClassNotFoundException e) {
            TransactionUtil.rollBack();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } finally {
            TransactionUtil.endTransaction();
        }
        return false;
    }

    private static boolean saveRent(RentDto dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.sql("INSERT INTO renting VALUES(?,?,?,?,?)",
                dto.getRentId(),
                dto.getPropertyId(),
                dto.getCustomerId(),
                dto.getDate(),
                dto.getDuration()
        );
    }

    public static ObservableList<RentingDto> loadAllRentals() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.sql("SELECT * FROM renting");
        ObservableList<RentingDto> rentingList = FXCollections.observableArrayList();

        while (resultSet.next()) {
            rentingList.add(new RentingDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            ));
        }
        return rentingList;
    }

    public boolean deleteRenting(String rentId) throws SQLException, ClassNotFoundException {
        return SQLUtil.sql("DELETE FROM renting WHERE rentId=?", rentId);
    }

    public String generateRentId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.sql("SELECT rent_id FROM renting ORDER BY rent_id DESC LIMIT 1");
        if (resultSet.next()){
            String id = resultSet.getString("rent_id");
            String numericPart = id.replaceAll("\\D","");
            int newRentId = Integer.parseInt(numericPart)+1;
            return String.format("R%03d",newRentId);
        } else {
            return "R001";
        }
    }
}
