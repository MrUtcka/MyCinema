package com.example.mycinema;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mycinema.model.Film;
import com.example.mycinema.model.Genre;
import com.example.mycinema.model.Role;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddActivity extends AppCompatActivity {

    Button BSelectImage, add;

    ImageView IVPreviewImage;

    TextView name, description, genre;

    List<Genre> genres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);

        BSelectImage = findViewById(R.id.addImage);
        IVPreviewImage = findViewById(R.id.icon);
        add = findViewById(R.id.add);
        name = findViewById(R.id.name);
        description = findViewById(R.id.description);
        genre = findViewById(R.id.genre);

        BSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(description != null && name != null && genre != null) {
                    try {
                        FilmDAO filmDao = HelperFactory.getHelper().getFilmDao();
                        Film film = new Film();
                        film.setName(name.getText().toString());
                        film.setDescription(description.getText().toString());
                        film.setGenre(choose(genre.getText().toString()));

                        filmDao.create(film);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Ошибка!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private Genre choose(String txt) {
        GenreDAO genreDao = null;
        try {
            genreDao = HelperFactory.getHelper().getGenreDao();
            genres = genreDao.queryForAll();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        for (Genre item: genres) {
           if(item.getName().equals(txt))
               return item;
        }

        return genres.get(0);
    }

    private void imageChooser()
    {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        launchSomeActivity.launch(i);
    }

    ActivityResultLauncher<Intent> launchSomeActivity
            = registerForActivityResult(
            new ActivityResultContracts
                    .StartActivityForResult(),
            result -> {
                if (result.getResultCode()
                        == Activity.RESULT_OK) {
                    Intent data = result.getData();

                    if (data != null
                            && data.getData() != null) {
                        Uri selectedImageUri = data.getData();
                        Bitmap selectedImageBitmap = null;
                        try {
                            selectedImageBitmap
                                    = MediaStore.Images.Media.getBitmap(
                                    this.getContentResolver(),
                                    selectedImageUri);
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }
                        IVPreviewImage.setImageBitmap(
                                selectedImageBitmap);
                    }
                }
            });
}
