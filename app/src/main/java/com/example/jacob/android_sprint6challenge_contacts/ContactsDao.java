package com.example.jacob.android_sprint6challenge_contacts;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class ContactsDao {

    public interface ObjectCallback<T> {
        void returnContacts(T object);
    }

    public static void getContacts(final AtomicBoolean canceled, final ObjectCallback<ArrayList<Contact>> objectCallback) {

        final ArrayList<Contact> contacts = new ArrayList<>();
        final NetworkAdapter.NetworkCallback callback = new NetworkAdapter.NetworkCallback() {
            @Override
            public void returnResult(Boolean success, String page) {
                // process page of data
                if (canceled.get()) {
                    Log.i("GetRequestCanceled", page);
                    return;
                }
                try {
                    JSONObject pageJson = new JSONObject(page);
                    JSONArray resultsArray = pageJson.getJSONArray("results");

                    if (canceled.get()) {
                        Log.i("GetRequestCanceled", page);
                        return;
                    }

                    for (int i = 0; i < resultsArray.length(); ++i) {
                        try {
                            contacts.add(new Contact(resultsArray.getJSONObject(i),contacts.size()));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                    objectCallback.returnContacts(contacts);
            }
        };
        if (canceled.get()) {
            Log.i("GetRequestCanceled", "First");
            return;
        }
        NetworkAdapter.httpGetRequest("https://randomuser.me/api/?format=json&inc=name,email,phone,picture&results=1000", canceled, callback);
    }
}
