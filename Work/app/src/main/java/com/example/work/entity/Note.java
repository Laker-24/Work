package com.example.work.entity;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "note",foreignKeys = @ForeignKey(entity = User.class,parentColumns = "id",childColumns = "emp_id",onDelete = CASCADE),
                indices = @Index(value = {"emp_id"}))
public class Note {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name="emp_id")
    public int emp_id;
    @ColumnInfo(name="title")
    public String title;
    @ColumnInfo(name="content")
    public String content;
    @ColumnInfo(name="time")
    public long time;

    public int getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(int emp_id) {
        this.emp_id = emp_id;
    }

    public int getId(){return id;}
    public String getTitle(){return title;}
    public String getContent(){return content;}
    public long getTime(){return time;}

    public void setTitle(String title){this.title= title;}
    public void setContent(String content){this.content= content;}
    public void setTime(long time){this.time=time;}

    @Override
    public String toString() {
        return "Note{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", time='" + time + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return id == note.id && emp_id == note.emp_id && time == note.time && Objects.equals(title, note.title) && Objects.equals(content, note.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, emp_id, title, content, time);
    }
}
