package lk.ijse.elite.model;

import lk.ijse.elite.dto.RentingDetailDto;
import lk.ijse.elite.util.SQLUtil;
import java.sql.SQLException;

public class RentingDetailModel {
    public static boolean saveRentingDetail(RentingDetailDto dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.sql("INSERT INTO renting_details VALUES(?,?,?)",dto.getRentId(),dto.getPropertyId(),dto.getDescription());
    }
}
