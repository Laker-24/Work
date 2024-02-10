package com.example.work.entity;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName="user")
public class User {
    @PrimaryKey(autoGenerate=true)
    public int id;//用户id

    @ColumnInfo(name="name")
    public String name;
    @ColumnInfo(name="phone")
    public String phone;//用户手机号

    @ColumnInfo(name="password")
    public  String password;//用户密码
    @Ignore
    public User(String username, String password) {
        this.name=username;
        this.password=password;

    }

    public User() {

    }


    public int getUid(){
        return id;
    }
    public String getName(){
        return name;
    }
    public String getPassword(){
        return password;
    }
    public void setUid(int uid){
        this.id=uid;
    }
    public void setName(String name){
        this.name=name;
    }
    public void setPassword(String password){
        this.password=password;
    }


}
