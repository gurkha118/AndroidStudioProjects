package com.example.user.firebasedemo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


public class ProfileFragment extends Fragment {

    TextView gender,email,username;
    ImageView profile_pic;
    RecyclerView recyclerView;
    ProgressBar progressBar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile, container, false);
        gender=view.findViewById(R.id.gender);
        email=view.findViewById(R.id.email);
        username=view.findViewById(R.id.username);
        profile_pic=view.findViewById(R.id.profile_pic);
        recyclerView=view.findViewById(R.id.recyclerview);
        progressBar=view.findViewById(R.id.progressbar);


        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        getUserDetails();
        getUserPosts();
        return view;
    }
    private void getUserPosts(){
        FirebaseDatabase.getInstance().getReference().child("posts")
                .orderByChild("postuploaderid").equalTo(FirebaseAuth.getInstance().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        List<Post> postList=new ArrayList<>();
                            Iterator<DataSnapshot> iterator=dataSnapshot.getChildren().iterator();
                            while (iterator.hasNext()){
                                DataSnapshot snap=iterator.next();
                                postList.add(snap.getValue(Post.class));

                            }
                            HomeAdapter homeAdapter =new HomeAdapter(postList, new HomeAdapter.OnFeedClickHandleListener() {
                                @Override
                                public void onFeedClicked(Post post) {
                                    Intent intent= new Intent(getContext(),PostDetails.class);
                                    intent.putExtra("caption",post.getCaption());
                                    intent.putExtra("description",post.getDescription());
                                    intent.putExtra("postId",post.getPostid());
                                    intent.putExtra("image",post.getImage());

                                    startActivity(intent);

                                }

                                @Override
                                public void onLikeButtonToggled(Post post, boolean like) {
                                    HashMap<String, Boolean> Likes = new HashMap<>();
                                    if (like) {
                                        post.setLike(post.getLike() + 1);
                                        Likes.put(FirebaseAuth.getInstance().getUid(), true);

                                        //Toast.makeText(getContext(), "Liked", Toast.LENGTH_SHORT).show();
                                    } else {
                                        post.setLike(post.getLike() - 1);
                                        Likes.put(FirebaseAuth.getInstance().getUid(), false);
                                        //Toast.makeText(getContext(), "Unliked", Toast.LENGTH_SHORT).show();
                                    }
                                    post.setLikes(Likes);
                                    FirebaseDatabase.getInstance().getReference().child("posts")
                                            .child(post.getPostid())
                                            .setValue(post);
                                }
                            });
                            recyclerView.setAdapter(homeAdapter);
                            progressBar.setVisibility(View.GONE);
                        }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }

    private void  getUserDetails(){
        FirebaseDatabase.getInstance().getReference()
                .child("user")
                .child(FirebaseAuth.getInstance().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);
                        username.setText(user.getName());
                        email.setText(user.getEmail());
                        gender.setText(user.getGender());

                        Glide.with(profile_pic.getContext()).load(user.getProfile_image()).into(profile_pic);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }


}
