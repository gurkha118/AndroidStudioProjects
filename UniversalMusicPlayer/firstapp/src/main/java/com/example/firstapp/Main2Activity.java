package com.example.firstapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {
String input;
String input1;
TextView username;
TextView password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        username=findViewById(R.id.showtext);
        password=findViewById(R.id.showtext1);
        input=getIntent().getExtras().getString("userinput");
        input1=getIntent().getExtras().getString("password");


        username.setText(input);
        password.setText(input1);
       // Toast.makeText(this, input, Toast.LENGTH_SHORT).show();
    }
}
