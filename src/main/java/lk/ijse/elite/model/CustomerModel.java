package lk.ijse.elite.model;

import lk.ijse.elite.dto.CustomerDto;
import lk.ijse.elite.util.SQLUtil;
import java.sql.*;
import java.util.*;

public class CustomerModel {
    public static List<CustomerDto> getAllCustomers() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.sql("SELECT * FROM customer");
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

    public static int getCustomerCount() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.sql("SELECT COUNT(*) FROM customer");
        return resultSet.next() ? resultSet.getInt(1) : 0;
    }

    public boolean saveCustomer(CustomerDto dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.sql("INSERT INTO customer VALUES (?,?,?,?,?,?)", dto.getCustomer_id(), dto.getShedule_id(), dto.getName(), dto.getAddress(), dto.getMobile(), dto.getEmail());
    }

    public boolean updateCustomer(CustomerDto dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.sql("UPDATE customer SET Shedule_id=?, Name=?, Address=?, Mobile=?, Email=? WHERE Customer_id=?", dto.getShedule_id(), dto.getName(), dto.getAddress(), dto.getMobile(), dto.getEmail(), dto.getCustomer_id());
    }

    public static CustomerDto searchCustomer(String cid) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.sql("SELECT * FROM customer WHERE Customer_id=?", cid);
        if (resultSet.next()) {
            return new CustomerDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6)
            );
        }
        return null;
    }

    public boolean deleteCustomer(String cid) throws SQLException, ClassNotFoundException {
        return SQLUtil.sql("DELETE FROM customer WHERE Customer_id=?", cid);
    }

    public String generateCustomerId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.sql("SELECT Customer_id FROM customer ORDER BY Customer_id DESC LIMIT 1");
        if (resultSet.next()) {
            String id = resultSet.getString("Customer_id");
            String numericPart = id.replaceAll("\\D", "");
            int newCustomerId = Integer.parseInt(numericPart) + 1;
            return String.format("C%03d", newCustomerId);
        } else {
            return "C001";
        }
    }
}
