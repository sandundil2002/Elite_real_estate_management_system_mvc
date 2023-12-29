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
import lk.ijse.elite.model.PaymentModel;
import lk.ijse.elite.model.PropertyModel;
import lk.ijse.elite.model.RentingModel;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
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

    public void initialize(){
        loadAllProperty();
        loadAllCustomer();
        try {
            autoGenarateRentId();
            autoGenaratePaymentId();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        txtDate.setValue(java.time.LocalDate.now());

        comProid.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
            try {
                PropertyDto propertyDto = PropertyModel.searchProperty(t1.toString());
                txtPropertyPrice.setText(propertyDto.getPrice());
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
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
                jasperReport();
                btnRentClearOnAction();
                autoGenarateRentId();
            }
        } catch(SQLException | ClassNotFoundException e){
            throw new RuntimeException(e);
        }
    }

    public void btnRentClearOnAction() {
        txtCustomerName.clear();
        txtPropertyPrice.clear();
    }

    private void jasperReport(){
        HashMap buyProperty = new HashMap<>();
        buyProperty.put("Payment_id",txtpayId.getText());

        InputStream resourceAsStream = getClass().getResourceAsStream("/reports/buyProperty.jrxml");
        try {
            JasperDesign load = JRXmlLoader.load(resourceAsStream);
            JasperReport compileReport = JasperCompileManager.compileReport(load);
            JasperPrint jasperPrint =
                    JasperFillManager.fillReport(
                            compileReport,
                            buyProperty,
                            DbConnection.getInstance().getConnection()
                    );
            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException | SQLException e) {
            e.printStackTrace();
        }
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
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
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

    private void autoGenarateRentId() throws SQLException, ClassNotFoundException {
        txtRentid.setText(new RentingModel().generateRentId());
    }

    private void autoGenaratePaymentId() throws SQLException, ClassNotFoundException {
        txtpayId.setText(new PaymentModel().generatePaymentId());
    }
}
