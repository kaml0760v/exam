package com.example.mybusinessbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;

import com.example.mybusinessbook.Fragements.AddCustomers;
import com.example.mybusinessbook.Fragements.CustomerList;

public class Customers extends AppCompatActivity {
    Button btnAdd,btnView;
    boolean IsAddCustomerOpen = false;
    boolean IsViewCustomerOpen = false;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menus,menu);
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customers);
        btnAdd = findViewById(R.id.button);
        btnView = findViewById(R.id.button2);
        btnAdd.setOnClickListener(v->{
            if(!IsAddCustomerOpen){
                displayAddCustomer();
            }
        });
        btnView.setOnClickListener(v->{
            if(!IsViewCustomerOpen){
                displayCustomerList();
            }
        });

    }

//    private void closeList() {
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        CustomerList clist = (CustomerList) fragmentManager.findFragmentById(R.id.placeholder);
//        if(clist != null){
//            FragmentTransaction ft = fragmentManager.beginTransaction();
//            ft.re
//        }
//    }

    private void displayCustomerList() {
        CustomerList clist = CustomerList.newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.placeholder,clist).addToBackStack(null).commit();
        IsViewCustomerOpen = true;
        IsAddCustomerOpen = false;
        //closeAddCustomer();
    }

//    private void closeAddCustomer() {
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        AddCustomers c = (AddCustomers) fragmentManager.findFragmentById(R.id.placeholder);
//        if(c!=null){
//            FragmentTransaction ft = fragmentManager.beginTransaction();
//            ft.remove(c).commit();
//        }
//        IsAddCustomerOpen = false;
//    }

    private void displayAddCustomer() {
        AddCustomers c= AddCustomers.newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.placeholder,c).addToBackStack(null).commit();
        IsAddCustomerOpen = true;
        IsViewCustomerOpen = false;
        //closeList();
    }
}