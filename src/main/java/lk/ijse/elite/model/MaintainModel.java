package lk.ijse.elite.model;

import lk.ijse.elite.db.DbConnection;
import lk.ijse.elite.dto.MaintainDto;
import lk.ijse.elite.dto.RentingDto;
import lk.ijse.elite.utill.SQLUtill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaintainModel {
    public boolean addMaintain(MaintainDto dto) throws SQLException, ClassNotFoundException {
       return SQLUtill.sql("INSERT INTO maintain VALUES (?,?,?,?)",dto.getMaintain_id(),dto.getRent_id(),dto.getDate(),dto.getStatus());
    }

    public List<MaintainDto> loadAllMaintenance() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtill.sql("SELECT * FROM maintain");
        List<MaintainDto> maintainList = new ArrayList<>();

        while (resultSet.next()) {
            maintainList.add(new MaintainDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            ));
        }
        return maintainList;
    }

    public boolean updateMaintainComplete(String maintainId) throws SQLException, ClassNotFoundException {
        return SQLUtill.sql("UPDATE maintain SET Status = ? WHERE Maintain_id = ?","Completed",maintainId);
    }

    public boolean updateMaintainCansel(String maintainId) throws SQLException, ClassNotFoundException {
        return SQLUtill.sql("UPDATE maintain SET Status = ? WHERE Maintain_id = ?","Canceled",maintainId);
    }
}
