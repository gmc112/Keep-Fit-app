package com.example.goals.history;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.goals.Utils.Converters;
import com.example.goals.goal.Goal;

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
    private String name;

    @NonNull
    private int target;


    public History(Date date, int steps, String name, int target){
        this.date = date;
        this.steps = 0;
        this.name = name;
        this.target = target;
    }

    public History(Date date, Goal goal){
        this.date = date;
        this.steps = 0;
        this.name = goal.getName();
        this.target = goal.getTarget();
    }

    @NonNull
    public Goal getGoal() {
        return new Goal(name, target);
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
    public Date getDate() {
        return date;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public int getTarget() {
        return target;
    }
}
