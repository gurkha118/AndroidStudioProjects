package com.surya.notebook;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class NewscreenActivity extends AppCompatActivity {

    EditText title;
    EditText desc;
    Spinner spn;
    Button btn;
    String category, noteTitle, noteDescription;
    int id;
    NoteDatabase database;
    Boolean isFromUpdate;
    Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newscreen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        note=new Note();



        title = (EditText) findViewById(R.id.title_hintID);
        desc = (EditText) findViewById(R.id.note_hintID);
        spn = (Spinner) findViewById(R.id.spinID);
        btn = (Button) findViewById(R.id.saveID);

        database = NoteDatabase.getAppDatabase(this);
        handleClickListener();

        isFromUpdate=getIntent().getExtras().getBoolean("isfrom()");
        if(isFromUpdate==true){
            noteTitle = getIntent().getExtras().getString("title");
            id= getIntent().getExtras().getInt("id");
            category = getIntent().getExtras().getString("category");

            String categoryList[] = getResources().getStringArray(R.array.note_category);
            List list = Arrays.asList(categoryList);
            spn.setSelection(list.indexOf(category));

            noteDescription = getIntent().getExtras().getString("desc");

            title.setText(noteTitle);
            desc.setText(noteDescription);


            btn.setText("Update");
        }else{
            btn.setText("Save");
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void handleClickListener() {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    note.setNoteTitle(noteTitle);
                    note.setNoteDescription(noteDescription);
                    note.setNoteCategory(category);

                    if(isFromUpdate){
                        note.setId(id);
                        updateNoteToDatabase();


                    }else {
                        saveNoteToDatabase();
                    }
                }
            }


        });


        spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = spn.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    public boolean validate() {
        boolean isValid = false;
        noteTitle = title.getText().toString();
        noteDescription = desc.getText().toString();
        if (TextUtils.isEmpty(noteTitle)) {
            title.setError("Required.");
        } else if (TextUtils.isEmpty(noteDescription)) {
            desc.setError("Required.");
        } else {
            isValid = true;
        }
        return isValid;
    }

    private void saveNoteToDatabase() {



        database.newNoteDAO().insertNote(note);
        Intent intent = new Intent(NewscreenActivity.this,MainActivity.class);
        startActivity(intent);
    }
    private void updateNoteToDatabase(){


        database.newNoteDAO().updateNote(note);
        Intent intent = new Intent(NewscreenActivity.this, MainActivity.class);
        startActivity(intent);
    }









}
