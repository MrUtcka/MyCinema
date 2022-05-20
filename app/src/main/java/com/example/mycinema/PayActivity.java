package com.example.mycinema;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mycinema.model.Booking;
import com.example.mycinema.model.Film;
import com.example.mycinema.model.Place;
import com.example.mycinema.model.Row;
import com.example.mycinema.model.Schedule;
import com.example.mycinema.model.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PayActivity extends AppCompatActivity {

    FilmDAO filmDao;
    List<Film> films = new ArrayList<>();
    Film currFilm = new Film();

    UserDAO userDao;
    List<User> users = new ArrayList<>();
    User currUser = new User();

    ScheduleDAO scheduleDao;
    List<Schedule> schedules = new ArrayList<>();
    List<Date> dates = new ArrayList<>();
    Schedule currSchedule = new Schedule();

    PlaceDAO placeDao;
    List<Place> places = new ArrayList<>();
    List<String> namePlaces = new ArrayList<>();
    List<Place> currPlaces = new ArrayList<>();
    List<String> currNamePlaces = new ArrayList<>();
    Place currPlace = new Place();
    ArrayAdapter<String> placeAdapter;

    BookingDAO bookingDao;
    List<Booking> bookings = new ArrayList<>();

    RowDAO rowDao;
    List<Row> rows = new ArrayList<>();
    List<String> nameRows = new ArrayList<>();

    Spinner date, row, place;
    Button pay;
    TextView nameView;

    String name, email, role;
    String selected;
    Object selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay);

        Bundle arguments = getIntent().getExtras();

        if(arguments != null) {
            name = arguments.getString("name");
            email = arguments.getString("user");
            role = arguments.getString("role");
        }

        try {
            userDao = HelperFactory.getHelper().getUserDao();
            scheduleDao = HelperFactory.getHelper().getScheduleDao();
            rowDao = HelperFactory.getHelper().getRowDao();
            placeDao = HelperFactory.getHelper().getPlaceDao();
            filmDao = HelperFactory.getHelper().getFilmDao();
            bookingDao = HelperFactory.getHelper().getBookingDao();

            users = userDao.queryForAll();
            schedules = scheduleDao.queryForAll();
            rows = rowDao.queryForAll();
            places = placeDao.queryForAll();
            films = filmDao.queryForAll();
            bookings = bookingDao.queryForAll();

            currUser = getCurrUser(email);
            currFilm = getCurrFilm(name);

            for (Row item: rows) {
                nameRows.add(item.getName());
            }

            for (Place item: places) {
                namePlaces.add(item.getName());
            }

            for (Schedule item: schedules) {
                dates.add(item.getDate());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        pay = (Button) findViewById(R.id.pay);

        date = (Spinner) findViewById(R.id.date);
        row = (Spinner) findViewById(R.id.row);
        place = (Spinner) findViewById(R.id.place);

        nameView = (TextView) findViewById(R.id.name);
        nameView.setText("Название: " + name);

        ArrayAdapter<Date> dateAdapter = new ArrayAdapter<Date>(this,
                android.R.layout.simple_spinner_item, dates);
        dateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        date.setAdapter(dateAdapter);

        date.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedDate = date.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<String> rowAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, nameRows);
        rowAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        row.setAdapter(rowAdapter);

        row.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selected = row.getSelectedItem().toString();

                currPlaces = searchCurrPlaces(searchCurrRows(selected));
                currNamePlaces.clear();
                place.setAdapter(null);

                for (Place item: currPlaces) {

                    currNamePlaces.add(item.getName());
                }

                place.setAdapter(placeAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        placeAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, currNamePlaces);
        placeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        place.setAdapter(placeAdapter);

        place.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selected = place.getSelectedItem().toString();

                currPlace = getCurrPlace(selected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currPlace.getFree()) {
                    Toast.makeText(getApplicationContext(), "Свободно", Toast.LENGTH_SHORT).show();
                    currPlace.setFree(false);
                    try {
                        placeDao.update(currPlace);
                        currSchedule = getCurrSchedule((Date) selectedDate, currFilm.getName());

                        Booking currBooking = new Booking();
                        currBooking.setSchedule(currSchedule);
                        currBooking.setPlace(currPlace);
                        currBooking.setUser(currUser);

                        bookingDao.create(currBooking);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
                else if(!currPlace.getFree() && role.equals("admin")) {
                    currSchedule = getCurrSchedule((Date) selectedDate, currFilm.getName());

                    Booking currBooking = new Booking();
                    currBooking.setSchedule(currSchedule);
                    currBooking.setPlace(currPlace);
                    currBooking.setUser(currUser);

                    try {
                        bookingDao.create(currBooking);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                    for (Booking item: bookings) {
                        item.setUser(users.get(item.getUser().getId() - 1));
                        item.setPlace(places.get(item.getPlace().getId() - 1));
                        item.setSchedule(schedules.get(item.getSchedule().getId() - 1));
                        if(item.getPlace().getName().equals(currPlace.getName())) {
                            Toast.makeText(getApplicationContext(), item.getUser().getEmail(), Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Занято", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public List<Place> searchCurrPlaces(Row currRow) {
        List<Place> currPlaces = new ArrayList<>();
        for (Place item: places){
            if(item.getName().contains(currRow.getName())) {
                currPlaces.add(item);
            }
        }
        return currPlaces;
    }

    public Row searchCurrRows(String name) {
        for (Row item: rows){
            if(item.getName().equals(name)) {
                return item;
            }
        }
        return null;
    }

    public Place getCurrPlace(String name) {
        for (Place item: currPlaces){
            if(item.getName().equals(name)) {
                return item;
            }
        }
        return null;
    }

    public User getCurrUser(String email) {
        for (User item: users){
            if(item.getEmail().equals(email)) {
                return item;
            }
        }
        return new User();
    }

    public Film getCurrFilm(String name) {
        for (Film item: films){
            if(item.getName().equals(name)) {
                return item;
            }
        }
        return new Film();
    }

    public Schedule getCurrSchedule(Date date, String name) {
        for (Schedule item: schedules) {
            item.setFilm(films.get(item.getFilm().getId() - 1));
            if(item.getDate().equals(date) && item.getFilm().getName().equals(name)) {
                return item;
            }
        }
        return new Schedule();
    }
}
