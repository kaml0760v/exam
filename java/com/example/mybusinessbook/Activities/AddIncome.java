package com.example.mybusinessbook.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mybusinessbook.R;
import com.example.mybusinessbook.helper.SQLiteHandler;
import com.example.mybusinessbook.registration;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

public class AddIncome extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Button btnSave;
    TextInputLayout txtAmount,txtDetail,txtDate;
    Set<String> data;
    Spinner spnCategory;
    List<String> tmp = new ArrayList<>();
    ArrayAdapter<String> adapter;
    DatePickerDialog picker;
    SQLiteHandler helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_income);
        SharedPreferences prefs = getSharedPreferences("INCOME",MODE_PRIVATE);
        String type = getIntent().getStringExtra("Option");
        spnCategory = findViewById(R.id.spnCategory);
        txtAmount = findViewById(R.id.Amount);
        txtDetail = findViewById(R.id.Details);
        txtDate = findViewById(R.id.Date);
        btnSave = findViewById(R.id.btnSave);
        helper = new SQLiteHandler(this);


        if(type.equals("income")){
            getSupportActionBar().setTitle("Add Income");
            //data = prefs.getStringSet("Income",null);
            tmp=  helper.getAllIncomeCategory();
//            for(String a:data)
//                tmp.add(a);
            tmp.add(0,"- - - - -income- - - - -");
        }else{
            getSupportActionBar().setTitle("Add Expense");
//            data = prefs.getStringSet("Expense",null);
//            for(String a:data)
//                tmp.add(a);
            tmp = helper.getAllExpenseCategory();
            tmp.add(0,"- - - - -Expense- - - - -");
        }
//        SharedPreferences.Editor editor = prefs.edit();
        adapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,tmp);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCategory.setAdapter(adapter);
        txtDate.getEditText().setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(AddIncome.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                txtDate.getEditText().setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });
        btnSave.setOnClickListener(v->{
           String amount = txtAmount.getEditText().getText().toString().trim();
            String detail = txtDetail.getEditText().getText().toString().trim();
            String category = spnCategory.getSelectedItem().toString();
            String date = txtDate.getEditText().getText().toString();
            if(type.equals("income")){
                String FILE_NAME = "income.txt";
                try {
                    FileOutputStream fos = openFileOutput(FILE_NAME, Context.MODE_APPEND);
                    PrintWriter writer = new PrintWriter(new OutputStreamWriter(fos));
                    writer.println("income"+"@"+amount+"@"+detail+"@"+category+"@"+date);
                    writer.close();
                    Toast.makeText(this,"Scuccessfully added.",Toast.LENGTH_LONG).show();
                    txtAmount.getEditText().setText("");
                    txtDetail.getEditText().setText("");
                    txtDate.getEditText().setText("");

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }else{
                String FILE_NAME = "income.txt";
                try {
                    FileOutputStream fos = openFileOutput(FILE_NAME, Context.MODE_APPEND);
                    PrintWriter writer = new PrintWriter(new OutputStreamWriter(fos));
                    writer.println("expense"+"@"+amount+"@"+detail+"@"+category+"@"+date);
                    writer.close();
                    Toast.makeText(this,"Scuccessfully added.",Toast.LENGTH_LONG).show();
                    txtAmount.getEditText().setText("");
                    txtDetail.getEditText().setText("");
                    txtDate.getEditText().setText("");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}