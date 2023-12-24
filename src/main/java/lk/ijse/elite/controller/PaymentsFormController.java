package lk.ijse.elite.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.elite.dto.PaymentDto;
import lk.ijse.elite.dto.tm.PaymentTm;
import lk.ijse.elite.model.PaymentModel;
import java.sql.SQLException;
import java.util.List;

public class PaymentsFormController {

    public TableColumn colPaymentId;
    public TableColumn colPropertyId;
    public TableColumn colCustomerId;
    public TableColumn colDate;
    public TableColumn colPrice;
    public TableColumn colMethod;
    @FXML
    private TableView<PaymentTm> tblPayment;

    public void initialize() {
        cellValueFactory();
        loadAllPayments();
    }

    private void cellValueFactory() {
        colPaymentId.setCellValueFactory(new PropertyValueFactory<>("payment_id"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customer_id"));
        colPropertyId.setCellValueFactory(new PropertyValueFactory<>("property_id"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colMethod.setCellValueFactory(new PropertyValueFactory<>("method"));
    }

    private void loadAllPayments() {
        var model = new PaymentModel();
        ObservableList<PaymentTm> obList = FXCollections.observableArrayList();

        try {
            List<PaymentDto> dtoList = model.getAllPayments();

            for (PaymentDto dto : dtoList) {
                obList.add(new PaymentTm(
                        dto.getPayment_id(),
                        dto.getCustomer_id(),
                        dto.getProperty_id(),
                        dto.getDate(),
                        dto.getPrice(),
                        dto.getMethod()
                ));
            }
            tblPayment.setItems(obList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
}

