package lk.ijse.elite.model;

import lk.ijse.elite.dto.EmployeeDto;
import lk.ijse.elite.util.SQLUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeModel {
    public static List<EmployeeDto> loadAllEmployees() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.sql("SELECT * FROM employee");
        List<EmployeeDto> employeeList = new ArrayList<>();

        while (resultSet.next()) {
            employeeList.add(new EmployeeDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7)
            ));
        }
        return employeeList;
    }

    public static int getEmployeeCount() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.sql("SELECT COUNT(*) FROM employee");
        return resultSet.next() ? resultSet.getInt(1) : 0;
    }

    public boolean saveEmployee(EmployeeDto dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.sql("INSERT INTO employee VALUES (?,?,?,?,?,?,?)",
                dto.getEmpid(),dto.getAdid(),dto.getName(),dto.getAddress(),dto.getMobile(),dto.getPosition(),dto.getBasicSalary());
    }

    public boolean updateEmployee(EmployeeDto dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.sql("UPDATE employee SET Admin_id=?,Name=?,Address=?,Mobile=?,Position=?,Basic_salary=? WHERE Employee_id=?",
                dto.getAdid(),dto.getName(),dto.getAddress(),dto.getMobile(),dto.getPosition(),dto.getBasicSalary(),dto.getEmpid());
    }

    public EmployeeDto searchEmployee(String eid) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.sql("SELECT * FROM employee WHERE Employee_id=?", eid);

        if (resultSet.next()) {
            return new EmployeeDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7)
            );
        }
        return null;
    }

    public static EmployeeDto searchEmployeePosition(String position) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.sql("SELECT * FROM employee WHERE Position=?", position);

        if (resultSet.next()) {
            return new EmployeeDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7)
            );
        }
        return null;
    }

    public boolean deleteEmployee(String eid) throws SQLException, ClassNotFoundException {
        return SQLUtil.sql("DELETE FROM employee WHERE Employee_id=?", eid);
    }
}
