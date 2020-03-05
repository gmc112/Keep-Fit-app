package com.example.goals.history;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class HistoryViewModel extends AndroidViewModel {
    private final HistoryRepository repository;

    public HistoryViewModel(@NonNull Application application) {
        super(application);
        repository = new HistoryRepository(application);
//        historyBetween = repository.getHistoryBetween();      ToDo: do this
    }

    void insert(History history){
        repository.insert(history);
    }

    LiveData<List<History>> getAllHistories(){
        return repository.getAllHistories();
    }
}
