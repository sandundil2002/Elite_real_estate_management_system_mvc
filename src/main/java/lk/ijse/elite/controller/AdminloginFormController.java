package lk.ijse.elite.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.elite.model.AdminModel;
import java.io.IOException;
import java.sql.SQLException;

public class AdminloginFormController {
    public TextField txtId;
    public TextField txtPassword;
    public JFXButton btnLogin;
    public AnchorPane admin;

    public void btnLoginOnAction(ActionEvent actionEvent) throws SQLException, IOException {
        AdminModel adminModel = new AdminModel();
        boolean isValidAdmin = adminModel.loginAdmin(txtId.getText(),txtPassword.getText());

        if (isValidAdmin){
            AnchorPane anchorPane = FXMLLoader.load(this.getClass().getResource("/view/dashboard_form.fxml"));
            Scene scene = new Scene(anchorPane);
            Stage stage = (Stage) admin.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Dashboard Form");
            stage.centerOnScreen();
        } else {
            new Alert(Alert.AlertType.ERROR, "Invalid User id or Password Please try again!!!").show();
            txtId.clear();
            txtPassword.clear();
            txtId.requestFocus();
        }
    }

    public void btnRegisterOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(this.getClass().getResource("/view/adminregister_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) admin.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Admin Register Form");
        stage.centerOnScreen();
    }
}

