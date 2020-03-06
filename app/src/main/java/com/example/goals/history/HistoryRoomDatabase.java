package com.example.goals.history;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;


import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Database(entities = {History.class}, version = 1, exportSchema = false)
public abstract class HistoryRoomDatabase extends RoomDatabase {

    private static RoomDatabase.Callback roomDatabaseCallback = new RoomDatabase.Callback() {
        public void onCreate(@NonNull SupportSQLiteDatabase db){
            super.onCreate(db);
            databaseWriteExecutor.execute(
                    () -> {
                        HistoryDAO dao = instance.historyDAO(); // ToDo: maybe add default
                        History history = new History(Calendar.getInstance().getTime(), 0, 1583385277578L);
                    });
        }
    };

    public abstract HistoryDAO historyDAO();
    private static volatile HistoryRoomDatabase instance;

    private static final int NUMBER_OF_THREADS = 4;

    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static HistoryRoomDatabase getDatabase(final Context context){
        if (instance == null){
            synchronized (HistoryRoomDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            HistoryRoomDatabase.class,
                            "history_database")
                            .addCallback(roomDatabaseCallback)
                            .build();
                }
            }
        }
        return instance;
    }
}
