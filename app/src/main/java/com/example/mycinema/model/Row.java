package com.example.mycinema.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.Collection;

@DatabaseTable
public class Row {

    @DatabaseField(generatedId = true)
    private Integer id;

    @DatabaseField(canBeNull = false)
    private String name;

    @DatabaseField(foreign = true, canBeNull = false)
    private Hall hall;

    @ForeignCollectionField(eager = true)
    private Collection<Place> places;

    @ForeignCollectionField(eager = true)
    private Collection<Booking> booking;

    private static Integer placeCount;
}
