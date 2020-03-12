package com.example.goals.goal;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class GoalViewModel extends AndroidViewModel {

    private GoalRepository repository;
    private LiveData<List<Goal>> allGoals;

    public GoalViewModel(Application application) {
        super(application);
        repository = new GoalRepository(application);
        allGoals = repository.getAllGoals();
    }

    public LiveData<List<Goal>> getAllGoals() {
        return allGoals;
    }

    public Goal getActiveGoal(){
        return repository.getActiveGoal();
    }

    public void delete(Goal goal){
        repository.delete(goal);
    }

    public void update(Goal goal){
        repository.update(goal);
    }

    public boolean insert(Goal goal){
        List<Goal> goals = getAllGoals().getValue();
        boolean flag = false;
        if(goals != null) {
            for (Goal g : goals) {
                if (g.getName().equals(goal.getName())) {
                    flag = true;
                    break;
                }
            }
            if(!flag){
                repository.insert(goal);
                return true;
            }
        }
        return false;
    }

    public void toggleGoal(Goal goal){
        List<Goal> goals = getAllGoals().getValue();
        if(goals!= null){
            for(Goal g: goals){
                if(g.isActive()){
                    g.setActive(false);
                    update(g);
                    break;
                }
            }
            goal.setActive(true);
            repository.update(goal);

        }



    }


}
