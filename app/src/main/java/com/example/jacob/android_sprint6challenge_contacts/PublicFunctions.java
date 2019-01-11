package com.example.jacob.android_sprint6challenge_contacts;

public class PublicFunctions {

    public static String getSearchText(String inputString) {
        String outputString = inputString.substring(inputString.indexOf("thumb/") + 6);
        outputString = outputString.replace('/','-');
        return outputString;
    }
}
