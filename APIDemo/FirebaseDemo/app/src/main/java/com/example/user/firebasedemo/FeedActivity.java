package com.example.user.firebasedemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class FeedActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Button log_out;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        log_out=findViewById(R.id.log_out);
        setSupportActionBar(toolbar);

        bottomNavigationView=findViewById(R.id.navigation_botton);
        log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logout();
            }
        });


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_home:{
                        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.main_container,new HomeFragment()).commit();
                       // Toast.makeText(FeedActivity.this, "Selected home", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case R.id.menu_profile:{
                        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.main_container,new ProfileFragment()).commit();
                       // Toast.makeText(FeedActivity.this, "Selected profile", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
                return false;
            }
        });
        Fragment fragment=new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container,fragment).commit();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(FeedActivity.this,CreatePostActivity.class);
                startActivity(intent);
            }
        });
    }
    private void Logout(){
        FirebaseAuth.getInstance().signOut();
        Intent intent=new Intent(FeedActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

}
