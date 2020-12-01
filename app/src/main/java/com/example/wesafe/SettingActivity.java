package com.example.wesafe;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wesafe.UtilService.DatabaseHelper;
import com.example.wesafe.UtilService.SharedPreferenceClass;

public class SettingActivity extends AppCompatActivity {

    EditText etEmergencyContact1,etEmergencyContact2,etEmergencyContact3;
    Button btnSaveUpdatedContact;
    SharedPreferenceClass sharedPreferenceClass;
    DatabaseHelper myDb;
    String emergencyContact1,emergencyContact2,emergencyContact3,username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        etEmergencyContact1=findViewById(R.id.etEmergencyContact1);
        etEmergencyContact2=findViewById(R.id.etEmergencyContact2);
        etEmergencyContact3=findViewById(R.id.etEmergencyContact3);
        btnSaveUpdatedContact=findViewById(R.id.btnSaveUpdatedContact);
        sharedPreferenceClass=new SharedPreferenceClass(this);
        myDb=new DatabaseHelper(this);
        setCurrentValueOfContact();
        btnSaveUpdatedContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkContact())
                {
                    updateContact();
                }
            }
        });
    }
    private Boolean checkContact(){
        String contact1 = etEmergencyContact1.getText().toString();
        String contact2 = etEmergencyContact2.getText().toString();
        String contact3 = etEmergencyContact3.getText().toString();
        boolean exact_result = true ;
        if(contact1.length() != 10){
            Log.wtf("RegisterActivity","length"+contact1.length());
            Log.wtf("RegisterActivity","hello "+contact1);
            etEmergencyContact1.setError("Contact Number must be of 10 digits");
            exact_result = false ;
        }
        if(contact2.length() != 10){
            etEmergencyContact2.setError("Contact Number must be of 10 digits");
            exact_result = false ;
        }
        if(contact3.length() != 10){
            etEmergencyContact3.setError("Contact Number must be of 10 digits");
            exact_result = false ;
        }
        return exact_result ;
    }
    protected void updateContact()
    {
        String EmergencyContact1=etEmergencyContact1.getText().toString();
        String EmergencyContact2=etEmergencyContact2.getText().toString();
        String EmergencyContact3=etEmergencyContact3.getText().toString();
        if(myDb.updateData(username,EmergencyContact1,EmergencyContact2,EmergencyContact3))
        {
            Toast.makeText(getApplicationContext(),"Emergency Contact Successfully Updated",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Error While Updating Emergency Contact",Toast.LENGTH_SHORT).show();
        }
    }
    protected void setCurrentValueOfContact()
    {
        Cursor res=myDb.getAllData();
        while(res.moveToNext())
        {
            emergencyContact1=res.getString(1);
            emergencyContact2=res.getString(2);
            emergencyContact3=res.getString(3);
            username=res.getString(4);
            etEmergencyContact1.setText(emergencyContact1);
            etEmergencyContact2.setText(emergencyContact2);
            etEmergencyContact3.setText(emergencyContact3);
        }
    }
}