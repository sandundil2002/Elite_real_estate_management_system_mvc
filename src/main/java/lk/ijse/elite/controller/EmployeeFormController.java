package lk.ijse.elite.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lk.ijse.elite.db.DbConnection;
import lk.ijse.elite.dto.EmployeeDto;
import lk.ijse.elite.dto.tm.EmployeeTm;
import lk.ijse.elite.model.EmployeeModel;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class EmployeeFormController {

    @FXML
    private TableColumn colEmpid;

    @FXML
    private TableColumn colAdid;

    @FXML
    private TableColumn colName;

    @FXML
    private TableColumn colAddress;

    @FXML
    private TableColumn colMobile;

    @FXML
    private TableColumn colPosition;

    @FXML
    private TableColumn colPayment;

    @FXML
    private TableColumn colSalary;

    @FXML
    private TableView<EmployeeTm> tblEmployee;

    public void initialize() {
        cellValueFactory();
        loadAllEmployees();
    }

    @FXML
    private void btnEmployeemanageOnAction() {
        try {
            URL resource = EmployeeManageformController.class.getResource("/view/EmployeeManageform.fxml");
            Parent parent = FXMLLoader.load(resource);
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Employee Manage Form");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void cellValueFactory(){
        colEmpid.setCellValueFactory(new PropertyValueFactory<>("empid"));
        colAdid.setCellValueFactory(new PropertyValueFactory<>("adid"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colMobile.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        colPosition.setCellValueFactory(new PropertyValueFactory<>("position"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("basicSalary"));
        colPayment.setCellValueFactory(new PropertyValueFactory<>("Pay_Salary"));
    }

    private void loadAllEmployees(){
        var model = new EmployeeModel();

        ObservableList<EmployeeTm> obList = FXCollections.observableArrayList();

        try{
            List<EmployeeDto> dtoList = model.loadAllEmployees();

            for(EmployeeDto dto : dtoList){
                Button Pay_Salary = new Button("Pay_Salary");
                Pay_Salary.setCursor(javafx.scene.Cursor.HAND);

                Pay_Salary.setOnAction((e) ->{
                    ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                    ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

                    Optional<ButtonType> result = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to pay salary for this Employee?", yes, no).showAndWait();
                    if (result.orElse(no) == yes) {
                        try {
                            URL resource = SalaryManageFormController.class.getResource("/view/salaryManage_Form.fxml");
                            Parent parent = FXMLLoader.load(resource);
                            Scene scene = new Scene(parent);
                            Stage stage = new Stage();
                            stage.setTitle("Salary Form");
                            stage.setScene(scene);
                            stage.show();
                        } catch (IOException ex) {
                            new Alert(Alert.AlertType.ERROR, ex.getMessage()).show();
                        }
                    }
                });
                obList.add(new EmployeeTm(
                        dto.getEmpid(),
                        dto.getAdid(),
                        dto.getName(),
                        dto.getAddress(),
                        dto.getMobile(),
                        dto.getPosition(),
                        dto.getBasicSalary(),
                        Pay_Salary
                ));
            }
            tblEmployee.setItems(obList);
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    private void btnRefeshOnAction() {
        initialize();
    }

    @FXML
    private void btnsalaryDetailsOnAction(){
        try {
            InputStream resourceAsStream = getClass().getResourceAsStream("/reports/Salary_details.jrxml");
            JasperDesign load = JRXmlLoader.load(resourceAsStream);
            JasperReport compileReport = JasperCompileManager.compileReport(load);
            JasperPrint jasperPrint =
                    JasperFillManager.fillReport(
                            compileReport,
                            null,
                            DbConnection.getInstance().getConnection()
                    );
            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException | SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
}
