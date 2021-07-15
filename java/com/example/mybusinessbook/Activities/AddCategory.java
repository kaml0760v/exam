package com.example.mybusinessbook.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.mybusinessbook.R;
import com.google.android.material.textfield.TextInputLayout;

public class AddCategory extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String[] category = {"Income","Expense"};
    Spinner spnCategory;
    ArrayAdapter<String> adapter;
    Button btnsubmit;
    TextInputLayout edtcatName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        spnCategory = findViewById(R.id.spnCategory);
        btnsubmit = findViewById(R.id.btnSubmit);
        edtcatName = findViewById(R.id.edtCatName);
        spnCategory.setOnItemSelectedListener(this);
        adapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,category);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCategory.setAdapter(adapter);
        btnsubmit.setOnClickListener(v->{
            String cat_Name = edtcatName.getEditText().getText().toString().trim();
            String category = spnCategory.getSelectedItem().toString();
            Intent i = new Intent();
            i.putExtra("CAT_NAME",cat_Name);
            i.putExtra("CATEGORY",category);
            setResult(1,i);
            finish();
        });

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}