package lk.ijse.elite.model;

import lk.ijse.elite.db.DbConnection;
import lk.ijse.elite.dto.CustomerDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerModel {
    public static Connection connection;

    static {
        try {
            connection = DbConnection.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<CustomerDto> getAllCustomers() throws SQLException {
        String sql = "SELECT * FROM customer";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<CustomerDto> customerList = new ArrayList<>();

        while (resultSet.next()) {
            customerList.add(new CustomerDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6)
            ));
        }
        return customerList;
    }

    public static int getCustomerCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM customer";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 0;
    }

    public boolean saveCustomer(CustomerDto dto) throws SQLException {
        String sql = "INSERT INTO customer VALUES (?,?,?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getCustomer_id());
        pstm.setString(2, dto.getShedule_id());
        pstm.setString(3, dto.getName());
        pstm.setString(4, dto.getAddress());
        pstm.setString(5, dto.getMobile());
        pstm.setString(6, dto.getEmail());

        return pstm.executeUpdate() > 0;
    }

    public boolean updateCustomer(CustomerDto dto) throws SQLException {
        String sql = "UPDATE customer SET Shedule_id=?,Name=?, Address=?, Mobile=?, Email=? WHERE Customer_id=?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getCustomer_id());
        pstm.setString(2, dto.getName());
        pstm.setString(3, dto.getAddress());
        pstm.setString(4, dto.getMobile());
        pstm.setString(5, dto.getEmail());
        pstm.setString(6, dto.getCustomer_id());

        return pstm.executeUpdate() > 0;
    }

    public static CustomerDto searchCustomer(String cid) throws SQLException {
        String sql = "SELECT * FROM customer WHERE Customer_id=?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, cid);

        ResultSet resultSet = pstm.executeQuery();
        CustomerDto dto = null;
        if (resultSet.next()) {
            dto = new CustomerDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6)
            );
        }
        return dto;
    }

    public boolean deleteCustomer(String cid) throws SQLException {
        String sql = "DELETE FROM customer WHERE Customer_id=?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, cid);

        return pstm.executeUpdate() > 0;
    }
}
