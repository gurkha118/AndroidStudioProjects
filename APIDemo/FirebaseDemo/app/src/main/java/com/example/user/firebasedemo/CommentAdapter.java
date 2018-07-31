package com.example.user.firebasedemo;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder>{
    List<Comment> commentList;

    public CommentAdapter(List<Comment> commentList) {
        this.commentList = commentList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindView(commentList.get(position));

    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView showcomment;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            showcomment=itemView.findViewById(R.id.showcomment);
        }

        public void bindView(Comment comment){
            showcomment.setText(comment.getComment());
        }
    }
}

