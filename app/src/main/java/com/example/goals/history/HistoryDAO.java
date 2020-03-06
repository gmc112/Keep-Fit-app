package com.example.goals.history;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.TypeConverters;
import androidx.room.Update;

import com.example.goals.Utils.Converters;

import java.util.Date;
import java.util.List;

@Dao
public interface HistoryDAO {

    @Insert
    void insertHistory(History history);

    @Update
    void updateHistory(History history);

    @Query("Select * from history_table")
    LiveData<List<History>> getAllHistories();

    @TypeConverters(Converters.class)
    @Query("Select * from history_table where date between :start and :end ")
    LiveData<History> getHistoryByDate(Date start, Date end);
}
