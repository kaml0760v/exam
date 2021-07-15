package com.example.mybusinessbook.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.mybusinessbook.R;
import com.example.mybusinessbook.SettingsFragment;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.placeholder2, new SettingsFragment())
                .commit();
    }
}