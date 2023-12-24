package lk.ijse.elite.model;

import lk.ijse.elite.dto.RentingDetailDto;
import lk.ijse.elite.utill.SQLUtill;
import java.sql.SQLException;

public class RentingDetailModel {
    public static boolean saveRentingDetail(RentingDetailDto dto) throws SQLException, ClassNotFoundException {
        return SQLUtill.sql("INSERT INTO renting_details VALUES(?,?,?)",dto.getRentId(),dto.getPropertyId(),dto.getDescription());
    }
}
