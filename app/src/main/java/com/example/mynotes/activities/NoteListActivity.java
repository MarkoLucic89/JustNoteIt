package com.example.mynotes.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import com.example.mynotes.R;
import com.example.mynotes.adapters.NotesAdapter;
import com.example.mynotes.databinding.ActivityMainBinding;
import com.example.mynotes.dialogs.DialogDeleteSelectedNotes;
import com.example.mynotes.persistence.Note;
import com.example.mynotes.viewmodel.NoteViewModel;

import java.util.ArrayList;
import java.util.List;

public class NoteListActivity extends AppCompatActivity implements
        NotesAdapter.NoteAdapterListener,
        DialogDeleteSelectedNotes.DeleteSelectedNotesListener {

    public static final String IS_NEW_NOTE = "is_new_note";
    public static final String NOTE_FROM_NOTE_LIST_ACTIVITY = "note_from_note_list_activity";
    private static final String TAG = "NoteListActivity";

    private ActivityMainBinding binding;
    private NoteViewModel noteViewModel;
    private NotesAdapter notesAdapter;

    private List<Note> notes;
    private List<Note> selectedNotes;

    private boolean isActionMode;
    private boolean isSelectedAll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initLists();
        initViewModel();
        initRecyclerView();
        notesObserver();
        setSearchView();
        setListeners();
        disableActionMode();
    }

    private void setListeners() {
        binding.floatingActionButtonAddNote.setOnClickListener(v -> goToNoteActivity());
        binding.imageViewBackToolbarNoteListActivity.setOnClickListener(v -> disableActionMode());
        binding.imageViewSelectAllToolbar.setOnClickListener(v -> selectAllNotes());
        binding.imageViewDeleteToolbarNoteListActivity.setOnClickListener(v -> showDeleteSelectedListDialog());
    }

    private void showDeleteSelectedListDialog() {
        DialogDeleteSelectedNotes dialogDeleteSelectedNotes = new DialogDeleteSelectedNotes(selectedNotes.size());
        dialogDeleteSelectedNotes.show(getSupportFragmentManager(), null);
    }

    private void selectAllNotes() {
        if (isSelectedAll) {
            binding.imageViewDeleteToolbarNoteListActivity.setVisibility(View.GONE);
            binding.imageViewSelectAllToolbar.setBackgroundResource(0);
            isSelectedAll = false;
            notesAdapter.unselectAllNotes();
        } else {
            binding.imageViewDeleteToolbarNoteListActivity.setVisibility(View.VISIBLE);
            binding.imageViewSelectAllToolbar.setBackgroundResource(R.color.teal_700);
            isSelectedAll = true;
            notesAdapter.selectAllNotes();
            Log.d(TAG, "selectAllNotes: ");
        }
    }

    private void initLists() {
        notes = new ArrayList<>();
        selectedNotes = new ArrayList<>();
    }

    private void disableActionMode() {
        isActionMode = false;
        isSelectedAll = false;
        binding.imageViewBackToolbarNoteListActivity.setVisibility(View.GONE);
        binding.imageViewDeleteToolbarNoteListActivity.setVisibility(View.GONE);
        binding.textViewSelectedToolbarNoteListActivity.setVisibility(View.GONE);
        binding.imageViewSelectAllToolbar.setVisibility(View.GONE);
        selectedNotes.clear();
        notesAdapter.unselectAllNotes();
        binding.imageViewSelectAllToolbar.setBackgroundResource(0);
    }

    private void enableActionMode() {
        isActionMode = true;
        binding.imageViewBackToolbarNoteListActivity.setVisibility(View.VISIBLE);
        binding.textViewSelectedToolbarNoteListActivity.setVisibility(View.VISIBLE);
        binding.imageViewSelectAllToolbar.setVisibility(View.VISIBLE);
        notesAdapter.notifyDataSetChanged();
        setToolbarCounterText();
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
    public void onNoteClick(int position) {
        Intent intent = new Intent(this, NoteActivity.class);
        intent.putExtra(IS_NEW_NOTE, false);
        intent.putExtra(NOTE_FROM_NOTE_LIST_ACTIVITY, notesAdapter.getNote(position));
        startActivity(intent);
    }

    @Override
    public void onNoteLongClick() {
        enableActionMode();
    }

    @Override
    public boolean onCheckActionMode() {
        return isActionMode;
    }

    @Override
    public void onNoteCheck(boolean isChecked, int position) {
        Note selectedNote = notesAdapter.getNote(position);
        if (isChecked) {
            selectedNotes.add(selectedNote);
        } else {
            selectedNotes.remove(selectedNote);
        }
        setToolbarCounterText();
        showDeleteToolbarItem();
    }

    private void showDeleteToolbarItem() {
        if (selectedNotes.size() > 0) {
            binding.imageViewDeleteToolbarNoteListActivity.setVisibility(View.VISIBLE);
        } else {
            binding.imageViewDeleteToolbarNoteListActivity.setVisibility(View.GONE);
        }
    }

    public void setToolbarCounterText() {
        binding.textViewSelectedToolbarNoteListActivity.setText(selectedNotes.size() + " items selected");
    }

    @Override
    public void deleteSelectedNotes() {
        for (Note note : selectedNotes) {
            noteViewModel.deleteNote(note);
        }
        disableActionMode();
    }

    @Override
    protected void onStop() {
        super.onStop();
        disableActionMode();
    }
}