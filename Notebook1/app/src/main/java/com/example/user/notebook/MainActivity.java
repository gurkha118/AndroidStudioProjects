package com.example.user.notebook;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView noteList;
    NoteDatabase noteDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        noteList=findViewById(R.id.show_notes_list);


         noteDatabase = NoteDatabase.getAppDatabase(this);

        NoteAdaptar noteAdaptar=new NoteAdaptar(noteDatabase.newNoteDAO().getALLNotes(), new NoteAdaptar.OnNoteClickListener() {
            @Override
            public void onNoteClick(Note note) {
                Intent intent= new Intent(MainActivity.this,NoteDetailActivity.class);
                intent.putExtra("tshow",note.getNoteTitle());
                intent.putExtra("cshow",note.getNoteCategory());
                intent.putExtra("dshow",note.getNoteDescription());
                intent.putExtra("id",note.getId());
                startActivity(intent );

            }
        });
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        noteList.setLayoutManager(linearLayoutManager);
        noteList.setAdapter(noteAdaptar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,AddNoteActivity.class);
                startActivity(intent);

            }
        });
    }
    public ArrayList<Note> returnNoteList(){
        ArrayList<Note> noteArrayList=new ArrayList<>();
        for(int i=0;i<13;i++){
            Note note= new Note();
            note.setNoteCategory("Work");
            note.setNoteTitle("Texas");
            note.setNoteDescription("jsdhssjk");
            noteArrayList.add(note);

        }
        return noteArrayList;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
