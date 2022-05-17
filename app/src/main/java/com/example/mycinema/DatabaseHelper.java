package com.example.mycinema;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.mycinema.model.Booking;
import com.example.mycinema.model.Film;
import com.example.mycinema.model.Genre;
import com.example.mycinema.model.Hall;
import com.example.mycinema.model.Place;
import com.example.mycinema.model.Role;
import com.example.mycinema.model.Row;
import com.example.mycinema.model.Schedule;
import com.example.mycinema.model.User;
import com.example.mycinema.model.UserBooking;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;


public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String TAG = DatabaseHelper.class.getSimpleName();

    
    private static final String DATABASE_NAME ="user.db";

    private static final int DATABASE_VERSION = 1;

    private UserDAO userDao = null;
    private RoleDAO roleDao = null;
    private ScheduleDAO scheduleDao = null;
    private RowDAO rowDao = null;
    private PlaceDAO placeDao = null;
    private HallDAO hallDao = null;
    private GenreDAO genreDao = null;
    private FilmDAO filmDao = null;
    private BookingDAO bookingDao = null;
    private UserBookingDAO userBookingDao = null;


    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try
        {
            TableUtils.createTable(connectionSource, User.class);
            TableUtils.createTable(connectionSource, Role.class);
            TableUtils.createTable(connectionSource, Schedule.class);
            TableUtils.createTable(connectionSource, Row.class);
            TableUtils.createTable(connectionSource, Place.class);
            TableUtils.createTable(connectionSource, Hall.class);
            TableUtils.createTable(connectionSource, Genre.class);
            TableUtils.createTable(connectionSource, Film.class);
            TableUtils.createTable(connectionSource, Booking.class);
            TableUtils.createTable(connectionSource, UserBooking.class);
        }
        catch (SQLException e){
            Log.e(TAG, "error creating DB " + DATABASE_NAME);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int i, int i1) {
        try{
            
            TableUtils.dropTable(connectionSource, User.class, true);
            TableUtils.dropTable(connectionSource, Role.class, true);
            TableUtils.dropTable(connectionSource, Schedule.class, true);
            TableUtils.dropTable(connectionSource, Row.class, true);
            TableUtils.dropTable(connectionSource, Place.class, true);
            TableUtils.dropTable(connectionSource, Hall.class, true);
            TableUtils.dropTable(connectionSource, Genre.class, true);
            TableUtils.dropTable(connectionSource, Film.class, true);
            TableUtils.dropTable(connectionSource, Booking.class, true);
            TableUtils.dropTable(connectionSource, UserBooking.class, true);
            onCreate(db, connectionSource);
        }
        catch (SQLException e){
            Log.e(TAG,"error upgrading db "+DATABASE_NAME+"from ver ");
            throw new RuntimeException(e);
        }
    }

    public UserDAO getUserDao() throws SQLException{
        if(userDao == null){
            userDao = new UserDAO(connectionSource, User.class);
        }
        return userDao;
    }

    public RoleDAO getRoleDao() throws SQLException{
        if(roleDao == null){
            roleDao = new RoleDAO(connectionSource, Role.class);
        }
        return roleDao;
    }

    public ScheduleDAO getScheduleDao() throws SQLException{
        if(scheduleDao == null){
            scheduleDao = new ScheduleDAO(connectionSource, Schedule.class);
        }
        return scheduleDao;
    }

    public RowDAO getRowDao() throws SQLException{
        if(rowDao == null){
            rowDao = new RowDAO(connectionSource, Row.class);
        }
        return rowDao;
    }

    public PlaceDAO getPlaceDao() throws SQLException{
        if(placeDao == null){
            placeDao = new PlaceDAO(connectionSource, Place.class);
        }
        return placeDao;
    }

    public HallDAO getHallDao() throws SQLException{
        if(hallDao == null){
            hallDao = new HallDAO(connectionSource, Hall.class);
        }
        return hallDao;
    }

    public GenreDAO getGenreDao() throws SQLException{
        if(genreDao == null){
            genreDao = new GenreDAO(connectionSource, Genre.class);
        }
        return genreDao;
    }

    public FilmDAO getFilmDao() throws SQLException{
        if(filmDao == null){
            filmDao = new FilmDAO(connectionSource, Film.class);
        }
        return filmDao;
    }

    public BookingDAO getBookingDao() throws SQLException{
        if(bookingDao == null){
            bookingDao = new BookingDAO(connectionSource, Booking.class);
        }
        return bookingDao;
    }

    public UserBookingDAO getUserBookingDao() throws SQLException{
        if(userBookingDao == null){
            userBookingDao = new UserBookingDAO(connectionSource, UserBooking.class);
        }
        return userBookingDao;
    }

    @Override
    public void close(){
        super.close();
        userDao = null;
        roleDao = null;
        scheduleDao = null;
        rowDao = null;
        placeDao = null;
        hallDao = null;
        genreDao = null;
        filmDao = null;
        bookingDao = null;
        userBookingDao = null;
    }
}