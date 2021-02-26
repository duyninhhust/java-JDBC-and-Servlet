package com.bksoftware.ninhnd.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface BaseDao<T> {

    T getObject(ResultSet resultSet) throws SQLException;

    List<T> getList(ResultSet resultSet) throws SQLException;

    List<T> findAll() throws SQLException;

    T findById(int id) throws SQLException;

    T insert(T t) throws SQLException;

    boolean update(T t) throws SQLException;

    boolean delete(int id) throws SQLException;
}
