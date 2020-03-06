package com.example.goals.history;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.goals.goal.Goal;
import com.example.goals.goal.GoalRoomDatabase;

import java.util.List;

public class HistoryRepository {
    private HistoryDAO historyDAO;
    private LiveData<History> historyBetween;
    private LiveData<List<History>> histories;
    HistoryRepository(Application application) {
        HistoryRoomDatabase db = HistoryRoomDatabase.getDatabase(application);
        historyDAO = db.historyDAO();
//        historyBetween = historyDAO.getHistoryByDate()
        histories = historyDAO.getAllHistories();

    }

    void update(final History history){
        HistoryRoomDatabase.databaseWriteExecutor.execute(()->historyDAO.updateHistory(history));
    }
    LiveData<History> getHistoryBetween() {
        return historyBetween;
    }

    LiveData<List<History>> getAllHistories(){
        return histories;
    }
    void insert(final History history){
        HistoryRoomDatabase.databaseWriteExecutor.execute(()-> historyDAO.insertHistory(history));
    }

}
