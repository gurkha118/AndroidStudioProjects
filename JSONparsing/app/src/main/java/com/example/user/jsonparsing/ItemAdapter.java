package com.example.user.jsonparsing;

import android.content.ClipData;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder>{
    List<item> itemList;

    public ItemAdapter(List<item> itemList){this.itemList=itemList;}


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_items,parent,false);
        return new RecyclerView.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ViewHolder holder, int position) {
        holder.bindView(itemList.get(position));

    }


    @Override
    public int getItemCount(){return itemList.size();}

    class ViewHolder extends RecyclerViews.ViewHolder{
        TextView
    }


}
