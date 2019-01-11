package com.joshuahalvorson.android_sprint6challenge_contacts;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.atomic.AtomicBoolean;

public class NetworkAdapter {
    static ImageCache cache = ImageCache.getInstance();


    public interface NetworkCallback {
        void returnResult(Boolean success, String result);
    }

    public static void httpGetRequest(final String urlString, final NetworkCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String result = "";
                boolean success = false;
                HttpURLConnection connection = null;
                InputStream stream = null;
                try {
                    URL url = new URL(urlString);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.connect();
                    int responseCode = connection.getResponseCode();
                    if(responseCode == HttpURLConnection.HTTP_OK) {
                        stream = connection.getInputStream();
                        if(stream != null) {
                            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                            StringBuilder builder = new StringBuilder();
                            String line = reader.readLine();
                            while(line != null){
                                builder.append(line);
                                line = reader.readLine();
                            }
                            result = builder.toString();
                            success = true;
                        }
                    } else {
                        result = String.valueOf(responseCode);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if(connection != null) {
                        connection.disconnect();
                    }

                    if(stream != null) {
                        try {
                            stream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    callback.returnResult(success, result);
                }
                //return result;

            }
        }).start();

    }

    public static Bitmap httpImageRequest(String urlString, final AtomicBoolean canceled){
        cache.initializeCache();
        if(canceled.get()){
            Log.i("ImageRequestCanceled", urlString);
            return null;
        }
        Bitmap image = null;
        InputStream stream = null;
        HttpURLConnection connection = null;
        try{
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            if(canceled.get()){
                Log.i("ImageRequestCanceled", urlString);
                throw new IOException();
            }
            connection.connect();
            int responseCode = connection.getResponseCode();
            if(canceled.get()){
                Log.i("ImageRequestCanceled", urlString);
                throw new IOException();
            }
            if(responseCode == HttpURLConnection.HTTP_OK){
                stream = connection.getInputStream();
                if(stream != null){
                    image = BitmapFactory.decodeStream(stream);
                }
            }else{
                throw new IOException("HTTP Error code: " + responseCode);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(stream != null){
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(connection != null){
                connection.disconnect();
            }
        }
        cache.addImageToCache(urlString.substring(urlString.length() - 6, urlString.length()), image);
        return image;
    }
}
