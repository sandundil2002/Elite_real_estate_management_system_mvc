package lk.ijse.elite.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import lk.ijse.elite.db.DbConnection;
import lk.ijse.elite.dto.AdminDto;
import lk.ijse.elite.dto.EmployeeDto;
import lk.ijse.elite.model.AdminModel;
import lk.ijse.elite.model.EmployeeModel;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

public class EmployeeManageformController {

    public TextField txtEmpid;
    public TextField txtName;
    public TextField txtAddress;
    public TextField txtMobile;
    public JFXComboBox cmbAdminid;
    public JFXComboBox cmbEmployeeposition;
    public TextField txtAmount;

    public void initialize() throws SQLException {
        autoGenerateId();
        loadAllAdmin();

        cmbEmployeeposition.getItems().addAll("Director","Project Manager","Chief Operation","Chief Executive","lawyer","General Manager","Sales Manager","Charted Accountant","Admin Manager","Customer Service");

        cmbEmployeeposition.setOnAction(event -> {
            String selectedPosition = String.valueOf(cmbEmployeeposition.getValue());
            String amount = getAmountByPosition(selectedPosition);
            txtAmount.setText(amount);
        });
    }

    private String getAmountByPosition(String selectedPosition) {
        switch (selectedPosition) {
            case "Director":
                return "$14000";
            case "Project Manager":
                return "$11000";
            case "Chief Operation":
                return "$10000";
            case "Chief Executive":
                return "$9000";
            case "lawyer":
                return "$8000";
            case "General Manager":
                return "$6000";
            case "Sales Manager":
                return "$5000";
            case "Charted Accountant":
                return "$5000";
            case "Admin Manager":
                return "$4000";
            case "Customer Service":
                return "$3000";
            default:
                return "0";
        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String eid = txtEmpid.getText();
        String adid = String.valueOf(cmbAdminid.getValue());
        String name = txtName.getText();
        String address = txtAddress.getText();
        String mobile = txtMobile.getText();
        String position = String.valueOf(cmbEmployeeposition.getValue());
        String sal = txtAmount.getText();

        boolean isEmployeeValidated = validateEmployee();
        if (!isEmployeeValidated) {
            return;
        }

        var dto = new EmployeeDto(eid,adid, name, address, mobile, position, sal);
        var model = new EmployeeModel();

        try {
            boolean isSaved = model.saveEmployee(dto);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Employee Saved Succesfull").show();
                initialize();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void clearFields() {
        txtEmpid.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtMobile.setText("");
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String eid = txtEmpid.getText();
        String adid = String.valueOf(cmbAdminid.getValue());
        String name = txtName.getText();
        String address = txtAddress.getText();
        String mobile = txtMobile.getText();
        String position = String.valueOf(cmbEmployeeposition.getValue());
        String sal = txtAmount.getText();

        boolean isEmployeeValidated = validateEmployee();
        if (!isEmployeeValidated) {
            return;
        }

        var dto = new EmployeeDto(eid,adid, name, address, mobile, position, sal);
        var model = new EmployeeModel();

        try {
            boolean isUpdated = model.updateEmployee(dto);
            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Employee Update Succesfull!!!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String eid = txtEmpid.getText();
        var model = new EmployeeModel();

        try{
            var employeeModel = new EmployeeModel();
            EmployeeDto dto = model.searchEmployee(eid);
            if(dto != null) {
                boolean isDeleted = employeeModel.deleteEmployee(eid);
                if (isDeleted) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Employee Delete Succesfull!!!").show();
                    clearFields();
                }
            }else {
                new Alert(Alert.AlertType.WARNING, "Employee Not Found!!!").show();
                clearFields();
            }
        } catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {
        String eid = txtEmpid.getText();
        
        var model = new EmployeeModel();
        try {
            EmployeeDto dto = model.searchEmployee(eid);

            if(dto != null) {
                fillFields(dto);
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Employee not found!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void fillFields(EmployeeDto dto) {
        txtEmpid.setText(dto.getEmpid());
        cmbAdminid.setValue(dto.getAdid());
        txtName.setText(dto.getName());
        txtAddress.setText(dto.getAddress());
        txtMobile.setText(dto.getMobile());
        cmbEmployeeposition.setValue(dto.getPosition());
    }

    private void loadAllAdmin() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<AdminDto> adList = AdminModel.loadAllAdmin();

            for (AdminDto adminDto  : adList) {
                obList.add(adminDto.getAdmin_id());
            }

            cmbAdminid.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean validateEmployee(){
        String name = txtName.getText();
        boolean isNameValid = Pattern.compile("^[A-z ]{3,}$").matcher(name).matches();
        if (!isNameValid){
            txtName.requestFocus();
            txtName.setStyle("-fx-border-color: red");
            return false;
        }

        String address = txtAddress.getText();
        boolean isAddressValid = Pattern.compile("^[A-z0-9 ,]{3,}$").matcher(address).matches();
        if (!isAddressValid){
            txtAddress.requestFocus();
            txtAddress.setStyle("-fx-border-color: red");
            return false;
        }

        String mobile = txtMobile.getText();
        boolean isMobileValid = Pattern.compile("^[0-9]{10}$").matcher(mobile).matches();
        if (!isMobileValid){
            txtMobile.requestFocus();
            txtMobile.setStyle("-fx-border-color: red");
            return false;
        }
        return true;
    }

    private void autoGenerateId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        ResultSet resultSet = connection.prepareStatement("SELECT Employee_id FROM employee ORDER BY Employee_id DESC LIMIT 1").executeQuery();
        boolean isIdExists = resultSet.next();

        if (isIdExists) {
            String old_id = resultSet.getString(1);
            String[] split = old_id.split("E");
            int id = Integer.parseInt(split[1]);
            id++;
            if (id < 10) {
                txtEmpid.setText("E00" + id);
            } else if (id < 100) {
                txtEmpid.setText("E0" + id);
            } else {
                txtEmpid.setText("E" + id);
            }
        } else {
            txtEmpid.setText("E001");
        }
    }
}
