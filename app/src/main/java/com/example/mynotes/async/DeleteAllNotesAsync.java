package com.example.mynotes.async;

import android.os.AsyncTask;

import com.example.mynotes.persistence.Note;
import com.example.mynotes.persistence.NoteDao;

public class DeleteAllNotesAsync extends AsyncTask<Void, Void, Void> {

    private NoteDao noteDao;

    public DeleteAllNotesAsync(NoteDao noteDao) {
        this.noteDao = noteDao;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        noteDao.deleteAllNotes();
        return null;
    }
}
