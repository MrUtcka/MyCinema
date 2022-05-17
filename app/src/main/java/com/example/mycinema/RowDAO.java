package com.example.mycinema;

import com.example.mycinema.model.Row;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;


public class RowDAO extends BaseDaoImpl<Row, Integer> {
    protected RowDAO(ConnectionSource connectionSource,
                      Class<Row> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }
}
