package com.darkwoods.notes.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity
public class Note {

    @PrimaryKey(autoGenerate = true)
    int id;
    private String title;
    private String description;
    @ColumnInfo(name = "creation_date")
    private Date updateAt;

    @Ignore
    public Note(String title, String description, Date updateAt) {
        this.title = title;
        this.description = description;
        this.updateAt = updateAt;
    }

    public Note(int id, String title, String description, Date updateAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.updateAt = updateAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }
}