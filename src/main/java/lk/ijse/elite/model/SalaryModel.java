package lk.ijse.elite.model;

import lk.ijse.elite.dto.SalaryDto;
import lk.ijse.elite.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SalaryModel {
    public boolean saveSalary(SalaryDto dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.sql("insert into salary values(?,?,?,?)",dto.getSalary_id(),dto.getEmployee_id(),dto.getDate(),dto.getAmount());
    }

    public String autoGenarateSalaryId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.sql("select salary_id from salary order by salary_id desc limit 1");
        if (resultSet.next()){
            String id = resultSet.getString("salary_id");
            String numericPart = id.replaceAll("\\D+","");
            int newSalaryId = Integer.parseInt(numericPart) + 1;
            return String.format("Sal%03d",newSalaryId);
        } else {
            return "Sal001";
        }
    }
}
