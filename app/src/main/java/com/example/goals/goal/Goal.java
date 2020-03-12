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

    @NonNull
    private boolean active;

    public Goal(String name, int target, boolean active){
        this.name = name;
        this.target = target;
        this.active = active;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public String getName() {
        return name;
    }

    public int getTarget() {
        return target;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
