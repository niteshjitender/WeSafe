package com.example.wesafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
    EditText etOldPasswordLogin, etNewPasswordLogin ;
    TextInputLayout tilOldPasswordLogin,tilNewPasswordLogin;
    ProgressBar progressBarLogin;
    ApiCall apiCall;
    DatabaseHelper myDb;
    SharedPreferenceClass sharedPreferenceClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        btnLogin = findViewById(R.id.btnLogin);
        etOldPasswordLogin = findViewById(R.id.etOldPasswordLogin) ;
        etNewPasswordLogin = findViewById(R.id.etNewPasswordLogin) ;
        tilOldPasswordLogin = findViewById(R.id.tilOldPasswordLogin);
        tilNewPasswordLogin=findViewById(R.id.tilNewPasswordLogin);
        progressBarLogin = findViewById(R.id.progressBarLogin);
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
        if (!validateOldPassword()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    tilOldPasswordLogin.setPasswordVisibilityToggleEnabled(true);
                }
            }, 3000);
            progressBarLogin.setVisibility(View.GONE);
            is_expressions_valid = false;
        }
        if (!validateNewPassword()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    tilNewPasswordLogin.setPasswordVisibilityToggleEnabled(true);
                }
            }, 3000);
            progressBarLogin.setVisibility(View.GONE);
            is_expressions_valid = false;
        }
        if(is_expressions_valid)
        {
            progressBarLogin.setVisibility(View.VISIBLE);
            String oldPassword=etOldPasswordLogin.getText().toString();
            String newPassword=etNewPasswordLogin.getText().toString();
            String phone_number=sharedPreferenceClass.getValue_string("phone_number");
            HashMap<String,String> params=new HashMap<>();
            params.put("oldPassword",oldPassword);
            params.put("newPassword",newPassword);
            params.put("phonenumber",phone_number);
            String Localhost_Endpoint="http://10.0.2.2:8080/users/resetPassword";
            String Cloud_EndPoint="https://wesafe-app.herokuapp.com/users/resetPassword";
            apiCall.CallWithoutToken(params, Cloud_EndPoint, new GetResult() {
                @Override
                public void onSuccess(JSONObject data) throws JSONException {
                    startActivity(new Intent(ChangePassword.this,MainActivity.class));
                }

                @Override
                public void onFailure(String err) {
                }
            });
        }
    }

    private Boolean validateOldPassword() {
        String val = etOldPasswordLogin.getText().toString();
        if (val.isEmpty()) {
            tilOldPasswordLogin.setPasswordVisibilityToggleEnabled(false);
            etOldPasswordLogin.setError("Field cannot be empty");
            //Delay for three seconds
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    tilOldPasswordLogin.setPasswordVisibilityToggleEnabled(true);
                }
            }, 3000);
            return false;
        }
        else if (val.length() < 8) {
            //Hiding overlapping of password toggle and error icon
            tilOldPasswordLogin.setPasswordVisibilityToggleEnabled(false);
            etOldPasswordLogin.setError("Minimum length of Password should be 8");
            //Delay for three seconds
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    tilOldPasswordLogin.setPasswordVisibilityToggleEnabled(true);
                }
            }, 3000);

            return false;
        }
        else{
            etOldPasswordLogin.setError(null);
            return true;
        }
    }
    private Boolean validateNewPassword() {
        String val = etNewPasswordLogin.getText().toString();
        if (val.isEmpty()) {
            tilNewPasswordLogin.setPasswordVisibilityToggleEnabled(false);
            etNewPasswordLogin.setError("Field cannot be empty");
            //Delay for three seconds
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    tilNewPasswordLogin.setPasswordVisibilityToggleEnabled(true);
                }
            }, 3000);
            return false;
        }
        else if (val.length() < 8) {
            //Hiding overlapping of password toggle and error icon
            tilNewPasswordLogin.setPasswordVisibilityToggleEnabled(false);
            etNewPasswordLogin.setError("Minimum length of Password should be 8");
            //Delay for three seconds
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    tilNewPasswordLogin.setPasswordVisibilityToggleEnabled(true);
                }
            }, 3000);

            return false;
        }
        else{
            etNewPasswordLogin.setError(null);
            return true;
        }
    }
}