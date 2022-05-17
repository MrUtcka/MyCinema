package com.example.mycinema.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Collection;
import java.util.List;

@DatabaseTable
public class Booking {

    @DatabaseField(generatedId = true)
    private Integer id;

    @DatabaseField(foreign = true)
    private User user;

    @DatabaseField(foreign = true)
    private Schedule schedule;

    @DatabaseField(foreign = true)
    private Hall hall;

    @DatabaseField(foreign = true)
    private Row row;

    @DatabaseField(foreign = true)
    private Place place;
}
