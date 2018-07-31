package com.example.user.notebook;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class NoteDetailActivity extends AppCompatActivity {
    TextView t1, t2, t3;
    String t1Str, t2Str, t3Str;
    int tv4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        t1= findViewById(R.id.t1);
        t2 =findViewById(R.id.t2);
        t3 =findViewById(R.id.t3);

        t1Str=getIntent().getStringExtra("tshow");
        t2Str=getIntent().getStringExtra("cshow");
        t3Str=getIntent().getStringExtra("dshoW");
        tv4=getIntent().getExtras().getInt("id");






        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });
    }

}
