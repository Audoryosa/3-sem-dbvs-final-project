package com.auku.agentura.dao.impl;

import com.auku.agentura.dao.DetailedOwnerDao;
import com.auku.agentura.entity.House;
import com.auku.agentura.entity.Owner;
import com.auku.agentura.utils.PostgreSqlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class DetailedOwnerDaoImpl implements DetailedOwnerDao {

    private Logger logger = LoggerFactory.getLogger(DetailedOwnerDaoImpl.class);

    private static final String OWNERS_WITH_NO_HOUSES_SQL = "SELECT savininkas.id, savininkas.vardas, savininkas.pavarde, savininkas.adresas, savininkas.seimos_dydis, savininkas.pajamos\n" +
            "\tFROM namas RIGHT OUTER JOIN savininkas ON savininko_id = savininkas.id WHERE savininko_id is null";

    @Override
    public List<Owner> getOwnersWithNoHouses() {
        PostgreSqlUtils.loadDriver();
        Connection con = PostgreSqlUtils.getConnection();
        if( null != con ) {
            var ownerList = getOwners(con);
            logger.info("List retrieved");
            return ownerList;
        }
        return Collections.emptyList();
    }

    @Override
    public void sellHouse(House house, int id) {
        PostgreSqlUtils.loadDriver();
        Connection connection = PostgreSqlUtils.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement("BEGIN TRANSACTION; UPDATE namas " +
                    "SET savininko_id = " + id + ", " +
                    "agento_id = NULL WHERE namas.adresas = '" + house.getAddress() + "'");
            PreparedStatement commit = connection.prepareStatement("COMMIT");
            statement.executeUpdate();
            commit.execute();
//            connection.commit(); arba sitas

        } catch (SQLException e) {
            try {
                logger.info("Rolling back transaction.");
                // connection.rollback(); arba sitas
                PreparedStatement error = connection.prepareStatement("ROLLBACK");
                error.execute();
            } catch (SQLException ex) {
                logger.warn("Could not rollback transaction: ");
                ex.printStackTrace();
            }
            logger.error("Wild sql error:");
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("Could not close connection");
                e.printStackTrace();
            }
        }
    }

    @Override
    public void sellOwnersHouse(House house, int id) {
        PostgreSqlUtils.loadDriver();
        Connection connection = PostgreSqlUtils.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE namas " +
                    "SET savininko_id = NULL, " +
                    "agento_id = " + id + " WHERE namas.adresas = '" + house.getAddress() + "'");
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Wild sql error: ");
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("Could not close connection");
                e.printStackTrace();
            }
        }
    }

    private List<Owner> getOwners(Connection postGresConn)
    {
        if(postGresConn == null) {
            logger.error("We should never get here.");
            return Collections.emptyList();
        }

        PreparedStatement stmt = null ;
        ResultSet rs = null ;
        List<Owner> list = new ArrayList<>();
        try {
            stmt = postGresConn.prepareStatement(OWNERS_WITH_NO_HOUSES_SQL);
            rs = stmt.executeQuery();

            createOwners(rs, list);
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

    private void createOwners(ResultSet rs, List<Owner> list) throws SQLException {
        while (rs.next()) {
            Owner owner = new Owner();
            owner.setId(rs.getInt("id"));
            owner.setName(rs.getString("vardas"));
            owner.setSurname(rs.getString("pavarde"));
            owner.setAddress(rs.getString("adresas"));
            owner.setFamilySize(rs.getInt("seimos_dydis"));
            owner.setIncome(rs.getDouble("pajamos"));

            list.add(owner);
        }
    }

}
