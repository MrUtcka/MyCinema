package com.example.mycinema;

import com.example.mycinema.model.Role;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;


public class RoleDAO extends BaseDaoImpl<Role, Integer> {
    protected RoleDAO(ConnectionSource connectionSource,
                      Class<Role> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }
}
