package com.jingyu.otm.repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;

import com.jingyu.otm.OtmApplication;
import com.jingyu.otm.db.Run;
import com.jingyu.otm.db.RunDao;
import com.jingyu.otm.db.RunDataBase;
import com.jingyu.otm.db.User;
import com.jingyu.otm.db.UserDao;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class RunRepository {

    private UserDao userDao;
    private RunDao runDao;
    private LiveData<List<User>> allUsers;

//    private LiveData<List<Run>>

    public RunRepository() {
        RunDataBase database = OtmApplication.getDataBase();
        UserDao userDao = database.userDao();
        RunDao runDao = database.runDao();
        allUsers = userDao.getAllUsers();
    }





    public void updateUser(User user) {
        new UpdateUserAsyncTask(userDao).execute(user);
    }

    private static class UpdateUserAsyncTask extends AsyncTask<User, Void, Void> {

        private UserDao userDao;

        private UpdateUserAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.updateUser(users[0]);
            return null;
        }

    }

    public LiveData<List<User>> getAllUsers() {
        return allUsers;
    }

    public void insertRun(Run run) {
        new InsertRunAsyncTask(runDao).execute();
    }

    private static class InsertRunAsyncTask extends AsyncTask<Run, Void, Void> {

        private RunDao runDao;

        private InsertRunAsyncTask(RunDao runDao) {
            this.runDao = runDao;
        }

        @Override
        protected Void doInBackground(Run... runs) {
            runDao.insertRun(runs[0]);
            return null;
        }
    }

    public  LiveData<List<Run>> getAllRunsForUser(User user) throws ExecutionException, InterruptedException {
        return new GetAllRunsForUserAsyncTask(runDao).execute(user).get();
    }

    private static class GetAllRunsForUserAsyncTask extends AsyncTask<User, Void, LiveData<List<Run>>> {
        private RunDao runDao;

        private GetAllRunsForUserAsyncTask(RunDao runDao) {
            this.runDao = runDao;
        }

        @Override
        protected LiveData<List<Run>> doInBackground(User... users) {
            return runDao.getAllRunForUser(users[0].id_user);
        }
    }







}
