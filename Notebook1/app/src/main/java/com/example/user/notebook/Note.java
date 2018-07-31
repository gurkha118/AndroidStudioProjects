package com.example.user.notebook;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Note {
    @PrimaryKey
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ColumnInfo
    private String noteTitle;
    @ColumnInfo
    private String noteDescription;
    @ColumnInfo
    private String noteCategory;

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteDescription() {
        return noteDescription;
    }

    public void setNoteDescription(String noteDescription) {
        this.noteDescription = noteDescription;
    }

    public String getNoteCategory() {
        return noteCategory;
    }

    public void setNoteCategory(String noteCategory) {
        this.noteCategory = noteCategory;
    }
}
