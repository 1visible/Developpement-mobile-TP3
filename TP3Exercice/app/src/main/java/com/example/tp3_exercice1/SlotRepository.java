package com.example.tp3_exercice1;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class SlotRepository {
    private SlotDao slotDao;
    private LiveData<List<Slot>> slots;

    SlotRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        slotDao = db.slotDao();
        slots = slotDao.findAll();
    }

    LiveData<List<Slot>> findAll() {
        return slots;
    }

    public void insert(Slot slot) {
        new insertAsyncTask(slotDao).execute(slot);
    }

    private static class insertAsyncTask extends AsyncTask<Slot, Void, Void> {
        private SlotDao mAsyncTaskDao;

        insertAsyncTask(SlotDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Slot... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
