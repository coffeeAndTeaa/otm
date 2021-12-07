package com.jingyu.otm.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void insertUser(User user);

    @Update
    void updateUser(User user);

//    @Delete
//    void deleteUser(User user);
//
//    @Query("Delete from user")
//    void deleteAllUsers();

    @Query("SELECT * FROM user")
    LiveData<List<User>> getAllUsers();

    @Query("SELECT * FROM user Where name=(:name) and password=(:password)")
    User login(String name, String password);

}
