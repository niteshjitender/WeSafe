package com.example.wesafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

// Starting splash screen
public class SplashScreen extends AppCompatActivity {
    private Handler mainHandler ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent_main = new Intent(getApplicationContext(), SplashScreen_main.class);
        startActivity(intent_main);
        finish();
    }
}
