package com.surya.notebook;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.Cursor;

import java.util.List;

@Dao
public interface NoteDAO {

    @Query("Select * from note")
    List<Note> getAllNote();

    @Insert
    void insertNote(Note note);

    @Delete
    void deleteNote(Note note);

    @Update
    void updateNote(Note note);

    @Query("SELECT COUNT(*) FROM note")
    void showCount(Note note);

}
