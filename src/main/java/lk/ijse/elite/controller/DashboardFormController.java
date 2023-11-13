package lk.ijse.elite.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardFormController {
    public AnchorPane root;

    public void btnPropertyOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(this.getClass().getResource("/view/propertymanage_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Property Manage Form");
        stage.centerOnScreen();
    }

    public void btnSheduleOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(this.getClass().getResource("/view/shedulemanage_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Shedule Manage Form");
        stage.centerOnScreen();
    }

    public void btnEmployeeOnAction(ActionEvent actionEvent) {
    }

    public void btnBalanceOnAction(ActionEvent actionEvent) {
    }

    public void btnAgentOnAction(ActionEvent actionEvent) {
    }

    public void btnAboutOnAction(ActionEvent actionEvent) {
    }

    public void btnSignoutOnAction(ActionEvent actionEvent) {
    }
}
