package com.example.lambda_school_loaner_47.contactsapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.atomic.AtomicBoolean;

public class NetworkAdapter {

    public interface NetworkCallback {
        void returnResult(Boolean success, String result);
    }


    public static String httpRequest(String stringUrl, NetworkCallback callback) {
        String            result      = "";
        InputStream       stream      = null;
        HttpURLConnection connection  = null;
        try {
            URL url    = new URL(stringUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                stream = connection.getInputStream();
                if (stream != null) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                    StringBuilder builder = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        builder.append(line);
                    }
                    result = builder.toString();
                }
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
            result = e.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
            result = e.getMessage();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }

            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            callback.returnResult(true, result);
        }
        return result;
    }

    public static Bitmap httpImageRequest(String urlString, AtomicBoolean canceled) {

        if (canceled.get()){
            return null;
        }
        Bitmap            image      = null;
        InputStream       stream     = null;
        HttpURLConnection connection = null;
        try {
            URL url    = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            if (canceled.get()){
                return null;
            }
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                stream = connection.getInputStream();
                if (stream != null) {
                    image = BitmapFactory.decodeStream(stream);
                }
            } else {
                throw new IOException("HTTP Error code: " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
        return image;
    }
}
