package com.example.wesafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.wesafe.UtilService.ApiCall;
import com.example.wesafe.UtilService.GetResult;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity{
    Button btnRegister, btnVerify ;
    EditText etUserNameRegister,etPhoneNumberRegister,etOTP,etPasswordRegister,etPasswordRegister2,etContactRegister,etContactRegister2,etContactRegister3 ;
    ProgressBar progressBarRegister ;
    ApiCall apiCall;
//    LinearLayout linearlayout_otp ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btnRegister = findViewById(R.id.btnRegister) ;
//        btnVerify = findViewById(R.id.btnVerify) ;
        etUserNameRegister = findViewById(R.id.etUserNameRegister) ;
        etPhoneNumberRegister = findViewById(R.id.etPhoneNumber) ;
//        etOTP = findViewById(R.id.etOTP) ;

        etPasswordRegister = findViewById(R.id.etPasswordRegister) ;
        etPasswordRegister2 = findViewById(R.id.etPasswordRegister2) ;

        etContactRegister = findViewById(R.id.etContactRegister) ;
        etContactRegister2 = findViewById(R.id.etContactRegister2) ;
        etContactRegister3 = findViewById(R.id.etContactRegister3) ;
        apiCall= new ApiCall(this);

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
        if(false){
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
            String username = etUserNameRegister.getText().toString();
            String phonenumber= etPhoneNumberRegister.getText().toString();
            String password = etPasswordRegister.getText().toString();
            String emergency_contact1 = etContactRegister.getText().toString();
            String emergency_contact2 = etContactRegister2.getText().toString();
            String emergency_contact3 = etContactRegister3.getText().toString();
            HashMap<String,String> params=new HashMap<>();
            params.put("username",username);
            params.put("phonenumber",phonenumber);
            params.put("password",password);
            params.put("emergencyContact1",emergency_contact1);
            params.put("emergencyContact2",emergency_contact2);
            params.put("emergencyContact3",emergency_contact3);
            params.put("channel","sms");
            String Endpoint="http://10.0.2.2:8080/users/signup";
            //String Endpoint="http://192.168.43.80:8080/users/signup";
            apiCall.PostCall(params, Endpoint, new GetResult() {
                @Override
                public void onSuccess(boolean check) {
                    if(check)
                    {
                        startActivity(new Intent(RegisterActivity.this,RegisterActivity2.class));
                    }
                }
            });
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
//    private Boolean validateEmail() {
//        String val = etEmailRegister.getText().toString();
//        Log.wtf("RegisterActivity","email" + " " + val);
//        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
//
//        if (val.isEmpty()) {
//            etEmailRegister.setError("Field cannot be empty");
//            return false;
//        } else if (!val.matches(emailPattern)) {
//            etEmailRegister.setError("Invalid email address");
//            return false;
//        } else {
//            etEmailRegister.setError(null);
////            etEmail.setErrorEnabled(false);
//            return true;
//        }
//    }

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
        String phonenumber = etPhoneNumberRegister.getText().toString();
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
        if(phonenumber.length()!=10)
        {
            etPhoneNumberRegister.setError("Contact Number must be of 10 digits");
            exact_result=false;
        }
        return exact_result ;
    }
    protected void onStart()
    {
        super.onStart();
        SharedPreferences user_pref=getSharedPreferences("userPref",MODE_PRIVATE);
        if(user_pref.contains("token"))
        {
            startActivity(new Intent(this,HomeActivity.class));
            finish();
        }
    }
}