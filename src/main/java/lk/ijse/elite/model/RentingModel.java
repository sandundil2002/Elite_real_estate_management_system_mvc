package lk.ijse.elite.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import lk.ijse.elite.db.DbConnection;
import lk.ijse.elite.dto.*;
import lk.ijse.elite.dto.tm.RentingTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static lk.ijse.elite.model.PlaceOrderModel.updateProperty;
import static lk.ijse.elite.model.RentingDetailModel.saveRentingDetail;

public class RentingModel {
    public static boolean isUpdated(RentDto rentDto, RentingDetailDto rentDetailDto, PaymentDto paymentDto, PaymentdetailDto paymentdetailDto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);
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
                                connection.commit();
                                return true;
                            }
                        }
                    }
                }
            }
            connection.rollback();
            return false;
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } finally {
            connection.setAutoCommit(true);
        }
        return false;
    }

    private static boolean saveRent(RentDto rentDto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "insert into renting values (?,?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, rentDto.getRentId());
        statement.setString(2, rentDto.getPropertyId());
        statement.setString(3, rentDto.getCustomerId());
        statement.setString(4, rentDto.getDate());
        statement.setString(5, rentDto.getDuration());

        boolean isSaved = statement.executeUpdate() > 0;
        return isSaved;
    }

    public static ObservableList<RentingDto> loadAllRentals() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM renting";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

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

    public boolean deleteRenting(String rentId) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM renting WHERE rent_id=?";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, rentId);

        return statement.executeUpdate() > 0;
    }
}
