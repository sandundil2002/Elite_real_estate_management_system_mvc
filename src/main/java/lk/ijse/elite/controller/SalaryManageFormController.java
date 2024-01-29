package lk.ijse.elite.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import lk.ijse.elite.dto.EmployeeDto;
import lk.ijse.elite.dto.SalaryDto;
import lk.ijse.elite.model.EmployeeModel;
import lk.ijse.elite.model.SalaryModel;
import java.sql.SQLException;
import java.util.List;

public class SalaryManageFormController {

    @FXML
    private TextField txtSalaryid;

    @FXML
    private TextField txtEmployeeid;

    @FXML
    private TextField txtName;

    @FXML
    private JFXComboBox cmdPosition;

    @FXML
    private DatePicker dtpDate;

    @FXML
    private TextField txtAmount;

    public void initialize(){
        try {
            autoGenarateId();
        } catch (ClassNotFoundException | SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        loadAllEmployees();
        dtpDate.setValue(java.time.LocalDate.now());

        cmdPosition.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
            try {
                EmployeeDto employeeDto = EmployeeModel.searchEmployeePosition(t1.toString());
                txtEmployeeid.setText(employeeDto.getEmpid());
                txtName.setText(employeeDto.getName());
                txtAmount.setText(employeeDto.getBasicSalary());
            } catch (SQLException | ClassNotFoundException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        });
    }

    @FXML
    private void btnPayOnAction() {
        String salaryid = txtSalaryid.getText();
        String employeeid = txtEmployeeid.getText();
        String amount = txtAmount.getText();
        String date = dtpDate.getValue().toString();

        var dto = new SalaryDto(salaryid, employeeid, date, amount);
        var model = new SalaryModel();

        try {
            boolean isSaved = model.saveSalary(dto);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Salary Paid Succesfull").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void loadAllEmployees() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<EmployeeDto> empList = EmployeeModel.loadAllEmployees();

            for (EmployeeDto employeeDto  : empList) {
                obList.add(employeeDto.getPosition());
            }
            cmdPosition.setItems(obList);
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    private void btnClearOnAction() {
        txtSalaryid.clear();
        txtEmployeeid.clear();
        txtName.clear();
        txtAmount.clear();
        cmdPosition.getSelectionModel().clearSelection();
        dtpDate.setValue(java.time.LocalDate.now());
    }

    private void autoGenarateId() throws SQLException, ClassNotFoundException {
        txtSalaryid.setText(new SalaryModel().autoGenarateSalaryId());
    }
}
