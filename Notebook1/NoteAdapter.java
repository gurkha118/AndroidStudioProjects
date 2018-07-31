package com.surya.notebook;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    List<Note> noteList;
    OnNoteClickListener noteClickListener;



    public NoteAdapter(List<Note> noteList, OnNoteClickListener noteClickListener) {
        this.noteList = noteList;
        this.noteClickListener = noteClickListener;
    }

    @NonNull
    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemnote,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.ViewHolder holder, int position) {
        holder.bindView(noteList.get(position));
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView noteTitle, noteCategory, Desc;

        public ViewHolder(View itemView) {
            super(itemView);
            noteTitle = itemView.findViewById(R.id.notetitleID);
            noteCategory = itemView.findViewById(R.id.notecategoryID);
            Desc = itemView.findViewById(R.id.notedescID);
        }


        public void bindView(final Note note) {
            noteTitle.setText(note.getNoteTitle());
            noteCategory.setText(note.getNoteCategory());
            Desc.setText(note.getNoteDescription());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    noteClickListener.onNoteClick(note);
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    noteClickListener.onNoteLongClick(note);
                    return false;
                }
            });


        }
    }
    interface OnNoteClickListener{
        void onNoteClick(Note note);
        void onNoteLongClick(Note note);

    }

    public void deleteNote(Note note){
        noteList.remove(note);
        notifyDataSetChanged();
    }
}
