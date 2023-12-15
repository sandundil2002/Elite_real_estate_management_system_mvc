package lk.ijse.elite.model;

import javafx.scene.chart.XYChart;
import lk.ijse.elite.db.DbConnection;
import lk.ijse.elite.dto.TodayAppoinmentsDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DashboardModel {
    public static Connection connection;

    static {
        try {
            connection = DbConnection.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int getTotalAppointmentsCount()  {
        try {
            String sql = "SELECT COUNT(*) FROM schedule";
            ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int getTotalPropertiesCount() {
        try {
            String sql = "SELECT COUNT(*) FROM property";
            ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<TodayAppoinmentsDto> loadTodayShedules() throws SQLException {
        String sql = "SELECT schedule.Shedule_id,customer.Name,Schedule.Time,customer.Mobile FROM customer JOIN Schedule ON customer.Shedule_id = Schedule.Shedule_id WHERE Schedule.Date = CURDATE() ORDER BY Schedule.Shedule_id ASC";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<TodayAppoinmentsDto> TodayAppoinmentsList = new ArrayList<>();

        while (resultSet.next()) {
            TodayAppoinmentsList.add(new TodayAppoinmentsDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            ));
        }
        return TodayAppoinmentsList;
    }
}
