package com.example.mycinema;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycinema.model.Film;
import com.example.mycinema.model.Genre;
import com.example.mycinema.model.Schedule;
import com.example.mycinema.viewModel.FilmViewModel;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    Spinner spinner;
    GenreDAO genreDao;
    FilmDAO filmDao;
    String email;
    String role;
    String selected;
    List<FilmViewModel> films;
    List<Integer> id = new ArrayList<Integer>();
    //FilmAdapter filmAdapter;

    List<String> items = new ArrayList<>();
    List<String> filmsForShow = new ArrayList<>();
    ArrayList<String> listItems;
    ArrayAdapter<String> searchAdapter;
    ListView listView;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Genre> genres = new ArrayList<Genre>();
        List<String> genresList = new ArrayList<String>();
        films = new ArrayList<FilmViewModel>();

        filmsForShow.add("Пила");

        Bundle arguments = getIntent().getExtras();

        if(arguments != null) {
            email = arguments.getString("user");
            role = arguments.getString("role");
        }

        try {
            genreDao = HelperFactory.getHelper().getGenreDao();
            filmDao = HelperFactory.getHelper().getFilmDao();
            genres = genreDao.queryForAll();
            List<Film> filmsList = filmDao.queryForAll();
            for (Genre item: genres) {
                genresList.add(item.getName());
            }
            for (Film item: filmsList){
                FilmViewModel filmViewModel = new FilmViewModel();
                filmViewModel.setName(item.getName());
                filmViewModel.setGenre(item.getGenre());
                filmViewModel.setId(item.getId());
                items.add(item.getName());
                filmViewModel.setImage(item.getImage());
                filmViewModel.setDescription(item.getDescription());
                films.add(filmViewModel);
            }
            for (FilmViewModel item: films){
                id.add(item.getGenre().getId());
            }
            for (int i = 0; i < id.size(); i++) {
                films.get(i).getGenre().setName(genresList.get(id.get(i) - 1));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

//        FilmAdapter.OnFilmClickListener filmClickListener = new FilmAdapter.OnFilmClickListener() {
//            @Override
//            public void onFilmClick(FilmViewModel film, int position) {
//                Intent i = new Intent(getApplicationContext(), ViewActivity.class);
//                i.putExtra("user", email);
//                i.putExtra("name", film.getName());
//                i.putExtra("description", film.getDescription());
//                i.putExtra("genre", film.getGenre().getName());
//                startActivity(i);
//            }
//        };

        spinner = findViewById(R.id.spGenre);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, genresList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selected = spinner.getSelectedItem().toString();
                filmsForShow.clear();
                for (FilmViewModel item: films) {
                    if(item.getGenre().getName().equals(selected))
                        filmsForShow.add(item.getName());
                }

                listView.setAdapter(null);
                listView.setAdapter(searchAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        listView=(ListView)findViewById(R.id.listview);
        editText=(EditText)findViewById(R.id.txtsearch);
        initList();

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("")){

                    initList();
                } else {

                    searchItem(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            FilmViewModel currFilm;
            String filmName;
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
                filmName = listItems.get(position);
                currFilm = film(filmName);

                Intent i = new Intent(getApplicationContext(), ViewActivity.class);
                i.putExtra("name", currFilm.getName());
                i.putExtra("description", currFilm.getDescription());
                i.putExtra("genre", currFilm.getGenre().getName());
                i.putExtra("image", currFilm.getImage());
                i.putExtra("user", email);
                i.putExtra("role", role);

                startActivity(i);
            }
        });
    }

    public FilmViewModel film(String currFilm) {
        for (FilmViewModel item: films){
            if(item.getName().equals(currFilm)) {
                return item;
            }
        }
        return films.get(0);
    }

    public void searchItem(String textToSearch){
        int i = 0;
        for(String item:items){
            if(!item.contains(textToSearch) && !selected.equals(films.get(i).getGenre().getName()) ){
                listItems.remove(item);
            }
            i++;
        }
        searchAdapter.notifyDataSetChanged();
    }

    public void initList(){
        listItems=new ArrayList<>();
        for (String item: items) {
            listItems.add(item);
        }
        //searchAdapter=new ArrayAdapter<String>(this, R.layout.film_item, R.id.name, listItems);
        searchAdapter=new ArrayAdapter<String>(this, R.layout.film_item, R.id.name, filmsForShow);
        listView.setAdapter(searchAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int i = item.getItemId();
        if(i == R.id.addFilm && role.equals("admin")) {
            Intent intent = new Intent(getApplicationContext(), AddActivity.class);
            startActivity(intent);
            return true;
        }
        else if(i == R.id.addSchedule && role.equals("admin")) {
            Intent intent = new Intent(getApplicationContext(), ScheduleActivity.class);
            startActivity(intent);
            return true;
        }
        else if(i == R.id.info) {
            Intent intent = new Intent(getApplicationContext(), InfoActivity.class);
            intent.putExtra("email", email);
            startActivity(intent);
            return true;
        }
        else {
            return super.onOptionsItemSelected(item);
        }
    }
}