package com.example.mybusinessbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.mybusinessbook.helper.AppConfig;
import com.example.mybusinessbook.helper.AppController;
import com.example.mybusinessbook.helper.SQLiteHandler;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

public class registration extends AppCompatActivity {
    TextView txtsignin;
    Button btnregister;
    TextInputLayout txtname,txtEmail,txtnumber,txtpass,txtrepass,txtDOB;
    RadioButton radioButton;
    RadioGroup radioGroupGender;
    DatePickerDialog picker;
    String name,Email,number,Gender,DOB,password,repassword;
    private SQLiteHandler helper;
    private ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        setContentView(R.layout.activity_registration);
        txtsignin = findViewById(R.id.signin);
        btnregister = findViewById(R.id.btnregister);
        txtname = findViewById(R.id.name);
        txtEmail = findViewById(R.id.Email);
        txtnumber = findViewById(R.id.txtContact);
        radioGroupGender = findViewById(R.id.radioGender);
        txtDOB = findViewById(R.id.txtbirth);
        txtpass = findViewById(R.id.password);
        txtrepass = findViewById(R.id.conpassword);
        helper = new SQLiteHandler(this);

        txtsignin.setOnClickListener(v->startActivity(new Intent(getApplicationContext(),MainActivity.class)));
        txtnumber.getEditText().addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > txtnumber.getCounterMaxLength()){
                    txtnumber.setError("Max character length is" + txtnumber.getCounterMaxLength());
                }else if(s.length() < txtnumber.getCounterMaxLength()){
                    txtnumber.setError(txtnumber.getCounterMaxLength()+"digit required.");
                }else {
                    txtnumber.setError(null);
                }
            }
        });

        txtDOB.getEditText().setOnClickListener(v-> {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(registration.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                txtDOB.getEditText().setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            });
        txtDOB.getEditText().setInputType(InputType.TYPE_NULL);
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = txtname.getEditText().getText().toString().trim();
                Email = txtEmail.getEditText().getText().toString().trim();
                number = txtnumber.getEditText().getText().toString().trim();
                DOB = txtDOB.getEditText().getText().toString().trim();
                password = txtpass.getEditText().getText().toString().trim();
                repassword = txtrepass.getEditText().getText().toString().trim();
                int selectedId = radioGroupGender.getCheckedRadioButtonId();
                radioButton = findViewById(selectedId);
                Gender = radioButton.getText().toString();


                if(CheckEmail() && IsEmpty() && checkPassword()   )
                {
                    RegisterUser(name,Email,number,DOB,password,Gender);
                    // User user = new User(name,Email,number,DOB,Gender,password);
                    Toast.makeText(getApplicationContext(),"Registered Successfully",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(),MainActivity.class);
                    i.putExtra("NUMBER",number);
                    i.putExtra("PASSWORD",password);
                    startActivity(i);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),"Please fill up details",Toast.LENGTH_SHORT).show();
                }
            }


        });
    }

    private void RegisterUser(String name, String email, String number, String dob, String password, String gender) {
        String tag_string_req = "req_login";

        pDialog.setMessage("Registering ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_USER_REGISTER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                //Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        helper.adduser(name,Email,number,Gender,DOB,repassword);
                        SharedPreferences preferences = getSharedPreferences("MYPREFS",MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString(number+password+"data",number+password);
                        editor.putString(number+"number",number);
                        editor.putString(number+"name",name);
                        editor.putString(number+"password",password);
                        editor.putString(number+"Email",Email);
                        editor.putString(number+"DOB",DOB);
                        editor.putString(number+"Gender",Gender);
                        editor.commit();

                    } else {

                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("Name", name);
                params.put("Email", email);
                params.put("MoNumber", number);
                params.put("Gender", gender);
                params.put("DOB", dob);
                params.put("Password", password);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance(this).addToRequestQueue(strReq);
    }

    public boolean checkPassword(){
        final Pattern PASSWORD_PATTERN =Pattern.compile("^(?=.*[@#$%^&+=])(?=\\S+$).{4,}$");
        if(!password.isEmpty() && !repassword.isEmpty()){
            if(PASSWORD_PATTERN.matcher(password).matches()){
                if(password.equals(repassword)){
                    txtrepass.setError(null);
                    return true;
                }else{
                    txtrepass.setError("password mismatch");
                    return false;
                }
            }
            else {
                txtpass.setError(null);
                txtrepass.setError("Please enter 1 special character and size must be 4");

                return false;
            }
        }else{
            txtpass.setError("Please enter valid details");
            txtrepass.setError("Please enter valid details");
            return false;
        }
    }
    public boolean CheckEmail()
    {
        final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(Email.matches(emailPattern))
        {
            txtEmail.setError(null);
            return true;
        }else{
            txtEmail.requestFocus();
            txtEmail.setError("Invalid Email");
        }
        return false;
    }
    public boolean IsEmpty(){
        if(name.isEmpty())
        {
            txtname.requestFocus();
            txtname.setError("Required");
            return false;
        }else if(number.isEmpty()){
            txtnumber.requestFocus();
            return false;
        }
        else if(DOB.isEmpty())
        {
            txtname.setError(null);
            txtDOB.requestFocus();
            txtDOB.setError("Required");
            return false;
        }else {
            txtname.setError(null);
            txtDOB.setError(null);
        }
        return true;
    }
    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}