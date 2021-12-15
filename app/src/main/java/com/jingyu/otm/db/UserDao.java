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

    @Query("Delete FROM user")
    void deleteAllUsers();

    @Query("SELECT * FROM user")
    LiveData<List<User>> getAllUsers();

    @Query("SELECT * FROM user Where name=(:name) and password=(:password)")
    User login(String name, String password);

    @Query("SELECT * FROM user Where id_user=(:id)")
    User getUserById(Long id);

    @Query("SELECT * FROM user Where id_user=(:id)")
    LiveData<User> getUser(Long id);

    @Query("SELECT * FROM user where name=(:name)")
    User checkName(String name);

}
