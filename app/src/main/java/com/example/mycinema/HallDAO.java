package com.example.mycinema;

import com.example.mycinema.model.Hall;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;


public class HallDAO extends BaseDaoImpl<Hall, Integer> {
    protected HallDAO(ConnectionSource connectionSource,
                      Class<Hall> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }
}