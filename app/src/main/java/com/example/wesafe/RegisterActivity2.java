package com.example.wesafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class RegisterActivity2 extends AppCompatActivity {
    ProgressBar progressBarRegister2 ;
    Button btnVerifyOTP ;
    EditText etOTPVerfiy ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        progressBarRegister2 = findViewById(R.id.progressBarRegister2) ;
        etOTPVerfiy = findViewById(R.id.etOTPVerfiy) ;
        btnVerifyOTP = findViewById(R.id.btnVerifyOTP) ;
        progressBarRegister2.setVisibility(View.GONE);

        btnVerifyOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(verifyOTP()){
                    final Intent intent_registered = new Intent(getApplicationContext(), MainActivity.class) ;
                    Toast.makeText(getApplicationContext(),"Registeration Succesful", Toast.LENGTH_LONG).show();
                    Log.wtf("RegisterActivity","Internal Expressions are valid") ;
                    startActivity(intent_registered);
                    finish();
                }
            }
        });
    }

    private boolean verifyOTP(){
        String otp = etOTPVerfiy.getText().toString();
        progressBarRegister2.setVisibility(View.VISIBLE);
        boolean exact_result = true ;
        if(otp.length() != 4){
            etOTPVerfiy.setError("OTP must be of 4 digits");
            exact_result = false ;
        }
        progressBarRegister2.setVisibility(View.GONE);
        return exact_result ;
    }
}