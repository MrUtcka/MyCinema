package com.example.mycinema.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Place {

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Row getRow() {
        return row;
    }

    public void setRow(Row row) {
        this.row = row;
    }

    public Boolean getFree() {
        return isFree;
    }

    public void setFree(Boolean free) {
        isFree = free;
    }

    @DatabaseField(generatedId = true)
    private Integer id;

    @DatabaseField(foreign = true)
    private Row row;

    @DatabaseField
    private String name;

    @DatabaseField(foreign = true)
    private Booking booking;

    @DatabaseField
    private Boolean isFree;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }


}
