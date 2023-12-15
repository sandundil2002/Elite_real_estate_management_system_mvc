package lk.ijse.elite.model;

import lk.ijse.elite.db.DbConnection;
import lk.ijse.elite.dto.EmployeeDto;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeModel {
    public static Connection connection;
    static {
        try {
            connection = DbConnection.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<EmployeeDto> loadAllEmployees() throws SQLException {
        String sql = "SELECT * FROM employee";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

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

    public static int getEmployeeCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM employee";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 0;
    }

    public boolean saveEmployee(EmployeeDto dto) throws SQLException {
        String sql = "INSERT INTO employee VALUES (?,?,?,?,?,?,?)";
        var pstm = connection.prepareStatement(sql);

        pstm.setString(1,dto.getEmpid());
        pstm.setString(2,dto.getAdid());
        pstm.setString(3,dto.getName());
        pstm.setString(4,dto.getAddress());
        pstm.setString(5,dto.getMobile());
        pstm.setString(6,dto.getPosition());
        pstm.setString(7,dto.getBasicSalary());

        return pstm.executeUpdate() > 0;
    }

    public boolean updateEmployee(EmployeeDto dto) throws SQLException {
        String sql = "UPDATE employee SET Admin_id=?, Name=?, Address=?, Mobile=?, Position=?, Basic_salary=? WHERE Employee_id=?";
        var pstm = connection.prepareStatement(sql);

        pstm.setString(1,dto.getAdid());
        pstm.setString(2,dto.getName());
        pstm.setString(3,dto.getAddress());
        pstm.setString(4,dto.getMobile());
        pstm.setString(5,dto.getPosition());
        pstm.setString(6,dto.getBasicSalary());
        pstm.setString(7,dto.getEmpid());

        return pstm.executeUpdate() > 0;
    }

    public EmployeeDto searchEmployee(String eid) throws SQLException {
        String sql = "SELECT * FROM employee WHERE Employee_id=?";
        var pstm = connection.prepareStatement(sql);

        pstm.setString(1,eid);

        ResultSet resultSet = pstm.executeQuery();
        EmployeeDto dto = null;

        if (resultSet.next()) {
            dto = new EmployeeDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7)
            );
        }
        return dto;
    }

    public static EmployeeDto searchEmployeePosition(String position) throws SQLException {
        String sql = "SELECT * FROM employee WHERE Position=?";
        var pstm = connection.prepareStatement(sql);

        pstm.setString(1,position);

        ResultSet resultSet = pstm.executeQuery();
        EmployeeDto dto = null;

        if (resultSet.next()) {
            dto = new EmployeeDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7)
            );
        }
        return dto;
    }

    public boolean deleteEmployee(String eid) throws SQLException {
        String sql = "DELETE FROM employee WHERE Employee_id=?";
        var pstm = connection.prepareStatement(sql);

        pstm.setString(1,eid);

        return pstm.executeUpdate() > 0;
    }
}
