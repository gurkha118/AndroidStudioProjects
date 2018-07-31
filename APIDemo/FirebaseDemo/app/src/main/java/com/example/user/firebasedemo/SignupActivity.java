package com.example.user.firebasedemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class SignupActivity extends AppCompatActivity {
    EditText name, email, password;
    TextView view;
    RadioGroup gender;
    Button sign,login;
    String nameStr,emailStr,passwordStr,genderStr;
    ImageView profile_image;
    int PICK_IMAGE_REQUEST=1;
    private final String TAG = SignupActivity.class.getSimpleName();
    Bitmap bitmap;
    Uri uri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        gender = findViewById(R.id.gender);
        profile_image=findViewById(R.id.profile_image);
        sign=findViewById(R.id.sign);
        login=findViewById(R.id.login);
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()){
                    User user = new User();
                    user.setEmail(emailStr);
                    user.setName(nameStr);
                    user.setPassword(passwordStr);
                    user.setGender(genderStr);
                    registerUserToFirebase(user);
                }

            }
        });


        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select picture"),PICK_IMAGE_REQUEST);

            }
        });
        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton checkbtn=findViewById(i);
                genderStr=checkbtn.getText().toString();
            }
        });



    }



    private boolean validate() {
        boolean isValidate = false;
        nameStr=name.getText().toString();
        emailStr=email.getText().toString();
        passwordStr=password.getText().toString();
        if(TextUtils.isEmpty(nameStr)){
            name.setError("Required");
        }
        else if (TextUtils.isEmpty(emailStr)){
            email.setError("Reduired");
        }
        else if (TextUtils.isEmpty(genderStr)){
            Toast.makeText(this,"Enter Gender",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(passwordStr)){
            password.setError("Required");
        }
        else if (passwordStr.length()<8){
            Toast.makeText(this, "minimum 8 charecter", Toast.LENGTH_SHORT).show();
        }
        else{
            isValidate = true;
        }
        return isValidate;
        }
        StorageReference storageReference;

    private  void registerUserToFirebase(final User user){
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(user.getEmail(),user.getPassword())
                .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("Signup", "inside oncomplete");
                        if (task.isSuccessful()) {

                            if(bitmap!=null) {
                                storageReference = FirebaseStorage.getInstance()
                                        .getReference().child("profile_Pictures")
                                        .child(FirebaseAuth.getInstance().getUid());
                                storageReference.putFile(uri)
                                        .continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                                            @Override
                                            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                                if (!task.isSuccessful()) {
                                                    throw task.getException();
                                                }

                                                Log.d("Create","the url is:"+storageReference.getDownloadUrl());
                                                return storageReference.getDownloadUrl();
                                            }

                                        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Uri> task) {
                                        if (task.isSuccessful()){
                                            Uri uri = task.getResult();
                                            Log.d("Create","The url is:"+uri.toString());
                                            String downloadurl=uri.toString();
                                            user.setProfile_image(downloadurl);
                                            addUser(user);
                                        }else{
                                            Toast.makeText(SignupActivity.this, "error::"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }else {

                                user.setProfile_image("");
                                addUser(user);
                            }

                        }
                    }
                });
    }

    private void addUser(User user){
        FirebaseDatabase.getInstance().getReference()
                .child("user")
                .child(FirebaseAuth.getInstance().getUid())
                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){

                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== PICK_IMAGE_REQUEST && resultCode==RESULT_OK && data!=null && data.getData() != null){
            uri = data.getData();
            try{
                bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(),uri);
                profile_image.setImageBitmap(bitmap);

            }catch (IOException e){
                e.printStackTrace();
            }
        }

    }
}
