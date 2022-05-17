package com.example.mycinema.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class UserBooking {

    @DatabaseField(generatedId = true)
    private Integer id;

    @DatabaseField(foreign = true)
    private User user;

    @DatabaseField(foreign = true)
    private Booking booking;
}
