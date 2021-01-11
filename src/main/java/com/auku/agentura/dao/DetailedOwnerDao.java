package com.auku.agentura.dao;

import com.auku.agentura.entity.House;
import com.auku.agentura.entity.Owner;

import java.util.List;

public interface DetailedOwnerDao {
    List<Owner> getOwnersWithNoHouses();

    void sellHouse(House house, int id);

    void sellOwnersHouse(House house, int id);
}
