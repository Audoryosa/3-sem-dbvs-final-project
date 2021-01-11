package com.auku.agentura.dao.impl;

import com.auku.agentura.dao.Dao;
import com.auku.agentura.utils.PostgreSqlUtils;
import com.auku.agentura.entity.Agent;
import com.auku.agentura.entity.ResponseWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class AgentDaoImpl implements Dao<Agent> {

    private Logger logger = LoggerFactory.getLogger(AgentDaoImpl.class);

    @Override
    public List<Agent> get() {
        PostgreSqlUtils.loadDriver();
        Connection con = PostgreSqlUtils.getConnection();
        if( null != con ) {
            var agents = getAgents(con);
            logger.info("List retrieved");
            return agents;
        }
        return Collections.emptyList();
    }

    @Override
    public Agent get(int id) {
        return null;
    }

    @Override
    public Agent get(String param) {
        return null;
    }

    @Override
    public List<Agent> search(String searchInput) {
        return null;
    }

    @Override
    public ResponseWrapper save(Agent saveObj) {
        return null;
    }

    @Override
    public void update(Agent newObj, int id) {

    }

    @Override
    public void delete(int id) {

    }

    private List<Agent> getAgents(Connection postGresConn)
    {
        if(postGresConn == null) {
            logger.error("We should never get here.");
            return Collections.emptyList();
        }

        Statement stmt = null ;
        ResultSet rs = null ;
        List<Agent> list = new ArrayList<>();
        try {
            stmt = postGresConn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM agentas");

            while (rs.next()) {
                Agent agent = new Agent();
                agent.setId(rs.getInt("id"));
                agent.setName(rs.getString("vardas"));
                agent.setSurname(rs.getString("pavarde"));
                agent.setAgency(rs.getString("imone"));
                agent.setDateOfBirth(rs.getDate("gimimo_data"));
                agent.setExperience(rs.getInt("stazas"));

                list.add(agent);
            }
        }
        catch (SQLException e) {
            logger.error("SQL Error!");
            e.printStackTrace();
        }
        finally {
            try {
                if(null != rs)
                    rs.close() ;
                if(null != stmt)
                    stmt.close() ;
            }
            catch (SQLException exp) {
                logger.error("Unexpected SQL Error!");
                exp.printStackTrace();
            }
        }
        return list;
    }
}
