package com.seventech.tesourodosaber;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import com.google.android.material.snackbar.Snackbar;
import com.seventech.tesourodosaber.navigation.BackNavigable;
import com.seventech.tesourodosaber.utils.view.PointsOverlayView;

public class QRCodeDecoderFragment extends Fragment implements BackNavigable, ActivityCompat.OnRequestPermissionsResultCallback, QRCodeReaderView.OnQRCodeReadListener {

    private static final int MY_PERMISSION_REQUEST_CAMERA = 0;
    public static final String EXTRA_QRCODE_DECODED_RESULT = "com.seventech.tesourodosaber.qrCodeDecodedResult";

    private ViewGroup mainLayout;

    private String result;
    private TextView resultTextView;
    private QRCodeReaderView qrCodeReaderView;
    private CheckBox flashlightCheckBox;
    private CheckBox enableDecodingCheckBox;
    private PointsOverlayView pointsOverlayView;

    private QRCodeDecoderActivity mQRCodeDecoderActivity;




    public static QRCodeDecoderFragment newInstance() {
        return new QRCodeDecoderFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

//        super.onCreateView(inflater, container, savedInstanceState);

//        View view = inflater.inflate(R.layout.fragment_qrcode_decoder, container, false);

        mainLayout = container;

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            initQRCodeReaderView();
        } else {
            requestCameraPermission();
        }

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void initQRCodeReaderView() {
        View content = getLayoutInflater().inflate(R.layout.fragment_qrcode_decoder, mainLayout, true);

        qrCodeReaderView = (QRCodeReaderView) content.findViewById(R.id.qrdecoderview);
        resultTextView = (TextView) content.findViewById(R.id.result_text_view);
        flashlightCheckBox = (CheckBox) content.findViewById(R.id.flashlight_checkbox);
        enableDecodingCheckBox = (CheckBox) content.findViewById(R.id.enable_decoding_checkbox);
        pointsOverlayView = (PointsOverlayView) content.findViewById(R.id.points_overlay_view);

        qrCodeReaderView.setAutofocusInterval(2000L);
        qrCodeReaderView.setOnQRCodeReadListener(this);
        qrCodeReaderView.setBackCamera();
        flashlightCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                qrCodeReaderView.setTorchEnabled(isChecked);
            }
        });
        enableDecodingCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                qrCodeReaderView.setQRDecodingEnabled(isChecked);
            }
        });
        qrCodeReaderView.startCamera();
    }

    @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                                     @NonNull int[] grantResults) {
        if (requestCode != MY_PERMISSION_REQUEST_CAMERA) {
            return;
        }

        if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Snackbar.make(mainLayout, "Camera permission was granted.", Snackbar.LENGTH_SHORT).show();
            initQRCodeReaderView();
            //gambiarra pra corrigir bug
            qrCodeReaderView.setVisibility(View.GONE);
            qrCodeReaderView.setVisibility(View.VISIBLE);
        } else {
            Snackbar.make(mainLayout, "Camera permission request was denied.", Snackbar.LENGTH_SHORT)
                    .show();
        }
    }

    private void requestCameraPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(mQRCodeDecoderActivity, Manifest.permission.CAMERA)) {
            Snackbar.make(mainLayout, "Camera access is required to display the camera preview.",
                    Snackbar.LENGTH_INDEFINITE).setAction("OK", new View.OnClickListener() {
                @Override public void onClick(View view) {
                    ActivityCompat.requestPermissions(mQRCodeDecoderActivity, new String[] {
                            Manifest.permission.CAMERA
                    }, MY_PERMISSION_REQUEST_CAMERA);
                }
            }).show();
        } else {
            Snackbar.make(mainLayout, "Permission is not available. Requesting camera permission.",
                    Snackbar.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(mQRCodeDecoderActivity, new String[] {
                    Manifest.permission.CAMERA
            }, MY_PERMISSION_REQUEST_CAMERA);
        }
    }

//    /** Check if this device has a camera */
//    private boolean checkCameraHardware(Context context) {
//        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
//            // this device has a camera
//            return true;
//        } else {
//            // no camera on this device
//            return false;
//        }
//    }


    @Override
    public void onResume() {
        super.onResume();

        if (qrCodeReaderView != null) {
            qrCodeReaderView.startCamera();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
//        if (checkCameraHardware(getContext()))
//        else
//            Messages.with(getContext()).showInfoMessage("Erro","Não foi possível detectar uma câmera neste dispositivo.");

        if (qrCodeReaderView != null) {
            qrCodeReaderView.stopCamera();
        }
    }

    private void sendResultAndFinish() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(EXTRA_QRCODE_DECODED_RESULT, result);
        mQRCodeDecoderActivity.setResult(Activity.RESULT_OK, returnIntent);
        mQRCodeDecoderActivity.finish();
    }


    // Called when a QR is decoded
    // "text" : the text encoded in QR
    // "points" : points where QR control points are placed
    @Override public void onQRCodeRead(String text, PointF[] points) {
        pointsOverlayView.setPoints(points);
        result = text;
        sendResultAndFinish();
//        resultTextView.setText(result);
    }

    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        mQRCodeDecoderActivity.setResult(Activity.RESULT_CANCELED, returnIntent);
        mQRCodeDecoderActivity.finish();
    }

    // gambiarra p/ contornar bug do android
    @Override
    public void onAttach(Activity activity) {
        mQRCodeDecoderActivity = (QRCodeDecoderActivity) activity;
        super.onAttach(activity);
    }

}
