package com.example.mycinema;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mycinema.model.Role;
import com.example.mycinema.model.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoginActivity extends Activity {

    private EditText email, password;
    private Button login, registration;
    private UserDAO userDao;
    private RoleDAO roleDao;
    private User user;
    private List<Role> roles;
    private List<String> adminEmails = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        HelperFactory.setHelper(getApplicationContext());

        adminEmails.add("utycraft2000@gmail.com");

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
                if(email.getText() != null && password.getText() != null && emailValidator(email)) {
                    try {
                        roleDao = HelperFactory.getHelper().getRoleDao();
                        roles = roleDao.queryForAll();

                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                    User user = new User();
                    user.setEmail(email.getText().toString());
                    user.setName(email.getText().toString());
                    user.setPassword(password.getText().toString());
                    user.setRole(roles.get(1));

                    try {
                        userDao.create(user);
                        Toast.makeText(getApplicationContext(), "Успешно!", Toast.LENGTH_LONG).show();
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
                user = userDao.getUserByEmail(email.getText().toString());
                try {
                    roleDao = HelperFactory.getHelper().getRoleDao();
                    roles = roleDao.queryForAll();

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                if(user != null && password.getText() != null && user.getPassword().equals(password.getText().toString()) && isAdmin()) {
                    user.setRole(roles.get(user.getRole().getId() - 1));
                    try {
                        userDao.update(user);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    Toast.makeText(getApplicationContext(), user.getRole().getRole(), Toast.LENGTH_LONG).show();
                    i.putExtra("role", "admin");
                    i.putExtra("user", email.getText().toString());
                    startActivity(i);
                }
                else if(user != null && password.getText() != null && user.getPassword().equals(password.getText().toString()) && !isAdmin()) {
                    user.setRole(roles.get(1));
                    try {
                        userDao.update(user);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    Toast.makeText(getApplicationContext(), user.getRole().getRole(), Toast.LENGTH_LONG).show();
                    i.putExtra("role", "user");
                    i.putExtra("user", email.getText().toString());
                    startActivity(i);
                }
            }
        });
    }

    public boolean emailValidator(EditText etMail) {

        String emailToText = etMail.getText().toString();

        if (!emailToText.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailToText).matches()) {
            Toast.makeText(this, "Успешно!", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Toast.makeText(this, "Введите верную почту!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean isAdmin() {
        for (int i = 0; i < adminEmails.size(); i++) {
            if(user.getEmail().equals(adminEmails.get(i))) return true;
            else return false;
        }

        return false;
    }
}
