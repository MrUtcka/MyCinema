package com.example.mycinema;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycinema.model.Booking;
import com.example.mycinema.model.Film;
import com.example.mycinema.model.Place;
import com.example.mycinema.model.Schedule;
import com.example.mycinema.model.User;
import com.example.mycinema.viewModel.FilmViewModel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InfoActivity extends Activity {

    ScheduleDAO scheduleDao;
    List<Schedule> schedules;

    UserDAO userDao;
    List<User> users;

    PlaceDAO placeDao;
    List<Place> places;

    BookingDAO bookingDao;
    List<Booking> bookings;
    List<Booking> currBookings = new ArrayList<>();

    FilmDAO filmDao;
    List<Film> films;

    List<String> filmForShow = new ArrayList<>();
    List<Date> dateForShow = new ArrayList<>();
    List<Integer> imageForShow = new ArrayList<>();

    TextView emailTV;
    ListView bookingLV;

    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);

        emailTV = (TextView) findViewById(R.id.emailTV);
        bookingLV = (ListView) findViewById(R.id.bookingLV);

        Bundle arguments = getIntent().getExtras();

        if(arguments != null) {
            email = arguments.getString("email");
        }


        try {
            bookingDao = HelperFactory.getHelper().getBookingDao();
            userDao = HelperFactory.getHelper().getUserDao();
            scheduleDao = HelperFactory.getHelper().getScheduleDao();
            placeDao = HelperFactory.getHelper().getPlaceDao();
            filmDao = HelperFactory.getHelper().getFilmDao();

            films = filmDao.queryForAll();
            users = userDao.queryForAll();
            places = placeDao.queryForAll();
            schedules = scheduleDao.queryForAll();
            bookings = bookingDao.queryForAll();

            for (Booking item: bookings) {
                item.setSchedule(schedules.get(item.getSchedule().getId() - 1));
                item.setUser(users.get(item.getUser().getId() - 1));
                item.setPlace(places.get(item.getPlace().getId() - 1));
            }

            for (Schedule item: schedules) {
                filmForShow.add(films.get(item.getFilmId() - 1).getName());
            }

            for (Booking item: bookings) {
                if(item.getUser().getEmail().equals(email)) {
                    currBookings.add(item);
                    dateForShow.add(item.getSchedule().getDate());
                    imageForShow.add(item.getSchedule().getFilm().getImage());
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        ArrayAdapter<String> infoAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.film_item, R.id.name, filmForShow);
        bookingLV.setAdapter(infoAdapter);

        bookingLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
                Booking currBooking = currBookings.get(position);
                try {
                    bookingDao.delete(currBooking);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                bookingLV.setAdapter(null);
                bookingLV.setAdapter(infoAdapter);
                Toast.makeText(getApplicationContext(), "Отменено", Toast.LENGTH_LONG).show();
            }
        });
    }
}



