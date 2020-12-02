package com.example.wesafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.wesafe.UtilService.ApiCall;
import com.example.wesafe.UtilService.GetResult;
import com.example.wesafe.UtilService.SharedPreferenceClass;

import org.json.JSONObject;

import java.util.HashMap;

public class RegisterActivity2 extends AppCompatActivity{
    ProgressBar progressBarRegister2 ;
    Button btnVerifyOTP;
    EditText etOTPVerify;
    ApiCall apiCall;
    SharedPreferenceClass sharedPreferenceClass;
    private SharedPreferences preferences ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        progressBarRegister2 = findViewById(R.id.progressBarRegister2) ;
        etOTPVerify = findViewById(R.id.etOTPVerfiy) ;
        btnVerifyOTP = findViewById(R.id.btnVerifyOTP) ;
        progressBarRegister2.setVisibility(View.GONE);
        apiCall=new ApiCall(this);
        sharedPreferenceClass= new SharedPreferenceClass(this);
        btnVerifyOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(verifyOTP()){
                    sendOTP(v);
                }
            }
        });
        preferences = getSharedPreferences("PREFERENCE",MODE_PRIVATE);
    }
    @Override
    public void onBackPressed() {

        SharedPreferences user_pref=getSharedPreferences("userPref",MODE_PRIVATE);
        if(user_pref.contains("resetPassword"))
        {
            user_pref.edit().remove("resetPassword").commit();
        }
//        moveTaskToBack(true);
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
        params.put("phone_number",sharedPreferenceClass.getValue_string("phone_number"));
        params.put("code",otp);
        String Localhost_Endpoint="http://10.0.2.2:8080/users/optVerify";
        String Cloud_EndPoint="https://wesafe-app.herokuapp.com/users/optVerify";
        apiCall.CallWithoutToken(params,Cloud_EndPoint, new GetResult() {
            @Override
            public void onSuccess(JSONObject data)
            {
                SharedPreferences user_pref=getSharedPreferences("userPref",MODE_PRIVATE);
                if(user_pref.contains("resetPassword"))
                {
                    startActivity(new Intent(RegisterActivity2.this,ChangePassword.class));
                }
                else if(user_pref.contains("token"))
                {
                    startActivity(new Intent(RegisterActivity2.this,HomeActivity.class));
                    finish();
                }
                else {
                    startActivity(new Intent(RegisterActivity2.this,MainActivity.class));
                }
                SharedPreferences.Editor editor = preferences.edit() ;
                editor.putString("FirstTimeInstall","Yes") ; // Key and value pair
                editor.apply();
                finish();
            }
            public void onFailure(String err)
            {
                progressBarRegister2.setVisibility(View.GONE);
            }
        });
    }
}