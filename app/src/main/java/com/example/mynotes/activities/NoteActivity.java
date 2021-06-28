package com.example.mynotes.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.mynotes.R;
import com.example.mynotes.databinding.ActivityNoteBinding;
import com.example.mynotes.dialogs.DialogDeleteNote;
import com.example.mynotes.persistence.Note;
import com.example.mynotes.viewmodel.NoteViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NoteActivity extends AppCompatActivity implements DialogDeleteNote.DeleteNoteListener {

    private ActivityNoteBinding binding;
    private NoteViewModel noteViewModel;

    //note vars
    private String title, description, date, priority;
    private Note noteFromIntent;
    private Intent intent;
    private boolean isNewNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNoteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        checkIsNewNote();
        setValuesFromIntent();
        setViewModel();
        setListeners();
    }

    private void checkIsNewNote() {
        intent = getIntent();
        isNewNote = intent.getBooleanExtra(NoteListActivity.IS_NEW_NOTE, false);
    }

    private void setValuesFromIntent() {
        if (isNewNote) {
            setPriority("1");
        } else {
            noteFromIntent = (Note) intent.getSerializableExtra(NoteListActivity.NOTE_FROM_NOTE_LIST_ACTIVITY);
            binding.editTextTitle.setText(noteFromIntent.getNoteTitle());
            binding.editTextDescription.setText(noteFromIntent.getNoteDescription());
            setPriority(noteFromIntent.getNotePriority());
        }
    }

    private void setViewModel() {
        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
    }

    private void resetColorButtons() {
        binding.imageViewGreen.setImageResource(0);
        binding.imageViewOrange.setImageResource(0);
        binding.imageViewRed.setImageResource(0);
    }

    private void setListeners() {
        binding.floatingActionButtonSaveNote.setOnClickListener(v -> saveNote());
        binding.imageViewGreen.setOnClickListener(v -> setPriority("1"));
        binding.imageViewOrange.setOnClickListener(v -> setPriority("2"));
        binding.imageViewRed.setOnClickListener(v -> setPriority("3"));
    }

    private void saveNote() {
        if (getNoteValues()) {
            if (isNewNote) {
                insertNote();
            } else {
                updateNote();
            }
        }
    }

    private void updateNote() {
        noteFromIntent.setNoteTitle(title);
        noteFromIntent.setNoteDescription(description);
        noteFromIntent.setNoteDate(date);
        noteFromIntent.setNotePriority(priority);
        noteViewModel.updateNote(noteFromIntent);
        Toast.makeText(this, "Note " + title + " successfully updated.", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void insertNote() {
            Note newNote = new Note(title, description, date, priority);
            noteViewModel.insertNote(newNote);
        Toast.makeText(this, "note " + title + " successfully inserted", Toast.LENGTH_SHORT).show();
        finish();
    }

    private boolean getNoteValues() {
        title = binding.editTextTitle.getText().toString();
        description = binding.editTextDescription.getText().toString();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd. MMMM yyyy. HH:mm", Locale.getDefault());
        date = simpleDateFormat.format(new Date());
        if (title.isEmpty() && description.isEmpty()) {
            Toast.makeText(this, "Note is empty.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void setPriority(String priority) {
        resetColorButtons();
        this.priority = priority;
        switch (priority) {
            case "1":
                binding.imageViewGreen.setImageResource(R.drawable.ic_baseline_check_24);
                break;
            case "2":
                binding.imageViewOrange.setImageResource(R.drawable.ic_baseline_check_24);
                break;
            case "3":
                binding.imageViewRed.setImageResource(R.drawable.ic_baseline_check_24);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu_note_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.optionsItemDeleteNote:
                showDialogDeleteNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showDialogDeleteNote() {
        DialogDeleteNote dialogDeleteNote = new DialogDeleteNote();
        dialogDeleteNote.show(getSupportFragmentManager(), null);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (isNewNote) {
            menu.removeItem(R.id.optionsItemDeleteNote);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        saveNote();
    }

    @Override
    public void onDeleteNote() {
        noteViewModel.deleteNote(noteFromIntent);
        Toast.makeText(this, "Note deleted", Toast.LENGTH_SHORT).show();
        finish();
    }
}