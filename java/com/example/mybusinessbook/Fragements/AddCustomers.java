package com.example.mybusinessbook.Fragements;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.mybusinessbook.R;
import com.google.android.material.textfield.TextInputLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddCustomers#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddCustomers extends Fragment {

    private String number;
    TextInputLayout edtnumber;

    public AddCustomers() {
        // Required empty public constructor
    }
    public static AddCustomers newInstance(){return new AddCustomers();}


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 10){
            if(resultCode == Activity.RESULT_OK){
                Uri contactData = data.getData();
                Cursor c = getActivity().getContentResolver().query(contactData, null, null, null, null);
                if(c.moveToNext()){
                    String id =
                            c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));

                    String hasPhone =
                            c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                    if (hasPhone.equalsIgnoreCase("1")) {
                        Cursor phones = getActivity().getContentResolver().query(
                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ id,
                                null, null);
                        phones.moveToFirst();
                        number = phones.getString(phones.getColumnIndex("data1"));
                        edtnumber.getEditText().setText(number);
                        String name = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.DISPLAY_NAME));
                }
                }
                c.close();
            }
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v =  inflater.inflate(R.layout.fragment_add_customers, container, false);
        final TextInputLayout edtname = v.findViewById(R.id.name);
        edtnumber = v.findViewById(R.id.txtContact);
        final TextInputLayout edtAddress = v.findViewById(R.id.address);
        final Button btnAdd = v.findViewById(R.id.btnAddCustomer);

//        edtnumber.getEditText().addTextChangedListener(new TextWatcher(){
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if(s.length() > edtnumber.getCounterMaxLength()){
//                    edtnumber.setError("Max character length is" + edtnumber.getCounterMaxLength());
//                }else if(s.length() < edtnumber.getCounterMaxLength()){
//                    edtnumber.setError(edtnumber.getCounterMaxLength()+"digit required.");
//                }else {
//                    edtnumber.setError(null);
//                }
//            }
//        });
//        edtnumber.getEditText().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
//                startActivityForResult(intent, 1);
//            }
//        });
        edtnumber.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent,10 );
            }
        });
        //edtnumber.getEditText().setText(number);
        btnAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String name = edtname.getEditText().getText().toString().trim();
                String Address = edtAddress.getEditText().getText().toString().trim();
                String  number = edtnumber.getEditText().getText().toString().trim();
                if(!name.isEmpty() && !Address.isEmpty() && !number.isEmpty()){
                    Toast.makeText(getActivity(),"Customer Successfully added",Toast.LENGTH_LONG).show();
                }else
                {
                    Toast.makeText(getActivity(),"please fill all the details",Toast.LENGTH_LONG).show();
                }
            }
        });


        return v;
    }
}