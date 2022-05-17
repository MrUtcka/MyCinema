package com.example.mycinema;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mycinema.model.User;

import java.sql.SQLException;

public class LoginActivity extends Activity {

    private EditText email, password;
    private Button login, registration;
    private UserDAO userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        HelperFactory.setHelper(getApplicationContext());

        try {
            userDao = HelperFactory.getHelper().getUserDao();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);

        login = (Button) findViewById(R.id.login);
        registration = (Button) findViewById(R.id.register);

        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email.getText() != null && password.getText() != null) {
                    User user = new User();
                    user.setEmail(email.getText().toString());
                    user.setName(email.getText().toString());
                    user.setPassword(password.getText().toString());

                    try {
                        userDao.create(user);
                        Toast.makeText(getApplicationContext(), "Успешно!", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(i);
                    } catch (Exception throwables) {
                        throwables.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Ошибка!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = userDao.getUserByEmail(email.getText().toString());
                if(user != null && password.getText() != null && user.getPassword().equals(password.getText().toString())) {
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(i);
                }
            }
        });
    }
}
