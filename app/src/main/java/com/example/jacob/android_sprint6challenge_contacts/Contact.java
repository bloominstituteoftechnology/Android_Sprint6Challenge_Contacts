package com.example.jacob.android_sprint6challenge_contacts;

import org.json.JSONException;
import org.json.JSONObject;

public class Contact {
    int id;
    String name;
    private String phone;
    private String email;
    private String imageUrl;

    public Contact(int id, String name, String phone, String email, String imageUrl) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.imageUrl = imageUrl;
    }

    public Contact(JSONObject json, int inputId) {
        this.id = inputId;
        try {
            JSONObject names = json.getJSONObject("name");
            String fullName = "";

            fullName += names.getString("title") + ", ";
            fullName += names.getString("first") + ", ";
            fullName += names.getString("last") + ", ";

            this.name = fullName;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.phone = json.getString("phone");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.email = json.getString("email");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject imageUrls = null;
        try {
            imageUrls = json.getJSONObject("picture");
            this.imageUrl =  imageUrls.getString("thumbnail");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
