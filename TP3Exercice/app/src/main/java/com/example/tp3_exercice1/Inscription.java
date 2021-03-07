package com.example.tp3_exercice1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

public class Inscription extends AppCompatActivity {

    EditText lastname, firstname, age, phone_number;
    Button signup, submit, planning;
    String uuid;

    public static String LN_KEY = "LASTNAME";
    public static String FN_KEY = "FIRSTNAME";
    public static String A_KEY = "AGE";
    public static String PN_KEY = "PHONE_NUMBER";
    public static String ID_KEY = "ID";
    public static String F_KEY = "FILE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lastname = findViewById(R.id.lastname);
        firstname = findViewById(R.id.firstname);
        age = findViewById(R.id.age);
        phone_number = findViewById(R.id.phone_number);
        signup = findViewById(R.id.signup);
        submit = findViewById(R.id.submit);
        planning = findViewById(R.id.planning);

        if(savedInstanceState != null) {
            if (savedInstanceState.containsKey(LN_KEY))
                lastname.setText(savedInstanceState.getString(LN_KEY));

            if (savedInstanceState.containsKey(FN_KEY))
                firstname.setText(savedInstanceState.getString(FN_KEY));

            if (savedInstanceState.containsKey(A_KEY))
                age.setText(savedInstanceState.getString(A_KEY));

            if (savedInstanceState.containsKey(PN_KEY))
                phone_number.setText(savedInstanceState.getString(PN_KEY));

            if (savedInstanceState.containsKey(ID_KEY))
                uuid = savedInstanceState.getString(ID_KEY);
        }

        planning.setOnClickListener(v -> {
            Intent intent = new Intent(this, Planning.class);
            startActivity(intent);
        });

        signup.setOnClickListener(v -> {
            if(lastname.getText().length() == 0 || firstname.getText().length() == 0 || age.getText().length() == 0 || phone_number.getText().length() == 0) {
                Toast.makeText(this, R.string.warning, Toast.LENGTH_SHORT).show();
                return;
            }
            uuid = UUID.randomUUID().toString();
            Toast.makeText(this, R.string.success, Toast.LENGTH_SHORT).show();
        });

        submit.setOnClickListener(v -> {
            if(lastname.getText().length() == 0 || firstname.getText().length() == 0 || age.getText().length() == 0 || phone_number.getText().length() == 0) {
                Toast.makeText(this, R.string.warning, Toast.LENGTH_SHORT).show();
                return;
            }
            uuid = UUID.randomUUID().toString();
            String filename = lastname.getText().toString().concat(uuid);

            try {
                FileOutputStream file = openFileOutput(filename, Context.MODE_PRIVATE);
                file.write(lastname.getText().toString().concat("\n").getBytes());
                file.write(firstname.getText().toString().concat("\n").getBytes());
                file.write(age.getText().toString().concat("\n").getBytes());
                file.write(phone_number.getText().toString().concat("\n").getBytes());
                file.write(uuid.getBytes());
                file.close();
            } catch (Exception ignored) { }

            Toast.makeText(this, R.string.success, Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, Informations.class);
            intent.putExtra(F_KEY, filename);
            startActivity(intent);
        });
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

            if (savedInstanceState.containsKey(LN_KEY))
                lastname.setText(savedInstanceState.getString(LN_KEY));

            if (savedInstanceState.containsKey(FN_KEY))
                firstname.setText(savedInstanceState.getString(FN_KEY));

            if (savedInstanceState.containsKey(A_KEY))
                age.setText(savedInstanceState.getString(A_KEY));

            if (savedInstanceState.containsKey(PN_KEY))
                phone_number.setText(savedInstanceState.getString(PN_KEY));

            if (savedInstanceState.containsKey(ID_KEY))
                uuid = savedInstanceState.getString(ID_KEY);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        if(lastname.getText().length() > 0)
            savedInstanceState.putString(LN_KEY, lastname.getText().toString());

        if(firstname.getText().length() > 0)
            savedInstanceState.putString(FN_KEY, firstname.getText().toString());

        if(age.getText().length() > 0)
            savedInstanceState.putString(A_KEY, age.getText().toString());

        if(phone_number.getText().length() > 0)
            savedInstanceState.putString(PN_KEY, phone_number.getText().toString());

        if(uuid != null)
            savedInstanceState.putString(ID_KEY, uuid);
    }
}