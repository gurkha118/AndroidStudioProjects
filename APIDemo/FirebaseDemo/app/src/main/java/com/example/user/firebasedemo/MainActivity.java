package com.example.user.firebasedemo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    Button signup, login;
    TextView textView;
    EditText email, password;
    String emailStr, passwordStr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!TextUtils.isEmpty(FirebaseAuth.getInstance().getUid())) {
            Intent intent = new Intent(MainActivity.this, FeedActivity.class);
            startActivity(intent);
        } else {


            setContentView(R.layout.activity_main);
            signup = findViewById(R.id.signup);
            login = findViewById(R.id.login);
            email = findViewById(R.id.email);
            password = findViewById(R.id.password);
            signup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(MainActivity.this, "clicke3d", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, SignupActivity.class);
                    startActivity(intent);
                    finish();
                }
            });

            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Validation()) {
                        loginUserToFirebase();
                    }
                }
            });
        }
    }

    private boolean Validation() {
        boolean isValid = false;
        emailStr = email.getText().toString();
        passwordStr = password.getText().toString();
        if (TextUtils.isEmpty(emailStr)) {
            email.setError("Required");
        } else if (TextUtils.isEmpty(passwordStr)) {
            password.setError("Required");


        } else if (passwordStr.length() < 8) {
            password.setError("Minimum 8 characters");
        } else {
            isValid = true;
        }
        return isValid;
    }

    private void loginUserToFirebase() {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(emailStr, passwordStr).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(MainActivity.this, FeedActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(MainActivity.this, "error:" + task.getException().toString(), Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}








