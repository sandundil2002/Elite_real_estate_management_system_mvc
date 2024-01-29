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
import javafx.stage.Stage;
import lk.ijse.elite.db.DbConnection;
import lk.ijse.elite.dto.PropertyDto;
import lk.ijse.elite.dto.tm.PropertyTm;
import lk.ijse.elite.model.PropertyModel;
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

public class PropertyFormController {

    @FXML
    private TableView<PropertyTm> tblproperty;

    @FXML
    private TableColumn colPropertyid;

    @FXML
    private TableColumn colAgentid;

    @FXML
    private TableColumn colType;

    @FXML
    private TableColumn colAddress;

    @FXML
    private TableColumn colPrice;

    @FXML
    private TableColumn colStatus;

    @FXML
    private TableColumn colRemove;

    @FXML
    private TableColumn colPerches;

    public void initialize() {
        setCellValueFactory();
        loadAllProperty();
    }

    private void setCellValueFactory() {
        colPropertyid.setCellValueFactory(new PropertyValueFactory<>("propertyId"));
        colAgentid.setCellValueFactory(new PropertyValueFactory<>("agentId"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colPerches.setCellValueFactory(new PropertyValueFactory<>("perches"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colRemove.setCellValueFactory(new PropertyValueFactory<>("btnRemove"));
    }

    @FXML
    private void btnPropertymanageOnAction(){
        try {
            URL resource = PropertymanageFormcCntroller.class.getResource("/view/propertymanage_form.fxml");
            Parent parent = FXMLLoader.load(resource);
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Property Buy Form");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    private void btnBuypropertyOnAction() {
        try {
            URL resource = PlaceorderFromController.class.getResource("/view/placeorder_from.fxml");
            Parent parent = FXMLLoader.load(resource);
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Property Buy Form");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void loadAllProperty() {
        var model = new PropertyModel();

        ObservableList<PropertyTm> obList = FXCollections.observableArrayList();

        try {
            List<PropertyDto> dtoList = model.loadAllProperty();

            for(PropertyDto dto : dtoList) {
                Button Remove = new Button("Remove");
                Remove.setCursor(Cursor.HAND);

                Remove.setOnAction((e) ->{
                    ButtonType ok = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
                    ButtonType no = new ButtonType("NO", ButtonBar.ButtonData.CANCEL_CLOSE);

                    Optional<ButtonType> result = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to remove this Property?", ok, no).showAndWait();
                    if (result.orElse(no) == ok) {
                        try {
                            boolean isDeleted = model.deleteProperty(dto.getPropertyId());
                            if (isDeleted) {
                                new Alert(Alert.AlertType.CONFIRMATION, "Deleted Successfully !", ButtonType.OK).show();
                                initialize();
                            } else {
                                new Alert(Alert.AlertType.WARNING, "Try Again !", ButtonType.OK).show();
                            }
                        } catch (SQLException | ClassNotFoundException ex) {
                            new Alert(Alert.AlertType.ERROR, ex.getMessage()).show();
                        }
                    }
                });
                obList.add(new PropertyTm(
                        dto.getPropertyId(),
                        dto.getAgentId(),
                        dto.getType(),
                        dto.getAddress(),
                        dto.getPrice(),
                        dto.getStatus(),
                        dto.getPerches(),
                        Remove
                ));
            }
            tblproperty.setItems(obList);
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    private void btnPrintOnAction(){
        try {
            InputStream resourceAsStream = getClass().getResourceAsStream("/reports/property.jrxml");
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

    @FXML
    private void btnRefeshOnAction() {
        initialize();
    }

    @FXML
    private void btnRentpropertyOnAction() {
        try {
            URL resource = RentPropertyFormController.class.getResource("/view/rentProperty_form.fxml");
            Parent parent = FXMLLoader.load(resource);
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Property Rent Form");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
}

