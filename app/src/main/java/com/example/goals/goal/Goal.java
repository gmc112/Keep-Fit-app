package com.example.goals.goal;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Calendar;

@Entity(tableName = "goal_table")
public class Goal {
    @PrimaryKey
    private long id;

    @NonNull
    private String name;

    @NonNull
    private int target;

    public Goal(long id, String name, int target){
        this.id = id;
        this.name = name;
        this.target = target;
    }

    @Ignore
    public Goal(String name, int target){
        id = Calendar.getInstance().getTimeInMillis();
        this.name = name;
        this.target = target;
    }


    public String getName() {
        return name;
    }

    public int getTarget() {
        return target;
    }

    public void setId(long id){
        this.id = id;
    }

    public long getId(){
        return id;
    }
    @Override
    public boolean equals(Object object){
        if(!(object instanceof Goal)) {
            return false;
        }
        Goal g = (Goal) object;
        if (id == g.getId()){
            return true;
        }
        return false;
    }
}

