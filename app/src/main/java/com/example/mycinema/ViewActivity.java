package com.example.mycinema;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class ViewActivity extends Activity {

    TextView name_view, description_view, genre_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view);

        Bundle arguments = getIntent().getExtras();
        String name, description, genre;
        if(arguments != null) {
            name = arguments.getString("name");
            description = arguments.getString("description");
            genre = arguments.getString("genre");
        }
        else {
            name = "";
            description = "";
            genre = "";
        }

        name_view = (TextView) findViewById(R.id.name);
        description_view = (TextView) findViewById(R.id.description);
        genre_view = (TextView) findViewById(R.id.genre);

        name_view.setText("Название: " + name);
        description_view.setText(description);
        genre_view.setText(genre);

    }
}
