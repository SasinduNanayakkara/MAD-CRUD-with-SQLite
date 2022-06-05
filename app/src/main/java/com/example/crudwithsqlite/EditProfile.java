package com.example.crudwithsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.crudwithsqlite.Database.DBHandler;

import java.util.List;

public class EditProfile extends AppCompatActivity {

    EditText username, password, dob;
    Button edit, delete, search;
    RadioButton male, female;
    String gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_management);

        username = findViewById(R.id.EP_username);
        password = findViewById(R.id.ED_Password);
        dob = findViewById(R.id.EP_date);
        edit = findViewById(R.id.EP_Edit);
        delete = findViewById(R.id.EP_Delete);
        search = findViewById(R.id.EP_Search);
        male = findViewById(R.id.EP_Male);
        female = findViewById(R.id.EP_Female);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHandler dbHandler = new DBHandler(getApplicationContext());
                List user = dbHandler.readAllInfo(username.getText().toString());

                if (user.isEmpty()) {
                    Toast.makeText(EditProfile.this, "No user", Toast.LENGTH_SHORT).show();
                    username.setText(null);
                }
                else {
                    Toast.makeText(EditProfile.this, "User found", Toast.LENGTH_SHORT).show();
                    username.setText(user.get(0).toString());
                    dob.setText(user.get(1).toString());
                    password.setText(user.get(2).toString());
                    if (user.get(3).toString().equals("Male")) {
                        male.setChecked(true);
                    }
                    else {
                        female.setChecked(true);
                    }
                }
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (male.isChecked()) {
                    gender = "Male";
                }
                else {
                    gender = "Female";
                }

                DBHandler dbHandler = new DBHandler(getApplicationContext());
                Boolean status = dbHandler.updateInfo(username.getText().toString(), dob.getText().toString(), password.getText().toString(), gender);

                if (status) {
                    Toast.makeText(EditProfile.this, "User Updated", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(EditProfile.this, "User Update Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHandler dbHandler = new DBHandler(getApplicationContext());
                dbHandler.deleteInfo(username.getText().toString());

                Toast.makeText(EditProfile.this, "User deleted successfully", Toast.LENGTH_SHORT).show();

                username.setText(null);
                dob.setText(null);
                password.setText(null);
                male.setChecked(false);
                female.setChecked(false);
            }
        });
    }
}