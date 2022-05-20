package com.example.mycinema.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Collection;
import java.util.List;

@DatabaseTable
public class Booking {

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public Hall getHall() {
        return hall;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }

    public Row getRow() {
        return row;
    }

    public void setRow(Row row) {
        this.row = row;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    @DatabaseField(generatedId = true)
    private Integer id;

    @DatabaseField(foreign = true)
    private User user;

    @DatabaseField(foreign = true)
    private Film film;

    @DatabaseField(foreign = true)
    private Schedule schedule;

    @DatabaseField(foreign = true)
    private Hall hall;

    @DatabaseField(foreign = true)
    private Row row;

    @DatabaseField(foreign = true)
    private Place place;
}
