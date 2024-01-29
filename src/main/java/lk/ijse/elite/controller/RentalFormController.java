package lk.ijse.elite.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.elite.dto.RentingDto;
import lk.ijse.elite.dto.tm.RentingTm;
import lk.ijse.elite.model.RentingModel;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class RentalFormController {

    @FXML
    private TableColumn colRentId;

    @FXML
    private TableColumn colPropertyId;

    @FXML
    private TableColumn colAgentId;

    @FXML
    private TableColumn colDate;

    @FXML
    private TableColumn colDuration;

    @FXML
    private AnchorPane rentPane;

    @FXML
    private TableView tblRent;

    @FXML
    private TableColumn colMaintain;

    @FXML
    private TableColumn colDelete;

    public void initialize(){
        setCellValueFactories();
        loadAllRentals();
    }

    @FXML
    private void btnViewPropertyOnAction() {
        rentPane.getChildren().clear();
        try {
            AnchorPane anchorPane = FXMLLoader.load(this.getClass().getResource("/view/PropertyForm.fxml"));
            rentPane.getChildren().add(anchorPane);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void setCellValueFactories() {
        colRentId.setCellValueFactory(new PropertyValueFactory<>("rent_id"));
        colPropertyId.setCellValueFactory(new PropertyValueFactory<>("property_id"));
        colAgentId.setCellValueFactory(new PropertyValueFactory<>("agent_id"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        colMaintain.setCellValueFactory(new PropertyValueFactory<>("btnMaintain"));
        colDelete.setCellValueFactory(new PropertyValueFactory<>("btnDelete"));
    }

    private void loadAllRentals() {
        var model = new RentingModel();

        ObservableList<RentingTm> obList = FXCollections.observableArrayList();

        try {
            List<RentingDto> dtoList = model.loadAllRentals();
            for (RentingDto dto : dtoList) {
                Button Maintain = new Button("Maintain");
                Button Delete = new Button("Delete");
                Maintain.setCursor(Cursor.HAND);
                Delete.setCursor(Cursor.HAND);

                Maintain.setOnAction((e) ->{
                    ButtonType ok = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
                    ButtonType no = new ButtonType("NO", ButtonBar.ButtonData.CANCEL_CLOSE);

                    Optional<ButtonType> result = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to maintain this Renting?", ok, no).showAndWait();
                    if (result.orElse(no) == ok) {
                        try {
                            URL resource = MaintainmanageFormController.class.getResource("/view/maintainmanage_form.fxml");
                            Parent parent = FXMLLoader.load(resource);
                            Scene scene = new Scene(parent);
                            Stage stage = new Stage();
                            stage.setTitle("Property Maintain Form");
                            stage.setScene(scene);
                            stage.show();
                        } catch (IOException ex) {
                            new Alert(Alert.AlertType.ERROR, ex.getMessage()).show();
                        }
                    }
                });

                Delete.setOnAction((e) ->{
                    ButtonType ok = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
                    ButtonType no = new ButtonType("NO", ButtonBar.ButtonData.CANCEL_CLOSE);

                    Optional<ButtonType> result = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to delete this Renting?", ok, no).showAndWait();
                    if (result.orElse(no) == ok) {
                        try {
                            boolean isDeleted = model.deleteRenting(dto.getRent_id());
                            if (isDeleted){
                                new Alert(Alert.AlertType.CONFIRMATION, "Renting has been deleted successfully").show();
                                initialize();
                            }else{
                                new Alert(Alert.AlertType.ERROR, "Failed to delete the Renting").show();
                            }
                        } catch (SQLException | ClassNotFoundException ex) {
                            new Alert(Alert.AlertType.ERROR, ex.getMessage()).show();
                        }
                    }
                });

               obList.add(new RentingTm(
                        dto.getRent_id(),
                        dto.getProperty_id(),
                        dto.getAgent_id(),
                        dto.getDate(),
                        dto.getDuration(),
                        Maintain,
                        Delete
                ));
            }
            tblRent.setItems(obList);
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    private void btnViewMaintainOnAction() {
        rentPane.getChildren().clear();
        try {
            AnchorPane anchorPane = FXMLLoader.load(this.getClass().getResource("/view/maintain_Form.fxml"));
            rentPane.getChildren().add(anchorPane);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
}
