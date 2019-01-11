package com.joshuahalvorson.android_sprint6challenge_contacts;

import android.graphics.Bitmap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UsersDbDao {
    public interface ObjectCallback<T> {
        void usersResult(T result);
    }
    private static final String URL =
            "https://randomuser.me/api/?format=json&inc=name,email,phone,picture&results=1000";

    public static void getAllUsers(final ObjectCallback<List<User>> usersCallback){
        final List<User> users = new ArrayList<>();
        final NetworkAdapter.NetworkCallback callback = new NetworkAdapter.NetworkCallback() {
            @Override
            public void returnResult(Boolean success, String result) {
                try {
                    JSONObject pageJson = new JSONObject(result);
                    JSONArray resultsArray = pageJson.getJSONArray("results");
                    for (int i = 0; i < resultsArray.length(); ++i) {
                        try {
                            users.add(new User(resultsArray.getJSONObject(i)));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    usersCallback.usersResult(users);
                }
            }
        };
        NetworkAdapter.httpGetRequest(URL, callback);
    }

    public static Bitmap getImage(String url){
        return NetworkAdapter.httpImageRequest(url);
    }
}
