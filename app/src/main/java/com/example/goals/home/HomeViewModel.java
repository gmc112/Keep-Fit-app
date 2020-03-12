package com.example.goals.home;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.example.goals.goal.DMY;
import com.example.goals.goal.Goal;

import java.util.List;

public class HomeViewModel extends AndroidViewModel {
    private Goal goal;
    private int steps;
    private DMY date;
    private boolean notifySuccess = false;
    private boolean notifyClose = false;

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

    public void updateActiveGoal(List<Goal> goals) {
        for(Goal goal: goals){
            if (goal.isActive()){
                this.goal= goal;
                break;
            }
        }
    }

    public DMY getDate() {
        return date;
    }

    public void setDate(DMY date) {
        this.date = date;
    }

    public boolean isNotifySuccess() {
        return notifySuccess;
    }

    public void setNotifySuccess(boolean notifySuccess) {
        this.notifySuccess = notifySuccess;
    }

    public boolean isNotifyClose() {
        return notifyClose;
    }

    public void setNotifyClose(boolean notifyClose) {
        this.notifyClose = notifyClose;
    }

    public void clearNotifications() {
        notifyClose = false;
        notifySuccess = false;
    }
}
