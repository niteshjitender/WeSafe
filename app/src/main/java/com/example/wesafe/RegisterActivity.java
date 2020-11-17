package com.example.wesafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    Button btnRegister, btnVerify ;
    EditText etUserNameRegister,etEmailRegister,etOTP,etPasswordRegister,etPasswordRegister2,etContactRegister,etContactRegister2,etContactRegister3 ;
    ProgressBar progressBarRegister ;
//    LinearLayout linearlayout_otp ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btnRegister = findViewById(R.id.btnRegister) ;
//        btnVerify = findViewById(R.id.btnVerify) ;
        etUserNameRegister = findViewById(R.id.etUserNameRegister) ;
        etEmailRegister = findViewById(R.id.etEmailRegister) ;
//        etOTP = findViewById(R.id.etOTP) ;

        etPasswordRegister = findViewById(R.id.etPasswordRegister) ;
        etPasswordRegister2 = findViewById(R.id.etPasswordRegister2) ;

        etContactRegister = findViewById(R.id.etContactRegister) ;
        etContactRegister2 = findViewById(R.id.etContactRegister2) ;
        etContactRegister3 = findViewById(R.id.etContactRegister3) ;

//        progressBarRegister = findViewById(R.id.progressBarRegister) ;
//        progressBarRegister.setVisibility(View.GONE);

//        linearlayout_otp = findViewById(R.id.linearlayout_otp) ;
//        linearlayout_otp.setVisibility(View.GONE);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
//        btnVerify.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(),"OTP verified succesfully",Toast.LENGTH_LONG).show();
//                Log.wtf("RegisterActivity","OTP verified succesfully");
//            }
//        });
//        //For OTP
//        etEmailRegister.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean hasFocus) {
//                if (hasFocus) {
//                    Log.wtf("RegisterActivity","Focused on email address");
//                } else {
//                    Log.wtf("RegisterActivity","Lost the focus on email address");
//                    Toast.makeText(getApplicationContext(),"OTP sent",Toast.LENGTH_LONG).show();
//                    if(validateEmail())
//                        linearlayout_otp.setVisibility(View.VISIBLE);
//                }
//            }
//        });
    }

    private void registerUser(){
//        progressBarRegister.setVisibility(View.VISIBLE);
        boolean is_expressions_valid = true ;
        if(!validateUsername()){
//            progressBarRegister.setVisibility(View.GONE);
            is_expressions_valid = false;
        }
        if(!validateEmail()){
//            progressBarRegister.setVisibility(View.GONE);
            is_expressions_valid = false;
        }
        if(!validatePassword()){
//            progressBarRegister.setVisibility(View.GONE);
            is_expressions_valid = false;
        }
        if(!matchPassword()){
//            progressBarRegister.setVisibility(View.GONE);
            is_expressions_valid = false;
        }
        if(!checkContact()){
//            progressBarRegister.setVisibility(View.GONE);
            is_expressions_valid = false ;
        }


        if(is_expressions_valid) {
            final Intent intent_registered = new Intent(getApplicationContext(), RegisterActivity2.class) ;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
//                    progressBarRegister.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(),"Registering...", Toast.LENGTH_LONG).show();
                    Log.wtf("RegisterActivity","Internal Expressions are valid") ;
                    startActivity(intent_registered);
//                    finish();
                }
            }, 1500);
        }
    }

    //Expression Validation
    private Boolean validateUsername() {
        String val = etUserNameRegister.getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";

        if (val.isEmpty()) {
            etUserNameRegister.setError("Field cannot be empty");
            return false;
        } else if (val.length() >= 15) {
            etUserNameRegister.setError("Username too long");
            return false;
        } else if (!val.matches(noWhiteSpace)) {
            etUserNameRegister.setError("White Spaces are not allowed");
            return false;
        } else {
            etUserNameRegister.setError(null);
//            etEmail.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validateEmail() {
        String val = etEmailRegister.getText().toString();
        Log.wtf("RegisterActivity","email" + " " + val);
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            etEmailRegister.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(emailPattern)) {
            etEmailRegister.setError("Invalid email address");
            return false;
        } else {
            etEmailRegister.setError(null);
//            etEmail.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword() {
        String val = etPasswordRegister.getText().toString();
        if (val.isEmpty()) {
            etPasswordRegister.setError("Field cannot be empty");
            return false;
        }
        else if (val.length() < 8) {
            etPasswordRegister.setError("Minimum length of Password should be 8");
            return false;
        }
        else{
            etPasswordRegister.setError(null);
            return true;
        }
    }

    private Boolean matchPassword(){
        String val1 = etPasswordRegister.getText().toString();
        String val2 = etPasswordRegister2.getText().toString();
        Log.wtf("RegisterActivity","hello "+val1 + " " + val2);
        if(val1.equals(val2)){

            etPasswordRegister2.setError(null);
            return true;
        }
        else{
            etPasswordRegister2.setError("Password should be same");
            return false;
        }
    }

    private Boolean checkContact(){
        String contact1 = etContactRegister.getText().toString();
        String contact2 = etContactRegister2.getText().toString();
        String contact3 = etContactRegister3.getText().toString();

        boolean exact_result = true ;
        if(contact1.length() != 10){
            Log.wtf("RegisterActivity","length"+contact1.length());
            Log.wtf("RegisterActivity","hello "+contact1);
            etContactRegister.setError("Contact Number must be of 10 digits");
            exact_result = false ;
        }
        if(contact2.length() != 10){
            etContactRegister2.setError("Contact Number must be of 10 digits");
            exact_result = false ;
        }
        if(contact3.length() != 10){
            etContactRegister3.setError("Contact Number must be of 10 digits");
            exact_result = false ;
        }
        return exact_result ;
    }



}