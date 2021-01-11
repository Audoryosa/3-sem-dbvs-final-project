package com.auku.agentura.dao.impl;

import com.auku.agentura.dao.HouseDataDao;
import com.auku.agentura.entity.House;
import com.auku.agentura.utils.PostgreSqlUtils;
import com.auku.agentura.entity.HouseDataEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class HouseDataDaoImpl implements HouseDataDao {
	
	private Logger logger = LoggerFactory.getLogger(HouseDataDao.class);

    private static final String SELECT_ALL = "SELECT namas.adresas, namas.kaina, COALESCE(Savininkas.vardas, '-') AS vardas, COALESCE(Savininkas.Pavarde, 'Namas parduodamas'), agentas.pavarde AS \"agento pavarde\"\n" +
            "FROM \n" +
            "namas LEFT JOIN savininkas ON namas.savininko_id = savininkas.id\n" +
            "LEFT OUTER JOIN agentas ON namas.agento_id = agentas.id";

    @Override
    public List<HouseDataEntity> get() {
        PostgreSqlUtils.loadDriver();
        Connection con = PostgreSqlUtils.getConnection();
        List<HouseDataEntity> houseList = null;
        if( null != con ) {
            houseList = getInfo(con);
            logger.info("List retrieved");
        }
        if( null != con ) {
            try {
                con.close() ;
            }
            catch (SQLException exp) {
                logger.warn("Can not close connection!");
                exp.printStackTrace();
            }
        }

        return houseList;
    }

    @Override
    public List<HouseDataEntity> search(String param) {
        PostgreSqlUtils.loadDriver();
        Connection con = PostgreSqlUtils.getConnection();
        List<HouseDataEntity> houseList = null;
        if( null != con ) {
            houseList = search(con, param);
            logger.info("List retrieved");
        }
        if( null != con ) {
            try {
                con.close() ;
            }
            catch (SQLException exp) {
                logger.warn("Can not close connection!");
                exp.printStackTrace();
            }
        }

        return houseList;
    }

    @Override
    public String getHouseOwner(House house) {
        PostgreSqlUtils.loadDriver();
        Connection con = PostgreSqlUtils.getConnection();
        String name = "";
        if( null != con ) {
            name = getName(con, house);
            logger.info("House retrieved");
        }
        if( null != con ) {
            try {
                con.close() ;
            }
            catch (SQLException exp) {
                logger.warn("Can not close connection!");
                exp.printStackTrace();
            }
        }

        return name;
    }

    private String getName(Connection con, House house) {
        if (con == null) {
            logger.error("We should never get here.");
            return null;
        }

        PreparedStatement stmt = null;
        ResultSet rs = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            stmt = con.prepareStatement("SELECT vardas, pavarde\n" +
                    "\tFROM public.savininkas, namas\n" +
                    "\tWHERE namas.savininko_id = savininkas.id\n" +
                    "\tAND namas.adresas = '" + house.getAddress() + "'");
            rs = stmt.executeQuery();

            while (rs.next()) {
               stringBuilder.append(rs.getString("vardas")).append(" ").append(rs.getString("pavarde"));
            }

        } catch (SQLException e) {
            logger.error("SQL Error!");
            e.printStackTrace();
        } finally {
            try {
                if (null != rs)
                    rs.close();
                if (null != stmt)
                    stmt.close();
            } catch (SQLException exp) {
                logger.error("Unexpected SQL Error!");
                exp.printStackTrace();
            }
        }
        return stringBuilder.toString();
    }

    private List<HouseDataEntity> search(Connection postGresConn, String param) {
        if (postGresConn == null) {
            logger.error("We should never get here.");
            return Collections.emptyList();
        }

        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<HouseDataEntity> list = new ArrayList<>();
        try {
            stmt = postGresConn.prepareStatement("SELECT * FROM (\n" +
                    "\tSELECT namas.adresas, namas.kaina, Savininkas.vardas, Savininkas.Pavarde, agentas.pavarde AS \"agento_pavarde\"\n" +
                    "\tFROM \n" +
                    "namas RIGHT OUTER JOIN savininkas ON namas.savininko_id = savininkas.id\n" +
                    "LEFT OUTER JOIN agentas ON namas.agento_id = agentas.id" +
                    " ) AS \"laikina\" \n" +
                    "WHERE LOWER(laikina.adresas) LIKE LOWER('%" + param + "%')\n" +
                    "OR LOWER(laikina.vardas) LIKE LOWER('%" + param + "%')\n" +
                    "OR LOWER(laikina.pavarde) LIKE LOWER('%" + param + "%')\n" +
                    "OR LOWER(laikina.agento_pavarde) LIKE LOWER('%" + param + "%')");
            rs = stmt.executeQuery();

            createHouseInfo(rs, list);

        } catch (SQLException e) {
            logger.error("SQL Error!");
            e.printStackTrace();
        } finally {
            try {
                if (null != rs)
                    rs.close();
                if (null != stmt)
                    stmt.close();
            } catch (SQLException exp) {
                logger.error("Unexpected SQL Error!");
                exp.printStackTrace();
            }
        }
        return list;
    }

    private void createHouseInfo(ResultSet rs, List<HouseDataEntity> list) throws SQLException {
        while (rs.next()) {
            HouseDataEntity house = new HouseDataEntity();
            house.setAddress(rs.getString(1));
            house.setPrice(rs.getDouble(2));
            house.setOwnerName(rs.getString(3));
            house.setOwnerSurname(rs.getString(4));
            house.setAgentSurname(rs.getString(5));

            list.add(house);
        }
    }

    private List<HouseDataEntity> getInfo(Connection postGresConn) {
        if(postGresConn == null) {
            logger.warn("We should never get here.");
            return Collections.emptyList();
        }

        PreparedStatement stmt = null ;
        ResultSet rs = null ;
        List<HouseDataEntity> list = new ArrayList<>();
        try {
            stmt = postGresConn.prepareStatement(SELECT_ALL);
            rs = stmt.executeQuery();

            createHouseInfo(rs, list);
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
