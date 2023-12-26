package lk.ijse.elite.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;

public class DashboardFormController {
    @FXML
    private Pane bodyPane;
    public AnchorPane dashboard;

    public void initialize() throws IOException {
        btnDashboardOnAction(new ActionEvent());
    }

    public void btnPropertyOnAction(ActionEvent actionEvent) throws IOException {
        bodyPane.getChildren().clear();
        try {
            AnchorPane anchorPane = FXMLLoader.load(this.getClass().getResource("/view/PropertyForm.fxml"));
            bodyPane.getChildren().add(anchorPane);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void btnEmployeeOnAction(ActionEvent actionEvent) {
        bodyPane.getChildren().clear();
        try {
            AnchorPane anchorPane = FXMLLoader.load(this.getClass().getResource("/view/EmployeeForm.fxml"));
            bodyPane.getChildren().add(anchorPane);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void btnSignoutOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(this.getClass().getResource("/view/adminlogin_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) dashboard.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Login Form");
        stage.centerOnScreen();
    }

    public void btnDashboardOnAction(ActionEvent actionEvent) {
        bodyPane.getChildren().clear();
        try {
            AnchorPane anchorPane = FXMLLoader.load(this.getClass().getResource("/view/mainDashboardForm.fxml"));
            bodyPane.getChildren().add(anchorPane);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void btnAppoinmentsOnAction(ActionEvent actionEvent) {
        bodyPane.getChildren().clear();
        try {
            AnchorPane anchorPane = FXMLLoader.load(this.getClass().getResource("/view/shedule_form.fxml"));
            bodyPane.getChildren().add(anchorPane);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void btnCustomersOnAction(ActionEvent actionEvent) {
        bodyPane.getChildren().clear();
        try {
            AnchorPane anchorPane = FXMLLoader.load(this.getClass().getResource("/view/customerForm.fxml"));
            bodyPane.getChildren().add(anchorPane);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void btnPaymentsOnAction(ActionEvent actionEvent) {
        bodyPane.getChildren().clear();
        try {
            AnchorPane anchorPane = FXMLLoader.load(this.getClass().getResource("/view/PaymentsForm.fxml"));
            bodyPane.getChildren().add(anchorPane);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void btnRentalOnAction(ActionEvent actionEvent) {
        bodyPane.getChildren().clear();
        try {
            AnchorPane anchorPane = FXMLLoader.load(this.getClass().getResource("/view/RentalForm.fxml"));
            bodyPane.getChildren().add(anchorPane);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void btnAgentsOnAction(ActionEvent actionEvent) {
        bodyPane.getChildren().clear();
        try {
            AnchorPane anchorPane = FXMLLoader.load(this.getClass().getResource("/view/AgentForm.fxml"));
            bodyPane.getChildren().add(anchorPane);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
