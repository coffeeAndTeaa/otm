package com.jingyu.otm.db;

import static androidx.room.ForeignKey.CASCADE;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "run")
public class Run {

    @PrimaryKey(autoGenerate = true)
    public Long id_run;

    @NonNull
    public Long id_user;

    public String runName;

    public Integer seconds;

    public Integer steps;

    public Long time;

    public Run(Long id_user, String runName, Integer seconds, Integer steps) {
        this.id_user = id_user;
        this.runName = runName;
        this.seconds = seconds;
        this.steps = steps;
        Date date = new Date();
        this.time = date.getTime();
    }
}
