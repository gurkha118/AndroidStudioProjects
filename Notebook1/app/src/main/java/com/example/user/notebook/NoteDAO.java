package com.example.user.notebook;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface NoteDAO {
    @Query("Select*from note")
    List<Note> getALLNotes();

    @Insert
    void insertnote(Note note);

    @Delete
    void deletenote(Note note);
}
