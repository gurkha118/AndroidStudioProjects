package com.example.user.notebook;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NoteAdaptar extends RecyclerView.Adapter<NoteAdaptar.Viewholder>{
    List<Note> noteArrayList;
    OnNoteClickListener noteClickListener;

    public NoteAdaptar(List<Note> noteArrayList,OnNoteClickListener noteClickListener) {
        this.noteArrayList = noteArrayList;
        this.noteClickListener=noteClickListener;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note,parent,false);
        return new Viewholder ( view );
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        holder.bindview(noteArrayList.get(position));

    }


    @Override
    public int getItemCount() {
        return noteArrayList.size();
    }

    class Viewholder extends RecyclerView.ViewHolder{

        TextView noteTitle,noteCategory,noteDescription;

        public Viewholder(View itemView){
            super(itemView);
            noteTitle=itemView.findViewById(R.id.title);
            noteCategory=itemView.findViewById(R.id.category);
            noteDescription=itemView.findViewById(R.id.description);

        }
        public void bindview(final Note note){
            noteTitle.setText(note.getNoteTitle());
            noteCategory.setText(note.getNoteCategory());
            noteDescription.setText(note.getNoteDescription());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    noteClickListener.onNoteClick(note);
                }
            });
        }


        }
    interface OnNoteClickListener{
        void onNoteClick(Note note);
    }

}
