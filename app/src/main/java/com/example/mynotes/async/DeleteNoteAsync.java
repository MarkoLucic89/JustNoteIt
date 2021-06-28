package com.example.mynotes.async;

import android.os.AsyncTask;

import com.example.mynotes.persistence.Note;
import com.example.mynotes.persistence.NoteDao;

public class DeleteNoteAsync extends AsyncTask<Note, Void, Void> {

    private NoteDao noteDao;

    public DeleteNoteAsync(NoteDao noteDao) {
        this.noteDao = noteDao;
    }

    @Override
    protected Void doInBackground(Note... notes) {
        noteDao.deleteNote(notes);
        return null;
    }
}
