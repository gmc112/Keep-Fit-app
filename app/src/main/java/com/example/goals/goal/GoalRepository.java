package com.example.goals.goal;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class GoalRepository {
    private GoalDAO goalDAO;
    private LiveData<List<Goal>> allGoals;

    GoalRepository(Application application) {
        GoalRoomDatabase db = GoalRoomDatabase.getDatabase(application);
        goalDAO = db.goalDAO();
        allGoals = goalDAO.getAllGoals();
    }

    LiveData<List<Goal>> getAllGoals(){
        return allGoals;
    }

    void insert(final Goal goal){
        new insertAsyncTask(goalDAO).execute(goal);
    }

    void delete(Goal goal){
        new deleteAsyncTask(goalDAO).execute(goal);
    }

    void deleteAll(){
        new deleteAllAsyncTask(goalDAO).execute();
    }

    void update(Goal goal){
        new updateAsyncTask(goalDAO).execute(goal);
    }

    public Goal getActiveGoal(){
        try {
            return new getActiveGoalAsyncTask(goalDAO).execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class insertAsyncTask extends AsyncTask<Goal, Void, Void> {
        private final GoalDAO dao;
        insertAsyncTask(GoalDAO dao){
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Goal... goals) {
            dao.insert(goals[0]);
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<Goal, Void, Void> {
        private final GoalDAO dao;
        updateAsyncTask(GoalDAO dao){
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Goal... goals) {
            dao.update(goals[0]);
            return null;
        }
    }
    private static class deleteAsyncTask extends AsyncTask<Goal, Void, Void> {
        private final GoalDAO dao;
        deleteAsyncTask(GoalDAO dao){
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Goal... goals) {
            dao.delete(goals[0]);
            return null;
        }
    }
    private static class deleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private final GoalDAO dao;
        deleteAllAsyncTask(GoalDAO dao){
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAll();
            return null;
        }
    }


    private class getActiveGoalAsyncTask extends AsyncTask<Void, Void, Goal>{
        private final GoalDAO dao;

        public getActiveGoalAsyncTask(GoalDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Goal doInBackground(Void... voids) {
            return dao.getActiveGoal();
        }
    }
}
