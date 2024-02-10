package com.example.work.Dao;

import android.content.ContentValues;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.work.entity.User;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM user")
    List<User> getAll();//查询所有

    @Query("SELECT * FROM user WHERE name=:name AND password=:password")
    User findUser(String name,String password);     //条件查询

    @Insert
    void insert(User user);  //新增，由于主键自增,user不用设置uid值

    @Delete
    void delete(User user);  //单个删除


    @Update
    void update(User user);  //单个更新

    @Query("DELETE FROM User")
    void deleteAll(); //删除所有（批量删除）

}
