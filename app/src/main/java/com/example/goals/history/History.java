package com.example.goals.history;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.goals.Utils.Converters;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Entity(tableName = "history_table")
public class History {


    @PrimaryKey
    @NonNull
    @TypeConverters(Converters.class)
    private Date date;

    @NonNull
    private int steps;



    @NonNull
    private long goalId;


    public History(Date date, int steps, long goalId){
        this.date = date;
        this.steps = steps;
        this.goalId = goalId;
    }

    @Ignore
    public History(Date date, long goalId){
        this.date = date;
        this.steps = 0;
        this.goalId = goalId;
    }



    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(@NonNull Date date) {
        this.date = date;
    }

    public long getGoalId() {
        return goalId;
    }

    public void setGoalId(long goalId) {
        this.goalId = goalId;
    }
    @Override
    public boolean equals(Object object){
        if (object instanceof History){
            History hist = (History) object;
            LocalDate localDate = date
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            LocalDate localDateComp = hist.getDate()
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            if (localDate.getDayOfYear() == localDateComp.getDayOfYear()){
                if(localDate.getYear() == localDateComp.getYear()){
                    return true;
                }
            }
        }
        return false;

    }


}
