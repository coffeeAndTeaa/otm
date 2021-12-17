package com.jingyu.otm.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
@Dao
public interface RunDao {

    @Insert
    void insertRun(Run run);

    @Query("SELECT * FROM run Where id_user =(:id)")
    LiveData<List<Run>> getAllRunForUser(Long id);

    @Query("DELETE FROM run")
    void deleteAll();

    @Query("SELECT * FROM run Where time Between (:current - 86400000) AND (:current) And id_user=(:id)")
    List<Run> getAllRunInDay(Long current, Long id);

}
