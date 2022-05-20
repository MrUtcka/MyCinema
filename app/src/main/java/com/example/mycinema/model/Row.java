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

    @DatabaseField(foreign = true)
    private Hall hall;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Hall getHall() {
        return hall;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }

    public Collection<Place> getPlaces() {
        return places;
    }

    public void setPlaces(Collection<Place> places) {
        this.places = places;
    }

    public Collection<Booking> getBooking() {
        return booking;
    }

    public void setBooking(Collection<Booking> booking) {
        this.booking = booking;
    }

    public static Integer getPlaceCount() {
        return placeCount;
    }

    public static void setPlaceCount(Integer placeCount) {
        Row.placeCount = placeCount;
    }

    @ForeignCollectionField(eager = true)
    private Collection<Place> places;

    @ForeignCollectionField(eager = true)
    private Collection<Booking> booking;

    private static Integer placeCount;
}
