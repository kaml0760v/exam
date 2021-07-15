package com.example.mybusinessbook.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mybusinessbook.R;
import com.example.mybusinessbook.helper.SQLiteHandler;
import com.example.mybusinessbook.helper.SessionManager;
import com.google.android.material.textfield.TextInputLayout;

public class UpdateCategory extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String[] category = {"Income","Expense"};
    Spinner spnCategory;
    ArrayAdapter<String> adapter;
    Button btnUpdate;
    TextInputLayout edtcatName;
    String temp;
    SQLiteHandler helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_category);
        temp = getIntent().getStringExtra("CAT");
        helper = new SQLiteHandler(this);
        spnCategory = findViewById(R.id.spnCategory);
        btnUpdate = findViewById(R.id.btnUpdate);
        edtcatName = findViewById(R.id.edtCatName);
        spnCategory.setOnItemSelectedListener(this);
        adapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,category);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCategory.setAdapter(adapter);
        edtcatName.getEditText().setText(temp);
        btnUpdate.setOnClickListener(v->{
            String cat_Name = edtcatName.getEditText().getText().toString().trim();
            helper.UpdateCategory(cat_Name);
            Toast.makeText(this,"Updated Successfully",Toast.LENGTH_LONG).show();
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}