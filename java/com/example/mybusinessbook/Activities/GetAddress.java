package com.example.mybusinessbook.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mybusinessbook.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GetAddress extends AppCompatActivity {
    EditText editLatitude,editLongitude,editCity;
    Button btnGetData,btnGetLat;
    TextView txtResult,txtResult1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_address);
        editLatitude = findViewById(R.id.editLatitude);
        editLongitude = findViewById(R.id.editLongitude);
        btnGetData = findViewById(R.id.btnGetData);
        txtResult = findViewById(R.id.txtResult);
        txtResult1 = findViewById(R.id.txtResult1);
        btnGetLat = findViewById(R.id.btnGetLati);
        editCity = findViewById(R.id.editCity);

        btnGetData.setOnClickListener(v->{
            double lat = Double.parseDouble(editLatitude.getText().toString().trim()!=null ? editLatitude.getText().toString().trim() : "0");
            double longi = Double.parseDouble(editLongitude.getText().toString().trim() != null ? editLongitude.getText().toString().trim() : "0"   );
            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(this, Locale.getDefault());

            try {
                addresses = geocoder.getFromLocation(lat, longi, 1);
                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                //String knownName = addresses.get(0).getFeatureName();
                txtResult.setText("address :" + address+ "\ncity :"+city+"\nstate :"+state+"\ncountry"+country+"\nPincode :"+postalCode);
                Toast.makeText(this,address,Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }


        });

        btnGetLat.setOnClickListener(v->{
            Geocoder gc = new Geocoder(this);
            if (gc.isPresent()) {
                List<Address> loc = null;
                try {
                    loc = gc.getFromLocationName(editCity.getText().toString().trim(), 1);
                    Address add = loc.get(0);
                    double lat = add.getLatitude();
                    double lng = add.getLongitude();
                    txtResult1.setText("Latitute = " + lat + "\nLongitute = " + lng);

                } catch (Exception e) {
                    txtResult1.setText(e.toString());
                }
            }
        });
    }
}