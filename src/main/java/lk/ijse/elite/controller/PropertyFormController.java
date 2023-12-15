package lk.ijse.elite.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
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
    public TableView<PropertyTm> tblproperty;

    public AnchorPane property;
    public TableColumn colPropertyid;
    public TableColumn colAgentid;
    public TableColumn colType;
    public TableColumn colAddress;
    public TableColumn colPrice;
    public TableColumn colStatus;
    public TableColumn colRemove;
    public TableColumn colPerches;

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

    public void btnPropertymanageOnAction(ActionEvent actionEvent) throws IOException {
        try {
            URL resource = PropertymanageFormcCntroller.class.getResource("/view/propertymanage_form.fxml");
            Parent parent = FXMLLoader.load(resource);
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Property Buy Form");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void btnBuypropertyOnAction(ActionEvent event) {
        try {
            URL resource = PlaceorderFromController.class.getResource("/view/placeorder_from.fxml");
            Parent parent = FXMLLoader.load(resource);
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Property Buy Form");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
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
                        } catch (SQLException ex) {
                            ex.printStackTrace();
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnPrintOnAction() throws JRException, SQLException {
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
    }

    public void btnRefeshOnAction(ActionEvent actionEvent) {
        initialize();
    }

    public void btnRentpropertyOnAction(ActionEvent actionEvent) {
        try {
            URL resource = RentPropertyFormController.class.getResource("/view/rentProperty_form.fxml");
            Parent parent = FXMLLoader.load(resource);
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Property Rent Form");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

