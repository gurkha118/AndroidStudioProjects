package com.example.firstapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText userinput;
    EditText userinput1;
    Button button1;
    TextView textview;
    TextView textview1;
    String input,input1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        userinput=findViewById(R.id.user_input);
        userinput1=findViewById(R.id.user_input1);
        button1=findViewById(R.id.button);
        textview=findViewById(R.id.showtext);
        textview1=findViewById(R.id.showtext1);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input=userinput.getText().toString();
                input1=userinput1.getText().toString();
                if(input.length()==0){
               textview.setText("please enter username");

            }
            else if(input1.length()==0) {
                    textview1.setText("please enter password");
                }
              else {
                    textview.setText(input);
                    Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                    intent.putExtra("userinput",input );
                    intent.putExtra("password",input1 );

                    startActivity(intent);
                }

                }






        });
    }

    //@Override
    //public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.menu_main, menu);
       // return true;
   // }

  //  @Override
  //  public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
      //  int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
       //     return true;
        }

      //  return super.onOptionsItemSelected(item);
   // }
//}
