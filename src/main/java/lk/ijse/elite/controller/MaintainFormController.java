package lk.ijse.elite.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.elite.db.DbConnection;
import lk.ijse.elite.dto.MaintainDto;
import lk.ijse.elite.dto.tm.MaintainTm;
import lk.ijse.elite.model.MaintainModel;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class MaintainFormController {
    public AnchorPane maintainPane;
    public TableView tblMaintain;
    public TableColumn colMaintainid;
    public TableColumn colRentId;
    public TableColumn colDate;
    public TableColumn colStatus;
    public TableColumn colFinished;
    public TableColumn colCansel;

    public void initialize() {
        cellValueFactory();
        loadAllMaintain();
    }

    private void cellValueFactory() {
        colMaintainid.setCellValueFactory(new PropertyValueFactory<>("maintain_id"));
        colRentId.setCellValueFactory(new PropertyValueFactory<>("rent_id"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colFinished.setCellValueFactory(new PropertyValueFactory<>("btnFinished"));
        colCansel.setCellValueFactory(new PropertyValueFactory<>("btnCansel"));
    }
    private void loadAllMaintain() {
        var model = new MaintainModel();

        ObservableList<MaintainTm> obList = FXCollections.observableArrayList();

        try {
            List<MaintainDto> dtoList = model.loadAllMaintenance();

            for (MaintainDto dto : dtoList) {
                Button Finished = new Button("Complete");
                Button Canseled = new Button("Cansel");
                Finished.setCursor(Cursor.HAND);
                Canseled.setCursor(Cursor.HAND);

                Finished.setOnAction((e) -> {
                    ButtonType ok = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
                    ButtonType no = new ButtonType("NO", ButtonBar.ButtonData.CANCEL_CLOSE);

                    Optional<ButtonType> result = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to Completed this Schedule?", ok, no).showAndWait();
                    if (result.orElse(no) == ok) {
                        try {
                            boolean isUpdated = model.updateMaintainComplete(dto.getMaintain_id());
                            if (isUpdated) {
                                new Alert(Alert.AlertType.CONFIRMATION, "Maintenance Finished", ButtonType.OK).show();
                                loadAllMaintain();
                            } else {
                                new Alert(Alert.AlertType.WARNING, "Try Again", ButtonType.OK).show();
                            }
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    }
                });

                Canseled.setOnAction((e) -> {
                    ButtonType ok = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
                    ButtonType no = new ButtonType("NO", ButtonBar.ButtonData.CANCEL_CLOSE);

                    Optional<ButtonType> result = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to Cansel this Schedule?", ok, no).showAndWait();
                    if (result.orElse(no) == ok) {
                        try {
                            boolean isUpdated = model.updateMaintainCansel(dto.getMaintain_id());
                            if (isUpdated) {
                                new Alert(Alert.AlertType.CONFIRMATION, "Maintenance Cansel", ButtonType.OK).show();
                                loadAllMaintain();
                            } else {
                                new Alert(Alert.AlertType.WARNING, "Try Again", ButtonType.OK).show();
                            }
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    }
                });
                obList.add(new MaintainTm(
                        dto.getMaintain_id(),
                        dto.getRent_id(),
                        dto.getDate(),
                        dto.getStatus(),
                        Finished,
                        Canseled
                ));
                }
            tblMaintain.setItems(obList);
            } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }

    public void btnPrintOnAction(ActionEvent actionEvent) throws JRException, SQLException {
        InputStream resourceAsStream = getClass().getResourceAsStream("/reports/Maintain_details.jrxml");
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
}

