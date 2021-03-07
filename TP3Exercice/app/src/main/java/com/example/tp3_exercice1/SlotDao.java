package com.example.tp3_exercice1;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SlotDao {
    @Query("SELECT * FROM slot")
    LiveData<List<Slot>> findAll();

    @Insert
    void insert(Slot slot);

    @Query("DELETE FROM slot")
    void deleteAll();
}
