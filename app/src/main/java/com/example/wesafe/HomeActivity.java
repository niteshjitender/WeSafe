package com.example.wesafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.wesafe.UtilService.SharedPreferenceClass;

public class HomeActivity extends AppCompatActivity {
    private Button logout;
    SharedPreferenceClass sharedPreferencesClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        logout=(Button)findViewById(R.id.logout);
        sharedPreferencesClass=new SharedPreferenceClass(this);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferencesClass.clear();
                startActivity(new Intent(HomeActivity.this,MainActivity.class));
                finish();
            }
        });
    }
}