package com.example.wesafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wesafe.UtilService.ApiCall;
import com.example.wesafe.UtilService.DatabaseHelper;
import com.example.wesafe.UtilService.GetResult;
import com.example.wesafe.UtilService.SharedPreferenceClass;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ChangePassword extends AppCompatActivity {

    Button btnLogin;
    EditText etPasswordLogin, etConfirmPasswordLogin ;
    TextInputLayout tilPasswordLogin,tilConfirmPasswordLogin;
    ProgressBar progressBarLogin;
    ApiCall apiCall;
    DatabaseHelper myDb;
    SharedPreferenceClass sharedPreferenceClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        btnLogin = findViewById(R.id.btnLogin);
        etPasswordLogin = findViewById(R.id.etPasswordLogin) ;
        etConfirmPasswordLogin = findViewById(R.id.etConfirmPasswordLogin) ;
        tilPasswordLogin = findViewById(R.id.tilPasswordLogin);
        tilConfirmPasswordLogin=findViewById(R.id.tilConfirmPasswordLogin);
        progressBarLogin = findViewById(R.id.progressBarChangePassword);
        apiCall= new ApiCall(this);
        sharedPreferenceClass=new SharedPreferenceClass(this);
        myDb=new DatabaseHelper(this);
        progressBarLogin.setVisibility(View.GONE);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword(v);
            }
        });
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    protected void changePassword(View v)
    {
        progressBarLogin.setVisibility(View.VISIBLE);
        boolean is_expressions_valid = true;
        if (!validatePassword()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    tilPasswordLogin.setPasswordVisibilityToggleEnabled(true);
                }
            }, 3000);
            progressBarLogin.setVisibility(View.GONE);
            is_expressions_valid = false;
        }
        if (!validateConfirmPassword()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    tilConfirmPasswordLogin.setPasswordVisibilityToggleEnabled(true);
                }
            }, 3000);
            progressBarLogin.setVisibility(View.GONE);
            is_expressions_valid = false;
        }
        if(!matchPassword())
        {
            progressBarLogin.setVisibility(View.GONE);
            is_expressions_valid = false;
        }
        if(is_expressions_valid)
        {
            progressBarLogin.setVisibility(View.VISIBLE);
            String Password=etPasswordLogin.getText().toString();
            String phone_number=sharedPreferenceClass.getValue_string("phone_number");
            HashMap<String,String> params=new HashMap<>();
            params.put("Password",Password);
            params.put("phonenumber",phone_number);
            String Localhost_Endpoint="http://10.0.2.2:8080/users/resetPassword";
            String Cloud_EndPoint="https://wesafe-app.herokuapp.com/users/resetPassword";
            apiCall.CallWithoutToken(params, Cloud_EndPoint, new GetResult() {
                @Override
                public void onSuccess(JSONObject data) throws JSONException {
                    SharedPreferences user_pref=getSharedPreferences("userPref",MODE_PRIVATE);
                    user_pref.edit().remove("resetPassword").commit();
                    startActivity(new Intent(ChangePassword.this,MainActivity.class));
                }
                @Override
                public void onFailure(String err) {
                }
            });
        }
    }
    private Boolean matchPassword()
    {
        String val1 = etPasswordLogin.getText().toString();
        String val2 = etConfirmPasswordLogin.getText().toString();
        if (val1.equals(val2)) {

            etConfirmPasswordLogin.setError(null);
            return true;
        } else {
            etConfirmPasswordLogin.setError("Password should be same");
            return false;
        }
    }
    private Boolean validatePassword() {
        String val = etPasswordLogin.getText().toString();
        if (val.isEmpty()) {
            tilPasswordLogin.setPasswordVisibilityToggleEnabled(false);
            etPasswordLogin.setError("Field cannot be empty");
            //Delay for three seconds
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    tilPasswordLogin.setPasswordVisibilityToggleEnabled(true);
                }
            }, 3000);
            return false;
        }
        else if (val.length() < 8) {
            //Hiding overlapping of password toggle and error icon
            tilPasswordLogin.setPasswordVisibilityToggleEnabled(false);
            etPasswordLogin.setError("Minimum length of Password should be 8");
            //Delay for three seconds
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    tilPasswordLogin.setPasswordVisibilityToggleEnabled(true);
                }
            }, 3000);

            return false;
        }
        else{
            etPasswordLogin.setError(null);
            return true;
        }
    }
    private Boolean validateConfirmPassword() {
        String val = etConfirmPasswordLogin.getText().toString();
        if (val.isEmpty()) {
            tilConfirmPasswordLogin.setPasswordVisibilityToggleEnabled(false);
            etConfirmPasswordLogin.setError("Field cannot be empty");
            //Delay for three seconds
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    tilConfirmPasswordLogin.setPasswordVisibilityToggleEnabled(true);
                }
            }, 3000);
            return false;
        }
        else if (val.length() < 8) {
            //Hiding overlapping of password toggle and error icon
            tilConfirmPasswordLogin.setPasswordVisibilityToggleEnabled(false);
            etConfirmPasswordLogin.setError("Minimum length of Password should be 8");
            //Delay for three seconds
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    tilConfirmPasswordLogin.setPasswordVisibilityToggleEnabled(true);
                }
            }, 3000);

            return false;
        }
        else{
            etConfirmPasswordLogin.setError(null);
            return true;
        }
    }
}