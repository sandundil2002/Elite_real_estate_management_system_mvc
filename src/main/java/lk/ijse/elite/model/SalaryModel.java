package lk.ijse.elite.model;

import lk.ijse.elite.db.DbConnection;
import lk.ijse.elite.dto.SalaryDto;
import lk.ijse.elite.utill.SQLUtill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SalaryModel {
    public boolean saveSalary(SalaryDto dto) throws SQLException, ClassNotFoundException {
        return SQLUtill.sql("insert into salary values(?,?,?,?)",dto.getSalary_id(),dto.getEmployee_id(),dto.getDate(),dto.getAmount());
    }
}
