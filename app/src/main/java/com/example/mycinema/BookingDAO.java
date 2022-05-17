package com.example.mycinema;

import com.example.mycinema.model.Booking;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;


public class BookingDAO extends BaseDaoImpl<Booking, Integer> {
    protected BookingDAO(ConnectionSource connectionSource,
                      Class<Booking> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }
}