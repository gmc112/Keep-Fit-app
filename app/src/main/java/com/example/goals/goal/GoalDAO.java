package com.example.goals.goal;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface GoalDAO {
    @Insert
    void insert(Goal goal);

    @Query("DELETE from goal_table")
    void deleteAll();

    @Query("SELECT * from goal_table")
    LiveData<List<Goal>> getAllGoals();

}
