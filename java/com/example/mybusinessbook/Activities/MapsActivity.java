package com.example.mybusinessbook.Activities;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.example.mybusinessbook.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ArrayList<LatLng> locationArrayList;
    //    LatLng location1 = new LatLng(21.215785, 72.861942);
    //    LatLng location2 = new LatLng(21.219203, 72.875938);
    //    LatLng location3 = new LatLng(21.220313, 72.874307);
    //    LatLng location4 = new LatLng(21.219683, 72.872000);
    //    LatLng location5 = new LatLng(21.217673, 72.870874);
    //String name[] = {"sanjay kirana store","shiv mobile","om stationary","Easy electronics","clothes"};
    String name[] = {"Anjali BeutyParler","Relience Fresh","Om Jwellary","Mi Mobile Store","Rajhans Mall"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        locationArrayList = new ArrayList<>();

        // on below line we are adding our
        // locations in our array list.
        locationArrayList .add(new LatLng(21.219361, 72.882767));
        locationArrayList .add(new LatLng(21.231242, 72.867060));
        locationArrayList .add(new LatLng(21.213579, 72.844620));
        locationArrayList .add(new LatLng(21.219683, 72.872000));
        locationArrayList .add(new LatLng(21.217673, 72.870874));


    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        for (int i = 0; i < locationArrayList.size(); i++) {

            // below line is use to add marker to each location of our array list.
            mMap.addMarker(new MarkerOptions().position(locationArrayList.get(i)).title(name[i])
            .icon(BitmapFromVector(getApplicationContext(), R.drawable.marker)));

            // below lin is use to zoom our camera on map.
            mMap.animateCamera(CameraUpdateFactory.zoomTo(18.0f));

            // below line is use to move our camera to the specific location.
            mMap.moveCamera(CameraUpdateFactory.newLatLng(locationArrayList.get(i)));
        }
    }

    private BitmapDescriptor BitmapFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);

        // below line is use to set bounds to our vector drawable.
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());

        // below line is use to create a bitmap for our
        // drawable which we have added.
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.RGB_565);

        // below line is use to add bitmap in our canvas.
        Canvas canvas = new Canvas(bitmap);
        // below line is use to draw our
        // vector drawable in canvas.
        vectorDrawable.draw(canvas);

        // after generating our bitmap we are returning our bitmap.
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
}
