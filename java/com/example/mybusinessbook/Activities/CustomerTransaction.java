package com.example.mybusinessbook.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mybusinessbook.Fragements.ReceivePayment;
import com.example.mybusinessbook.R;

public class CustomerTransaction extends AppCompatActivity {
    Button btnReceive,btnLand;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_transaction);
        getSupportActionBar().setTitle(getIntent().getStringExtra("Name"));
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnReceive = findViewById(R.id.btnReceive);
        btnLand = findViewById(R.id.btnLand);
            btnReceive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                String name = getIntent().getStringExtra("Name");
                String number = getIntent().getStringExtra("Number");
                bundle.putString("Name",name);
                bundle.putString("Number",name);
                ReceivePayment r = ReceivePayment.newInstance();
                r.setArguments(bundle);
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.placeholdertrasaction,r).addToBackStack(null).commit();
            }
        });
        btnLand.setOnClickListener(v->{
            Bundle bundle = new Bundle();
            String name = getIntent().getStringExtra("Name");
            bundle.putString("Name",name);
            bundle.putString("Choice","Land");
            ReceivePayment r = ReceivePayment.newInstance();
            r.setArguments(bundle);
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.placeholdertrasaction,r).addToBackStack(null).commit();
        });
    }
}