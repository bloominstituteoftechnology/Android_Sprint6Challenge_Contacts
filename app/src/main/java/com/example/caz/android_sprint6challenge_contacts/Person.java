package com.example.caz.android_sprint6challenge_contacts;

import org.json.JSONException;
import org.json.JSONObject;

public class Person {
    private String title;
    private String first;
    private String last;
    private String email;
    private String phone;
    private String thumbnail;
    private String large;
    public Person(){

    }
    public Person(JSONObject personJson){


        try {
            JSONObject nameJson = personJson.getJSONObject("name");
            this.title = nameJson.getString("title");
            this.first = nameJson.getString("first");
            this.last = nameJson.getString("last");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //phone and email directly from the personJson object
        try {
            this.email = personJson.getString("email");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            this.phone = personJson.getString("phone");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Get json from person json and then get thumbnails and large links
        try {
            JSONObject pictureJson = personJson.getJSONObject("picture");
            this.thumbnail = pictureJson.getString("thumbnail");
            this.large = pictureJson.getString("large");

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
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

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }



}

