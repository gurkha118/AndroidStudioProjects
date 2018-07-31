package com.example.user.firebasedemo;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PostDetails extends AppCompatActivity {
    TextView caption,description;
    ImageView imageview;
    Button post;
    EditText editcomment;
    TextView comment;
    String captionStr,postid,descriptionStr,imageurl,commentStr;
    RecyclerView recyclerView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);
        handleToolbarAction();

        post=findViewById(R.id.post);
        caption=findViewById(R.id.caption);
        description=findViewById(R.id.description);
        imageview=findViewById(R.id.show_image);
        editcomment=findViewById(R.id.editcomment);
        recyclerView=findViewById(R.id.show_comment_list);

        captionStr=getIntent().getExtras().getString("caption");
        postid=getIntent().getExtras().getString("postId");
        descriptionStr=getIntent().getExtras().getString("description");
        imageurl=getIntent().getExtras().getString("image");

        caption.setText(captionStr);
        description.setText(descriptionStr);
        Glide.with(imageview.getContext()).load(imageurl).into(imageview);


        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commentStr=editcomment.getText().toString();
                if(!TextUtils.isEmpty(commentStr))
                {
                    addCommentToDatabase();

                }else{
                    editcomment.setError("Required");
                }
            }
        });


        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        getCommentsList();



    }
    private  void handleToolbarAction(){
        getSupportActionBar().setTitle("Post detail");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;

    }

    private void addCommentToDatabase(){
        Comment comment = new Comment();
        comment.setComment(commentStr);
        comment.setCommenterid(FirebaseAuth.getInstance().getUid());
        comment.setPostid(postid);


       String commentId= FirebaseDatabase.getInstance().getReference()
                .child("comments")
                .push().getKey();

       FirebaseDatabase.getInstance().getReference().child("comments").child(postid)
               .child(commentId)
               .setValue(comment)
               .addOnCompleteListener(new OnCompleteListener<Void>() {
                   @Override
                   public void onComplete(@NonNull Task<Void> task) {
                       if(task.isSuccessful()){

                           Toast.makeText(PostDetails.this, "Added to databse", Toast.LENGTH_SHORT).show();

                           FirebaseDatabase.getInstance().getReference().child("posts")
                                   .child(postid)
                                   .addListenerForSingleValueEvent(new ValueEventListener() {
                                       @Override
                                       public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                           Post post=dataSnapshot.getValue(Post.class);
                                           post.setCommentcount(post.getCommentcount()+1);
                                           FirebaseDatabase.getInstance().getReference().child("posts")
                                           .child(postid).setValue(post);
                                       }

                                       @Override
                                       public void onCancelled(@NonNull DatabaseError databaseError) {

                                       }
                                   });
                       }else{
                           Toast.makeText(PostDetails.this, "failed", Toast.LENGTH_SHORT).show();
                       }
                   }
               });

    }
    private void getCommentsList(){
       FirebaseDatabase.getInstance().getReference().child("comments").child(postid)
               .addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                       List<Comment> commentList=new ArrayList<>();
                       Iterator<DataSnapshot> iterator=dataSnapshot.getChildren().iterator();
                       while (iterator.hasNext()){
                           DataSnapshot snap=iterator.next();
                           commentList.add(snap.getValue(Comment.class));


                       }
                       CommentAdapter commentAdapter=new CommentAdapter(commentList);
                       recyclerView .setAdapter(commentAdapter);

                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError databaseError) {

                   }
               });

    }
}
