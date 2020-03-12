package com.example.goals.goal;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Goal.class, History.class}, version = 1, exportSchema = false)
public abstract class GoalRoomDatabase extends RoomDatabase {

    private static RoomDatabase.Callback onOpenCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new populateDefaultAsync(instance).execute();
        }
    };

    private static RoomDatabase.Callback onCreateCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db){
            super.onCreate(db);
            new populateDefaultAsync(instance).execute();
        }
    };

    public abstract GoalDAO goalDAO();

    public abstract HistoryDAO historyDAO();

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
                            .fallbackToDestructiveMigration()
                            .addCallback(onCreateCallback)
                            .addCallback(onOpenCallback)
                            .build();
                }
            }
        }
        return instance;
    }


    private static class populateDefaultAsync extends AsyncTask<Void, Void, Void> {
        private final GoalDAO dao;
        private final HistoryDAO hDao;
        public populateDefaultAsync(GoalRoomDatabase instance) {
            dao = instance.goalDAO();
            hDao = instance.historyDAO();
        }

        @Override
        protected Void doInBackground(final Void... voids){
            Goal goal = new Goal("Default", 5000, true);
            if(dao.getAnyGoal().length < 1){
                dao.insert(goal);
            }
            if(hDao.getAnyHistory().length < 1){
                History history = new History(Calendar.getInstance().getTime(), goal);
                hDao.insert(history);
            }
            return null;
        }
    }
}
