package com.meterstoinches.earthdefensesystem.android_sprint6challenge_contacts;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ContactsApiDao {
    public static void getAllContacts(final ObjectCallback<Contact> objectCallback) {
        final ArrayList<Contact> contacts = new ArrayList<>();
        final NetworkAdapter.NetworkCallback callback = new NetworkAdapter.NetworkCallback() {
            @Override
            public void returnResult(Boolean success, String page) {
                // process page of data
                String nextUrl = null;
                try {
                    nextUrl = new JSONObject(page).getString("next");
                } catch (JSONException e) {
                    e.printStackTrace();
                    nextUrl = null;
                }
                // yay recursion!
                if (nextUrl != null) {
                    NetworkAdapter.httpGetRequest(nextUrl, this);
                }

                try {
                    JSONObject pageJson     = new JSONObject(page);
                    JSONArray resultsArray = pageJson.getJSONArray("results");
                    for (int i = 0; i < resultsArray.length(); ++i) {
                        try {
                            contacts.add(new Contact(resultsArray.getJSONObject(i)));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (nextUrl == null) {
                    /*synchronized (planets) {
                        planets.notify();
                    }*/
                    objectCallback.returnContacts(contacts);
                }
            }
        };
        NetworkAdapter.httpGetRequest("https://swapi.co/api/planets", callback);
    }
}
