package com.puttaku.project.seeable.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ContactRegisActivity extends AppCompatActivity {
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regis_people_contact);
        Button back_button = findViewById(R.id.back_button);
        Button finish_button = findViewById(R.id.finish_button);
        final EditText contact_person_name_box = findViewById(R.id.contact_person_name_box);
        final EditText contact_tel_box = findViewById(R.id.contact_tel_box);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        finish_button.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v) {
                    if(TextUtils.isEmpty(contact_person_name_box.getText()) || TextUtils.isEmpty(contact_tel_box.getText())){
                        if(TextUtils.isEmpty(contact_person_name_box.getText())){
                            contact_person_name_box.setError("Please insert user's contact name.");
                        }
                        if(TextUtils.isEmpty(contact_tel_box.getText())){
                            contact_tel_box.setError("Please insert user's contact number.");
                        }
                    }
                    else {
                        String userData = "userData";
                        SharedPreferences pref = getSharedPreferences(userData, 0);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("Contactname", contact_person_name_box.getText().toString());
                        editor.putString("ContactTel", contact_tel_box.getText().toString());
                        editor.putBoolean("FinishedInformation", true);
                        editor.apply();
                        onFinish();
                    }
                }
            });
        }

    private void onFinish() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}