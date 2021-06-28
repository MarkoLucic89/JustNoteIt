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

import com.example.mynotes.databinding.DialogDeleteNoteBinding;

import org.jetbrains.annotations.NotNull;

public class DialogDeleteNote extends DialogFragment {

    private DialogDeleteNoteBinding binding;
    private DeleteNoteListener deleteNoteListener;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = DialogDeleteNoteBinding.inflate(inflater, container, false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        binding.textViewDialogDeleteNoteOk.setOnClickListener(v -> {
            deleteNoteListener.onDeleteNote();
            dismiss();
        });
        binding.textViewDialogDeleteNoteCancel.setOnClickListener(v -> dismiss());
        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        deleteNoteListener = (DeleteNoteListener) context;
    }

    public interface DeleteNoteListener {
        void onDeleteNote();
    }


}
