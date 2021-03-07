package com.example.tp3_exercice1;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

public class PlanningModel extends AndroidViewModel {
    private SlotRepository repository;
    private LiveData<List<Slot>> slots;
    private MutableLiveData<List<Slot>> mSlots;

    public PlanningModel(Application application) {
        super(application);
        repository = new SlotRepository(application);
        slots = repository.findAll();
    }

    public MutableLiveData<List<Slot>> getSlots() {
        if(mSlots == null)
            initSlots();
        return mSlots;
    }

    private void initSlots() {
        List<Slot> slots = new ArrayList<>();
        slots.add(new Slot("Rendez-vous 1"));
        slots.add(new Slot("Rendez-vous 2"));
        slots.add(new Slot("Rendez-vous 3"));
        slots.add(new Slot("Rendez-vous 4"));

        mSlots = new MutableLiveData<>();
        mSlots.setValue(slots);
    }

    public LiveData<List<Slot>> findAll() {
        return slots;
    }

    public void insert(Slot slot) {
        repository.insert(slot);
    }
}
