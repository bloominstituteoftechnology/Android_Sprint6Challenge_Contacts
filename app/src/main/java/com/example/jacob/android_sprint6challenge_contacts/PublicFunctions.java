package com.example.jacob.android_sprint6challenge_contacts;

public class PublicFunctions {

    public static String getSearchText(String inputString) {
        String[] urlParts = inputString.substring(inputString.indexOf("thumb/") + 6).split(".");
        return urlParts[0];
    }
}
