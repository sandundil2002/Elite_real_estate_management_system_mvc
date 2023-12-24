package lk.ijse.elite.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.elite.db.DbConnection;
import lk.ijse.elite.dto.TodayAppoinmentsDto;
import lk.ijse.elite.dto.tm.TodayAppointmentsTm;
import lk.ijse.elite.model.DashboardModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MainDashboardFormController {
    public Label lblTime;
    public Label lblDate;
    public TableColumn colSheduleid;
    public TableColumn colCusname;
    public TableColumn colTime;
    public TableColumn colMobile;
    public TableView tblTodayAppointment;
    public Label lblTotalPro;
    public Label lblTotalApp;
    public BarChart<String, Number> barChart;

    public void initialize() {
        loadTodayShedules();
        setCellValueFactory();
        LocalTime time = LocalTime.now();
        LocalDate date = LocalDate.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("EEEE(dd)-MMM-yyyy");
        String formattedTime = time.format(formatter);
        String formattedDate = date.format(formatter1);

        lblTime.setText(formattedTime);
        lblDate.setText(formattedDate);
        lblTotalApp.setText(String.valueOf(getTotalAppointmentsCount()));
        lblTotalPro.setText(String.valueOf(getTotalPropertiesCount()));

        try {
            populateChart(barChart);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void populateChart(BarChart<String, Number> barChart) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT Type, COUNT(*) as Count FROM Property GROUP BY Type";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        while (resultSet.next()) {
            String propertyType = resultSet.getString("Type");
            int count = resultSet.getInt("Count");
            series.getData().add(new XYChart.Data<>(propertyType, count));
        }
        barChart.getData().add(series);
        for(Node n:barChart.lookupAll(".default-color0.chart-bar")) {
            n.setStyle("-fx-bar-fill: #E0A383;");
        }
    }


    private int getTotalPropertiesCount() {
        try {
            return DashboardModel.getTotalPropertiesCount();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        return 0;
    }

    private int getTotalAppointmentsCount() {
        try {
            return DashboardModel.getTotalAppointmentsCount();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        return 0;
    }

    private void setCellValueFactory(){
        colSheduleid.setCellValueFactory(new PropertyValueFactory<>("shedule_id"));
        colCusname.setCellValueFactory(new PropertyValueFactory<>("name"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colMobile.setCellValueFactory(new PropertyValueFactory<>("mobile"));
    }

    private void loadTodayShedules(){
        var model = new DashboardModel();

        ObservableList<TodayAppointmentsTm> obList = FXCollections.observableArrayList();

        try{
            List<TodayAppoinmentsDto> dtoList = model.loadTodayShedules();

            for (TodayAppoinmentsDto dto : dtoList) {
                obList.add(new TodayAppointmentsTm(
                        dto.getShedule_id(),
                        dto.getName(),
                        dto.getTime(),
                        dto.getMobile()
                ));
            }
            tblTodayAppointment.setItems(obList);
    } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
