package com.jingyu.otm.db;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.jingyu.otm.repository.RunRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

//public class RunViewModel extends AndroidViewModel {
////    private RunRepository repository;
////    private LiveData<List<User>> allUsers;
////
////    public RunViewModel(@NonNull Application application) {
////        super(application);
////        repository = new RunRepository(application);
////        allUsers = repository.getAllUsers();
////    }
////
////    public void insertUser(User user) {
////        repository.insertUser(user);
////    }
////
////    public void updateUser(User user) {
////        repository.updateUser(user);
////    }
////
////    public void insertRun(Run run) {
////        repository.insertRun(run);
////    }
////
////    public LiveData<List<Run>> getAllRunForUser(User user) throws ExecutionException, InterruptedException {
////        return repository.getAllRunsForUser(user);
////    }
////
////    public User login(String name, String password) throws ExecutionException, InterruptedException {
////        return repository.login(name, password);
//    }
//
//
//
//}
