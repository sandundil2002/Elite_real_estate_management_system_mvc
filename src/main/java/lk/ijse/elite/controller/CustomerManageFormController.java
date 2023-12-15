package lk.ijse.elite.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import lk.ijse.elite.db.DbConnection;
import lk.ijse.elite.dto.CustomerDto;
import lk.ijse.elite.dto.SheduleDto;
import lk.ijse.elite.model.CustomerModel;
import lk.ijse.elite.model.ScheduleModel;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

public class CustomerManageFormController {
    public TextField txtCustomerid;
    
    public TextField txtAddress;
    public TextField txtMobile;
    public TextField txtEmail;
    public TextField txtName;
    public JFXComboBox cmbSheduleid;

    public void initialize() throws SQLException {
        autoGenerateId();
        loadAllShedule();
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String cid = txtCustomerid.getText();
        String sheduleid = String.valueOf(cmbSheduleid.getValue());
        String name = txtName.getText();
        String address = txtAddress.getText();
        String mobile = txtMobile.getText();
        String email = txtEmail.getText();

        boolean isCustomerValidated = validateCustomer();
        if (!isCustomerValidated) {
            return;
        }

        var dto = new CustomerDto(cid,sheduleid,name, address, mobile, email);
        var model = new CustomerModel();

        try {
            boolean isSaved = model.saveCustomer(dto);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Customer Saved Succesfull").show();
                clearFields();
                autoGenerateId();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void clearFields() {
        txtCustomerid.setText("");
        cmbSheduleid.setValue("");
        txtName.setText("");
        txtAddress.setText("");
        txtMobile.setText("");
        txtEmail.setText("");
    }


    public void btnSearchOnAction(ActionEvent actionEvent) {
        String cid = txtCustomerid.getText();
        var model = new CustomerModel();
        try {
            CustomerDto dto = model.searchCustomer(cid);

            if(dto != null) {
                fillFields(dto);
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Customer Not Found!!!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void fillFields(CustomerDto dto) {
        txtCustomerid.setText(dto.getCustomer_id());
        cmbSheduleid.setValue(dto.getShedule_id());
        txtName.setText(dto.getName());
        txtAddress.setText(dto.getAddress());
        txtMobile.setText(dto.getMobile());
        txtEmail.setText(dto.getEmail());
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String cid = txtCustomerid.getText();
        var model = new CustomerModel();

        try{
            var customerModel = new CustomerModel();
            CustomerDto dto = model.searchCustomer(cid);
            if(dto != null) {
                boolean isDeleted = customerModel.deleteCustomer(cid);
                if (isDeleted) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Customer Delete Succesfull!!!").show();
                    clearFields();
                    autoGenerateId();
                }
            }else {
                new Alert(Alert.AlertType.ERROR, "Customer Not Found!!!").show();
                clearFields();
            }
        } catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String cid = txtCustomerid.getText();
        String sheduleid = String.valueOf(cmbSheduleid.getValue());
        String name = txtName.getText();
        String address = txtAddress.getText();
        String mobile = txtMobile.getText();
        String email = txtEmail.getText();

        boolean isCustomerValidated = validateCustomer();
        if (!isCustomerValidated) {
            return;
        }

        var dto = new CustomerDto(cid,sheduleid,name, address, mobile, email);
        var model = new CustomerModel();

        try {
            boolean isUpdated = model.updateCustomer(dto);
            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Customer Update Succesfull!!!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private boolean validateCustomer() {
        String name = txtName.getText();
        boolean isNameValidated = Pattern.compile("^[A-z]{1,20}$").matcher(name).matches();
        if (!isNameValidated) {
            txtName.requestFocus();
            txtName.setStyle("-fx-border-color:#ff0000;");
            return false;
        }

        String address = txtAddress.getText();
        boolean isAddressValidated = Pattern.compile("^[A-z]{1,20}$").matcher(address).matches();
        if (!isAddressValidated) {
            txtAddress.requestFocus();
            txtAddress.setStyle("-fx-border-color:#ff0000;");
            return false;
        }

        String mobile = txtMobile.getText();
        boolean isMobileValidated = Pattern.compile("^[0-9]{10}$").matcher(mobile).matches();
        if (!isMobileValidated) {
            txtMobile.requestFocus();
            txtMobile.setStyle("-fx-border-color:#ff0000;");
            return false;
        }

        String email = txtEmail.getText();
        boolean isEmailValidated = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$").matcher(email).matches();
        if (!isEmailValidated) {
            txtEmail.requestFocus();
            txtEmail.setStyle("-fx-border-color:#ff0000;");
            return false;
        }
        return true;
    }

    private void loadAllShedule() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<SheduleDto> sheList = ScheduleModel.loadAllSchedule();

            for (SheduleDto sheduleDto  : sheList) {
                obList.add(sheduleDto.getScheduleId());
            }
            cmbSheduleid.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void autoGenerateId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        ResultSet resultSet = connection.prepareStatement("SELECT Customer_id FROM customer ORDER BY Customer_id DESC LIMIT 1").executeQuery();
        boolean isExists = resultSet.next();

        if (isExists) {
            String old_id = resultSet.getString(1);
            String[] split = old_id.split("C");
            int id = Integer.parseInt(split[1]);
            id++;
            if (id < 10) {
                txtCustomerid.setText("C00" + id);
            } else if (id < 100) {
                txtCustomerid.setText("C0" + id);
            } else {
                txtCustomerid.setText("C" + id);
            }
        } else {
            txtCustomerid.setText("C001");
        }
    }
}
