package com.example.mycinema;

import com.example.mycinema.model.UserBooking;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import java.sql.SQLException;

public class UserBookingDAO extends BaseDaoImpl<UserBooking, Integer> {
    protected UserBookingDAO(ConnectionSource connectionSource,
                          Class<UserBooking> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }
}