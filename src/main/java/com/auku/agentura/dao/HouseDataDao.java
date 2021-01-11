package com.auku.agentura.dao;

import com.auku.agentura.entity.House;
import com.auku.agentura.entity.HouseDataEntity;

import java.util.List;

public interface HouseDataDao {
    List<HouseDataEntity> get();

    List<HouseDataEntity> search(String param);

    String getHouseOwner(House house);
}
