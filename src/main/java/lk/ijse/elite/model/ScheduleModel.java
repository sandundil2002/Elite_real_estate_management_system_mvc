package lk.ijse.elite.model;

import lk.ijse.elite.db.DbConnection;
import lk.ijse.elite.dto.PropertyDto;
import lk.ijse.elite.dto.SheduleDto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ScheduleModel {
    public static Connection connection;

    static {
        try {
            connection = DbConnection.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<SheduleDto> loadAllSchedule() throws SQLException {
        String sql = "SELECT * FROM schedule";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<SheduleDto> ScheduleList = new ArrayList<>();

        while (resultSet.next()) {
            ScheduleList.add(new SheduleDto(
                    resultSet.getString(1),
                    resultSet.getString(4),
                    resultSet.getString(3),
                    resultSet.getString(2),
                    resultSet.getString(5)
            ));
        }
        return ScheduleList;
    }

    public static int getScheduleCount() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT COUNT(*) FROM schedule";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 0;
    }

    public boolean saveShedule(SheduleDto dto) throws SQLException {
        String sql = "insert into schedule values (?,?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, dto.getScheduleId());
        statement.setString(2, dto.getAdminId());
        statement.setString(3, dto.getDate());
        statement.setString(4, dto.getTime());
        statement.setString(5, dto.getStatus());

        boolean isSaved = statement.executeUpdate() > 0;
        return isSaved;
    }

    public boolean updateShedule(SheduleDto dto) throws SQLException {
        String sql = "UPDATE schedule SET Admin_id = ?, Date = ?, Time = ?, Status = ? WHERE Shedule_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, dto.getAdminId());
        statement.setString(2, dto.getDate());
        statement.setString(3, dto.getTime());
        statement.setString(4, dto.getStatus());
        statement.setString(5, dto.getScheduleId());

        return statement.executeUpdate() > 0;
    }

    public SheduleDto searchShedule(String id) throws SQLException {
        String sql = "SELECT * FROM schedule WHERE Shedule_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, id);

        ResultSet resultSet = statement.executeQuery();
        SheduleDto dto = null;

        if (resultSet.next()) {
            String shedule_id = resultSet.getString(1);
            String admin_id = resultSet.getString(2);
            String date = resultSet.getString(3);
            String time = resultSet.getString(4);
            String status = resultSet.getString(5);

            dto = new SheduleDto(shedule_id, admin_id, date, time,status);
        }
        return dto;
    }

    public boolean deleteShedule(String id) throws SQLException {
        String sql = "DELETE FROM schedule WHERE Shedule_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, id);
        return statement.executeUpdate() > 0;
    }

    public List<SheduleDto> loadAllShedules() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM schedule";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<SheduleDto> sheduleList = new ArrayList<>();

        while (resultSet.next()) {
            sheduleList.add(new SheduleDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            ));
        }
        return sheduleList;
    }

    public boolean updateSheduleCompleted(String id) throws SQLException {
        String sql = "UPDATE schedule SET Status = ? WHERE Shedule_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, "Completed");
        statement.setString(2, id);

        return statement.executeUpdate() > 0;
    }

    public boolean updateSheduleCansel(String id) throws SQLException {
        String sql = "UPDATE schedule SET Status = ? WHERE Shedule_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, "Canseled");
        statement.setString(2, id);

        return statement.executeUpdate() > 0;
    }
}


