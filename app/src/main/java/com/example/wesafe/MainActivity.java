package com.example.wesafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {
    Button btnLogin ;
    EditText etEmailLogin, etPasswordLogin ;
    TextInputLayout tilPasswordLogin;
    ProgressBar progressBarLogin ;
    TextView tvRegister ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLogin = findViewById(R.id.btnLogin);
        etEmailLogin = findViewById(R.id.etEmailLogin) ;
        etPasswordLogin = findViewById(R.id.etPasswordLogin) ;
        tilPasswordLogin = findViewById(R.id.tilPasswordLogin) ;
        tvRegister = findViewById(R.id.tvRegister) ;
        progressBarLogin = findViewById(R.id.progressBarLogin);
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

    //Expression Validations
    private Boolean validateEmail() {
        String val = etEmailLogin.getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            etEmailLogin.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(emailPattern)) {
            etEmailLogin.setError("Invalid email address");
            return false;
        } else {
            etEmailLogin.setError(null);
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
        String email = etEmailLogin.getText().toString().trim();
        String password = etPasswordLogin.getText().toString().trim();
        boolean is_expressions_valid = true;
        if (!validateEmail()) {
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
            progressBarLogin.setVisibility(View.GONE);
        }
        if (is_expressions_valid) {
            Toast.makeText(this,"Login Successful", Toast.LENGTH_LONG).show();
            Log.wtf("MainActivity","Internal Expressions are valid") ;
        }
    }
}
