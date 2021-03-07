package com.example.tp3_exercice1;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Slot {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @NonNull
    private final String content;

    public Slot(@NonNull String content) {
        this.content = content;
    }

    @NonNull
    public String getContent() {
        return content;
    }
}
