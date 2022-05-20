package com.example.mycinema;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ViewActivity extends Activity {

    ImageView image_view;
    TextView name_view, description_view, genre_view;
    Button issue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view);

        Bundle arguments = getIntent().getExtras();
        String name, description, genre, email, role;
        int image;
        if(arguments != null) {
            name = arguments.getString("name");
            description = arguments.getString("description");
            genre = arguments.getString("genre");
            email = arguments.getString("user");
            role = arguments.getString("role");
            image = arguments.getInt("image");
        }
        else {
            email = "";
            name = "";
            description = "";
            genre = "";
            role = "";
            image = 0;
        }

        name_view = (TextView) findViewById(R.id.name);
        description_view = (TextView) findViewById(R.id.description);
        genre_view = (TextView) findViewById(R.id.genre);
        issue = (Button) findViewById(R.id.arrange);
        image_view = (ImageView) findViewById(R.id.imageView);

        name_view.setText("Название: " + name);
        description_view.setText("Описание: " + description);
        genre_view.setText("Жанр: " + genre);
        image_view.setImageResource(image);

        issue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), PayActivity.class);
                i.putExtra("name", name);
                i.putExtra("user", email);
                i.putExtra("role", role);
                startActivity(i);
            }
        });
    }
}
