package com.example.patrickjmartin.android_sprint6challenge_contacts;

import org.json.JSONException;
import org.json.JSONObject;

public class Contact {

    String titleName, firstName, lastName, email, phoneNumber, pictureLarge, pictureThumb;

    public Contact (JSONObject json) {

        JSONObject pictures = null;

        try {
            JSONObject name = json.getJSONObject("name");
            String fullName = "";

            titleName = name.getString("title") + ", ";
            firstName = name.getString("first") + ", ";
            lastName = name.getString("last") + ", ";

        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            this.email = json.getString("email");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            this.phoneNumber = json.getString("phone");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            pictures = json.getJSONObject("picture");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            this.pictureLarge = pictures.getString("large");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            this.pictureThumb = pictures.getString("thumbnail");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public String getTitleName() {
        return titleName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPictureLarge() {
        return pictureLarge;
    }

    public String getPictureThumb() {
        return pictureThumb;
    }
}
