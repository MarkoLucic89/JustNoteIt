package com.example.mynotes.persistence;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.mynotes.async.DeleteAllNotesAsync;
import com.example.mynotes.async.DeleteNoteAsync;
import com.example.mynotes.async.InsertNoteAsync;
import com.example.mynotes.async.UpdateNoteAsync;

import java.util.List;

public class NoteRepository {

    private NoteDao noteDao;
    private LiveData<List<Note>> allNotes;

    public NoteRepository(Application application) {
        NoteDatabase database = NoteDatabase.getInstance(application);
        noteDao = database.noteDao();
        allNotes = noteDao.getAllNotes();
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    public void insertNote(Note note) {
        new InsertNoteAsync(noteDao).execute(note);
    }

    public void updateNote(Note note) {
        new UpdateNoteAsync(noteDao).execute(note);
    }

    public void deleteNote(Note note) {
        new DeleteNoteAsync(noteDao).execute(note);
    }

    public void deleteAllNotes() {
        new DeleteAllNotesAsync(noteDao).execute();
    }
}
