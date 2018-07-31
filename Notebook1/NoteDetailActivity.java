package com.surya.notebook;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class NoteDetailActivity extends AppCompatActivity {

    TextView t1, t2, t3;
    String tv1, tv2, tv3;
    int tv4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        t1 = findViewById(R.id.view1);
        t2 = findViewById(R.id.view2);
        t3 = findViewById(R.id.view3);

        tv1 = getIntent().getStringExtra("tshow");
        tv2 = getIntent().getStringExtra("cshow");
        tv3 = getIntent().getStringExtra("dshow");
        tv4 = getIntent().getExtras().getInt("id");

        t1.setText(tv1);
        t2.setText(tv2);
        t3.setText(tv3);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NoteDetailActivity.this, NewscreenActivity.class);
                intent.putExtra("isfrom()",true);
                intent.putExtra("title",tv1);
                intent.putExtra("category",tv2);
                intent.putExtra("desc",tv3);
                intent.putExtra("id",tv4);
                startActivity(intent);

            }
        });
    }

}
