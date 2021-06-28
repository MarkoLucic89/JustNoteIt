package com.example.mynotes.persistence;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

@Entity(tableName = "note_table")
public class Note implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "note_title")
    private String noteTitle;

    @ColumnInfo(name = "note_description")
    private String noteDescription;

    @ColumnInfo(name = "note_date")
    private String noteDate;

    @ColumnInfo(name = "note_priority")
    private String notePriority;

    public Note(String noteTitle, String noteDescription, String noteDate, String notePriority) {
        this.noteTitle = noteTitle;
        this.noteDescription = noteDescription;
        this.noteDate = noteDate;
        this.notePriority = notePriority;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getNoteDate() {
        return noteDate;
    }

    public void setNoteDate(String noteDate) {
        this.noteDate = noteDate;
    }

    public String getNotePriority() {
        return notePriority;
    }

    public void setNotePriority(String notePriority) {
        this.notePriority = notePriority;
    }

    @NotNull
    @Override
    public String toString() {
        return noteTitle + " : " + noteDate;
    }
}
