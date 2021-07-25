package com.example.mynotes.dialogs;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.mynotes.databinding.DialogDeleteSelectedNotesBinding;

import org.jetbrains.annotations.NotNull;

public class DialogDeleteSelectedNotes extends DialogFragment {

    private int selectedNotes;
    private DialogDeleteSelectedNotesBinding binding;
    private DeleteSelectedNotesListener deleteSelectedNotesListener;

    public DialogDeleteSelectedNotes(int selectedNotes) {
        this.selectedNotes = selectedNotes;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = DialogDeleteSelectedNotesBinding.inflate(inflater, container, false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        binding.textViewDialogDeleteSelectedNotesDescription
                .setText("Are you sure you want to delete " + selectedNotes + " selected notes?");
        binding.textViewDialogDeleteSelectedNotesOk.setOnClickListener(v -> {
            deleteSelectedNotesListener.deleteSelectedNotes();
            dismiss();
        });
        binding.textViewDialogDeleteSelectedNotesCancel.setOnClickListener(v -> dismiss());
        return binding.getRoot();
    }

    public interface DeleteSelectedNotesListener {
        void deleteSelectedNotes();
    }

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        deleteSelectedNotesListener = (DeleteSelectedNotesListener) context;
    }
}
