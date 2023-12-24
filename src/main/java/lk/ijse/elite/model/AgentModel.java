package lk.ijse.elite.model;

import lk.ijse.elite.dto.AgentDto;
import lk.ijse.elite.utill.SQLUtill;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AgentModel {
    public static List<AgentDto> loadAllAgents() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtill.sql("SELECT * FROM agent");
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

    public static AgentDto searchAgent(String id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtill.sql("SELECT * FROM agent WHERE agent_id = ?", id);

        if (resultSet.next()) {
            return new AgentDto(resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5));
        }
        return null;
    }

    public boolean saveAgent(AgentDto dto) throws SQLException, ClassNotFoundException {
        return SQLUtill.sql("INSERT INTO agent VALUES (?,?,?,?,?)",dto.getAgent_id(),dto.getName(),dto.getAddress(),dto.getMobile(),dto.getEmail());
    }

    public boolean updateAgent(AgentDto dto) throws SQLException, ClassNotFoundException {
        return SQLUtill.sql("UPDATE agent SET Name = ?, Address = ?, Mobile = ?, Email = ? WHERE agent_id = ?",dto.getName(),dto.getAddress(),dto.getMobile(),dto.getEmail(),dto.getAgent_id());
    }

    public boolean deleteAgent(String agentid) throws SQLException, ClassNotFoundException {
        return SQLUtill.sql("DELETE FROM agent WHERE agent_id = ?", agentid);
    }
}
