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

import com.example.mynotes.databinding.DialogDeleteAllNotesBinding;

import org.jetbrains.annotations.NotNull;

public class DialogDeleteAllNotes extends DialogFragment {
    private DialogDeleteAllNotesBinding binding;
    private DeleteAllNotesListener deleteAllNotesListener;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = DialogDeleteAllNotesBinding.inflate(inflater, container, false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        binding.textViewDialogDeleteAllNotesCancel.setOnClickListener(v -> dismiss());
        binding.textViewDialogDeleteAllNotesOk.setOnClickListener(v -> {
            deleteAllNotesListener.onDeleteAllNotes();
            dismiss();
        });
        return binding.getRoot();
    }

    public interface DeleteAllNotesListener {
        void onDeleteAllNotes();
    }

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        deleteAllNotesListener = (DeleteAllNotesListener) context;
    }
}
