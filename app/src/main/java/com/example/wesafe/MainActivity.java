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

import com.example.wesafe.UtilService.ApiCall;
import com.example.wesafe.UtilService.GetResult;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity{
    Button btnLogin ;
    EditText etUsernameLogin, etPasswordLogin ;
    TextInputLayout tilPasswordLogin;
    ProgressBar progressBarLogin ;
    TextView tvRegister ;
    ApiCall apiCall;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLogin = findViewById(R.id.btnLogin);
        etUsernameLogin = findViewById(R.id.etUserNameLogin) ;
        etPasswordLogin = findViewById(R.id.etPasswordLogin) ;
        tilPasswordLogin = findViewById(R.id.tilPasswordLogin) ;
        tvRegister = findViewById(R.id.tvRegister) ;
        progressBarLogin = findViewById(R.id.progressBarLogin);
        apiCall= new ApiCall(this);
        progressBarLogin.setVisibility(View.GONE);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent_register = new Intent(getApplicationContext(), RegisterActivity.class) ;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressBarLogin.setVisibility(View.GONE);
                        startActivity(intent_register);
                    }
                }, 1500);
            }
        });
    }
    //Overriding back button function : restarting of app
    @Override
    public void onBackPressed() {
        moveTaskToBack(true); //it goes to background.
    }

    private Boolean validateUsername() {
        String val = etUsernameLogin.getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";

        if (val.isEmpty()) {
            etUsernameLogin.setError("Field cannot be empty");
            return false;
        } else if (val.length() >= 15) {
            etUsernameLogin.setError("Username too long");
            return false;
        } else if (!val.matches(noWhiteSpace)) {
            etUsernameLogin.setError("White Spaces are not allowed");
            return false;
        } else {
            etUsernameLogin.setError(null);
//            etEmail.setErrorEnabled(false);
            return true;
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

    void Login() {
        progressBarLogin.setVisibility(View.VISIBLE);
        boolean is_expressions_valid = true;
        if (!validateUsername()) {
            progressBarLogin.setVisibility(View.GONE);
            is_expressions_valid = false;
        }
        if (!validatePassword()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    tilPasswordLogin.setPasswordVisibilityToggleEnabled(true);
                }
            }, 3000);
            is_expressions_valid = false;
        }
        if (is_expressions_valid) {
            String username = etUsernameLogin.getText().toString();
            String password = etPasswordLogin.getText().toString();
            HashMap<String,String> params=new HashMap<>();
            params.put("username",username);
            params.put("password",password);
            String Localhost_Endpoint="http://10.0.2.2:8080/users/login";
            String Cloud_EndPoint="https://wesafe-app.herokuapp.com/users/login";
            apiCall.PostCall(params,Cloud_EndPoint,new GetResult(){
                @Override
                public void onSuccess(boolean check) {
                    if(check)
                    {
                        startActivity(new Intent(MainActivity.this,HomeActivity.class));
                        finish();
                    }
                }
            });
            progressBarLogin.setVisibility(View.GONE);
        }
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
