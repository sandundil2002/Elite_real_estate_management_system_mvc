package lk.ijse.elite.model;

import lk.ijse.elite.dto.AdminDto;
import lk.ijse.elite.utill.SQLUtill;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class AdminModel {
    public static List<AdminDto> loadAllAdmin() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtill.sql("SELECT * FROM admin");
        List<AdminDto> adminList = new ArrayList<>();

        while (resultSet.next()) {
            adminList.add(new AdminDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(5),
                    resultSet.getString(4),
                    resultSet.getString(3),
                    resultSet.getString(6)
            ));
        }
        return adminList;
    }

    public static void searchAdmin(String id) throws SQLException, ClassNotFoundException {
        SQLUtill.sql("SELECT * FROM admin WHERE Admin_id=?",id);
    }

    public boolean searchAdminPassword(String string) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtill.sql("SELECT * FROM admin WHERE password=?", string);
        return resultSet.next();
    }

    public boolean searchAdminUserId(String string) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtill.sql("SELECT * FROM admin WHERE Admin_id=?", string);
        return resultSet.next();
    }

    public boolean registerAdmin(AdminDto dto) throws SQLException, ClassNotFoundException {
        return SQLUtill.sql("INSERT INTO admin VALUES (?,?,?,?,?,?)",dto.getAdmin_id(),dto.getName(),dto.getAddress(),dto.getMobile(),dto.getPassword(),dto.getEmail());
    }
}

