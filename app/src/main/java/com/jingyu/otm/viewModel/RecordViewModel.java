package com.jingyu.otm.viewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.jingyu.otm.db.Run;
import com.jingyu.otm.repository.RunRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class RecordViewModel extends ViewModel {
    private static final String TAG = "RecordViewModel";
    private final RunRepository repository;
    private final Long userId;
    private LiveData<List<Run>> allRuns;

    public RecordViewModel(RunRepository repository, Long userId) {
        this.repository = repository;
        this.userId = userId;
        try {
            allRuns = repository.getAllRunsForUser(userId);
        } catch (Exception e) {}
    }

    public LiveData<List<Run>> getAllRuns() {
        return allRuns;
    }

}
