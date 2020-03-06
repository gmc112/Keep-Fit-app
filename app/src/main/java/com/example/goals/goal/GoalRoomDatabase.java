package com.example.goals.goal;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Goal.class}, version = 1, exportSchema = false)
public abstract class GoalRoomDatabase extends RoomDatabase {

    private static RoomDatabase.Callback roomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(
                    () -> {
                        GoalDAO dao = instance.goalDAO();
                        Goal goal = new Goal(1583385277578L, "Default", 10000 );
                        dao.insert(goal);
                    });
        }
    };

    public abstract GoalDAO goalDAO();

    private static volatile GoalRoomDatabase instance;
    private static final int NUMBER_OF_THREADS = 4;

    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static GoalRoomDatabase getDatabase(final Context context) {
        if (instance == null) {
            synchronized (GoalRoomDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            GoalRoomDatabase.class,
                            "goal_database")
                            .addCallback(roomDatabaseCallback)
                            .build();
                }
            }
        }
        return instance;
    }


}
