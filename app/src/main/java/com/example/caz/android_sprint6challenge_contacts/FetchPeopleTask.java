package com.example.caz.android_sprint6challenge_contacts;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FetchPeopleTask extends AsyncTask <Void, String, List<Person>>{

    PeopleResponse peopleResponse;
    public FetchPeopleTask(PeopleResponse peopleResponse){


        this.peopleResponse = peopleResponse;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<Person> doInBackground(Void... voids) {

        String url = "https://randomuser.me/api/?format=json&inc=name,email,phone,picture&results=1000";
        String response = NetworkAdapter.httpRequest(url);
        Log.d("Response", response);
        // Parse the response and create list of people
        List<Person> people = new ArrayList<>();


        try {
            JSONArray result = new JSONObject(response).getJSONArray("results");

            for(int i=0; i<result.length(); ++i){
                JSONObject personObject = result.getJSONObject(i);
                Person person = new Person(personObject);
                people.add(person);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return people;
    }

    @Override
    protected void onPostExecute(List<Person> people) {
        super.onPostExecute(people);
        peopleResponse.onPeopleLoaded(people); // for getting data
    }
}
