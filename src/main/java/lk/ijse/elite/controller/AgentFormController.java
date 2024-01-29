package lk.ijse.elite.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import lk.ijse.elite.dto.AgentDto;
import lk.ijse.elite.dto.tm.AgentTm;
import lk.ijse.elite.model.AgentModel;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;

public class AgentFormController {

    @FXML
    private TableColumn colAgentid;

    @FXML
    private TableColumn colName;

    @FXML
    private TableColumn colAddress;

    @FXML
    private TableColumn colMobile;

    @FXML
    private TableColumn colEmail;

    @FXML
    private TableView tblAgent;

    public void initialize(){
        setCellValueFactory();
        loadAllAgents();
    }

    @FXML
    private void btnAgentmanageOnAction() {
        try {
            URL resource = AgentFormController.class.getResource("/view/agentsManageForm.fxml");
            Parent parent = FXMLLoader.load(resource);
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Agent Manage Form");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void setCellValueFactory() {
        colAgentid.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("agent_id"));
        colName.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("address"));
        colMobile.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("mobile"));
        colEmail.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("email"));
    }

    private void loadAllAgents(){
        var model = new AgentModel();

        ObservableList<AgentTm> obList = FXCollections.observableArrayList();

        try{
            List<AgentDto> dtoList = model.loadAllAgents();

            for(AgentDto dto : dtoList){
                obList.add(new AgentTm(
                        dto.getAgent_id(),
                        dto.getName(),
                        dto.getAddress(),
                        dto.getMobile(),
                        dto.getEmail()
                ));
            }
            tblAgent.setItems(obList);
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    @FXML
    private void btnBuyOnAction() {
        try {
            URL resource = PropertymanageFormcCntroller.class.getResource("/view/propertymanage_form.fxml");
            Parent parent = FXMLLoader.load(resource);
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Property Buy Form");
            stage.setAlwaysOnTop(true);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    private void btnRefeshOnAction() {
        initialize();
    }
}
