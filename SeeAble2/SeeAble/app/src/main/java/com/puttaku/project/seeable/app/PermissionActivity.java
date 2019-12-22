package com.puttaku.project.seeable.app;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class PermissionActivity extends AppCompatActivity {
    int REQUEST_CODE_ASK_PERMISSIONS = 123;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.permissionlayout);
        int CallPermission = checkSelfPermission(Manifest.permission.CALL_PHONE);
        int CameraPermission = checkSelfPermission(Manifest.permission.CAMERA);
        int AudioPermission = checkSelfPermission(Manifest.permission.MODIFY_AUDIO_SETTINGS);
        if(CallPermission != PackageManager.PERMISSION_GRANTED || CameraPermission != PackageManager.PERMISSION_GRANTED  || AudioPermission != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[] {Manifest.permission.CALL_PHONE,Manifest.permission.CAMERA,Manifest.permission.MODIFY_AUDIO_SETTINGS},
                    REQUEST_CODE_ASK_PERMISSIONS);
            return;
        }
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
