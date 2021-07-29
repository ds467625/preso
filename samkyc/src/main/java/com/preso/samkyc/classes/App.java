package com.preso.samkyc.classes;

import android.app.Application;

import com.preso.samkyc.listerner.OnUriListerner;

public class App  {
    private static App sInstance;
    private static byte[] mCapturedPhotoData;
    private static OnUriListerner monUriListerner;

    // Getters & Setters
    public static byte[] getCapturedPhotoData() {
        return mCapturedPhotoData;
    }

    public static void setOnUriListerner(OnUriListerner onUriListerner){
        monUriListerner = onUriListerner;
    }

    public static OnUriListerner getOnUriListerner() {
        return monUriListerner;
    }

    public static void setCapturedPhotoData(byte[] capturedPhotoData) {
        mCapturedPhotoData = capturedPhotoData;
    }

    // Singleton code
    public static App getInstance() { return sInstance; }


}
