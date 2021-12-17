package com.jingyu.otm;

import android.app.Application;
import android.os.AsyncTask;

import androidx.room.Room;

import com.jingyu.otm.db.Run;
import com.jingyu.otm.db.RunDao;
import com.jingyu.otm.db.RunDataBase;
import com.jingyu.otm.db.User;
import com.jingyu.otm.db.UserDao;

public class OtmApplication extends Application {
    private static RunDataBase dataBase;

    @Override
    public void onCreate() {
        super.onCreate();
        dataBase = Room.databaseBuilder(this, RunDataBase.class, "my_database" ).build();

        // insert some data for testing
        new PopulateDbAsyncTask(dataBase).execute();

    }

    public static RunDataBase getDataBase() {
        return dataBase;
    }

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private UserDao userDao;
        private RunDao runDao;

        private PopulateDbAsyncTask(RunDataBase db) {
            userDao = db.userDao();
            runDao = db.runDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            runDao.deleteAll();
            userDao.deleteAllUsers();

            User Bruce = new User("Bruce", 1.91, 24, 100.0, "123456", "Male");
            User faker = new User("faker", 1.78, 25, 77.0, "123456", "Male");
            userDao.insertUser(Bruce);
            userDao.insertUser(faker);

            User B = userDao.login(Bruce.name, Bruce.password);
            User F = userDao.login(faker.name, faker.password);
            Run r1 = new Run(B.id_user, "Bruce First run", 10, 100);
            Run r2 = new Run(B.id_user, "Bruce Second run", 11, 101);
            Run r3 = new Run(B.id_user, "Bruce Third run", 12, 103);
            Run r4 = new Run(F.id_user, "faker First run", 9, 10);
            Run r5 = new Run(F.id_user, "faker second run", 10, 11);
            runDao.insertRun(r1);
            runDao.insertRun(r2);
            runDao.insertRun(r3);
            runDao.insertRun(r4);
            runDao.insertRun(r5);
            return null;
        }



    }

}
