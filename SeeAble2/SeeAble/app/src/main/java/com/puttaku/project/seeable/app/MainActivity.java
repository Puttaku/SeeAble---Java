package com.puttaku.project.seeable.app;
import android.Manifest;
import android.content.Intent;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.speech.tts.TextToSpeech;
import android.net.Uri;

import com.wonderkiln.camerakit.CameraKit;
import com.wonderkiln.camerakit.CameraKitError;
import com.wonderkiln.camerakit.CameraKitEvent;
import com.wonderkiln.camerakit.CameraKitEventListener;
import com.wonderkiln.camerakit.CameraKitImage;
import com.wonderkiln.camerakit.CameraKitVideo;
import com.wonderkiln.camerakit.CameraView;
import android.content.pm.PackageManager;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener{
    private TextToSpeech tts;
    private static final String MODEL_PATH = "optimized_graph.lite";
    private static final String LABEL_PATH = "retrained_labels.txt";
    private static final int INPUT_SIZE = 224;
    private Dialog dialog;
    private Classifier classifier;
    String name,tel,contactName,contactTel;
    private Executor executor = Executors.newSingleThreadExecutor();
    private TextView textViewResult,nameText,telText,conName,conTel;
    private Button btnDetectObject, btnToggleCamera,plus_icon;
    private ImageView imageViewResult;
    private CameraView cameraView;
    private Handler handler = new Handler();
    Runnable runnable;
    String userData = "userData";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AudioManager audioManager = (AudioManager)getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        audioManager.setMode(AudioManager.MODE_IN_CALL);
        audioManager.setSpeakerphoneOn(true);
        dialog = new Dialog(this);
        plus_icon = findViewById(R.id.plus_button);
        cameraView = findViewById(R.id.cameraView);
        cameraView.setMethod(CameraKit.Constants.METHOD_STILL);
        tts = new TextToSpeech(this, this,"com.google.android.tts");
//        imageViewResult = findViewById(R.id.imageViewResult);
        textViewResult = findViewById(R.id.textViewResult);
        textViewResult.setMovementMethod(new ScrollingMovementMethod());
//        btnToggleCamera = findViewById(R.id.btnToggleCamera);
        btnDetectObject = findViewById(R.id.btnDetectObject);
//        nameText = findViewById(R.id.name_main);
//        telText = findViewById(R.id.name_main2);
        SharedPreferences pref = getSharedPreferences(userData, 0);
        name = pref.getString("Username","-");
        tel = pref.getString("Tel","-");
        contactName = pref.getString("Contactname","-");
        contactTel = pref.getString("ContactTel","-");
//        nameText.setText("Name : " + name);
//        telText.setText("Tel : " + tel);
        textViewResult.setText("Waiting for new Event...");
        cameraView.addCameraKitListener(new CameraKitEventListener() {
            @Override
            public void onEvent(CameraKitEvent cameraKitEvent) {

            }

            @Override
            public void onError(CameraKitError cameraKitError) {

            }

            @Override
            public void onImage(CameraKitImage cameraKitImage) {
                Bitmap bitmap = cameraKitImage.getBitmap();

                bitmap = Bitmap.createScaledBitmap(bitmap, INPUT_SIZE, INPUT_SIZE, false);

//                imageViewResult.setImageBitmap(bitmap);

                final List<Classifier.Recognition> results = classifier.recognizeImage(bitmap);
                if(!tts.isSpeaking()){
                    if(results.get(0).toString().equals("crosswalk")){
                        tts.speak("There is a "+results.get(0).toString()+" ahead. You can call someone to help", TextToSpeech.QUEUE_FLUSH, null);
                    }
                    else if(results.get(0).toString().equals("bussign")){
                        tts.speak("There is a "+results.get(0).toString()+" ahead.", TextToSpeech.QUEUE_FLUSH, null);
                    }
                }
                textViewResult.setText("There is a "+results.get(0).toString()+" ahead.");

            }

            @Override
            public void onVideo(CameraKitVideo cameraKitVideo) {

            }
        });

//        btnToggleCamera.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                cameraView.toggleFacing();
//            }
//        });

        btnDetectObject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraView.captureImage();
            }
        });
        plus_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopUp(view);
            }
        });

        initTensorFlowAndLoadModel();
        runnable = new Runnable() {
            @Override
            public void run() {
                if(checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                    cameraView.captureImage();
                    handler.postDelayed(this, 1000);
                }
            }
        };
        handler.postDelayed(runnable, 1000);
    }
    @Override
    public void onInit(int status) {
        if(status == TextToSpeech.SUCCESS) {
            tts.setLanguage(Locale.US);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(runnable, 1000);
        cameraView.start();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onPause() {
        cameraView.stop();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tts.shutdown();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                classifier.close();
            }
        });
    }

    private void initTensorFlowAndLoadModel() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    classifier = TensorFlowImageClassifier.create(
                            getAssets(),
                            MODEL_PATH,
                            LABEL_PATH,
                            INPUT_SIZE);
                    makeButtonVisible();
                } catch (final Exception e) {
                    throw new RuntimeException("Error initializing TensorFlow!", e);
                }
            }
        });
    }

    private void makeButtonVisible() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                btnDetectObject.setVisibility(View.VISIBLE);
            }
        });
    }
    public void showPopUp(View v){
        Button btnClose,btnCall,btnCall2;
        dialog.setContentView(R.layout.popup_information);
        btnClose = dialog.findViewById(R.id.close_popup);
        btnCall = dialog.findViewById(R.id.emergency);
        btnCall2 = dialog.findViewById(R.id.button4);
        nameText = dialog.findViewById(R.id.name_tag_inf);
        telText = dialog.findViewById(R.id.name_tag_inf2);
        conName = dialog.findViewById(R.id.contact_name);
        conTel = dialog.findViewById(R.id.contact_tel);
        btnClose.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnCall.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                handler.removeCallbacks(runnable);
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:0957322923"));
                startActivity(callIntent);
            }
        });
        btnCall2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                handler.removeCallbacks(runnable);
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + contactTel));
                startActivity(callIntent);
            }
        });
        nameText.setText("Name : " + name);
        telText.setText("Tel : " + tel);
        conName.setText("Notified Person : " + contactName);
        conTel.setText("Notified Tel : " + contactTel);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));
        dialog.show();
    }
}
