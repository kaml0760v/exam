package com.example.mybusinessbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.example.mybusinessbook.Activities.GetAddress;
import com.example.mybusinessbook.Activities.IncomeExpense;
import com.example.mybusinessbook.Activities.MapsActivity;
import com.example.mybusinessbook.Activities.Settings;
import com.example.mybusinessbook.helper.SessionManager;

public class HomeScreen extends AppCompatActivity {
    Button btnProfile,btnExpense,btnCustomer,btnKhatabook,btnSummury,btnSearch;
    String number,DOB,Email,Gender,name;
    AlertDialog.Builder builder;
    private SessionManager session;
    SharedPreferences.Editor editor;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menus,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.dashboard:
                startActivity(new Intent(this,HomeScreen.class));
                return true;
            case R.id.profile:
                Intent i = new Intent(this,CustomerProfile.class);
                i.putExtra("NUMBER",number);
                i.putExtra("DOB",DOB);
                i.putExtra("EMAIL",Email);
                i.putExtra("GENDER",Gender);
                i.putExtra("NAME",name);
                startActivity(i);
                return true;
            case R.id.customers:
                startActivity(new Intent(this,Customers.class));
                return true;
            case R.id.logout:
                Logout();
                return true;
            case R.id.setting:
                startActivity(new Intent(this, Settings.class));
                return true;
            case R.id.Address:
                startActivity(new Intent(this, GetAddress.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }

    private void Logout() {
        builder.setMessage("Are you sure want to Logout?").setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        session.setLogin(false);
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        finish();
                    }
                }).setNegativeButton("Cancel",new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setTitle("Alert");
        dialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        ActionBar bar = getActionBar();
//        bar.setBackgroundDrawable(new ColorDrawable(Color.YELLOW));
        builder = new AlertDialog.Builder(this);
        session = new SessionManager(this);

        if (!session.isLoggedIn()) {
        //calliing logout function if session time out
            startActivity(new Intent(this,MainActivity.class));
            finish();
            //editor.clear();
            //editor.commit();
            //logoutUser();
        }
        SharedPreferences preferences = getSharedPreferences("MYPREFS", MODE_PRIVATE);
        number = getIntent().getStringExtra("NUMBER");
        DOB = preferences.getString(number+"DOB",null);
        Email = preferences.getString(number+"Email",null);
        Gender = preferences.getString(number+"Gender",null);
        name = preferences.getString(number+"name",null);
        btnSummury = findViewById(R.id.btnSummury);
        btnProfile = findViewById(R.id.btnprofile);
        btnExpense = findViewById(R.id.btnexpense);
        btnCustomer = findViewById(R.id.btncustomer);
        btnKhatabook = findViewById(R.id.btnkhatabook);
        btnSearch = findViewById(R.id.btnSearch);
        btnProfile.setOnClickListener(v->{
            Intent i = new Intent(this,CustomerProfile.class);
            i.putExtra("NUMBER",number);
            i.putExtra("DOB",DOB);
            i.putExtra("EMAIL",Email);
            i.putExtra("GENDER",Gender);
            i.putExtra("NAME",name);
            startActivity(i);
        });
        btnExpense.setOnClickListener(v->{
            startActivity(new Intent(this,Expense.class));
        });
        btnCustomer.setOnClickListener(v->{
            startActivity(new Intent(this,Customers.class));
        });
        btnKhatabook.setOnClickListener(v->{
            startActivity(new Intent(this,Khatabook.class));
        });
        btnSummury.setOnClickListener(v->{
            startActivity(new Intent(this, IncomeExpense.class));
        });
        btnSearch.setOnClickListener(v->{
            startActivity(new Intent(this, MapsActivity.class));
        });


    }
}