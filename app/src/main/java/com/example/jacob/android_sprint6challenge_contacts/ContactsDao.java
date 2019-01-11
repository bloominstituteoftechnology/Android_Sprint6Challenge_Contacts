package com.example.jacob.android_sprint6challenge_contacts;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class ContactsDao {

    public interface ObjectCallback<T> {
        void returnObjects(T object);
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
                            contacts.add(new Contact(resultsArray.getJSONObject(i), contacts.size()));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                objectCallback.returnObjects(contacts);
            }
        };
        if (canceled.get()) {
            Log.i("GetRequestCanceled", "First");
            return;
        }
        NetworkAdapter.httpGetRequest("https://randomuser.me/api/?format=json&inc=name,email,phone,picture&results=1000", canceled, callback);
    }


    static void getImageFile(String url, Context context) {
        File file = null;
        if (url != null) {
            String searchText = PublicFunctions.getSearchText(url);
            File[] items = context.getCacheDir().listFiles();
            Boolean fileFound = false;
            for (File item : items) {
                if (item.getName().contains(searchText)) {
                    fileFound = true;
                    break;
                }
            }
            if (!fileFound) {
                Bitmap bitmap = NetworkAdapter.httpImageRequest(url);
                FileOutputStream fileOutputStream = null;
                try {
                    file = File.createTempFile(searchText, null, context.getCacheDir());
                    fileOutputStream = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    static void getImageFile(final String url, final Context context, final AtomicBoolean canceled, final boolean objectCallback) {

        final NetworkAdapter.NetworkImageCallback callback = new NetworkAdapter.NetworkImageCallback() {

            @Override
            public void returnResult(Boolean success, Bitmap result) {
                if (canceled.get()) {
                    Log.i("GetRequestCanceled", url);
                    return;
                }

                File file = null;
                if (url != null) {
                    String searchText = PublicFunctions.getSearchText(url);
                    File[] items = context.getCacheDir().listFiles();
                    Boolean fileFound = false;
                    for (File item : items) {
                        if (item.getName().contains(searchText)) {
                            fileFound = true;
                            break;
                        }
                    }
                    if (!fileFound) {
                        Bitmap bitmap = NetworkAdapter.httpImageRequest(url);
                        FileOutputStream fileOutputStream = null;
                        try {
                            file = File.createTempFile(searchText, null, context.getCacheDir());
                            fileOutputStream = new FileOutputStream(file);
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            if (fileOutputStream != null) {
                                try {
                                    fileOutputStream.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }
        };
        NetworkAdapter.httpImageRequest(url, canceled, callback);
    }
}
