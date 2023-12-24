package lk.ijse.elite.model;

import lk.ijse.elite.dto.SheduleDto;
import lk.ijse.elite.utill.SQLUtill;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ScheduleModel {
    public static List<SheduleDto> loadAllSchedule() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtill.sql("SELECT * FROM schedule");
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

    public static int getScheduleCount() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtill.sql("SELECT COUNT(*) FROM schedule");
        return resultSet.next() ? resultSet.getInt(1) : 0;
    }

    public boolean saveShedule(SheduleDto dto) throws SQLException, ClassNotFoundException {
        return SQLUtill.sql("INSERT INTO schedule VALUES(?,?,?,?,?)",
                dto.getScheduleId(),
                dto.getAdminId(),
                dto.getDate(),
                dto.getTime(),
                dto.getStatus()
        );
    }

    public boolean updateShedule(SheduleDto dto) throws SQLException, ClassNotFoundException {
        return SQLUtill.sql("UPDATE schedule SET Admin_id=?, Date=?, Time=?, Status=? WHERE Shedule_id=?",
                dto.getAdminId(),
                dto.getDate(),
                dto.getTime(),
                dto.getStatus(),
                dto.getScheduleId());
    }

    public SheduleDto searchShedule(String id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtill.sql("SELECT * FROM schedule WHERE Shedule_id=?", id);

        if (resultSet.next()) {
            String shedule_id = resultSet.getString(1);
            String admin_id = resultSet.getString(2);
            String date = resultSet.getString(3);
            String time = resultSet.getString(4);
            String status = resultSet.getString(5);
            return new SheduleDto(shedule_id, admin_id, date, time,status);
        }
        return null;
    }

    public boolean deleteShedule(String id) throws SQLException, ClassNotFoundException {
        return SQLUtill.sql("DELETE FROM schedule WHERE Shedule_id=?", id);
    }

    public List<SheduleDto> loadAllShedules() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtill.sql("SELECT * FROM schedule");
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

    public boolean updateSheduleCompleted(String id) throws SQLException, ClassNotFoundException {
        return SQLUtill.sql("UPDATE schedule SET Status = ? WHERE Shedule_id = ?", "Completed", id);
    }

    public boolean updateSheduleCansel(String id) throws SQLException, ClassNotFoundException {
        return SQLUtill.sql("UPDATE schedule SET Status = ? WHERE Shedule_id = ?", "Cansel", id);
    }
}


