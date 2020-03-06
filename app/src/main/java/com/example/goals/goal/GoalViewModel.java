package com.example.goals.goal;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.goals.history.HistoryGoals;

import java.util.List;

public class GoalViewModel extends AndroidViewModel {

    private GoalRepostitory repostitory;
    private LiveData<List<Goal>> allGoals;

    public GoalViewModel(Application application) {
        super(application);
        repostitory = new GoalRepostitory(application);
        allGoals = repostitory.getAllGoals();
    }

    public LiveData<List<Goal>> getAllGoals() {
        return allGoals;
    }

    public void insert(Goal goal){
        repostitory.insert(goal);
    }

    public List<HistoryGoals> getHistoryGoals() { return repostitory.getHistoryGoals(); }
}
