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

    private final RunDataBase dataBase;
    private LiveData<List<User>> allUsers;

//    private LiveData<List<Run>>

    public RunRepository() {
        dataBase = OtmApplication.getDataBase();
    }

    public void updateUser(User user) {
        new UpdateUserAsyncTask(dataBase.userDao()).execute(user);
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
        new InsertRunAsyncTask(dataBase.runDao()).execute();
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
        return new GetAllRunsForUserAsyncTask(dataBase.runDao()).execute(user).get();
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

    // get the bmi value
    public Double getTheBmiForUser(User user) {
        return user.weight / (user.height * user.height);
    }

    public User getUser(Long userId) throws ExecutionException, InterruptedException {
        return new GetUserAsyncTask(dataBase.userDao()).execute(userId).get();
    }

    private static class GetUserAsyncTask extends AsyncTask<Long, Void, User> {
        private UserDao userDao;

        private GetUserAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected User doInBackground(Long... longs) {
            return userDao.getUserById(longs[0]);
        }
    }

}
