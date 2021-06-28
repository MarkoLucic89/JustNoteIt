package com.example.mynotes.async;

import android.os.AsyncTask;

import com.example.mynotes.persistence.Note;
import com.example.mynotes.persistence.NoteDao;
import com.example.mynotes.persistence.NoteDatabase;

public class InsertNoteAsync extends AsyncTask<Note, Void, Void> {

    private NoteDao noteDao;

    public InsertNoteAsync(NoteDao noteDao) {
        this.noteDao = noteDao;
    }

    @Override
    protected Void doInBackground(Note... notes) {
        noteDao.insertNote(notes);
        return null;
    }
}
