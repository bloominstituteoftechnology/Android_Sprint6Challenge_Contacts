package com.joshuahalvorson.android_sprint6challenge_contacts;

import android.graphics.Bitmap;
import android.util.LruCache;

public class ImageCache<T> {

    private LruCache<String, T> cache;
    private static ImageCache imageCache;

    public static ImageCache getInstance() {
        if(imageCache == null) {
            imageCache = new ImageCache();
        }
        return imageCache;
    }

    public void initializeCache() {
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() /1024);
        final int cacheSize = maxMemory / 8;

        cache = new LruCache<String, T>(cacheSize)
        {
            protected int sizeOf(String key, Bitmap value)
            {
                int bitmapByteCount = value.getRowBytes() * value.getHeight();
                return bitmapByteCount / 1024;
            }
        };
    }

    public void addImageToCache(String key, T bitmap){
        if(cache != null && cache.get(key) == null){
            cache.put(key, bitmap);
        }
    }

    public T getImageFromCache(String key) {
        if(key != null) {
            return cache.get(key);
        }
        else{
            return null;
        }
    }

    public void clearCache(){
        if(cache != null){
            cache.evictAll();
        }
    }
}
