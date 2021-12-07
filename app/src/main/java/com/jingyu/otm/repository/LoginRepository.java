package com.jingyu.otm.repository;

import android.os.AsyncTask;
import android.util.Log;

import com.jingyu.otm.OtmApplication;
import com.jingyu.otm.db.RunDataBase;
import com.jingyu.otm.db.User;
import com.jingyu.otm.db.UserDao;

import java.util.concurrent.ExecutionException;

public class LoginRepository {

    private final RunDataBase dataBase;

    public LoginRepository() {
        dataBase = OtmApplication.getDataBase();
    }

    public User login(String name, String password) throws ExecutionException, InterruptedException {
        LoginParam param = new LoginParam(name, password);
        return new LoginAsyncTask(dataBase.userDao()).execute(param).get();
    }

    private static class LoginParam {
        String name;
        String password;

        public LoginParam(String name, String password) {
            this.name = name;
            this.password = password;
        }
    }

    private static class LoginAsyncTask extends AsyncTask<LoginParam, Void, User> {
        private UserDao userDao;

        private LoginAsyncTask(UserDao dao) {
            this.userDao = dao;
        }

        @Override
        protected User doInBackground(LoginParam... loginParams) {
            if (userDao == null) {
                Log.d("RunRepository", "userDao is just null ");
            }
            return userDao.login(loginParams[0].name, loginParams[0].password);
        }
    }

    public void insert(User user) {
        new InsertUserAsyncTask(dataBase.userDao()).execute(user);
    }

    private static class InsertUserAsyncTask extends AsyncTask<User, Void, Void> {

        private UserDao userDao;

        private InsertUserAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.insertUser(users[0]);
            return null;
        }
    }

    public void update(User user) {
        new UpdateUserAsyncTask(dataBase.userDao()).execute(user);
    }

    private static class UpdateUserAsyncTask extends AsyncTask<User, Void, Void> {

        private UserDao userDao;

        private UpdateUserAsyncTask(UserDao userDao) {this.userDao = userDao;}

        @Override
        protected Void doInBackground(User... users) {
            userDao.updateUser(users[0]);
            return null;
        }
    }

}


