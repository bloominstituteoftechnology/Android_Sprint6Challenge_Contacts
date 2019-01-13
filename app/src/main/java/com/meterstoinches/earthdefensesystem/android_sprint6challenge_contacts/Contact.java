package com.meterstoinches.earthdefensesystem.android_sprint6challenge_contacts;

import org.json.JSONException;
import org.json.JSONObject;

public class Contact {
    private String firstName, lastName, email, phone, imgUrl;

    public Contact(String firstName, String lastName, String email, String phone, String imgUrl) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.imgUrl = imgUrl;
    }

    public Contact (JSONObject json){
        try {
            this.firstName = json.getJSONObject("name").getString("first");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            this.lastName = json.getJSONObject("name").getString("last");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            this.email = json.getString("email");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            this.phone = json.getString("phone");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            this.imgUrl = json.getJSONObject("picture").getString("thumbnail");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
