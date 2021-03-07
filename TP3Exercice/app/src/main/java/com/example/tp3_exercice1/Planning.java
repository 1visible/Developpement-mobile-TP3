package com.example.tp3_exercice1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.room.Room;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Planning extends AppCompatActivity {

    TextView slot1, slot2, slot3, slot4;
    Button file_data, db_data;
    PlanningModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planning);

        slot1 = findViewById(R.id.slot1);
        slot2 = findViewById(R.id.slot2);
        slot3 = findViewById(R.id.slot3);
        slot4 = findViewById(R.id.slot4);
        file_data = findViewById(R.id.file_data);
        db_data = findViewById(R.id.db_data);

        final Observer<List<Slot>> observer = slots -> {
            if(slots.size() != 4)
                return;
            slot1.setText(getString(R.string.slot1, slots.get(0).getContent()));
            slot2.setText(getString(R.string.slot2, slots.get(1).getContent()));
            slot3.setText(getString(R.string.slot3, slots.get(2).getContent()));
            slot4.setText(getString(R.string.slot4, slots.get(3).getContent()));
        };

        model = ViewModelProviders.of(this).get(PlanningModel.class);
        model.getSlots().observe(this, observer);
        model.findAll().observe(this, observer);

        try {
            FileOutputStream file = openFileOutput("planning", Context.MODE_PRIVATE);
            file.write("Rendez-vous 1 (file)\n".getBytes());
            file.write("Rendez-vous 2 (file)\n".getBytes());
            file.write("Rendez-vous 3 (file)\n".getBytes());
            file.write("Rendez-vous 4 (file)".getBytes());
            file.close();
        } catch (Exception ignored) { }

        file_data.setOnClickListener(v -> updateFromFile());
        db_data.setOnClickListener(v -> updateFromDb());

        final Handler handler1 = new Handler(Looper.getMainLooper());
        handler1.postDelayed(this::updateFromFile, 30000);

        final Handler handler2 = new Handler(Looper.getMainLooper());
        handler2.postDelayed(this::updateFromDb, 45000);
    }

    private void updateFromDb() {
        if(model.findAll().getValue().size() < 4) {
            model.insert(new Slot("Rendez-vous 1 (db)"));
            model.insert(new Slot("Rendez-vous 2 (db)"));
            model.insert(new Slot("Rendez-vous 3 (db)"));
            model.insert(new Slot("Rendez-vous 4 (db)"));
        }
    }

    private void updateFromFile() {
        try {
            FileInputStream file = openFileInput("planning");
            StringBuilder content = new StringBuilder();
            byte[] buffer = new byte[1024];
            int n;

            while ((n = file.read(buffer)) != -1)
                content.append(new String(buffer, 0, n));

            String[] sSlots = content.toString().split("\n");
            List<Slot> slots = new ArrayList<>();
            for(String slot : sSlots)
                slots.add(new Slot(slot));
            model.getSlots().setValue(slots);
        } catch (Exception ignored) { }
    }
}