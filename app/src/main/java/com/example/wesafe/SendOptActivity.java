package com.example.wesafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.wesafe.UtilService.ApiCall;
import com.example.wesafe.UtilService.GetResult;
import com.example.wesafe.UtilService.SharedPreferenceClass;

import org.json.JSONObject;

import java.util.HashMap;

public class SendOptActivity extends AppCompatActivity {
    ProgressBar progressBarRegister2 ;
    Button btnSendOTP;
    EditText etSendOTP;
    ApiCall apiCall;
    SharedPreferenceClass sharedPreferenceClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_opt);
        progressBarRegister2 = findViewById(R.id.progressBarRegister2) ;
        etSendOTP = findViewById(R.id.etSendOTP) ;
        btnSendOTP = findViewById(R.id.btnSendOTP) ;
        progressBarRegister2.setVisibility(View.GONE);
        sharedPreferenceClass=new SharedPreferenceClass(this);
        apiCall=new ApiCall(this);
        btnSendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkMobileNumber())
                {
                    sendOTP(v);
                }
            }
        });
    }
    private Boolean checkMobileNumber(){
        String phonenumber = etSendOTP.getText().toString();
        boolean exact_result = true ;
        if(phonenumber.length()!=10)
        {
            etSendOTP.setError("Contact Number must be of 10 digits");
            exact_result=false;
        }
        return exact_result ;
    }
    private void sendOTP(View v)
    {
        final String mobile_number = etSendOTP.getText().toString();
        progressBarRegister2.setVisibility(View.VISIBLE);
        HashMap<String,String> params=new HashMap<>();
        params.put("mobile_number",mobile_number);
        params.put("channel","sms");
        String Localhost_Endpoint="http://10.0.2.2:8080/users/sendOTP";
        String Cloud_EndPoint="https://wesafe-app.herokuapp.com/users/sendOTP";
        apiCall.CallWithoutToken(params,Cloud_EndPoint, new GetResult() {
            @Override
            public void onSuccess(JSONObject data) {
                sharedPreferenceClass.setValue_string("phone_number",mobile_number);
                startActivity(new Intent(SendOptActivity.this,RegisterActivity2.class));
            }
            public void onFailure(String err)
            {
                progressBarRegister2.setVisibility(View.GONE);
            }
        });
    }
}