package com.example.lambda_school_loaner_47.contactsapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

class ContactsDao {

    public static final String BASE_URL = "https://randomuser.me/api/?format=json&inc=name,email,phone,picture&results=1000";

    public static ArrayList<Contacts> getContacts(){

        Contacts            contact = null;
        ArrayList<Contacts> list    = new ArrayList<>();
        String              url     = BASE_URL;
        String              result  = NetworkAdapter.httpRequest(url,
                NetworkAdapter.GET);

        try {
            JSONObject jsonObject   = new JSONObject(result);
            JSONArray  resultsArray = jsonObject.getJSONArray("results");

            int i = 0;
            String firstName  = null;
            String lastName   = null;
            String phone      = null;
            String email      = null;
            String largeImage = null;
            String thumbImage = null;
            Contacts current;

            while (i < resultsArray.length()){

                JSONObject contactObject = resultsArray.getJSONObject(i);
                current = new Contacts(resultsArray.getJSONObject(i));

                list.add(current);
                i++;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
}
