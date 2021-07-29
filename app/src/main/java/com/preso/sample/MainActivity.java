package com.preso.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.preso.samkyc.KYClient;
import com.preso.samkyc.listerner.OnKycListener;

public class MainActivity extends AppCompatActivity implements OnKycListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        KYClient.setOnKycListener(this);

        KYClient.launchKYC(this);

    }

    @Override
    public void onKycSuccess() {
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onKycFailure() {
        Toast.makeText(this, "Failure", Toast.LENGTH_SHORT).show();
    }
}