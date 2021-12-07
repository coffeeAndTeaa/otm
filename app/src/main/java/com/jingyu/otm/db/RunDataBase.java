package com.jingyu.otm.db;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Run.class, User.class}, version = 1, exportSchema = false)
public abstract class RunDataBase extends RoomDatabase {
    public abstract RunDao runDao();
    public abstract UserDao userDao();
}
