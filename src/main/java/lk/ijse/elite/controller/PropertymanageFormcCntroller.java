package lk.ijse.elite.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import lk.ijse.elite.db.DbConnection;
import lk.ijse.elite.dto.AgentDto;
import lk.ijse.elite.dto.PropertyDto;
import lk.ijse.elite.model.AgentModel;
import lk.ijse.elite.model.PropertyModel;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

public class PropertymanageFormcCntroller {
    public ComboBox txtAgentid;
    public TextField txtStatus;
    public ChoiceBox cmbType;
    @FXML
    private TextField txtAddress;
    
    @FXML
    private TextField txtPerches;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtPropertyId;


    public void initialize() throws SQLException {
        autoGenerateId();
        loadAllAdmin();

        txtAgentid.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
            try {
                txtAgentid.setValue(AgentModel.searchAgent(t1.toString()).getAgent_id());
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        cmbType.getItems().addAll("Land","House","Building","Apartment","Office","Warehouse","Shop","Cabin","Farm House");
        cmbType.setValue("Land");
    }

    private void clearFields() {
        txtPropertyId.setText("");
        txtPrice.setText("");
        txtAddress.setText("");
        txtPerches.setText("");
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String pid = txtPropertyId.getText();
        String aid = txtAgentid.toString();
        String price = txtPrice.getText();
        String address = txtAddress.getText();
        String type = String.valueOf(cmbType.getValue());
        String perches = txtPerches.getText();
        String status = txtStatus.getText();

        boolean isPropertyValidated = validateProperty();
        if (!isPropertyValidated) {
            return;
        }

        var dto = new PropertyDto(pid, aid, price, address, type, perches, status);
        var model = new PropertyModel();

        try {
            boolean isUpdated = model.updateProperty(dto);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Property Update Succesfull!!!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {
        String pid = txtPropertyId.getText();
        var model = new PropertyModel();
        try {
            PropertyDto dto = model.searchProperty(pid);
            if (dto != null) {
                fillFields(dto);
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Property Not Found!!!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void fillFields(PropertyDto dto) {
        txtPropertyId.setText(dto.getPropertyId());
        txtPrice.setText(dto.getPrice());
        txtAddress.setText(dto.getAddress());
        cmbType.setValue(dto.getType());
        txtPerches.setText(dto.getPerches());
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String pId = txtPropertyId.getText();
        String aId = String.valueOf(txtAgentid.getValue());
        String price = txtPrice.getText();
        String address = txtAddress.getText();
        String type = String.valueOf(cmbType.getValue());
        String perches = txtPerches.getText();
        String status = txtStatus.getText();

        boolean isPropertyValidated = validateProperty();
        if (!isPropertyValidated) {
            return;
        }

        var dto = new PropertyDto(pId, aId, price, address, type, perches, status);
        var model = new PropertyModel();

        try {
            boolean isSaved = model.saveProperty(dto);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Property Added Succesfull").show();
                clearFields();
                autoGenerateId();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void loadAllAdmin(){
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<AgentDto> ageList = AgentModel.loadAllAgents();

            for (AgentDto agentDto  : ageList) {
                obList.add(agentDto.getAgent_id());
            }
            txtAgentid.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private boolean validateProperty() {
        String priceText = txtPrice.getText();
        boolean priceValidate = Pattern.compile("[$][0-9]{3,}").matcher(priceText).matches();
        if (!priceValidate) {
            new Alert(Alert.AlertType.ERROR, "Invalid Price").show();
            txtPrice.requestFocus();
            return false;
        }

        String addressText = txtAddress.getText();
        boolean addressValidate = Pattern.compile("[A-Z]{1}[a-z]{1,}").matcher(addressText).matches();
        if (!addressValidate) {
            new Alert(Alert.AlertType.ERROR, "Invalid Address").show();
            txtAddress.requestFocus();
            return false;
        }
        return true;
    }

    private void autoGenerateId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT property_id FROM property ORDER BY property_id DESC LIMIT 1";
        ResultSet resultSet = connection.createStatement().executeQuery(sql);
        boolean isIdExists = resultSet.next();

        if (isIdExists) {
            String property_id = resultSet.getString(1);
            String[] tempArr = property_id.split("P");
            int id = Integer.parseInt(tempArr[1]);
            id++;
            if (id < 10) {
                txtPropertyId.setText("P00" + id);
            } else if (id < 100) {
                txtPropertyId.setText("P0" + id);
            } else {
                txtPropertyId.setText("P" + id);
            }
        } else {
            txtPropertyId.setText("P001");
        }
    }
}
