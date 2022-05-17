package com.example.mycinema;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycinema.model.Film;
import com.example.mycinema.model.Genre;
import com.example.mycinema.model.Schedule;
import com.example.mycinema.viewModel.FilmViewModel;
import com.google.android.material.navigation.NavigationBarView;
import com.j256.ormlite.stmt.query.In;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    Spinner spinner;
    GenreDAO genreDao;
    FilmDAO filmDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Genre> genres = new ArrayList<Genre>();
        List<String> genresList = new ArrayList<String>();
        List<FilmViewModel> films = new ArrayList<FilmViewModel>();

        try {
            genreDao = HelperFactory.getHelper().getGenreDao();
            filmDao = HelperFactory.getHelper().getFilmDao();
            genres = genreDao.queryForAll();
            List<Film> filmsList = filmDao.queryForAll();
            for (Film item: filmsList){
                FilmViewModel filmViewModel = new FilmViewModel();
                filmViewModel.setName(item.getName());
                filmViewModel.setGenre(item.getGenre());
                filmViewModel.setId(item.getId());
                Schedule date = new Schedule();
                date.setDate(new Date());
                filmViewModel.setSchedule(date);
                filmViewModel.setDescription(item.getDescription());
                films.add(filmViewModel);
            }
            for (Genre item: genres) {
                genresList.add(item.getName());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        FilmAdapter.OnFilmClickListener filmClickListener = new FilmAdapter.OnFilmClickListener() {
            @Override
            public void onFilmClick(FilmViewModel film, int position) {
                Intent i = new Intent(getApplicationContext(), ViewActivity.class);
                i.putExtra("name", film.getName());
                i.putExtra("description", film.getDescription());
                i.putExtra("genre", film.getGenre().getName());
                startActivity(i);
            }
        };

        RecyclerView recyclerView = findViewById(R.id.filmRecyclingView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        FilmAdapter filmAdapter = new FilmAdapter(this, films, filmClickListener);
        recyclerView.setAdapter(filmAdapter);

        spinner = findViewById(R.id.spGenre);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, genresList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selected = spinner.getSelectedItem().toString();

                Toast.makeText(getApplicationContext(), selected, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}

class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final List<FilmViewModel> films;
    private final OnFilmClickListener onClickListener;

    interface OnFilmClickListener{
        void onFilmClick(FilmViewModel film, int position);
    }

    FilmAdapter(Context context, List<FilmViewModel> films, OnFilmClickListener onClickListener) {
        this.onClickListener = onClickListener;
        this.films = films;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.film_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        FilmViewModel film = films.get(position);
        holder.imageView.setImageResource(R.drawable.ic_launcher_background);
        holder.nameView.setText(film.getName());
        holder.timeView.setText(film.getScheduleTime());

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                onClickListener.onFilmClick(film, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return films.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView imageView;
        final TextView nameView, timeView;
        ViewHolder(View view){
            super(view);
            imageView = view.findViewById(R.id.icon);
            nameView = view.findViewById(R.id.name);
            timeView = view.findViewById(R.id.date);
        }
    }
}