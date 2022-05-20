package com.example.mycinema;

import android.app.Activity;
import android.icu.util.LocaleData;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.mycinema.model.Film;
import com.example.mycinema.model.Place;
import com.example.mycinema.model.Schedule;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScheduleActivity extends Activity {

    ScheduleDAO scheduleDao;
    FilmDAO filmDao;

    EditText date, time;
    Spinner film;
    Button create;

    List<Film> films;
    List<String> filmNames = new ArrayList<>();

    String selected;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule);

        try {
            filmDao = HelperFactory.getHelper().getFilmDao();
            scheduleDao = HelperFactory.getHelper().getScheduleDao();

            films = filmDao.queryForAll();

            for (Film item: films) {
                filmNames.add(item.getName());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        date = (EditText) findViewById(R.id.dateCreate);
        time = (EditText) findViewById(R.id.timeCreate);
        film = (Spinner) findViewById(R.id.film);
        create = (Button) findViewById(R.id.create);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, filmNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        film.setAdapter(adapter);

        film.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selected = film.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Film currFilm = getFilm(selected);
                Schedule schedule = new Schedule();

                schedule.setFilm(currFilm);
                try {
                    schedule.setDate(new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(date.getText().toString() + " " + time.getText().toString()));
                    schedule.setFilmId(currFilm.getId());
                    scheduleDao.create(schedule);
                    Toast.makeText(getApplicationContext(), "Успешно!", Toast.LENGTH_LONG).show();
                } catch (ParseException | SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Film getFilm(String selected) {
        for (Film item: films) {
            if(item.getName().equals(selected)) {
                return item;
            }
        }
        return null;
    }
}
