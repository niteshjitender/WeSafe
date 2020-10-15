package com.example.wesafe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity {
    Button btnRegister ;
    EditText etUserNameRegister,etEmailRegister,etOTP,etPasswordRegister1,etPasswordRegister2,etContactRegister,etContactRegister2,etContactRegister3 ;
    ProgressBar progressBarRegister ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    private void RegisterUser(){
        boolean is_expressions_valid = true ;
        if(!validateUsername()) is_expressions_valid = false;
        if(!validateEmail()) is_expressions_valid = false;
        if(!validatePassword()) is_expressions_valid = false;
        if(!matchPassword()) is_expressions_valid = false;

        if(is_expressions_valid) {
            Toast.makeText(this,"All expression are valid", Toast.LENGTH_LONG).show();
            Log.wtf("RegisterActivity","Internal Expressions are valid") ;
        }
    }

    //Expression Validation
    private Boolean validateUsername() {
        String val = etUserName.getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";

        if (val.isEmpty()) {
            etUserName.setError("Field cannot be empty");
            return false;
        } else if (val.length() >= 15) {
            etUserName.setError("Username too long");
            return false;
        } else if (!val.matches(noWhiteSpace)) {
            etUserName.setError("White Spaces are not allowed");
            return false;
        } else {
            etUserName.setError(null);
//            etEmail.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validateEmail() {
        String val = etEmail.getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            etEmail.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(emailPattern)) {
            etEmail.setError("Invalid email address");
            return false;
        } else {
            etEmail.setError(null);
//            etEmail.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword() {
        String val = etPassword.getText().toString();
        if (val.isEmpty()) {
            etPassword.setError("Field cannot be empty");
            return false;
        }
        else if (val.length() < 8) {
            etPassword.setError("Minimum length of Password should be 8");
            return false;
        }
        else{
            etPassword.setError(null);
            return true;
        }
    }

    private Boolean matchPassword(){
        String val1 = etPassword.getText().toString();
        String val2 = etRetypePassword.getText().toString();
        if(val1.equals(val2)){

            etRetypePassword.setError(null);
            return true;
        }
        else{
            etRetypePassword.setError("Password should be same");
            return false;
        }
    }

}