package lk.ijse.elite.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import lk.ijse.elite.db.DbConnection;
import lk.ijse.elite.dto.MaintainDto;
import lk.ijse.elite.dto.RentingDto;
import lk.ijse.elite.model.MaintainModel;
import lk.ijse.elite.model.RentingModel;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MaintainmanageFormController {
    public DatePicker dtpDate;
    public TextField txtMaintainId;
    public TextField txtStatus;
    public JFXComboBox cmbRentId;

    public void initialize() throws SQLException {
        autoGenerateId();
        loadAllRentIds();
        dtpDate.setValue(java.time.LocalDate.now());
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String maintainId = txtMaintainId.getText();
        String rentId = String.valueOf(cmbRentId.getValue());
        String date = String.valueOf(dtpDate.getValue());
        String status = txtStatus.getText();

        var dto = new MaintainDto(maintainId, rentId, date, status);
        var model = new MaintainModel();

        try {
            boolean isAdded = model.addMaintain(dto);
            if (isAdded) {
                new Alert(Alert.AlertType.CONFIRMATION, "Maintenance Added Successfully").show();
                initialize();
            } else {
                new Alert(Alert.AlertType.WARNING, "Maintenance Not Added Please try again!!!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.WARNING, "Maintenance Not Added Please try again!!!").show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.WARNING, "Maintenance Not Added Please try again!!!").show();
        }
    }

    private void loadAllRentIds(){
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<RentingDto> rentList = RentingModel.loadAllRentals();

            for (RentingDto rentingDto  : rentList) {
                obList.add(rentingDto.getRent_id());
            }

            cmbRentId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void autoGenerateId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        ResultSet resultSet = connection.prepareStatement("SELECT maintain_id FROM maintain ORDER BY maintain_id DESC LIMIT 1").executeQuery();
        boolean isExists = resultSet.next();

        if (isExists) {
            String old_id = resultSet.getString(1);
            String[] split = old_id.split("M");
            int id = Integer.parseInt(split[1]);
            id++;
            if (id < 10) {
                txtMaintainId.setText("M00" + id);
            } else if (id < 100) {
                txtMaintainId.setText("M0" + id);
            } else {
                txtMaintainId.setText("M" + id);
            }
        } else {
            txtMaintainId.setText("M001");
        }
    }
}
