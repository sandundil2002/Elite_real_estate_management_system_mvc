package lk.ijse.elite.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lk.ijse.elite.dto.CustomerDto;
import lk.ijse.elite.dto.tm.CustomerTm;
import lk.ijse.elite.model.CustomerModel;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;

public class CustomerFormController {

    @FXML
    private TableColumn colCustomerid;

    @FXML
    private TableColumn colName;

    @FXML
    private TableColumn colAddress;

    @FXML
    private TableColumn colMobile;

    @FXML
    private TableColumn colEmail;

    @FXML
    private TableView tblCustomer;

    @FXML
    private TableColumn colSheduleid;

    public void initialize(){
        setCellValueFactory();
        loadAllCustomers();
    }

    @FXML
    private void btnCustomerManageOnAction() {
        try {
            URL resource = CustomerManageFormController.class.getResource("/view/customerManageForm.fxml");
            Parent parent = FXMLLoader.load(resource);
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Customer Manage Form");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void setCellValueFactory() {
        colCustomerid.setCellValueFactory(new PropertyValueFactory<>("customer_id"));
        colSheduleid.setCellValueFactory(new PropertyValueFactory<>("shedule_id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colMobile.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
    }

    private void loadAllCustomers(){
        var model = new CustomerModel();

        ObservableList<CustomerTm> obList = FXCollections.observableArrayList();

        try {
            List<CustomerDto> dtoList = model.getAllCustomers();

            for (CustomerDto dto : dtoList) {
                obList.add(new CustomerTm(
                        dto.getCustomer_id(),
                        dto.getShedule_id(),
                        dto.getName(),
                        dto.getAddress(),
                        dto.getMobile(),
                        dto.getEmail()
                ));
            }
            tblCustomer.setItems(obList);
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    private void btnRefeshOnAction(){
        initialize();
    }
}
