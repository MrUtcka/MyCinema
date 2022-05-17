package com.example.mycinema.viewModel;

import com.example.mycinema.model.Genre;
import com.example.mycinema.model.Schedule;


public class FilmViewModel {

    private Integer id;

    private Schedule schedule;

    private String name;

    private String description;

    private Genre genre;

    private Integer genreId;

    public String getScheduleTime() {
        return schedule.getDate().toString();
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Integer getGenreId() {
        return genreId;
    }

    public void setGenreId(Integer genreId) {
        this.genreId = genreId;
    }



    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
}
