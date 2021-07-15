package com.example.mybusinessbook;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link profile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class profile extends Fragment {


    public profile() {
        // Required empty public constructor
    }
    public static profile newInstance() {
        return new profile();
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View rootView =  inflater.inflate(R.layout.fragment_profile, container, false);
        final TextInputLayout edtAddress = rootView.findViewById(R.id.Address);
        final TextInputLayout edtBusiness = rootView.findViewById(R.id.Businessname);
        final Button btnUpdate = rootView.findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(v->{
           String address = edtAddress.getEditText().getText().toString();
           String BusinessName = edtBusiness.getEditText().getText().toString();
           String number = this.getActivity().getIntent().getStringExtra("NUMBER");
           if(!address.isEmpty() && !BusinessName.isEmpty()){
               SharedPreferences preferences = this.getActivity().getSharedPreferences("MYPREFS", MODE_PRIVATE);
               SharedPreferences.Editor editor = preferences.edit();
               editor.putString(number+"Address",address);
               editor.putString(number+"BusinessName",BusinessName);
               editor.commit(); 
               Toast.makeText(getActivity(),"Record Updated Successfully",Toast.LENGTH_SHORT).show();
           }else{
               Toast.makeText(getActivity(),"Please enter Details",Toast.LENGTH_SHORT).show();
           }
        });
        return rootView;
    }
}