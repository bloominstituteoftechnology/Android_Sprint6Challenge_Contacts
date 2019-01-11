package com.example.patrickjmartin.android_sprint6challenge_contacts;

import org.json.JSONException;
import org.json.JSONObject;

public class Contact {

    String titleName, firstName, lastName, email, phoneNumber, pictureLarge, pictureThumb;

    public Contact (JSONObject json) {

        try {
            JSONObject name = json.getJSONObject("name");
            String fullName = "";

            titleName = name.getString("title") + ", ";
            firstName = name.getString("first") + ", ";
            lastName = name.getString("last") + ", ";
            
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
