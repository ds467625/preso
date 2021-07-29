package com.preso.samkyc.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cloudinary.android.MediaManager;

import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.preso.samkyc.CameraActivity;
import com.preso.samkyc.R;
import com.preso.samkyc.classes.App;
import com.preso.samkyc.listerner.OnNextListerner;
import com.preso.samkyc.listerner.OnUriListerner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class OnBoardKycFrangment extends Fragment {

    private OnNextListerner onNextListerner;
    private Button onNext;
    private static int CAMERA_PERMISSION_CODE = 1;
    private static int ACTIVITY_CALLBACK = 2;
    private static String TAG = OnBoardKycFrangment.class.getSimpleName();
    public static int PROOF_IDENTITY = 1;
    public static int PROOF_SELFIE = 2;
    private ImageView proofIdentity, proofSelfie;
    public LinearLayout proofLayout, selfieLayout, checkLayout;
    private boolean isIndentity = false;
    private boolean isSelfie = false;
    boolean isCheck = true;


    public OnBoardKycFrangment() {
    }

    public OnBoardKycFrangment(OnNextListerner onNextListerner) {
        this.onNextListerner = onNextListerner;
    }

    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }


    private void startActivityResult(Intent intent) {
        // You can do the assignment inside onAttach or onCreate, i.e, before the activity is displayed
        ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            // There are no request codes
                            Intent data = result.getData();

                            byte[] img = App.getCapturedPhotoData();
                            Log.e(TAG, "onActivityResult: " + img);

                            if (img != null) {
                                String requestId = MediaManager.get().upload(img).callback(new UploadCallback() {
                                    @Override
                                    public void onStart(String requestId) {

                                    }

                                    @Override
                                    public void onProgress(String requestId, long bytes, long totalBytes) {

                                    }

                                    @Override
                                    public void onSuccess(String requestId, Map resultData) {

                                    }

                                    @Override
                                    public void onError(String requestId, ErrorInfo error) {

                                    }

                                    @Override
                                    public void onReschedule(String requestId, ErrorInfo error) {

                                    }
                                }).dispatch();
                            }

                        }
                    }
                });

        someActivityResultLauncher.launch(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CAMERA_PERMISSION_CODE) {

            // Checking whether user granted the permission or not.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                // Showing the toast message
                Toast.makeText(getContext(), "Camera Permission Granted", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), CameraActivity.class);
                intent.putExtra("category", 1);
                getContext().startActivity(intent);

            } else {
                Toast.makeText(getContext(), "Camera Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }

    }


    public boolean checkPermission(int requestCode, int category) {
        // Checking if permission is not granted
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED || ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED || ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, requestCode);
            return false;
        } else {
            Intent intent = new Intent(getContext(), CameraActivity.class);
            intent.putExtra("category", category);
            getContext().startActivity(intent);
            Toast.makeText(getContext(), "Permission already granted", Toast.LENGTH_SHORT).show();
        }
        return true;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_on_board_kyc_frangment, container, false);

        onNext = view.findViewById(R.id.nextbutton);
        proofIdentity = view.findViewById(R.id.proof_identity);
        proofSelfie = view.findViewById(R.id.proof_selfie);
        proofLayout = view.findViewById(R.id.proof_layout);
        selfieLayout = view.findViewById(R.id.selfi_layout);
        checkLayout = view.findViewById(R.id.check_layout);

        ((CheckBox) checkLayout.getChildAt(0)).setChecked(true);

        Map config = new HashMap();
        config.put("cloud_name", "dvu0tenxh");
        config.put("api_key", "537452829772183");
        config.put("api_secret", "9koEGhYDyst8zJkOJr8G9vUsNRM");
        MediaManager.init(getContext(), config);

        onNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isIndentity) {
                    Toast.makeText(getContext(), "Please upload your identity to continue", Toast.LENGTH_SHORT).show();
                    return;

                }

                if (!isSelfie) {
                    Toast.makeText(getContext(), "Please upload your Selfie with identity to continue", Toast.LENGTH_SHORT).show();
                    return;
                }

                onNextListerner.onNext(true);
            }
        });

        proofSelfie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission(CAMERA_PERMISSION_CODE, PROOF_SELFIE);
            }
        });
        proofIdentity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCheck) checkPermission(CAMERA_PERMISSION_CODE, PROOF_IDENTITY);
                else
                    Toast.makeText(getContext(), "Please one type of Identity", Toast.LENGTH_SHORT).show();
            }
        });

        checkBox();

        App.setOnUriListerner(new OnUriListerner() {
            @Override
            public void onUri(byte[] data, int i) {
                if (data != null) {
                    if (i == PROOF_IDENTITY) {
                        Glide.with(getContext()).load(R.raw.ic_loading).into((ImageView) proofLayout.getChildAt(0));
                        proofLayout.setVisibility(View.VISIBLE);
                        uploadDocument((ImageView) proofLayout.getChildAt(0), data, (TextView) proofLayout.getChildAt(1), PROOF_IDENTITY);

                    } else if (i == PROOF_SELFIE) {
                        Glide.with(getContext()).load(R.raw.ic_loading).into((ImageView) selfieLayout.getChildAt(0));
                        selfieLayout.setVisibility(View.VISIBLE);
                        uploadDocument((ImageView) selfieLayout.getChildAt(0), data, (TextView) selfieLayout.getChildAt(1), PROOF_SELFIE);
                    }

                }
            }
        });

        return view;
    }

    private void checkBox() {
        for (int i = 0; i < 3; i++) {
            ((CheckBox) checkLayout.getChildAt(i)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    isCheck = isChecked;
                }
            });

        }

    }

    private void uploadDocument(ImageView imageView, byte[] data, TextView textView, int i) {
        String requestId = MediaManager.get().upload(data).callback(new UploadCallback() {
            @Override
            public void onStart(String requestId) {

            }

            @Override
            public void onProgress(String requestId, long bytes, long totalBytes) {
                double progess = (bytes / totalBytes) * 100;
                textView.setText("Uploading.. " + String.format("%.2f", progess));
            }

            @Override
            public void onSuccess(String requestId, Map resultData) {
                imageView.setImageResource(R.drawable.ic_baseline_check_circle_24);
                textView.setText("Done");
                if (i == PROOF_IDENTITY) isIndentity = true;
                if (i == PROOF_SELFIE) isSelfie = true;

            }

            @Override
            public void onError(String requestId, ErrorInfo error) {
                textView.setText("Error");
            }

            @Override
            public void onReschedule(String requestId, ErrorInfo error) {

            }
        }).dispatch();
    }

}