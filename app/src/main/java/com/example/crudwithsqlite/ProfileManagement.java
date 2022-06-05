package com.example.crudwithsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.crudwithsqlite.Database.DBHandler;

public class ProfileManagement extends AppCompatActivity {
    EditText username, password, dob;
    Button add, update;
    RadioButton male, female;
    String gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_management);

        username = findViewById(R.id.PM_username);
        password = findViewById(R.id.PM_password);
        dob = findViewById(R.id.PM_dob);
        add = findViewById(R.id.PM_Add);
        update = findViewById(R.id.PM_Update);
        male = findViewById(R.id.PM_Male);
        female = findViewById(R.id.PM_Female);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EditProfile.class);
                startActivity(intent);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (male.isChecked()) {
                    gender = "Male";
                }
                else {
                    gender = "Female";
                }

                DBHandler dbHandler = new DBHandler(getApplicationContext());
                long newID = dbHandler.addInfo(username.getText().toString(), dob.getText().toString(), password.getText().toString(), gender);

                Toast.makeText(ProfileManagement.this, "User Added successfully user ID : " + newID, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), EditProfile.class);
                startActivity(intent);
                username.setText(null);
                dob.setText(null);
                password.setText(null);
                male.setChecked(true);
                female.setChecked(false);


            }
        });
    }
}