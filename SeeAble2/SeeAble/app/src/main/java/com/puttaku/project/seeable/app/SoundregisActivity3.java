package com.puttaku.project.seeable.app;
import android.os.Handler;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.speech.tts.TextToSpeech;

import java.util.ArrayList;
import java.util.Locale;

public class SoundregisActivity3 extends AppCompatActivity implements TextToSpeech.OnInitListener {
    Button type_regis,try_sound;
    ArrayList<String> result;
    private TextToSpeech tts;
    int REQUEST_CODE_VOICE_RECOGNITION = 1001;
    private String TAG = "SoundregisActivity2";
    String userData = "userData";
    private Handler handler = new Handler();
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Runnable r1;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regis_sound_rec3);
        tts = new TextToSpeech(this, this,"com.google.android.tts");
        pref = getSharedPreferences(userData,0);
        editor = pref.edit();
        type_regis = findViewById(R.id.regis_type_button3);
        try_sound = findViewById(R.id.regis_try_button3);
        AudioManager audioManager = (AudioManager)getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        audioManager.setMode(AudioManager.MODE_IN_CALL);
        audioManager.setSpeakerphoneOn(true);
        type_regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToRegis();
            }
        });
        try_sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                trySound();
            }
        });
    }
    @Override
    public void onInit(int status) {
        Log.d(TAG, "onInit: Started");
        if(status == TextToSpeech.SUCCESS) {
            tts.setLanguage(Locale.US);
            startVoiceRecognize();
        }
    }
    private void startVoiceRecognize(){
        int timer;
        if(!pref.contains("regisTrigger")){
            timer = 12000;
            tts.speak(getString(R.string.regis_contact_heading), TextToSpeech.QUEUE_ADD,null, null);
            tts.speak(getString(R.string.regis_contact_heading_2), TextToSpeech.QUEUE_ADD,null, null);
            tts.speak(getString(R.string.regis_contact_subheading), TextToSpeech.QUEUE_ADD,null, null);
            editor.putBoolean("regisTrigger",true);
            editor.apply();
        }
        else{
            timer = 1000;
        }
        final Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
//        startActivityForResult(intent,REQUEST_CODE_VOICE_RECOGNITION);
        Log.d(TAG, "onCreate: Access");
        final Runnable nameRunnable = new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "onCreate: speaking");
                tts.speak(getString(R.string.speech_notify_name), TextToSpeech.QUEUE_FLUSH,null, null);
                final Runnable r1 = new Runnable() {
                    @Override
                    public void run() {
                        startActivityForResult(intent,REQUEST_CODE_VOICE_RECOGNITION);
                    }
                };
                handler.postDelayed(r1,1000);
            }
        };
        handler.postDelayed(nameRunnable,timer);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_VOICE_RECOGNITION && data != null){
            result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if(!result.get(0).isEmpty()){
                editor.putString("Contactname",result.get(0));
                editor.apply();
            }
            Log.d(TAG, "onActivityResult: " + result);
            r1 = new Runnable() {
                @Override
                public void run() {
                    Runnable r11 = new Runnable() {
                        @Override
                        public void run() {
                            if(!pref.contains("Contactname")){
                                tts.speak("We can't receive anything, please try again.", TextToSpeech.QUEUE_FLUSH,null, null);
                            }
                            else{
                                goToNextPage();
                            }
                        }
                    };
                    handler.postDelayed(r11,5000);
                }
            };
            handler.postDelayed(r1,500);
        }
    }
    void goToRegis() {
        Intent intent = new Intent(this,RegisterActivity.class);
        tts.shutdown();
        handler.removeCallbacksAndMessages(null);
        startActivity(intent);
        finish();
    }
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
        tts.shutdown();
        finish();
    }
    public void onBackPressed() {
        super.onBackPressed();
        editor.clear();
        editor.apply();
    }
    void trySound(){
        startVoiceRecognize();
    }
    void goToNextPage(){
        Intent next = new Intent(this,SoundregisActivity4.class);
        handler.removeCallbacksAndMessages(null);
        startActivity(next);
        finish();
    }
}
