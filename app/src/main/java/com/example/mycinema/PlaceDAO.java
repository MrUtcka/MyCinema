package com.example.mycinema;

import com.example.mycinema.model.Place;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;


public class PlaceDAO extends BaseDaoImpl<Place, Integer> {
    protected PlaceDAO(ConnectionSource connectionSource,
                      Class<Place> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }
}
