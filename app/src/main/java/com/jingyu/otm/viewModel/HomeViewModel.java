package com.jingyu.otm.viewModel;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.jingyu.otm.db.User;
import com.jingyu.otm.repository.RunRepository;

import java.util.concurrent.ExecutionException;

public class HomeViewModel extends ViewModel {
    private final RunRepository repository;

    private final MutableLiveData<Long> userId = new MutableLiveData<>();

    public HomeViewModel(RunRepository repository) {
        this.repository = repository;
    }

    public void setUserId(Long id) {
        userId.setValue(id);
    }

    public LiveData<User> getUser() {
        return Transformations.switchMap(userId, repository::getUserById);
    }

}
