package com.example.tp3_exercice1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Informations extends AppCompatActivity {

    TextView lastname, firstname, age, phone_number, uuid, usages;
    Intent intent;
    Utilisation utilisation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informations);

        lastname = findViewById(R.id.lastname);
        firstname = findViewById(R.id.firstname);
        age = findViewById(R.id.age);
        phone_number = findViewById(R.id.phone_number);
        uuid = findViewById(R.id.uuid);
        usages = findViewById(R.id.usages);
        intent = getIntent();
        utilisation = new Utilisation();

        getLifecycle().addObserver(utilisation);

        if(intent != null && intent.hasExtra(Inscription.F_KEY)) {
            String filename = intent.getStringExtra(Inscription.F_KEY);
            try {
                FileInputStream file = openFileInput(filename);
                StringBuilder content = new StringBuilder();
                byte[] buffer = new byte[1024];
                int n = 0;

                while ((n = file.read(buffer)) != -1)
                    content.append(new String(buffer, 0, n));

                String[] informations = content.toString().split("\n");
                lastname.append(" : " + informations[0]);
                firstname.append(" : " + informations[1]);
                age.append(" : " + informations[2]);
                phone_number.append(" : " + informations[3]);
                uuid.append(" : " + informations[4]);
                usages.append(" " + utilisation.getCounter());

            } catch (Exception ignored) { }
        }
    }

}