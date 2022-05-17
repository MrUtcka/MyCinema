package com.example.mycinema;

import com.example.mycinema.model.Film;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;


public class FilmDAO extends BaseDaoImpl<Film, Integer> {
    protected FilmDAO(ConnectionSource connectionSource,
                      Class<Film> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }
}