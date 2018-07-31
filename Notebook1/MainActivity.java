package com.surya.notebook;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReferenceArray;

public class MainActivity extends AppCompatActivity {

    RecyclerView noteList;
    AlertDialog.Builder alertDialog;
    NoteAdapter noteAdapt;
    TextView count;
    NoteDatabase noteDatabase;
    Button btn;
    Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        count = findViewById(R.id.tView);
        setSupportActionBar(toolbar);
        setSupportActionBar(count);
        note = new Note();
        noteList = findViewById(R.id.recyclerID);
        noteDatabase = noteDatabase.getAppDatabase(this);





        noteAdapt = new NoteAdapter(noteDatabase.newNoteDAO().getAllNote(), new NoteAdapter.OnNoteClickListener() {
            @Override
            public void onNoteClick(Note note) {

                Toast.makeText(MainActivity.this, "id is::"+note.getId(), Toast.LENGTH_SHORT).show();
               Intent intent=new Intent(MainActivity.this,NoteDetailActivity.class);
                intent.putExtra("tshow",note.getNoteTitle());
                intent.putExtra("cshow",note.getNoteCategory());
                intent.putExtra("dshow",note.getNoteDescription());
                intent.putExtra("id",note.getId());



                startActivity(intent);

            }

            @Override
            public void onNoteLongClick(final Note note) {
                Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();
              //  Log.d("MAin","inside long click");
                alertDialog = new AlertDialog.Builder(MainActivity.this);
                alertDialog.setTitle(R.string.alttitle);

                alertDialog.setMessage(R.string.delete_text);

                alertDialog.setCancelable(false);

                alertDialog.setPositiveButton(getResources().getString(R.string.positive), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        deleteNoteFromDatabase(note);
                        noteAdapt.deleteNote(note);


                    }
                });

                alertDialog.setNegativeButton(getResources().getString(R.string.negative), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog dialog = alertDialog.create();
                dialog.show();

            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        noteList.setLayoutManager(layoutManager);
        noteList.setAdapter(noteAdapt);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                Intent intent = new Intent(MainActivity.this, NewscreenActivity.class);
                intent.putExtra("isfrom()",false);
                startActivity(intent);
            }
        });

    }

    private void setSupportActionBar(TextView count) {

    }

    private void deleteNoteFromDatabase(Note note){
        noteDatabase.newNoteDAO().deleteNote(note);

    }

    public ArrayList<Note> returnNoteList() {
        ArrayList<Note> noteArrayList = new ArrayList<>();


        Note note = new Note();
        note.setNoteCategory("Work");
        note.setNoteTitle("Texas");
        note.setNoteDescription("Surya Ghising");
        noteArrayList.add(note);

        Note nt = new Note();
        nt.setNoteCategory("Student");
        nt.setNoteTitle("Name");
        nt.setNoteDescription("Keshar");
        noteArrayList.add(nt);

        Note nb = new Note();
        nb.setNoteTitle("Class");
        nb.setNoteCategory("Student");
        nb.setNoteDescription("BSc.CSIT");
        noteArrayList.add(nb);

        return noteArrayList;


    }





}
