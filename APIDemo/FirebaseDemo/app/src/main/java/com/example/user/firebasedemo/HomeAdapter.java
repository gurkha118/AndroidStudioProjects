package com.example.user.firebasedemo;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder>{
    List<Post> postList;
    OnFeedClickHandleListener onFeedClickHandleListener;

    public HomeAdapter(List<Post> postList,OnFeedClickHandleListener onFeedClickHandleListener){
        this.postList= postList;
    this.onFeedClickHandleListener=onFeedClickHandleListener;}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindView(postList.get(position));

    }




    @Override
    public int getItemCount() {
        return postList.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder{
        TextView caption,description,commentcount,like;
        ToggleButton toggle_btn;
        ImageView imageView;
        public ViewHolder(View itemView){
            super(itemView);
            caption=itemView.findViewById(R.id.caption);
            description=itemView.findViewById(R.id.description);
            imageView=itemView.findViewById(R.id.imageview);
            commentcount=itemView.findViewById(R.id.commentcount);
            like=itemView.findViewById(R.id.like);
            toggle_btn=itemView.findViewById(R.id.toggle_btn);


        }
        public void bindView(final Post post){
            caption.setText(post.getCaption());
            description.setText(post.getDescription());
            commentcount.setText(post.getCommentcount()+ " comments");
            like.setText(post.getLike()+ " likes");

            if(post.getLikes()!=null){
                if(post.getLikes().containsKey(FirebaseAuth.getInstance().getUid())){
                    if (post.getLikes().get(FirebaseAuth.getInstance().getUid())){
                        toggle_btn.setChecked(true);
                    }else{
                        toggle_btn.setChecked(false);
                    }
                }

            }
            toggle_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b){
                        onFeedClickHandleListener.onLikeButtonToggled(post,true);

                    }
                    else {
                        onFeedClickHandleListener.onLikeButtonToggled(post,false);

                    }
                }
            });
            if(!TextUtils.isEmpty(post.getImage())) {
                imageView.setVisibility(View.VISIBLE);
                Glide.with(imageView.getContext()).load(post.getImage()).into(imageView);
            }else {
                imageView.setVisibility(View.GONE);
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onFeedClickHandleListener.onFeedClicked(post);
                }
            });

        }
    }

    interface OnFeedClickHandleListener{
        void onFeedClicked(Post post);
         void  onLikeButtonToggled(Post post, boolean like);
    }
    }







