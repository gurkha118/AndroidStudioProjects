package com.example.user.notebook;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class AddNoteActivity extends AppCompatActivity {
    String notetitle,category, noteDescription;
    EditText i1, i2;
    Spinner spinner;
    Button button;
    NoteDatabase notedatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        i1 = findViewById(R.id.text1);
        i2 = findViewById(R.id.text2);
        spinner = findViewById(R.id.spin);
        button = findViewById(R.id.save_note);
       notedatabase=NoteDatabase.getAppDatabase(this);
        handleClickListener();

    }

    private void  handleClickListener(){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    saveNoteToDatabase();

                }

            }
        });


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                category=spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    private void saveNoteToDatabase(){
        Note note= new Note();
        note.setNoteTitle(notetitle);
        note.setNoteCategory(category);
        note.setNoteDescription(noteDescription);
        notedatabase.newNoteDAO().insertnote(note);
        Intent intent=new Intent(AddNoteActivity.this,MainActivity.class);
        startActivity(intent);

    }


    public boolean validate() {
        boolean valid = false;
        notetitle = i1.getText().toString();
        noteDescription = i2.getText().toString();
        if (TextUtils.isEmpty(notetitle)) {
            i1.setError("Required");
        } else if (TextUtils.isEmpty(noteDescription)) {
            i2.setError("required");
        } else {
            valid = true;

        }
        return valid;
    }
}