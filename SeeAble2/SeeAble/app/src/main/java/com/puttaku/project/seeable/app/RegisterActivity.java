package com.puttaku.project.seeable.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regis_slide_content);
        Button regis_next_button = findViewById(R.id.regis_next_button);
        final EditText name_box = findViewById(R.id.name_box);
        final EditText tel_box = findViewById(R.id.tel_box);
        final EditText addr_box = findViewById(R.id.addr_box);
        regis_next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(name_box.getText()) || TextUtils.isEmpty(tel_box.getText()) || TextUtils.isEmpty(addr_box.getText())){
                    if(TextUtils.isEmpty(name_box.getText())){
                        name_box.setError("Please insert user name.");

                    }
                    if(TextUtils.isEmpty(tel_box.getText())){
                        tel_box.setError("Please insert user number.");
                    }
                    if(TextUtils.isEmpty(addr_box.getText())){
                        addr_box.setError("Please insert user Address.");
                    }
                }
                else{
                    String userData = "userData";
                    SharedPreferences pref = getSharedPreferences(userData,0);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("Username",name_box.getText().toString());
                    editor.putString("Tel",tel_box.getText().toString());
                    editor.putString("Address",addr_box.getText().toString());
                    editor.apply();
                    gotoNext();
                }
            }
        });
    }
    void gotoNext() {
        Intent intent = new Intent(this,ContactRegisActivity.class);
        startActivity(intent);
        finish();
    }
}