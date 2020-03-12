package com.example.goals.goal;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface GoalDAO {
    @Insert
    void insert(Goal goal);

    @Update
    void update(Goal goal);

    @Query("DELETE from goal_table")
    void deleteAll();

    @Delete
    void delete(Goal goal);

    @Query("SELECT * from goal_table ORDER BY active desc")
    LiveData<List<Goal>> getAllGoals();

    @Query("SELECT * from goal_table LIMIT 1")
    Goal[] getAnyGoal();

    @Query("SELECT * from goal_table ORDER BY active desc LIMIT 1")
    Goal getActiveGoal();
}
