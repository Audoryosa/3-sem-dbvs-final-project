package com.auku.agentura.dao.impl;

import com.auku.agentura.dao.Dao;
import com.auku.agentura.utils.PostgreSqlUtils;
import com.auku.agentura.entity.Owner;
import com.auku.agentura.entity.ResponseWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
public class OwnerDaoImpl implements Dao<Owner> {

    private Logger logger = LoggerFactory.getLogger(OwnerDaoImpl.class);

    @Override
    public List<Owner> get() {

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
    public List<Owner> search(String searchItem) {
        PostgreSqlUtils.loadDriver();
        Connection connection = PostgreSqlUtils.getConnection();
        List<Owner> foundOwners = null;
        if (connection != null) {
            foundOwners = searchOwners(connection, searchItem);
            logger.info("Found some owners");
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("Cannot close connection");
                e.printStackTrace();
            }
        }
        return foundOwners;
    }

    @Override
    public ResponseWrapper save(Owner owner) {
        PostgreSqlUtils.loadDriver();
        Connection connection = PostgreSqlUtils.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO savininkas(vardas, pavarde, adresas, seimos_dydis, pajamos) " +
                    "VALUES ('" + owner.getName() + "', '" + owner.getSurname() + "', '" + owner.getAddress() + "', " + owner.getFamilySize() + ", " + owner.getIncome() + ")");
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Could not create owner: " + e.getMessage());
            return new ResponseWrapper("Could not create new owner.", e.getMessage(), false);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("Could not close connection");
                e.printStackTrace();
            }
        }
        return new ResponseWrapper(null, null, true);
    }

    @Override
    public Owner get(int id) {
        PostgreSqlUtils.loadDriver();
        Connection connection = PostgreSqlUtils.getConnection();
        Owner foundOwner = null;
        if (connection != null) {
            var foundOwners = searchOwners(connection, id);
            if (foundOwners.isEmpty()) {
                return null;
            } else {
                foundOwner = foundOwners.get(0);
            }
            logger.info("Found a owner");
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("Cannot close connection");
                e.printStackTrace();
            }
        }
        return foundOwner;
    }

    @Override
    public Owner get(String param) {
        return null;
    }

    @Override
    public void update(Owner owner, int id) {
        PostgreSqlUtils.loadDriver();
        Connection connection = PostgreSqlUtils.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE savininkas " +
                    "SET vardas = '" + owner.getName() + "', " +
                    "pavarde = '" + owner.getSurname() + "', " +
                    "adresas = '" + owner.getAddress() + "', " +
                    "seimos_dydis = " + owner.getFamilySize() + ", " +
                    "pajamos = " + owner.getIncome() + " WHERE savininkas.id = " + id);
            statement.executeUpdate();
        } catch (SQLException e) {
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
    public void delete(int id) {
        PostgreSqlUtils.loadDriver();
        Connection connection = PostgreSqlUtils.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM savininkas WHERE savininkas.id = " + id);
            statement.execute();
        } catch (SQLException e) {
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

    private List<Owner> searchOwners(Connection connection, int id) {
        PreparedStatement stmt = null ;
        ResultSet rs = null ;
        List<Owner> list = new ArrayList<>();
        try {
            stmt = connection.prepareStatement("SELECT * FROM Savininkas WHERE Id = " + id);
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


    private List<Owner> searchOwners(Connection connection, String searchItem) {
        PreparedStatement stmt = null ;
        ResultSet rs = null ;
        List<Owner> list = new ArrayList<>();
        try {
            stmt = connection.prepareStatement("SELECT * FROM Savininkas WHERE LOWER(vardas) LIKE LOWER('%" + searchItem + "%')" +
                    "OR LOWER(pavarde) LIKE LOWER('%" + searchItem + "%')" +
                    "OR LOWER(adresas) LIKE LOWER('%" + searchItem + "%')");
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

    /********************************************************/
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
            stmt = postGresConn.prepareStatement("SELECT * FROM Savininkas");
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

}

