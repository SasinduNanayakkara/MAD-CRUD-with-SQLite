package com.example.crudwithsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Home extends AppCompatActivity {
    EditText username, password;
    Button login, register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        username = findViewById(R.id.profile_username);
        password =findViewById(R.id.profile_password);
        login = findViewById(R.id.H_Login);
        register = findViewById(R.id.H_Register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ProfileManagement.class);
                startActivity(intent);
            }
        });
    }
}