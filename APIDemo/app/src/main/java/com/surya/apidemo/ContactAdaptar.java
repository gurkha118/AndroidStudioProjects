package com.surya.apidemo;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ContactAdaptar extends RecyclerView.Adapter<ContactAdaptar.ViewHolder> {
    List<Contact> contactList;

    public ContactAdaptar(List<Contact> contactList) {
        this.contactList = contactList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindView(contactList.get(position));

    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        TextView id,name,email,address,gender,mobile,home,office;
        public ViewHolder(View itemView){
            super(itemView);
            id=itemView.findViewById(R.id.id);
            name=itemView.findViewById(R.id.name);
            email=itemView.findViewById(R.id.email);
            address=itemView.findViewById(R.id.address);
            gender=itemView.findViewById(R.id.gender);
            mobile=itemView.findViewById(R.id.mobile);
            home=itemView.findViewById(R.id.home);
            office=itemView.findViewById(R.id.office);
        }
        public void bindView(Contact contact){
            id.setText(contact.getId());
            name.setText(contact.getName());
            email.setText(contact.getEmail());
            address.setText(contact.getAddress());
            gender.setText(contact.getGender());
            mobile.setText((contact.getPhone().getMobile()));
            home.setText((contact.getPhone().getHome()));
            office.setText((contact.getPhone().getOffice()));
        }
    }
}
