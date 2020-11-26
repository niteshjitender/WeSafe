package com.example.wesafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.wesafe.UtilService.DatabaseHelper;
import com.example.wesafe.UtilService.SharedPreferenceClass;

public class HomeActivity extends AppCompatActivity {
    private Button logout;
    SharedPreferenceClass sharedPreferencesClass;
    DatabaseHelper myDb;
    Button btnviewAll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        logout=(Button)findViewById(R.id.logout);
        sharedPreferencesClass=new SharedPreferenceClass(this);
        btnviewAll=(Button)findViewById(R.id.viewAllContact);
        myDb= new DatabaseHelper(this);
        Logout();
        viewAll();

    }
    protected void Logout()
    {
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferencesClass.clear();
                startActivity(new Intent(HomeActivity.this,MainActivity.class));
                finish();
            }
        });
    }
    protected void viewAll()
    {
        btnviewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res=myDb.getAllData();
                if(res.getCount()==0)
                {
                    Toast.makeText(getApplicationContext(),"No data is present",Toast.LENGTH_LONG).show();
                }
                else
                {
                    StringBuffer buffer=new StringBuffer();
                    while(res.moveToNext())
                    {
                        buffer.append("Emergency Contact1: "+res.getString(1)+"\n");
                        buffer.append("Emergency Contact2: "+res.getString(2)+"\n");
                        buffer.append("Emergency Contact3: "+res.getString(3)+"\n");
                        buffer.append("Username: "+res.getString(4)+"\n");
                    }
                    Toast.makeText(getApplicationContext(),buffer.toString(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}