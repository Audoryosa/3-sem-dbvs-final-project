package com.auku.agentura.service;

import com.auku.agentura.dao.Dao;
import com.auku.agentura.dao.DetailedOwnerDao;
import com.auku.agentura.dao.HouseDataDao;
import com.auku.agentura.entity.*;
import com.auku.agentura.utils.PostgreSqlUtils;
import com.auku.agentura.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Service
public class AgencyServiceImpl implements AgencyService{

    @Autowired
    private Dao<Owner> ownerDao;

    @Autowired
    private Dao<House> houseDao;

    @Autowired
    private HouseDataDao houseDataDao;

    @Autowired
    private Dao<Agent> agentDao;

    @Autowired
    private DetailedOwnerDao detailedOwnerDao;

    @Override
    public List<Owner> getOwners() {
        return ownerDao.get();
    }

    @Override
    public List<House> getHouses() {
        return houseDao.get();
    }

    @Override
    public List<Owner> searchOwners(String searchItem) {
        return ownerDao.search(searchItem);
    }

    @Override
    public ResponseWrapper saveOwner(Owner owner) {
        return ownerDao.save(owner);
    }

    @Override
    public Owner getOwner(int id) {
        return ownerDao.get(id);
    }

    @Override
    public void update(Owner owner, int id) {
        ownerDao.update(owner, id);
    }

    @Override
    public void deleteOwner(int id) {
        ownerDao.delete(id);
    }

    @Override
    public List<HouseDataEntity> getHouseData() {
        return houseDataDao.get();
    }

    @Override
    public List<HouseDataEntity> searchHouseInfo(String param) {
        return houseDataDao.search(param);
    }

	@Override
	public List<House> searchHouses(String searchItem) {
		return houseDao.search(searchItem);
	}

    @Override
    public House getHouse(String id) {
        return houseDao.get(id);
    }

    @Override
    public List<Owner> getAvailableOwners() {
        return detailedOwnerDao.getOwnersWithNoHouses();
    }

    @Override
    public void sellHouse(House house, int id) {
        detailedOwnerDao.sellHouse(house, id);
    }

    @Override
    public List<Agent> getAgents() {
        return agentDao.get();
    }

    @Override
    public List<Agent> searchAgents(String param) {
        return agentDao.search(param);
    }

    @Override
    public void sellOwnersHouse(House house, int id) {
        detailedOwnerDao.sellOwnersHouse(house, id);
    }

    @Override
    public String getHouseOwner(House house) {
        return houseDataDao.getHouseOwner(house);
    }

    @Override
    public void saveHouse(House house) {
        houseDao.save(house);
    }

    @Override
    public void deleteHouse(String address) {
        PostgreSqlUtils.loadDriver();
        Connection connection = PostgreSqlUtils.getConnection();
        try {
            Statement statement = connection.createStatement();
            statement.execute("DELETE FROM namas WHERE namas.adresas = '" + address + "'");
        } catch (SQLException e) {
            System.err.println("Wild sql error:");
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Could not close connection");
                e.printStackTrace();
            }
        }
    }
}
