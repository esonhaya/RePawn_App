package com.example.systemscoreinc.repawn.Pawnshop;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.example.systemscoreinc.repawn.IpConfig;
import com.example.systemscoreinc.repawn.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

public class Pawnshop_Locate extends FragmentActivity implements OnMapReadyCallback {

    private double lat, lng;
    private String name, image;
    private Marker m;
    IpConfig ip=new IpConfig();
    Context context;
    Bitmap bitmap;
    MarkerOptions mo;
    PicassoMarker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_location);
        context = this;
        Bundle b = getIntent().getExtras();
        if (b != null) {
            lat = b.getDouble("lat");
            lng = b.getDouble("lng");
            name = b.getString("name");
            image = b.getString("user_image");
            Log.e("lat", String.valueOf(lng));

        }
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
        GoogleMap mMap = googleMap;

        // Add a marker in Sydney, Australia, and move the camera.
        Log.e("lat", String.valueOf(lat));
        Log.e("long", String.valueOf(lng));
        Log.e("image", image);
        LatLng sydney = new LatLng(lat, lng);

        m = mMap.addMarker(new MarkerOptions()
                .position(sydney)
                .title(name));
        marker = new PicassoMarker(m);
        Picasso.get().load(ip.getUrl_image()+image).into(marker);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,15));
        // Zoom in, animating the camera.
        googleMap.animateCamera(CameraUpdateFactory.zoomIn());
        // Zoom out to zoom level 10, animating with a duration of 2 seconds.
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
    }


}


