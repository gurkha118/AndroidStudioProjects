package com.surya.apidemo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Contacts {

    @SerializedName("contacts")
    @Expose

    private List<Contact> contactList;

    public List<Contact> getContactList() {

        return contactList;
    }

    public void setContactList(List<Contact> contactList) {

        this.contactList = contactList;
    }


}
