package com.example.mybusinessbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.material.badge.BadgeUtils;
import com.google.android.material.textfield.TextInputLayout;

public class CustomerProfile extends AppCompatActivity {
    Button btnUpdate;
    TextInputLayout txtname,txtEmail,txtnumber,txtBirthday;
    TextView txtViewmore;
    RadioGroup radioGroup;
    boolean isFragmentDisplay = false;
    String name,Email,number,Gender,DOB;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menus,menu);
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_profile);
        txtname = findViewById(R.id.name);
        txtEmail= findViewById(R.id.Email);
        txtnumber = findViewById(R.id.txtContact);
        txtBirthday = findViewById(R.id.txtbirth);
        radioGroup = findViewById(R.id.radioGender);
        name = getIntent().getStringExtra("NAME");
        Email = getIntent().getStringExtra("EMAIL");
        number = getIntent().getStringExtra("NUMBER");
        Gender = getIntent().getStringExtra("GENDER");
        DOB = getIntent().getStringExtra("DOB");
        if(Gender.equals("Female")){
            radioGroup.check(R.id.radiofemale);
        }else{
            radioGroup.check(R.id.radiomale);
        }
        txtViewmore = findViewById(R.id.txtViewmore);
        btnUpdate = findViewById(R.id.btnUpdate);
        txtname.getEditText().setText(name);
        txtEmail.getEditText().setText(Email);
        txtnumber.getEditText().setText(number);
        txtBirthday.getEditText().setText(DOB);
        txtViewmore.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //btnUpdate.setVisibility(View.INVISIBLE);
                if(!isFragmentDisplay){
                    displayFragemnt();
                }else{
                    closeFragment();
                }
            }
        });
        if(savedInstanceState != null){
            isFragmentDisplay = savedInstanceState.getBoolean("state_of_fragment");
        }
    }

    private void displayFragemnt() {
        profile p = profile.newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.yourplacejolder,p).addToBackStack(null).commit();
        txtViewmore.setText("-View Less");
        isFragmentDisplay = true;
    }

    private void closeFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        profile p = (profile)fragmentManager.findFragmentById(R.id.yourplacejolder);
        if(p!= null){
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(p).commit();
        }
        txtViewmore.setText("+View More");
        isFragmentDisplay = false;
    }
    public void onSaveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putBoolean("state_of_fragment",isFragmentDisplay);
        super.onSaveInstanceState(savedInstanceState);
    }
}