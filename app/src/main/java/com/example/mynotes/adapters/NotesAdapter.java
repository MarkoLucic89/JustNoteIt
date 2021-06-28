package com.example.mynotes.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mynotes.R;
import com.example.mynotes.persistence.Note;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {
    private List<Note> notes = new ArrayList<>();
    private OnNoteClickListener onNoteClickListener;

    public NotesAdapter(OnNoteClickListener onNoteClickListener) {
        this.onNoteClickListener = onNoteClickListener;
    }

    @NonNull
    @NotNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull NotesViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.textViewItemTitle.setText(note.getNoteTitle());
        holder.textViewItemDescription.setText(note.getNoteDescription());
        holder.textViewItemDate.setText(note.getNoteDate());
        switch (note.getNotePriority()) {
            case "1":
                holder.viewItemPriority.setBackgroundResource(R.drawable.background_priority_green);
                break;
            case "2":
                holder.viewItemPriority.setBackgroundResource(R.drawable.background_priority_yellow);
                break;
            case "3":
                holder.viewItemPriority.setBackgroundResource(R.drawable.background_priority_red);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public Note getNote(int position) {
        return notes.get(position);
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    public class NotesViewHolder extends RecyclerView.ViewHolder {
        TextView textViewItemTitle;
        TextView textViewItemDescription;
        TextView textViewItemDate;
        View viewItemPriority;

        public NotesViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            textViewItemTitle = itemView.findViewById(R.id.textViewItemTitle);
            textViewItemDescription = itemView.findViewById(R.id.textViewItemDescription);
            textViewItemDate = itemView.findViewById(R.id.textViewItemDate);
            viewItemPriority = itemView.findViewById(R.id.viewItemPriority);
            itemView.setOnClickListener(v -> onNoteClickListener.setOnNoteClickListener(getAdapterPosition()));
        }
    }

    public interface OnNoteClickListener {
        void setOnNoteClickListener(int position);
    }
}
