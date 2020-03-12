package com.example.goals.goal;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.TypeConverters;
import androidx.room.Update;

import com.example.goals.Utils.Converters;

import java.util.Date;
import java.util.List;

@Dao
public interface HistoryDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(History history);

    @Update
    void update(History history);

    @Query("Select * from history_table ORDER BY timestamp DESC")
    LiveData<List<History>> getAllHistories();

    @Query("DELETE from history_table")
    void deleteAll();

    @Query("SELECT * from history_table LIMIT 1")
    History[] getAnyHistory();

    @Query("SELECT * from history_table where day = :day and month = :month and year = :year LIMIT 1")
    History getByDate(Integer day, Integer month, Integer year);
}
