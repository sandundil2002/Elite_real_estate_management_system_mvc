package lk.ijse.elite.model;

import lk.ijse.elite.db.DbConnection;
import lk.ijse.elite.dto.AgentDto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AgentModel {
    public static Connection connection;
    static {
        try {
            connection = DbConnection.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<AgentDto> loadAllAgents() throws SQLException {
        String sql = "SELECT * FROM agent";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<AgentDto> agentList = new ArrayList<>();

        while (resultSet.next()) {
            var dto = new AgentDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            );
            agentList.add(dto);
        }
        return agentList;
    }

    public static AgentDto searchAgent(String string) throws SQLException {
        String sql = "SELECT * FROM agent WHERE agent_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, string);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            String agent_id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            String mobile = resultSet.getString(4);
            String email = resultSet.getString(5);

            return new AgentDto(agent_id, name, address, mobile, email);
        }
        return new AgentDto();
    }

    public boolean saveAgent(AgentDto dto) throws SQLException {
        String sql = "INSERT INTO agent VALUES (?,?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, dto.getAgent_id());
        statement.setString(2, dto.getName());
        statement.setString(3, dto.getAddress());
        statement.setString(4, dto.getMobile());
        statement.setString(5, dto.getEmail());

        boolean isSaved = statement.executeUpdate() > 0;
        return isSaved;
    }

    public boolean updateAgent(AgentDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE agent SET Name = ?, Address = ?, Mobile = ?, Email = ? WHERE agent_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, dto.getName());
        statement.setString(2, dto.getAddress());
        statement.setString(3, dto.getMobile());
        statement.setString(4, dto.getEmail());
        statement.setString(5, dto.getAgent_id());

        return statement.executeUpdate() > 0;
    }

    public boolean deleteAgent(String agentid) throws SQLException {
        String sql = "DELETE FROM agent WHERE agent_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, agentid);
        return statement.executeUpdate() > 0;
    }
}
