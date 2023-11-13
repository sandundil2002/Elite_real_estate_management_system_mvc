package lk.ijse.elite.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import lk.ijse.elite.model.AdminModel;
import java.sql.SQLException;

public class AdminloginFormController {
    public TextField txtId;
    public TextField txtPassword;
    public JFXButton btnLogin;

    public void btnLoginOnAction(ActionEvent actionEvent) throws SQLException {
        AdminModel adminModel = new AdminModel();
        boolean isValidAdmin = adminModel.loginAdmin(txtId.getText(),txtPassword.getText());

        if (isValidAdmin){
            System.out.println("Login Success");
        } else {
            new Alert(Alert.AlertType.ERROR, "Invalid User id or Password").show();
        }
    }


}

