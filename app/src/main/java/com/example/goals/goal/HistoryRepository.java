package com.example.goals.goal;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class HistoryRepository {
    private HistoryDAO historyDAO;
    private LiveData<List<History>> histories;

    public HistoryRepository(Application application) {
        GoalRoomDatabase db = GoalRoomDatabase.getDatabase(application);
        historyDAO = db.historyDAO();
        histories = historyDAO.getAllHistories();
    }

    public LiveData<List<History>> getAllHistories(){
        return histories;
    }

    public History getByDate(DMY date){
        try {
            return new getByDateAsyncTask(historyDAO).execute(date).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void insert(final History history){
        new insertAsyncTask(historyDAO).execute(history);
    }

    public void deleteAll(){
        new deleteAllAsyncTask(historyDAO).execute();
    }

    public void update(final History history){
        new updateAsyncTask(historyDAO).execute(history);
    }


    private static class  getByDateAsyncTask extends AsyncTask<DMY, Void, History> {
        private final HistoryDAO dao;

        getByDateAsyncTask(HistoryDAO dao){
            this.dao = dao;
        }

        @Override
        protected History doInBackground(DMY... dmys) {
            return dao.getByDate(dmys[0].getDay(), dmys[0].getMonth(), dmys[0].getYear());
        }
    }
    private static class insertAsyncTask extends AsyncTask<History, Void, Void> {
        private final HistoryDAO dao;
        insertAsyncTask(HistoryDAO dao){
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(History... histories) {
            dao.insert(histories[0]);
            return null;
        }
    }

    private static class deleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private final HistoryDAO dao;
        deleteAllAsyncTask(HistoryDAO dao){
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAll();
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<History, Void, Void> {
        private final HistoryDAO dao;
        updateAsyncTask(HistoryDAO dao){
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(History... histories) {
            dao.update(histories[0]);
            return null;
        }
    }
}
