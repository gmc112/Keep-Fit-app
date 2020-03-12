package com.example.goals.history;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.goals.goal.DMY;
import com.example.goals.goal.History;
import com.example.goals.goal.HistoryRepository;

import java.util.List;

public class HistoryViewModel extends AndroidViewModel {
    private final HistoryRepository repository;
    private LiveData<List<History>> histories;
    public HistoryViewModel(@NonNull Application application) {
        super(application);
        repository = new HistoryRepository(application);
        histories = repository.getAllHistories();

    }

    public void insert(History history){
        repository.insert(history);
    }

    public History getHistoryByDate(DMY date){
        return repository.getByDate(date);
    }

    public LiveData<List<History>> getAllHistories(){
        return histories;
    }
    public void deleteAll(){
        repository.deleteAll();
    }
    public void update(History history) {
        List<History> histories = this.histories.getValue();
        if(histories != null){
            if(histories.contains(history)){
                repository.update(history);
                return;
            }
        }
        repository.insert(history);
    }

}

