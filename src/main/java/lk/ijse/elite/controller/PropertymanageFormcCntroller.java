package lk.ijse.elite.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.elite.dto.PropertyDto;
import lk.ijse.elite.dto.tm.PropertyTm;
import lk.ijse.elite.model.PropertyModel;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class PropertymanageFormcCntroller {
    public TableView<PropertyTm> tblproperty;
    public AnchorPane property;
    public TableColumn<?, ?> coltype;
    public TableColumn<?, ?> coladdress;
    public TableColumn<?, ?> colprice;
    public TableColumn<?, ?> colproid;
    public TextField txtid;
    public TextField txtprice;
    public TextField txttype;
    public TextField txtaddress;

    public void initialize() {
        setCellValueFactory();
        loadAllCustomers();
    }

    private void setCellValueFactory() {
        colproid.setCellValueFactory(new PropertyValueFactory<>("propertyId"));
        coltype.setCellValueFactory(new PropertyValueFactory<>("type"));
        coladdress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colprice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
    private void loadAllCustomers() {
        var model = new PropertyModel();

        ObservableList<PropertyTm> obList = FXCollections.observableArrayList();

        try {
            List<PropertyDto> dtoList = model.loadAllProperty();

            for(PropertyDto dto : dtoList) {
                obList.add(new PropertyTm(dto.getPropertyId(), dto.getType(), dto.getAddress(),dto.getPrice())
                );
            }
            tblproperty.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnAddOnAction(ActionEvent actionEvent){
        String id = txtid.getText();
        String price = txtprice.getText();
        String address = txtaddress.getText();
        String type = txttype.getText();

        if (id.isEmpty() || price.isEmpty() || address.isEmpty() || type.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please fill out all fields").show();
            return;
        }

        var dto = new PropertyDto(id,price,address,type);
        var model = new PropertyModel();

        try {
            boolean isSaved = model.saveProperty(dto);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Property Added Succesfull").show();
                initialize();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void clearFields() {
        txtid.setText("");
        txtprice.setText("");
        txtaddress.setText("");
        txttype.setText("");
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String id = txtid.getText();
        String price = txtprice.getText();
        String address = txtaddress.getText();
        String type = txttype.getText();

        if (id.isEmpty() || price.isEmpty() || address.isEmpty() || type.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please fill out all fields").show();
            return;
        }

        var dto = new PropertyDto(id,price,address,type);
        var model = new PropertyModel();

        try {
            boolean isUpdated = model.updateProperty(dto);
            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Property Update Succesfull!!!").show();
                initialize();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    public void btnSearchOnAction(ActionEvent actionEvent) {
        String id = txtid.getText();
        var model = new PropertyModel();
        try {
            PropertyDto dto = model.searchProperty(id);

            if(dto != null) {
                fillFields(dto);
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Property Not Found!!!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String id = txtid.getText();
        var model = new PropertyModel();

        try{
            var propertyModel = new PropertyModel();
            PropertyDto dto = model.searchProperty(id);
            if(dto != null) {
                boolean isDeleted = propertyModel.deleteProperty(id);
                if (isDeleted) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Property Delete Succesfull!!!").show();
                    initialize();
                    clearFields();
                }
            }else {
                new Alert(Alert.AlertType.ERROR, "Property Not Found!!!").show();
                clearFields();
            }
        } catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void fillFields(PropertyDto dto) {
        txtid.setText(dto.getPropertyId());
        txtprice.setText(dto.getPrice());
        txtaddress.setText(dto.getAddress());
        txttype.setText(dto.getType());
    }

    public void btnbackOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));
        Stage stage = (Stage) property.getScene().getWindow();
        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Dashboard Form");
        stage.centerOnScreen();
    }
}
