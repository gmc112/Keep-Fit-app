package com.example.goals.goal;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
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
    @Embedded
    private DMY date;

    @NonNull
    private int steps;

    @NonNull
    private String name;

    @NonNull
    private int target;


    public History(@NonNull DMY date, int steps, @NonNull String name, int target) {
        this.date = date;
        this.steps = steps;
        this.name = name;
        this.target = target;
    }

    public History(Date date, int steps, @NonNull String name, int target){
        this.date = new DMY(date);
        this.steps = steps;
        this.name = name;
        this.target = target;
    }

    public History(@NonNull DMY date, int steps, Goal goal){
        this.date = date;
        this.steps = steps;
        this.name = goal.getName();
        this.target = goal.getTarget();
    }

    public History(Date date,int steps, Goal goal){
        this.date = new DMY(date);
        this.steps = steps;
        this.name = goal.getName();
        this.target = goal.getTarget();
    }

    public History(Date date, Goal goal){
        this.date = new DMY(date);
        this.steps = 0;
        this.name = goal.getName();
        this.target = goal.getTarget();
    }

    public void setGoal(@NonNull Goal goal) {
        this.name = goal.getName();
        this.target = goal.getTarget();
    }


    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    @NonNull
    public DMY getDate() {
        return date;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public int getTarget() {
        return target;
    }

    @Override
    public boolean equals(Object object){
        if (object == null){
            return false;
        }
        if (object instanceof History){
            History hist = (History) object;
            if(hist.getDate().equals(date)){
                return true;
            }

        }
        return false;

    }


}

