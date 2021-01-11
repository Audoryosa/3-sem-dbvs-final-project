package com.auku.agentura.dao;

import com.auku.agentura.entity.ResponseWrapper;

import java.util.List;

public interface Dao<T> {
    List<T> get();
    T get(int id);
    T get(String param);
    List<T> search(String searchInput);
    ResponseWrapper save(T saveObj);
    void update(T newObj, int id);
    void delete(int id);
}
