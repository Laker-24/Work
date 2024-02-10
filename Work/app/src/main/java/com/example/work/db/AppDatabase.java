package com.example.work.db;

import  android.content.Context;
import android.database.Cursor;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.work.Dao.NoteDao;
import com.example.work.Dao.UserDao;
import com.example.work.entity.Note;
import com.example.work.entity.User;

@Database(entities ={User.class, Note.class},version=4,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase{
    public static final String user_database="mydb";
    public abstract UserDao getuserDao();
    public abstract NoteDao noteDao();
    private static AppDatabase INSTANCE;
    public static synchronized AppDatabase getINSTANCE(Context context){
        if (INSTANCE==null){
            INSTANCE= create(context);
        }
        return INSTANCE;
    }
    public static AppDatabase create(final Context context){
        return Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,user_database)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
    }

}
