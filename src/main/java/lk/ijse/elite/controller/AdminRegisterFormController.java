package lk.ijse.elite.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;

public class AdminRegisterFormController {
    public AnchorPane register;
    public TextField txtadminId;
    public Button btnregister;
    public TextField txtemail;
    public TextField txtname;
    public TextField txtaddress;
    public TextField txtmobile;
    public TextField txtpassword;
    public TextField txtotp;
    public Button btnback;


    public void btnregisterOnAction(ActionEvent actionEvent) {
        
    }

    public void btnbackOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(this.getClass().getResource("/view/adminlogin_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) register.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Admin Login Form");
        stage.centerOnScreen();
    }
}
