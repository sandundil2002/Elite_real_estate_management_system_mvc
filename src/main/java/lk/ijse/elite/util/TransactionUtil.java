package lk.ijse.elite.util;

import javafx.scene.control.Alert;
import lk.ijse.elite.db.DbConnection;
import java.sql.*;

public class TransactionUtil {
    public static Connection connection;

    static {
        try {
            connection = DbConnection.getInstance().getConnection();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public TransactionUtil() throws SQLException, ClassNotFoundException {
    }

    public static void startTransaction() throws SQLException {
        connection.setAutoCommit(false);
    }

    public static void endTransaction() throws SQLException {
        connection.commit();
        connection.setAutoCommit(true);
    }

    public static void rollBack() throws SQLException {
        connection.rollback();
        connection.setAutoCommit(true);
    }
}
