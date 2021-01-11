package com.auku.agentura.dao.impl;

import com.auku.agentura.dao.Dao;
import com.auku.agentura.entity.House;
import com.auku.agentura.entity.ResponseWrapper;
import com.auku.agentura.utils.PostgreSqlUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class HouseDaoImpl implements Dao<House> {
	
	private Logger logger = LoggerFactory.getLogger(HouseDaoImpl.class);
	
    @Override
    public List<House> get() {
        PostgreSqlUtils.loadDriver();
        Connection con = PostgreSqlUtils.getConnection();
        List<House> houseList = null;
        if( null != con ) {
            houseList = getHouses(con);
            logger.info("List retrieved");
        }
        if( null != con ) {
            try {
                con.close() ;
            }
            catch (SQLException exp) {
                logger.error("Can not close connection!");
                exp.printStackTrace();
            }
        }

        return houseList;
    }

    @Override
    public House get(int id) {
        return null;
    }

    @Override
    public House get(String param) {
        PostgreSqlUtils.loadDriver();
        Connection con = PostgreSqlUtils.getConnection();
        House house = null;
        if( null != con ) {
            house = getHouse(con, param);
            logger.info("List retrieved");
        }
        if( null != con ) {
            try {
                con.close() ;
            }
            catch (SQLException exp) {
                logger.error("Can not close connection!");
                exp.printStackTrace();
            }
        }

        return house;
    }

    @Override
    public List<House> search(String searchInput) {
    	PostgreSqlUtils.loadDriver();
        Connection connection = PostgreSqlUtils.getConnection();
        List<House> foundHouses = null;
        if (connection != null) {
            foundHouses = searchHouses(connection, searchInput);
            logger.info("Found some houses");
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("Cannot close connection");
                e.printStackTrace();
            }
        }
        return foundHouses;
    }

	@Override
    public ResponseWrapper save(House saveObj) {
        PostgreSqlUtils.loadDriver();
        Connection connection = PostgreSqlUtils.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO namas(adresas, plotas, kaina, statybos_metai) " +
                    "VALUES ('" + saveObj.getAddress() + "', " + saveObj.getSize() + ", " + saveObj.getPrice() + ", " + saveObj.getBuildYear() +")");
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Could not create house: " + e.getMessage());

        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("Could not close connection");
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public void update(House newObj, int id) {

    }

    @Override
    public void delete(int id) {

    }

    private List<House> searchHouses(Connection connection, String searchInput) {
    	PreparedStatement stmt = null ;
        ResultSet rs = null ;
        List<House> list = new ArrayList<>();
        try {
            stmt = connection.prepareStatement("SELECT * FROM Namas WHERE LOWER(adresas) LIKE LOWER('%" + searchInput + "%')");
            rs = stmt.executeQuery();

            while (rs.next()) {
                House house = new House();
                house.setAddress(rs.getString("adresas"));
                house.setAgentId(rs.getInt("agento_id"));
                house.setOwnerId(rs.getInt("savininko_id"));
                house.setSize(rs.getDouble("plotas"));
                house.setPrice(rs.getDouble("kaina"));
                house.setBuildYear(rs.getInt("statybos_metai"));

                list.add(house);
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

    private House getHouse(Connection postGresConn, String param)
    {
        if(postGresConn == null) {
            logger.error("We should never get here.");
            return null;
        }

        PreparedStatement stmt = null ;
        ResultSet rs = null ;
        House house = null;
        try {
            stmt = postGresConn.prepareStatement("SELECT * FROM Namas WHERE adresas = '" + param + "'");
            rs = stmt.executeQuery();

            while (rs.next()) {
                house = new House();
                house.setAddress(rs.getString("adresas"));
                house.setAgentId(rs.getInt("agento_id"));
                house.setOwnerId(rs.getInt("savininko_id"));
                house.setSize(rs.getDouble("plotas"));
                house.setPrice(rs.getDouble("kaina"));
                house.setBuildYear(rs.getInt("statybos_metai"));
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
        return house;
    }

    private List<House> getHouses(Connection postGresConn)
    {
        if(postGresConn == null) {
        	logger.error("We should never get here.");
            return Collections.emptyList();
        }

        Statement stmt = null ;
        ResultSet rs = null ;
        List<House> list = new ArrayList<>();
        try {
            stmt = postGresConn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM Namas");

            while (rs.next()) {
                House house = new House();
                house.setAddress(rs.getString("adresas"));
                house.setAgentId(rs.getInt("agento_id"));
                house.setOwnerId(rs.getInt("savininko_id"));
                house.setSize(rs.getDouble("plotas"));
                house.setPrice(rs.getDouble("kaina"));
                house.setBuildYear(rs.getInt("statybos_metai"));

                list.add(house);
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
