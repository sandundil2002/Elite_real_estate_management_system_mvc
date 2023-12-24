package lk.ijse.elite.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import lk.ijse.elite.db.DbConnection;
import lk.ijse.elite.dto.CustomerDto;
import lk.ijse.elite.dto.PaymentDto;
import lk.ijse.elite.dto.PaymentdetailDto;
import lk.ijse.elite.dto.PropertyDto;
import lk.ijse.elite.model.CustomerModel;
import lk.ijse.elite.model.PlaceOrderModel;
import lk.ijse.elite.model.PropertyModel;
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

public class PlaceorderFromController {
    public TextField txtCustomerName;
    public TextField txtPropertyPrice;
    public ComboBox comProid;
    public ComboBox comCusid;
    public DatePicker txtDate;
    public TextField txtPaymentid;
    public ChoiceBox cmdPaymethod;

    public void initialize() throws SQLException {
        loadAllProperty();
        loadAllCustomer();
        autoGenarateId();
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
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        });

        cmdPaymethod.getItems().addAll("Cash","Card","Cheque","Online");
        cmdPaymethod.setValue("Cash");
    }

    public void btnBuyOnAction(ActionEvent actionEvent) {
        String paymentId = txtPaymentid.getText();
        String customerId = String.valueOf(comCusid.getValue());
        String propertyId = String.valueOf(comProid.getValue());
        String name = txtCustomerName.getText();
        String price = txtPropertyPrice.getText();
        String method = String.valueOf(cmdPaymethod.getValue());
        String date = txtDate.getValue().toString();

        var paymentDto = new PaymentDto(paymentId,customerId,propertyId,date,price,method);
        var paymentDetailDto = new PaymentdetailDto(propertyId,paymentId,method);

        try {
            boolean isSuccess = PlaceOrderModel.isUpdated(paymentDto,paymentDetailDto);
            if (isSuccess) {
                new Alert(Alert.AlertType.CONFIRMATION, "Payment Successfull", ButtonType.OK).show();
                jasperReport();
                btnClearOnAction();
                autoGenarateId();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnClearOnAction() {
        txtPaymentid.clear();
        txtCustomerName.clear();
        txtPropertyPrice.clear();
        txtDate.getEditor().clear();
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
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void jasperReport(){
        HashMap buyProperty = new HashMap<>();
        buyProperty.put("Payment_id",txtPaymentid.getText());

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


    private void autoGenarateId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        ResultSet resultSet = connection.prepareStatement("SELECT Payment_id FROM Payment ORDER BY Payment_id DESC LIMIT 1").executeQuery();
        boolean isExists = resultSet.next();

        if (isExists) {
            String old_id = resultSet.getString(1);
            String[] split = old_id.split("Pay");
            int id = Integer.parseInt(split[1]);
            id++;
            if (id < 10) {
                txtPaymentid.setText("Pay00" + id);
            } else if (id < 100) {
                txtPaymentid.setText("Pay0" + id);
            } else {
                txtPaymentid.setText("Pay" + id);
            }
        } else {
            txtPaymentid.setText("Pay001");
        }
    }
}
