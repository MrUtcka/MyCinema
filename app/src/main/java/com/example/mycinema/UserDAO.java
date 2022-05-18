package com.example.mycinema;


import com.example.mycinema.model.User;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class UserDAO extends BaseDaoImpl<User, Integer> {
    protected UserDAO(ConnectionSource connectionSource,
                      Class<User> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public User getUserByEmail(String email) {

        if (email == null)
            return null;

        try {
            List<User> users = this.queryForEq("email", email);
            if (users != null && users.size() > 0 && users.get(0).getEmail() != email) {
                User user = users.get(0);
                return user;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
