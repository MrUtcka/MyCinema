package com.example.mycinema;

import android.app.Application;
import android.util.Log;

import com.example.mycinema.model.Booking;
import com.example.mycinema.model.Film;
import com.example.mycinema.model.Genre;
import com.example.mycinema.model.Place;
import com.example.mycinema.model.Role;
import com.example.mycinema.model.Row;
import com.example.mycinema.model.Schedule;

import java.io.Console;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MyApplication extends Application {

    List<Role> roles;
    List<Genre> genres;
    List<Film> films;
    List<Booking> sctest;

    PlaceDAO placeDao;
    List<Place> places = new ArrayList<>();
    List<Place> placesA = new ArrayList<>();
    List<Place> placesB = new ArrayList<>();
    List<Place> placesC = new ArrayList<>();
    List<Place> placesD = new ArrayList<>();
    List<Place> placesE = new ArrayList<>();


    RowDAO rowDao;
    List<Row> rows = new ArrayList<>();

    List<Place> currPlaces = new ArrayList<Place>();

    @Override
    public void onCreate() {
        super.onCreate();
        HelperFactory.setHelper(getApplicationContext());
        try {
            initRoles();
            initGenre();
            initFilms();
            initHall();
            test();
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
            film1.setImage(R.drawable.saw);

            Film film2 = new Film();
            film2.setName("Человек-паук: Нет пути домой");
            film2.setDescription("«Челове́к-пау́к: Нет пути́ домо́й» (англ. Spider-Man: No Way Home) — американский супергеройский фильм, основанный на персонаже комиксов Marvel Человеке-пауке, созданный Columbia Pictures и Marvel Studios и распространяемый Sony Pictures Releasing. Продолжение фильмов «Человек-паук: Возвращение домой» (2017) и «Человек-паук: Вдали от дома» (2019), а также 27-я по счёту картина в медиафраншизе «Кинематографическая вселенная Marvel» (КВМ). Также фильм является кроссовером других фильмов о Человеке-пауке. Режиссёром фильма выступил Джон Уоттс, сценаристами — Крис Маккенна и Эрик Соммерс. Роль Питера Паркера / Человека-паука исполняет Том Холланд; в фильме сыграли Зендея, Бенедикт Камбербэтч, Джейкоб Баталон, Джон Фавро, Джейми Фокс, Уиллем Дефо, Альфред Молина, Бенедикт Вонг, Тони Револори, а также Мариса Томей, Эндрю Гарфилд и Тоби Магуайр[⇨]. По сюжету, Питер Паркер обращается за помощью к Стивену Стрэнджу, чтобы снова скрыть от всего мира тайну личности Человека-паука, что приводит к открытию мультивселенной, из-за чего суперзлодеи из альтернативных реальностей проникают во вселенную Паркера[⇨].");
            film2.setGenre(genres.get(5));
            film2.setImage(R.drawable.spidermannowayhome);

            Film film3 = new Film();
            film3.setName("1+1");
            film3.setDescription("«1+1» (фр. Intouchables — «Неприкасаемые») — французская комедийная драма 2011 года, основанная на реальных событиях об успешном аристократе Филиппе, который в результате несчастного случая оказывается в инвалидном кресле и берёт себе в качестве помощника чернокожего бывшего преступника — Дрисса. Главные роли исполняют Франсуа Клюзе и Омар Си, удостоенный за эту актёрскую работу национальной премии «Сезар»[3]. Премьера во Франции прошла 2 ноября 2011 года. В России фильм вышел в прокат 26 апреля 2012 под названием «1+1».");
            film3.setGenre(genres.get(4));
            film3.setImage(R.drawable.oneplusone);

            Film film4 = new Film();
            film4.setName("Пираты Карибского моря: Проклятие черной жемчужины");
            film4.setDescription("«Пираты Карибского моря: Проклятие Чёрной жемчужины»[2][3][4][5] (англ. Pirates of the Caribbean: The Curse of The Black Pearl) — приключенческий фильм о пиратах, действие которого разворачивается на Карибах в первой половине XVIII века. Фильм был поставлен режиссёром Гором Вербински и спродюсирован Джерри Брукхаймером. Идея картины пришла к создателям под впечатлением от одноимённого тематического водного аттракциона в Диснейленде. Это первый фильм Walt Disney Pictures, получивший рейтинг PG-13 от организации MPAA. Официальный российский возрастной рейтинг фильма 12+. Премьера фильма в России состоялась 22 августа 2003 года.");
            film4.setGenre(genres.get(2));
            film4.setImage(R.drawable.piratesofcaribian);

            Film film5 = new Film();
            film5.setName("Пила 3");
            film5.setDescription("«Пила 3» (англ. Saw III) — американский фильм ужасов 2006 года режиссёра Даррена Линна Боусмана, третья часть из серии фильмов Пила. В США фильм собрал $ 80 238 724, в остальном мире $ 84 635 551, что в общей сложности составило $ 164 874 275. Премьера фильма состоялась 26 октября 2006 года. Фильм посвящён памяти Грегга Хоффмана, который умер в декабре 2005 года и являлся продюсером первых двух частей.");
            film5.setGenre(genres.get(0));
            film5.setImage(R.drawable.sawiii);

            films.add(film1);
            films.add(film2);
            films.add(film3);
            films.add(film4);
            films.add(film5);

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
            genre2.setName("Мулитьфильмы");
            Genre genre3 = new Genre();
            genre3.setName("Боевики>");
            Genre genre4 = new Genre();
            genre4.setName("Исторические");
            Genre genre5 = new Genre();
            genre5.setName("Комедия");
            Genre genre6 = new Genre();
            genre6.setName("Фантастика");

            genres.add(genre1);
            genres.add(genre2);
            genres.add(genre3);
            genres.add(genre4);
            genres.add(genre5);
            genres.add(genre6);

            genreDao.create(genres);
        }
    }

    public void initHall() throws SQLException {
        rowDao = HelperFactory.getHelper().getRowDao();
        placeDao = HelperFactory.getHelper().getPlaceDao();

        rows = rowDao.queryForAll();
        places = placeDao.queryForAll();

        if(places == null || places.size() == 0) {
            Place placeA1 = new Place();
            placeA1.setName("A1");
            placeA1.setFree(true);
            Place placeA2 = new Place();
            placeA2.setName("A2");
            placeA2.setFree(true);
            Place placeA3 = new Place();
            placeA3.setName("A3");
            placeA3.setFree(true);
            Place placeA4 = new Place();
            placeA4.setName("A4");
            placeA4.setFree(true);
            Place placeA5 = new Place();
            placeA5.setName("A5");
            placeA5.setFree(true);
            Place placeB1 = new Place();
            placeB1.setName("B1");
            placeB1.setFree(true);
            Place placeB2 = new Place();
            placeB2.setName("B2");
            placeB2.setFree(true);
            Place placeB3 = new Place();
            placeB3.setName("B3");
            placeB3.setFree(true);
            Place placeB4 = new Place();
            placeB4.setName("B4");
            placeB4.setFree(true);
            Place placeB5 = new Place();
            placeB5.setName("B5");
            placeB5.setFree(true);
            Place placeC1 = new Place();
            placeC1.setName("C1");
            placeC1.setFree(true);
            Place placeC2 = new Place();
            placeC2.setName("C2");
            placeC2.setFree(true);
            Place placeC3 = new Place();
            placeC3.setName("C3");
            placeC3.setFree(true);
            Place placeC4 = new Place();
            placeC4.setName("C4");
            placeC4.setFree(true);
            Place placeC5 = new Place();
            placeC5.setName("C5");
            placeC5.setFree(true);
            Place placeD1 = new Place();
            placeD1.setName("D1");
            placeD1.setFree(true);
            Place placeD2 = new Place();
            placeD2.setName("D2");
            placeD2.setFree(true);
            Place placeD3 = new Place();
            placeD3.setName("D3");
            placeD3.setFree(true);
            Place placeD4 = new Place();
            placeD4.setName("D4");
            placeD4.setFree(true);
            Place placeD5 = new Place();
            placeD5.setName("D5");
            placeD5.setFree(true);

            placesA.add(placeA1);
            placesA.add(placeA2);
            placesA.add(placeA3);
            placesA.add(placeA4);
            placesA.add(placeA5);
            placesB.add(placeB1);
            placesB.add(placeB2);
            placesB.add(placeB3);
            placesB.add(placeB4);
            placesB.add(placeB5);
            placesC.add(placeC1);
            placesC.add(placeC2);
            placesC.add(placeC3);
            placesC.add(placeC4);
            placesC.add(placeC5);
            placesD.add(placeD1);
            placesD.add(placeD2);
            placesD.add(placeD3);
            placesD.add(placeD4);
            placesD.add(placeD5);

            places.add(placeA1);
            places.add(placeA2);
            places.add(placeB1);
            places.add(placeB2);
            places.add(placeA3);
            places.add(placeA4);
            places.add(placeB3);
            places.add(placeB4);
            places.add(placeA5);
            places.add(placeB5);
            places.add(placeC1);
            places.add(placeC2);
            places.add(placeC3);
            places.add(placeC4);
            places.add(placeC5);
            places.add(placeD1);
            places.add(placeD2);
            places.add(placeD3);
            places.add(placeD4);
            places.add(placeD5);

        }

        if(rows == null || rows.size() == 0) {
            Row rowA = new Row();
            rowA.setName("A");
            rowA.setPlaces(placesA);
            Row rowB = new Row();
            rowB.setName("B");
            rowB.setPlaces(placesB);
            Row rowC = new Row();
            rowC.setName("C");
            rowC.setPlaces(placesC);
            Row rowD = new Row();
            rowD.setName("D");
            rowD.setPlaces(placesD);

            for (int i = 0; i < 5; i++) {
                placesA.get(i).setRow(rowA);
            }

            for (int i = 0; i < 5; i++) {
                placesB.get(i).setRow(rowB);
            }

            for (int i = 0; i < 5; i++) {
                placesC.get(i).setRow(rowC);
            }

            for (int i = 0; i < 5; i++) {
                placesD.get(i).setRow(rowD);
            }

            rows.add(rowA);
            rows.add(rowB);
            rows.add(rowC);
            rows.add(rowD);

            placeDao.create(places);
            rowDao.create(rows);
        }
    }

    public void test() throws SQLException {
        BookingDAO bookingDao = HelperFactory.getHelper().getBookingDao();

        sctest = bookingDao.queryForAll();
        Log.d("","");
    }
}
