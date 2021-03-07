package com.example.tp3_exercice1;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Slot.class}, version = 3, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;
    public abstract SlotDao slotDao();

    private static AppDatabase.Callback dbCallback =
        new AppDatabase.Callback(){
            @Override
            public void onOpen (@NonNull SupportSQLiteDatabase db){
                super.onOpen(db);
                new ClearDbAsync(INSTANCE).execute();
            }
        };

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null)
            synchronized (AppDatabase.class) {
                if (INSTANCE == null)
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "planning")
                            .fallbackToDestructiveMigration()
                            .addCallback(dbCallback)
                            .build();
            }

        return INSTANCE;
    }

    private static class ClearDbAsync extends AsyncTask<Void, Void, Void> {

        private final SlotDao dao;

        ClearDbAsync(AppDatabase db) {
            dao = db.slotDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            dao.deleteAll();
            return null;
        }
    }
}
