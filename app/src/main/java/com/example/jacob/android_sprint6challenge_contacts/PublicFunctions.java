package com.example.jacob.android_sprint6challenge_contacts;

import android.content.Context;

import java.io.File;

public class PublicFunctions {

    static String getSearchText(String inputString) {
        String outputString = inputString.substring(inputString.indexOf("portraits/") + 10);
        outputString = outputString.replace('/','-');
        return outputString;
    }

    static File getFileFromCache(String searchText, Context context) {
        File file = null;
        File[] items = context.getCacheDir().listFiles();
        for (File item : items) {
            if (item.getName().contains(searchText)) {
                file = item;
                break;
            }
        }
        return file;
    }


}
