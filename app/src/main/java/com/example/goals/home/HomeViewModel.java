package com.example.goals.home;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.goals.goal.Goal;
import com.example.goals.goal.GoalRepostitory;

import java.util.List;

public class HomeViewModel extends AndroidViewModel {
    private Goal goal;
    private int steps;
    public HomeViewModel(Application application) {
        super(application);
    }


    public Goal getGoal() {
        return goal; // ToDO Deep copy
    }

    public void setGoal(Goal goal){
        this.goal = goal;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps){
        this.steps = steps;
    }

    public void addSteps(int steps) {
        this.steps = this.steps + steps;
    }
}
