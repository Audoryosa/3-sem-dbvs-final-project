package com.auku.agentura.service;

import com.auku.agentura.entity.*;
import com.auku.agentura.entity.*;

import java.util.List;

public interface AgencyService {
    List<Owner> getOwners();

    List<House> getHouses();

    List<Owner> searchOwners(String searchItem);

    ResponseWrapper saveOwner(Owner owner);

    Owner getOwner(int id);

    void update(Owner owner, int id);

    void deleteOwner(int id);

    List<HouseDataEntity> getHouseData();

    List<HouseDataEntity> searchHouseInfo(String param);

	List<House> searchHouses(String searchItem);

    House getHouse(String id);

    List<Owner> getAvailableOwners();

    void sellHouse(House house, int id);

    List<Agent> getAgents();

    List<Agent> searchAgents(String param);

    void sellOwnersHouse(House house, int id);

    String getHouseOwner(House house);

    void saveHouse(House house);

    void deleteHouse(String address);
}
