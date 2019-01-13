package com.example.patrickjmartin.android_sprint6challenge_contacts;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;


public class ContactDao {

    public interface ObjectCallback<T> {
        void returnContacts(T object);
    }

    private static final String URL =
            "https://randomuser.me/api/?format=json&inc=name,email,phone,picture&results=1000";

    public static void getAllUsers(final ObjectCallback<ArrayList<Contact>> objectCallBack) {

        final ArrayList<Contact> contacts = new ArrayList<>();
        final NetworkAdapter.NetworkCallback callback = (success, result) -> {
            try {
                JSONObject pageJSON = new JSONObject(result);
                JSONArray resultsArray = pageJSON.getJSONArray("results");

                for(int i = 0; i < resultsArray.length(); i++){
                    try {
                        contacts.add(new Contact(resultsArray.getJSONObject(i)));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                objectCallBack.returnContacts(contacts);
            }

        };
        NetworkAdapter.httpGetRequest(URL, callback);
    }



}
