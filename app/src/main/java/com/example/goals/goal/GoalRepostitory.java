package com.example.goals.goal;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.goals.history.HistoryGoals;

import java.util.List;

public class GoalRepostitory {
    private GoalDAO goalDAO;
    private LiveData<List<Goal>> allGoals;
    private List<HistoryGoals> historyGoals;

    GoalRepostitory(Application application) {
        GoalRoomDatabase db = GoalRoomDatabase.getDatabase(application);
        goalDAO = db.goalDAO();
        allGoals = goalDAO.getAllGoals();
        historyGoals = goalDAO.getHistoryGoals();
    }

    LiveData<List<Goal>> getAllGoals(){
        return allGoals;
    }


    List<HistoryGoals> getHistoryGoals(){return historyGoals; }

    void insert(final Goal goal){
        GoalRoomDatabase.databaseWriteExecutor.execute(
                ()-> goalDAO.insertAndDelete(goal)
        );
    }
}
