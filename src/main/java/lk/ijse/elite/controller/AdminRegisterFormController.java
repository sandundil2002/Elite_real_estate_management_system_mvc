package lk.ijse.elite.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.elite.dto.AdminDto;
import lk.ijse.elite.model.AdminModel;
import lk.ijse.elite.sendMail.SendEmail;
import javax.mail.MessagingException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.SQLException;
import java.util.Random;
import java.util.regex.Pattern;

public class AdminRegisterFormController {
    public TextField txtAdmin_id;
    public TextField txtEmail;
    public TextField txtName;
    public TextField txtOtp;
    public TextField txtMobile;
    public TextField txtPassword;
    public AnchorPane signupPane;
    int otp;

    public void initialize() {
        try {
            autoGenerateAdminId();
        } catch (ClassNotFoundException | SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnSignupOnAction(ActionEvent actionEvent) {
        String id = txtAdmin_id.getText();
        String name = txtName.getText();
        String otp = txtOtp.getText();
        String mobile = txtMobile.getText();
        String email = txtEmail.getText();
        String password = txtPassword.getText();

        boolean isAdminValidated = validateAdmin();
        if (!isAdminValidated) {
            return;
        }

        var dto = new AdminDto(id, name, otp, mobile, email, password);
        var model = new AdminModel();

        try {
            boolean isSaved = model.registerAdmin(dto);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Admin Register Succesfull").show();
                clearFields();
                initialize();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void clearFields() {
        txtAdmin_id.setText("");
        txtName.setText("");
        txtOtp.setText("");
        txtMobile.setText("");
        txtEmail.setText("");
        txtPassword.setText("");
    }

    public void btnRequestOtpOnAction(ActionEvent actionEvent) {
        Random random = new Random();
        otp = random.nextInt(9999);

        boolean isSend = sendMail("Elite Real Estate Management System", String.valueOf(otp),"sandundil2002@gmail.com");
            if(isSend) {
                new Alert(Alert.AlertType.CONFIRMATION, "OTP Send Succesfull").show();
            } else {
             new Alert(Alert.AlertType.ERROR, "OTP Send Failed").show();
            }
    }

    private boolean sendMail(String title,String message,String gmail){
        try {
            new SendEmail().sendMail(title,message,gmail);
            return true;
        } catch (IOException | MessagingException | GeneralSecurityException e) {
            e.printStackTrace();
            return false;
        }
    }
    public void btnLoginOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(this.getClass().getResource("/view/adminlogin_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) signupPane.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Admin Login Form");
        stage.centerOnScreen();
    }

    private boolean validateAdmin() {
        String nameText = txtName.getText();
        boolean isAdminNameValidated = Pattern.matches("[A-Za-z]{3,}", nameText);
        if (!isAdminNameValidated) {
            txtName.requestFocus();
            txtName.setStyle("-fx-border-color:#ff0000;");
            return false;
        }

        String mobileText = txtMobile.getText();
        boolean isAdminMobileValidated = Pattern.matches("[0-9]{10}", mobileText);
        if (!isAdminMobileValidated) {
            txtMobile.requestFocus();
            txtMobile.setStyle("-fx-border-color:#ff0000;");
            return false;
        }

        String emailText = txtEmail.getText();
        boolean isAdminEmailValidated = Pattern.matches("[A-Za-z0-9@.]{3,}", emailText);
        if (!isAdminEmailValidated) {
            txtEmail.requestFocus();
            txtEmail.setStyle("-fx-border-color:#ff0000;");
            return false;
        }

        String passwordText = txtPassword.getText();
        boolean isAdminPasswordValidated = Pattern.matches("[A-Za-z0-9]{3,}", passwordText);
        if (!isAdminPasswordValidated) {
            txtPassword.requestFocus();
            txtPassword.setStyle("-fx-border-color:#ff0000;");
            return false;
        }

        String otpText = txtOtp.getText();
        boolean isAdminOtpValidated = otpText.equals(String.valueOf(otp));
        if (!isAdminOtpValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid otp").show();
            txtOtp.requestFocus();
            txtOtp.setStyle("-fx-border-color:#ff0000;");
            return false;
        }
        return true;
    }

    public void autoGenerateAdminId() throws SQLException, ClassNotFoundException {
        txtAdmin_id.setText(new AdminModel().generateAdminId());
    }
}
