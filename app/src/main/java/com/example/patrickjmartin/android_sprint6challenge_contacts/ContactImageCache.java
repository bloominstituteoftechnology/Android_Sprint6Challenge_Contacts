package com.example.patrickjmartin.android_sprint6challenge_contacts;

import android.arch.paging.PagedListAdapter;
import android.graphics.Bitmap;
import android.util.LruCache;

public class ContactImageCache<T> {

    private static volatile ContactImageCache INSTANCE;
    private LruCache<String, T> genericCache;
    //Basically a linkedhashmap

    private ContactImageCache() {
        if (INSTANCE != null) {
            throw new RuntimeException("Use getINSTANCE()");
        } else {
            final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
            final int cacheSize = maxMemory / 8;
            genericCache = new LruCache<String, T>(cacheSize) {
                protected int sizeOf(String key, Bitmap bitmap) {
                    return bitmap.getByteCount() / 1024;
                }
            };
        }
    }

    public static ContactImageCache getINSTANCE() {
        if(INSTANCE == null) {
            synchronized (ContactImageCache.class) {
                if(INSTANCE == null) {
                    INSTANCE = new ContactImageCache();
                }
            }
        }
        return INSTANCE;
    }

    public void setObject(String key, T thing) {
        if (genericCache.get(key) == null) genericCache.put(key, thing);
    }

    public T getObject(String key) {
        return (key != null ? genericCache.get(key) : null);
    }

    public void clearGenericCache() {
        if (genericCache != null) genericCache.evictAll();
    }

}
