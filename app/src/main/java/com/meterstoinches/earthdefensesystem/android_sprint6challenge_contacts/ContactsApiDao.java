package com.meterstoinches.earthdefensesystem.android_sprint6challenge_contacts;

import android.graphics.Bitmap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class ContactsApiDao {
    public interface ObjectCallback<T> {
        void returnContacts(ArrayList<T> object);
    }

    public static void getAllContacts(final ObjectCallback<Contact> objectCallback) {
        final ArrayList<Contact> contacts = new ArrayList<>();
        final NetworkAdapter.NetworkCallback callback = new NetworkAdapter.NetworkCallback() {
            @Override
            public void returnResult(Boolean success, String page) {
                String nextUrl = null;
                try {
                    nextUrl = new JSONObject(page).getString("next");
                } catch (JSONException e) {
                    e.printStackTrace();
                    nextUrl = null;
                }
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
                    objectCallback.returnContacts(contacts);
                }
            }
        };
        NetworkAdapter.httpGetRequest("https://randomuser.me/api/?format=json&inc=name,email,phone,picture&results=1000", callback);
    }

    public static Bitmap getImage(String url, final AtomicBoolean atomicBoolean){
        return NetworkAdapter.httpImageRequest(url, atomicBoolean);
    }
}
