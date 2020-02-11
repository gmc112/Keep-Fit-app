package com.example.goals.goal;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "goal_table")
public class Goal {
    @PrimaryKey
    @NonNull
    private String name;

    @NonNull
    private int target;

    public Goal(String name, int target){
        this.name = name;
        this.target = target;
    }


    public String getName() {
        return name;
    }

    public int getTarget() {
        return target;
    }
}
