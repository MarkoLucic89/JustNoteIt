package com.example.mynotes.persistence;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDao {

    @Query("SELECT * FROM note_table")
    LiveData<List<Note>> getAllNotes();

    @Insert
    void insertNote(Note... notes);

    @Delete
    void deleteNote(Note... notes);

    @Update
    void updateNote(Note... notes);

    @Query("DELETE FROM note_table")
    void deleteAllNotes();
}
