package com.example.patrickjmartin.android_sprint6challenge_contacts;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Contact implements Serializable {

     private String titleName, firstName, lastName, email, phoneNumber, pictureLarge, pictureThumb, fullName;

    public Contact(String titleName, String firstName, String lastName, String email, String phoneNumber, String pictureLarge, String pictureThumb) {
        this.titleName = titleName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.pictureLarge = pictureLarge;
        this.pictureThumb = pictureThumb;
    }

    public Contact (JSONObject json) {

        JSONObject pictures = null;

        try {
            JSONObject name = json.getJSONObject("name");

            titleName = name.getString("title");
            firstName = name.getString("first");
            lastName = name.getString("last");

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

//        this.fullName = toCamelCase(titleName + " " + firstName + " " + lastName);

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

    public String getFullName() {
        if (fullName == null) {
            this.fullName = toCamelCase(titleName + " " + firstName + " " + lastName);
        }
        return fullName;
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

    public String getCacheKey(boolean isSmall) {
        if (isSmall) {
            return (pictureThumb.replaceFirst("^https://randomuser.me/api/portraits/", ""));
        }

        return null;
    }

    public static String toCamelCase(String initString) {
        String newString = "";
        if (initString == null || initString == "" ) {
            return newString;
        } else {
            String[] initAry = initString.split(" ");
            for (int i = 0; i < initAry.length; i++) {
                String first = initAry[i].substring(0,1).toUpperCase();
                String rest = initAry[i].substring(1).toLowerCase();

                if (i == 0) newString += (first + rest);
                else newString += (" " + first + rest);
            }
        }
        return newString;
    }
}
