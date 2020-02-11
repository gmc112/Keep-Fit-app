package com.example.goals.goal;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class GoalRepostitory {
    private GoalDAO goalDAO;
    private LiveData<List<Goal>> allGoals;

    GoalRepostitory(Application application) {
        GoalRoomDatabase db = GoalRoomDatabase.getDatabase(application);
        goalDAO = db.goalDAO();
        allGoals = goalDAO.getAllGoals();
    }

    LiveData<List<Goal>> getAllGoals(){
        return allGoals;
    }

    void insert(final Goal goal){
        GoalRoomDatabase.databaseWriteExecutor.execute(
                ()-> goalDAO.insert(goal)
        );
    }
}
