package com.example.user.firebasedemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class HomeFragment extends Fragment {
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home,container,false);
        recyclerView=view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        fetchFromDatabase();
        return view;
    }
    public void fetchFromDatabase(){
        FirebaseDatabase.getInstance().getReference().child("posts")
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
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }
}

