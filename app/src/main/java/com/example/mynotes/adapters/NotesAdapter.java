package com.example.mynotes.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mynotes.R;
import com.example.mynotes.persistence.Note;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {
    private static final String SELECT_ALL = "select_all_notes";
    private static final String UNSELECT_ALL = "unselect_all_notes";
    private List<Note> notes = new ArrayList<>();
    private NoteAdapterListener noteAdapterListener;
    private boolean isActionMode;
    private String checkNotes;

    public NotesAdapter(NoteAdapterListener noteAdapterListener) {
        this.noteAdapterListener = noteAdapterListener;
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

        if (noteAdapterListener.onCheckActionMode()) {
            holder.checkBoxItemSelect.setVisibility(View.VISIBLE);
        } else {
            holder.checkBoxItemSelect.setVisibility(View.GONE);
            holder.checkBoxItemSelect.setChecked(false);
        }

        if (checkNotes != null) {
            switch (checkNotes) {
                case SELECT_ALL:
                    holder.checkBoxItemSelect.setChecked(true);
                    break;
                case UNSELECT_ALL:
                    holder.checkBoxItemSelect.setChecked(false);
            }
        }
    }

    public void selectAllNotes() {
        checkNotes = SELECT_ALL;
        notifyDataSetChanged();
    }

    public void unselectAllNotes() {
        checkNotes = UNSELECT_ALL;
        notifyDataSetChanged();
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
        CheckBox checkBoxItemSelect;

        public NotesViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            initViews(itemView);
            setListeners(itemView);
        }

        private void initViews(View itemView) {
            textViewItemTitle = itemView.findViewById(R.id.textViewItemTitle);
            textViewItemDescription = itemView.findViewById(R.id.textViewItemDescription);
            textViewItemDate = itemView.findViewById(R.id.textViewItemDate);
            viewItemPriority = itemView.findViewById(R.id.viewItemPriority);
            checkBoxItemSelect = itemView.findViewById(R.id.checkBoxItemSelect);
        }

        private void setListeners(View itemView) {
            itemView.setOnClickListener(v -> noteAdapterListener.onNoteClick(getAdapterPosition()));
            itemView.setOnLongClickListener(v -> {
                noteAdapterListener.onNoteLongClick();
                notifyDataSetChanged();
                return true;
            });
            checkBoxItemSelect.setOnCheckedChangeListener((buttonView, isChecked) -> {
                noteAdapterListener.onNoteCheck(isChecked, getAdapterPosition());
            });
        }
    }

    public interface NoteAdapterListener {
        void onNoteClick(int position);

        void onNoteLongClick();

        boolean onCheckActionMode();

        void onNoteCheck(boolean isChecked, int position);
    }
}
