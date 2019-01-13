package com.meterstoinches.earthdefensesystem.android_sprint6challenge_contacts;

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
            }
        }).start();
    }
//
//    public static Bitmap httpImageRequest(String urlString) {
//        Bitmap resultImage = null;
//        boolean success = false;
//        InputStream stream = null;
//        HttpURLConnection connection = null;
//
//        URL url = null;
//        try {
//            url = new URL(urlString);
//            connection = (HttpURLConnection) url.openConnection();
//            connection.connect();
//            int responseCode = connection.getResponseCode();
//            if (responseCode == HttpURLConnection.HTTP_OK) {
//                stream = connection.getInputStream();
//                if (stream != null) {
//                    resultImage = BitmapFactory.decodeStream(stream);
//                }
//            }
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (stream != null) {
//                try {
//                    stream.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            if (connection != null) {
//                connection.disconnect();
//            }
//        }
//        return resultImage;
//
//    }

    public static Bitmap httpImageRequest(String urlString, final AtomicBoolean cancelRequest) {
        if(cancelRequest.get()){
            Log.i("Canceled", urlString);
            return null;
        }
        Bitmap resultImage = null;
        InputStream stream = null;
        boolean success = false;
        HttpURLConnection connection = null;

        URL url = null;


        try {
            if(cancelRequest.get()) {
                Log.i("Canceled", urlString);
                throw new IOException();
            }
            url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                if(cancelRequest.get()) {
                    Log.i("Canceled", urlString);
                    throw new IOException();
                }
                stream = connection.getInputStream();
                if (stream != null) {
                    resultImage = BitmapFactory.decodeStream(stream);
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
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
        if(cancelRequest.get()) {
            Log.i("Canceled", urlString);
        }
        return resultImage;
    }
}
