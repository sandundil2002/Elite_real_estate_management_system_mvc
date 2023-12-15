package lk.ijse.elite.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.elite.dto.CustomerDto;
import lk.ijse.elite.dto.tm.CustomerTm;
import lk.ijse.elite.model.CustomerModel;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;

public class CustomerFormController {
    public AnchorPane customer;
    public TableColumn colCustomerid;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colMobile;
    public TableColumn colEmail;
    public TableView tblCustomer;
    public TableColumn colSheduleid;

    public void initialize() throws SQLException {
        setCellValueFactory();
        loadAllCustomers();
    }

    public void btnCustomerManageOnAction(ActionEvent actionEvent) {
        try {
            URL resource = CustomerManageFormController.class.getResource("/view/customerManageForm.fxml");
            Parent parent = FXMLLoader.load(resource);
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Customer Manage Form");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
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

    private void loadAllCustomers() throws SQLException {
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
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnRefeshOnAction(ActionEvent actionEvent) throws SQLException {
        initialize();
    }
}
