package lk.ijse.elite.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import lk.ijse.elite.db.DbConnection;
import lk.ijse.elite.dto.*;
import lk.ijse.elite.model.CustomerModel;
import lk.ijse.elite.model.PropertyModel;
import lk.ijse.elite.model.RentingModel;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class RentPropertyFormController {
    public TextField txtCustomerName;
    public TextField txtPropertyPrice;
    public DatePicker txtDate;
    public TextField txtRentid;
    public JFXComboBox comProid;
    public JFXComboBox comCusid;
    public TextField txtpayId;
    public ChoiceBox cmdPaymethod;
    public ChoiceBox cmdDuration;

    public void initialize() throws SQLException {
        loadAllProperty();
        loadAllCustomer();
        autoGenarateRentId();
        autoGenaratePaymentId();
        txtDate.setValue(java.time.LocalDate.now());

        comProid.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
            try {
                PropertyDto propertyDto = PropertyModel.searchProperty(t1.toString());
                txtPropertyPrice.setText(propertyDto.getPrice());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        comCusid.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
            try {
                CustomerDto customerDto = CustomerModel.searchCustomer(t1.toString());
                txtCustomerName.setText(customerDto.getName());
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        });

        cmdPaymethod.getItems().addAll("Cash","Card","Cheque","Online");
        cmdPaymethod.setValue("Cash");

        cmdDuration.getItems().addAll("3 Months","6 Months","1 Year","2 Years","5 Years");
        cmdDuration.setValue("3 Months");
    }

    public void btnRentOnAction(ActionEvent actionEvent) {
        String rentId = txtRentid.getText();
        String paymentId = txtpayId.getText();
        String customerId = String.valueOf(comCusid.getValue());
        String propertyId = String.valueOf(comProid.getValue());
        String name = txtCustomerName.getText();
        String price = txtPropertyPrice.getText();
        String method = String.valueOf(cmdPaymethod.getValue());
        String date = txtDate.getValue().toString();
        String duration = String.valueOf(cmdDuration.getValue());

        var rentDto = new RentDto(rentId,propertyId,customerId,date,duration);
        var rentDetailDto = new RentingDetailDto(rentId,propertyId,duration);
        var paymentDto = new PaymentDto(paymentId,customerId,propertyId,date,price,method);
        var paymentDetailDto = new PaymentdetailDto(propertyId,paymentId,method);

        try {
            boolean isSuccess = RentingModel.isUpdated(rentDto,rentDetailDto,paymentDto,paymentDetailDto);
            if (isSuccess){
                new Alert(Alert.AlertType.CONFIRMATION,"Rent Succesfull").show();
                btnRentClearOnAction();
                autoGenarateRentId();
            }
        } catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void btnRentClearOnAction() {
        txtCustomerName.clear();
        txtPropertyPrice.clear();
    }

    private void loadAllProperty() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<PropertyDto> proList = PropertyModel.loadAllProperty();

            for (PropertyDto propertyDto  : proList) {
                obList.add(propertyDto.getPropertyId());
            }
            comProid.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadAllCustomer() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<CustomerDto> cusList = CustomerModel.getAllCustomers();

            for (CustomerDto customerDto  : cusList) {
                obList.add(customerDto.getCustomer_id());
            }

            comCusid.setItems(obList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void autoGenarateRentId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        ResultSet resultSet = connection.prepareStatement("SELECT rent_id FROM renting ORDER BY rent_id DESC LIMIT 1").executeQuery();
        boolean isExists = resultSet.next();
        if (isExists) {
            String old_id = resultSet.getString(1);
            String[] split = old_id.split("R");
            int id = Integer.parseInt(split[1]);
            id++;
            if (id < 10) {
                txtRentid.setText("R00" + id);
            } else if (id < 100) {
                txtRentid.setText("R0" + id);
            } else {
                txtRentid.setText("R" + id);
            }
        } else {
            txtRentid.setText("R001");
        }
    }

    private void autoGenaratePaymentId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        ResultSet resultSet = connection.prepareStatement("SELECT Payment_id FROM Payment ORDER BY Payment_id DESC LIMIT 1").executeQuery();
        boolean isExists = resultSet.next();

        if (isExists) {
            String old_id = resultSet.getString(1);
            String[] split = old_id.split("Pay");
            int id = Integer.parseInt(split[1]);
            id++;
            if (id < 10) {
                txtpayId.setText("Pay00" + id);
            } else if (id < 100) {
                txtpayId.setText("Pay0" + id);
            } else {
                txtpayId.setText("Pay" + id);
            }
        } else {
            txtpayId.setText("Pay001");
        }
    }
}
