package lk.ijse.elite.model;

import lk.ijse.elite.dto.SalaryDto;
import lk.ijse.elite.util.SQLUtil;

import java.sql.SQLException;

public class SalaryModel {
    public boolean saveSalary(SalaryDto dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.sql("insert into salary values(?,?,?,?)",dto.getSalary_id(),dto.getEmployee_id(),dto.getDate(),dto.getAmount());
    }
}
