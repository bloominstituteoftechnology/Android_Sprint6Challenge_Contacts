package com.example.jacob.android_sprint6challenge_contacts;

import org.json.JSONObject;

public class Contact {
    int id;
    private String name, phone, email, imageUrl;

    public Contact(int id, String name, String phone, String email, String imageUrl) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.imageUrl = imageUrl;
    }

    public Contact(JSONObject json) {

    }
}
