package com.example.mycinema;

import com.example.mycinema.model.Genre;
import com.example.mycinema.model.User;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;


public class GenreDAO extends BaseDaoImpl<Genre, Integer> {
    protected GenreDAO(ConnectionSource connectionSource,
                      Class<Genre> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public Genre genreSearch(String g) {
        if (g == null)
            return null;

        try {
            List<Genre> genres = this.queryForEq("name", g);
            if (genres != null && genres.size() > 0) {
                Genre genre = genres.get(0);
                return genre;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}