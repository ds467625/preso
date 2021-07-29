package com.preso.samkyc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;


import com.preso.samkyc.listerner.OnKycListener;

public class KYClient {

    public static OnKycListener onKycListener;
    static String TAG = KYClient.class.getSimpleName();





    public static void launchKYC(@NonNull Context context) {
        if (onKycListener != null) {
            Intent intent = new Intent(context, KYCAcitvity.class);
            context.startActivity(intent);
        } else {
            Log.e(TAG, "Please use setKycListerner before calling this method");
            Toast.makeText(context, "Please use setKycListerner before calling this method", Toast.LENGTH_LONG).show();
        }

    }

    public static void setOnKycListener(OnKycListener onKycListener) {
        KYClient.onKycListener = onKycListener;
    }

    protected static OnKycListener getOnKycListener() {
        return onKycListener;
    }
}
