package com.puttaku.project.seeable.app;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity{
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String userData = "userData";
        SharedPreferences pref = getSharedPreferences(userData, 0);
        if(pref.getBoolean("FinishedInformation",false) == true){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    goToMain();
                }
            }, 2000);
        }
        else{
            pref.edit().clear();
            pref.edit().apply();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    goToSlide();
                }
            }, 2000);
        }
    }
    void goToMain(){
        Intent i=new Intent(this,MainActivity.class);
        startActivity(i);
        finish();
    }
    void goToSlide(){
        Intent i=new Intent(this,SlideActivity.class);
        startActivity(i);
        finish();
    }
}