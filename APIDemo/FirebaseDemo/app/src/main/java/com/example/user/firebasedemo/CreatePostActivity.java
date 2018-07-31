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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class CreatePostActivity extends AppCompatActivity {

    EditText caption,description;
    Button addPost,selectImage;
    String captionStr,descriptionStr;
    int PICK_IMAGE_REQUEST=1;
    ImageView showImage;
    ProgressBar progressBar;
    Uri uri;
    Bitmap bitmap;
    StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        progressBar=findViewById(R.id.pb);
        caption=findViewById(R.id.caption_et);
        description=findViewById(R.id.description_et);
        addPost=findViewById(R.id.post_btn);
        selectImage=findViewById(R.id.select_image);
        showImage=findViewById(R.id.show_image);

        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select picture"),PICK_IMAGE_REQUEST);
            }
        });

        addPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()){
                    addPostToDatabase();
                }

            }
        });
    }


    private boolean validate(){
        boolean isValid=false;
        captionStr=caption.getText().toString();
        descriptionStr=description.getText().toString();

        if(TextUtils.isEmpty(captionStr)){
            caption.setError("Required");
        }else if(TextUtils.isEmpty(descriptionStr)){
            description.setError("Required");
        }else{
            isValid=true;
        }
        return isValid;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== PICK_IMAGE_REQUEST && resultCode==RESULT_OK && data!=null && data.getData() != null){
            uri = data.getData();
            try{
                bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(),uri);
                showImage.setImageBitmap(bitmap);

            }catch (IOException e){
                e.printStackTrace();
            }
        }

    }
    private void addPostToDatabase(){
        progressBar.setVisibility(View.VISIBLE);
        final Post post=new Post();
        post.setPostuploaderid(FirebaseAuth.getInstance().getCurrentUser().getUid());
        String postId=FirebaseDatabase.getInstance().getReference().child("posts").push().getKey();
        post.setCaption(captionStr);
        post.setPostid(postId);
        post.setDescription(descriptionStr);
        post.setCommentcount(0);
        post.setLike(0);
        if(bitmap!=null) {
            storageReference = FirebaseStorage.getInstance().getReference().child("post_Pictures").child(postId);
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
                    post.setImage(downloadurl);
                    uploadPost(post);
                    }else{
                        Toast.makeText(CreatePostActivity.this, "error::"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else {

            post.setImage("");
            uploadPost(post);
            }
        }

    private void uploadPost(Post post){
        FirebaseDatabase.getInstance().getReference().child("posts")
                .child(post.getPostid()).setValue(post).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    onBackPressed();
                }else {
                    Toast.makeText(CreatePostActivity.this, "erroe:"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



}
