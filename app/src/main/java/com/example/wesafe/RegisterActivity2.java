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

import com.example.wesafe.UtilService.ApiCall;
import com.example.wesafe.UtilService.GetResult;

import java.util.HashMap;

public class RegisterActivity2 extends AppCompatActivity{
    ProgressBar progressBarRegister2 ;
    Button btnVerifyOTP ;
    EditText etOTPVerify ;
    ApiCall apiCall;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        progressBarRegister2 = findViewById(R.id.progressBarRegister2) ;
        etOTPVerify = findViewById(R.id.etOTPVerfiy) ;
        btnVerifyOTP = findViewById(R.id.btnVerifyOTP) ;
        progressBarRegister2.setVisibility(View.GONE);
        apiCall=new ApiCall(this);
        btnVerifyOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(verifyOTP()){
                    sendOTP(v);
                }
            }
        });
    }

    private boolean verifyOTP(){
        String otp = etOTPVerify.getText().toString();
        progressBarRegister2.setVisibility(View.VISIBLE);
        boolean exact_result = true ;
        if(otp.length() != 6){
            etOTPVerify.setError("OTP must be of 6 digits");
            exact_result = false ;
        }
        progressBarRegister2.setVisibility(View.GONE);
        return exact_result ;
    }
    private void sendOTP(View v)
    {
        String otp = etOTPVerify.getText().toString();
        progressBarRegister2.setVisibility(View.VISIBLE);
        HashMap<String,String> params=new HashMap<>();
        params.put("code",otp);
        String EndPoint="http://10.0.2.2:8080/users/optVerify";
        //String EndPoint="http://192.168.43.80:8080/users/optVerify";
        apiCall.VerifiedPostCall(params, EndPoint, new GetResult() {
            @Override
            public void onSuccess(boolean check) {
                if(check)
                {
                    startActivity(new Intent(RegisterActivity2.this,HomeActivity.class));
                    finish();
                }
            }
        });
        progressBarRegister2.setVisibility(View.GONE);
    }
}