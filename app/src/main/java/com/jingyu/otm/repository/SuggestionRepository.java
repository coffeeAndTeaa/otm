package com.jingyu.otm.repository;

import android.os.AsyncTask;

import com.jingyu.otm.OtmApplication;
import com.jingyu.otm.db.Run;
import com.jingyu.otm.db.RunDao;
import com.jingyu.otm.db.RunDataBase;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SuggestionRepository {

    private final RunDataBase dataBase;

    public SuggestionRepository() {
        dataBase = OtmApplication.getDataBase();
    }

    public List<Run> RunForToday(Long current) throws ExecutionException, InterruptedException {
        return new RunAsyncTask(dataBase.runDao()).execute(current).get();
    }

    private static class RunAsyncTask extends AsyncTask<Long, Void, List<Run>> {
        private final RunDao runDao;

        private RunAsyncTask(RunDao runDao) {
            this.runDao = runDao;
        }

        @Override
        protected List<Run> doInBackground(Long... longs) {
            return runDao.getAllRunInDay(longs[0]);
        }
    }

    public int getStepsForToday() {
        Long current = new Date().getTime();
        try {
            List<Run> runs = RunForToday(current);
            if (runs == null) {
                return 0;
            } else {
                int count = 0;
                for (Run run : runs) {
                    count += run.steps;
                }
                return count;
            }

        } catch (Exception e){
        }
        return 0;
    }
}
