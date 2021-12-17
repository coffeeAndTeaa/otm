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
    private Long id;

    public SuggestionRepository(Long id) {
        dataBase = OtmApplication.getDataBase();
        this.id = id;
    }

    public List<Run> RunForToday(Long current) throws ExecutionException, InterruptedException {
        Param temp = new Param(current, id);
        return new RunAsyncTask(dataBase.runDao()).execute(temp).get();
    }

    private static class Param {
        Long current;
        Long id;

        private Param (Long current, Long id) {
            this.id = id;
            this.current = current;
        }
    }

    private static class RunAsyncTask extends AsyncTask<Param, Void, List<Run>> {
        private final RunDao runDao;

        private RunAsyncTask(RunDao runDao) {
            this.runDao = runDao;
        }

        @Override
        protected List<Run> doInBackground(Param... longs) {
            return runDao.getAllRunInDay(longs[0].current, longs[0].id);
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
