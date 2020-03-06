package com.example.goals.history;


import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.goals.goal.Goal;

import java.util.List;

public class HistoryGoals {

    @Embedded
    public  Goal goal;

    @Relation(
            parentColumn = "id",
            entityColumn = "goalId"
    )
    public List<History> histories;
}
