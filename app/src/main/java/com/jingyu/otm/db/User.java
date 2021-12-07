package com.jingyu.otm.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user")
public class User {

    @PrimaryKey(autoGenerate = true)
    public Long id_user;

    public String name;

    public Double height;

    public Integer age;

    public Double weight;

    public String password;

    public User(String name, Double height, Integer age, Double weight, String password) {
        this.name = name;
        this.height = height;
        this.age = age;
        this.weight = weight;
        this.password = password;
    }

}
