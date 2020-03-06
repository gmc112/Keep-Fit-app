package com.example.goals.goal;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.goals.history.HistoryGoals;

import java.util.List;

@Dao
public abstract class GoalDAO {

    @Insert
    abstract void insert(Goal goal);

    @Transaction
    void insertAndDelete(Goal goal){
        delete(goal.getName());
        insert(goal);
    }

    @Query("DELETE from goal_table where name = :name")
    abstract void delete(String name);

    @Query("DELETE from goal_table")
    abstract void deleteAll();

    @Query("SELECT * from goal_table")
    abstract LiveData<List<Goal>> getAllGoals();


    @Transaction @Query("Select * from goal_table")
    abstract List<HistoryGoals> getHistoryGoals();       //TODo make live data

    @Query("Select * from goal_table where id= :id")
    abstract LiveData<Goal> getGoalById(long id);
}
