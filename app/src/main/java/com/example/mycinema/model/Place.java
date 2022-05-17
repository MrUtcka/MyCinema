package com.example.mycinema.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Place {

    @DatabaseField(generatedId = true)
    private Integer id;

    @DatabaseField(foreign = true)
    private Row row;

    @DatabaseField(foreign = true)
    private Booking booking;

    @DatabaseField
    private Boolean isFree;
}
