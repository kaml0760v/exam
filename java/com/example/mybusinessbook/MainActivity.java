package com.example.mybusinessbook;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.mybusinessbook.helper.AppConfig;
import com.example.mybusinessbook.helper.AppController;
import com.example.mybusinessbook.helper.SessionManager;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    TextInputLayout txtcontact,txtpassword;
    TextView txtsignup;
    Button btnlogin;
    private ProgressDialog pDialog;
    AlertDialog.Builder builder;
    int attempt = 3;
    private SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        session = new SessionManager(getApplicationContext());
        if(session.isLoggedIn()){
            startActivity(new Intent(this,HomeScreen.class));
            finish();
        }

        String number = getIntent().getStringExtra("NUMBER");
        String password = getIntent().getStringExtra("PASSWORD");
        txtcontact = findViewById(R.id.contactnumber);
        txtpassword = findViewById(R.id.password);
        txtsignup = findViewById(R.id.signup);
        btnlogin = findViewById(R.id.btnlogin);
        builder = new AlertDialog.Builder(this);

        txtsignup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),registration.class));
            }
        });
        txtcontact.getEditText().setText("7405873877");
        txtpassword.getEditText().setText("kaml@");
        btnlogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String contact = txtcontact.getEditText().getText().toString().trim();
                String password = txtpassword.getEditText().getText().toString().trim();

                if(!contact.equals("") && !password.equals(""))
                {
                    SharedPreferences preferences = getSharedPreferences("MYPREFS", MODE_PRIVATE);
                    String userContact = preferences.getString(contact+"number",null);
                    Log.d("RESPONSE","ucontact: "+userContact);
                    Log.d("RESPONSE","contact: "+contact);
                    Log.d("RESPONSE","pass"+password);

//                    if(userContact.equals(contact))
//                    {
//                        String UserPassword = preferences.getString(contact+"password",null);
//                        if(password.equals(UserPassword)){
//                            session.setLogin(true);
//                            Intent i = new Intent(getApplicationContext(), HomeScreen.class);
//                            i.putExtra("NUMBER",contact);
//                            i.putExtra("PASSWORD",password);
//                            startActivity(i);
//                            finish();
//                        }else{
//                            Toast.makeText(getApplicationContext(),"password mismatch",Toast.LENGTH_SHORT).show();
//                            attempt--;
//                        }
//                    }else{
//                        Toast.makeText(getApplicationContext(),"Phone number is not exists...please register",Toast.LENGTH_SHORT).show();
//                    }
                    checkLogin(contact,password);


//                    String result = preferences.getString(contact+password+"data",null);

                }else{
                    Toast.makeText(getApplicationContext(),"Please enter valid details",Toast.LENGTH_SHORT).show();
                }
                if(attempt <= 0)
                {
                    builder.setMessage("Do you want to Contact administrator?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(getApplicationContext(),"7405873877",
                                        Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();
                                Toast.makeText(getApplicationContext(),"please register yourself.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                    //Creating dialog box
                    AlertDialog alert = builder.create();
                    //Setting the title manually
                    alert.setTitle("Too Many Wrong Attempts");
                    alert.show();
                }
            }


        });
    }

    private void checkLogin(String contact, String password) {

        String tag_string_req = "req_login";

        pDialog.setMessage("Logging in ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_USER_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
               // Log.d(TAG, "Login Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        Toast.makeText(getApplicationContext(),jObj.getString("message"),Toast.LENGTH_LONG).show();
                        session.setLogin(true);
                        Intent i = new Intent(getApplicationContext(), HomeScreen.class);
                        i.putExtra("NUMBER",contact);
                        i.putExtra("PASSWORD",password);
                        startActivity(i);
                        finish();
                        //isxValid = true;
                        // user successfully logged in
                        // Create login session
                        //session.setLogin(true);

                        // Now store the user in SQLite
//                        String uid = jObj.getString("uid");
//
//                        JSONObject user = jObj.getJSONObject("user");
//                        String name = user.getString("name");
//                        String email = user.getString("email");
//                        String created_at = user
//                                .getString("created_at");

                        // Inserting row in users table
                        //db.addUser(name, email, uid, created_at);

                        // Launch main activity
//                        Intent intent = new Intent(LoginActivity.this,
//                                MainActivity.class);
//                        startActivity(intent);
//                        finish();
                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
               // Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("MoNumber", contact);
                params.put("Password", password);
                return params;
            }
        };
        // Adding request to request queue
        AppController.getInstance(this).addToRequestQueue(strReq);
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