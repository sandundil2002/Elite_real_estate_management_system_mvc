package lk.ijse.elite.model;

import lk.ijse.elite.dto.TodayAppoinmentsDto;
import lk.ijse.elite.util.SQLUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DashboardModel {
    public static int getTotalAppointmentsCount() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.sql("SELECT COUNT(*) FROM schedule");
        return resultSet.next() ? resultSet.getInt(1) : 0;
    }

    public static int getTotalPropertiesCount() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.sql("SELECT COUNT(*) FROM property");
        return resultSet.next() ? resultSet.getInt(1) : 0;
    }

    public List<TodayAppoinmentsDto> loadTodayShedules() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.sql("SELECT schedule.Shedule_id,customer.Name,Schedule.Time,customer.Mobile FROM customer JOIN Schedule ON customer.Shedule_id = Schedule.Shedule_id WHERE Schedule.Date = CURDATE() ORDER BY Schedule.Shedule_id ASC");
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
