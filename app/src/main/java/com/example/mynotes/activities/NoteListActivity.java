package com.example.mynotes.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;

import com.example.mynotes.R;
import com.example.mynotes.adapters.NotesAdapter;
import com.example.mynotes.databinding.ActivityMainBinding;
import com.example.mynotes.dialogs.DialogDeleteAllNotes;
import com.example.mynotes.persistence.Note;
import com.example.mynotes.viewmodel.NoteViewModel;

import java.util.ArrayList;
import java.util.List;

public class NoteListActivity extends AppCompatActivity implements
        NotesAdapter.OnNoteClickListener, DialogDeleteAllNotes.DeleteAllNotesListener {

    public static final String IS_NEW_NOTE = "is_new_note";
    public static final String NOTE_FROM_NOTE_LIST_ACTIVITY = "note_from_note_list_activity";
    private ActivityMainBinding binding;
    private NoteViewModel noteViewModel;
    private NotesAdapter notesAdapter;
    private List<Note> notes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        notes = new ArrayList<>();
        initViewModel();
        initRecyclerView();
        notesObserver();
        setSearchView();
        binding.floatingActionButtonAddNote.setOnClickListener(v -> goToNoteActivity());
    }

    private void setSearchView() {
        binding.editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
    }

    private void filter(String s) {
        ArrayList<Note> filteredNotes = new ArrayList<>();

        for (Note note : notes) {
            if (note.getNoteTitle().contains(s) || note.getNoteDescription().contains(s))
                filteredNotes.add(note);
        }

        notesAdapter.setNotes(filteredNotes);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu_note_list_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.optionsItemDeleteAllNotes:
                showDeleteAllNotesDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showDeleteAllNotesDialog() {
        DialogDeleteAllNotes dialogDeleteAllNotes = new DialogDeleteAllNotes();
        dialogDeleteAllNotes.show(getSupportFragmentManager(), null);
    }

    private void goToNoteActivity() {
        Intent intent = new Intent(this, NoteActivity.class);
        intent.putExtra(IS_NEW_NOTE, true);
        startActivity(intent);
    }

    private void initRecyclerView() {
        notesAdapter = new NotesAdapter(this);
        binding.recyclerViewNotes.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        binding.recyclerViewNotes.setAdapter(notesAdapter);
    }

    private void initViewModel() {
        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
    }

    private void notesObserver() {
        noteViewModel.getAllNotes().observe(this, notes -> {
            notesAdapter.setNotes(notes);
            this.notes = notes;
        });
    }

    @Override
    public void setOnNoteClickListener(int position) {
        Intent intent = new Intent(this, NoteActivity.class);
        intent.putExtra(IS_NEW_NOTE, false);
        intent.putExtra(NOTE_FROM_NOTE_LIST_ACTIVITY, notesAdapter.getNote(position));
        startActivity(intent);
    }

    @Override
    public void onDeleteAllNotes() {
        noteViewModel.deleteAllNotes();
    }
}