package com.example.mycinema;

import com.example.mycinema.model.Schedule;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;


public class ScheduleDAO extends BaseDaoImpl<Schedule, Integer> {
    protected ScheduleDAO(ConnectionSource connectionSource,
                      Class<Schedule> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }
}
