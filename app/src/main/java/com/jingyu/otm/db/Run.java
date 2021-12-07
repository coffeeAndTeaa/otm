package com.jingyu.otm.db;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "run")
public class Run {

    @PrimaryKey(autoGenerate = true)
    public Long id_run;

    public Long id_user;

    public String runName;

    public Run(Long id_user, String runName) {
        this.id_user = id_user;
        this.runName = runName;
    }
}
