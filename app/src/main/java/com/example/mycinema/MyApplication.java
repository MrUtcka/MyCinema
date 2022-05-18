package com.example.mycinema;

import android.app.Application;

import com.example.mycinema.model.Film;
import com.example.mycinema.model.Genre;
import com.example.mycinema.model.Role;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MyApplication extends Application {

    List<Role> roles;
    List<Genre> genres;
    List<Film> films;

    @Override
    public void onCreate() {
        super.onCreate();
        HelperFactory.setHelper(getApplicationContext());
        try {
            initRoles();
            initGenre();
            initFilms();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    @Override
    public void onTerminate() {
        HelperFactory.releaseHelper();
        super.onTerminate();
    }

    public void initRoles() throws SQLException {
        RoleDAO roleDao = HelperFactory.getHelper().getRoleDao();

        roles = roleDao.queryForAll();

        if(roles == null || roles.size() == 0) {
            roles = new ArrayList<Role>();
            Role roleUser = new Role();
            roleUser.setRole("User");
            Role roleAdmin = new Role();
            roleAdmin.setRole("Admin");

            roles.add(roleAdmin);
            roles.add(roleUser);

            roleDao.create(roles);
        }
    }

    public void initFilms() throws SQLException {
        FilmDAO filmDao = HelperFactory.getHelper().getFilmDao();

        films = filmDao.queryForAll();

        if(films == null || films.size() == 0) {
            films = new ArrayList<Film>();
            Film film1 = new Film();
            film1.setName("Пила");
            film1.setDescription("Пила: Игра на выживание» (англ. Saw) — американский фильм ужасов 2004 года. Премьера фильма состоялась 19 января 2004 года. Изначально планировалось выпустить фильм только для продажи на видео, но премьера на кинофестивале «Санденс» изменила это решение. Триллер очень понравился зрителям и 29 октября вышел в широкий прокат. За первые выходные фильм собрал 18 млн $ (при этом бюджет составил 1,2 млн), а общие кассовые сборы составили $103 096 345, из них в США фильм собрал $55 185 045, в остальных странах $47 911 300. По окончании дебютного уик-энда было сразу же принято решение о запуске сиквела, за которым последовала целая серия фильмов.");
            film1.setGenre(genres.get(0));
            Film film2 = new Film();
            film2.setName("Пила 2");
            film2.setDescription("«Пила 2» (англ. Saw II) — фильм ужасов 2005 года. Является сиквелом вышедшего в 2004 году фильма «Пила: Игра на выживание», а также второй фильм серии фильмов про Конструктора. Релиз фильма состоялся 28 октября 2005 года. В первые выходные фильм собрал $31,5 млн, а общие мировые сборы составили $147 млн. Весь фильм, кроме некоторых сцен, был снят в одном здании за 25 дней. Бюджет фильма составил $4 млн. С учётом рекламного бюджета, производство и продвижение картины обошлось в $6 млн[1].");
            film2.setGenre(genres.get(1));

            films.add(film1);
            films.add(film2);

            filmDao.create(films);
        }
    }

    public void initGenre() throws SQLException {
        GenreDAO genreDao = HelperFactory.getHelper().getGenreDao();

        genres = genreDao.queryForAll();

        if(genres == null || genres.size() == 0) {
            genres = new ArrayList<Genre>();
            Genre genre1 = new Genre();
            genre1.setName("Ужасы");
            Genre genre2 = new Genre();
            genre2.setName("Комедия");

            genres.add(genre1);
            genres.add(genre2);

            genreDao.create(genres);
        }
    }
}
