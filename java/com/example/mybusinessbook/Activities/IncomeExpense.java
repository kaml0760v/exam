package com.example.mybusinessbook.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;
import android.widget.Toast;

import com.example.mybusinessbook.R;

public class IncomeExpense extends AppCompatActivity {
    Button btnIncome,btnExpese;
    String[] choice = {"Add","View"};
    AlertDialog.Builder dialog;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menus,menu);
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_expense);
        btnIncome = findViewById(R.id.btnIncome);
        btnExpese = findViewById(R.id.btnExpense);
        btnIncome.setOnClickListener(v->showDialog("income"));
        btnExpese.setOnClickListener(v->showDialog("expense"));
    }
    private void showDialog(String Choose){
        dialog = new AlertDialog.Builder(this);
        dialog.setTitle("What you want to do?");
        dialog.setItems(choice, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which == 0){
                    //first option clicked, do this...
                    Intent i = new Intent(getApplicationContext(),AddIncome.class);
                    i.putExtra("Option",Choose);
                    startActivity(i);
                }else if(which == 1){
                    //second option clicked, do this...
                    Intent i = new Intent(getApplicationContext(),ViewTransaction.class);
                    startActivity(i);
                }else{
                    //theres an error in what was selected
                    Toast.makeText(getApplicationContext(), "Hmmm I messed up. I detected that you clicked on : " + which + "?", Toast.LENGTH_LONG).show();
                }
            }
        });

        dialog.show();
    }
}