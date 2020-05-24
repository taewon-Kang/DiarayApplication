package com.example.myproject;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ViewDetails extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap3;
    private int position;
   private Double latitude, longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_details);
        ImageView imageView = (ImageView) findViewById(R.id.imageView5);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map3);
        Details details = (Details) getSupportFragmentManager().findFragmentById(R.id.details_fragment);

        mapFragment.getMapAsync(this);
        Intent intent = getIntent();

        Bundle bundle = intent.getExtras();
        if(bundle.getString("ClassID").equals("Home")) {
            position = bundle.getInt("position");

            SQLiteDatabase mclassDB1 = this.openOrCreateDatabase("ItemList.db", MODE_PRIVATE, null);
            Cursor cursor = mclassDB1.rawQuery("SELECT * FROM ITEMLIST", null);

            details.SetText(position);
            cursor.moveToFirst();
            for (int i = 0; i < position; i++) {
                cursor.moveToNext();
            }

            if (cursor.getBlob(3) != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(cursor.getBlob(3), 0, cursor.getBlob(3).length);
                imageView.setImageBitmap(bitmap);
            }
            mclassDB1.close();
        }

        else if(bundle.getString("ClassID" ).equals("Dashboard")) {
            position = 1000;
            latitude = bundle.getDouble("Latitude");
            longitude = bundle.getDouble("Longitude");
            SQLiteDatabase mclassDB1 = this.openOrCreateDatabase("ItemList.db", MODE_PRIVATE, null);
            Cursor cursor = mclassDB1.rawQuery("SELECT * FROM ITEMLIST", null);
            details.SetTextlatlng(latitude,longitude);
            cursor.moveToFirst();
            if(latitude == cursor.getDouble(4) && longitude == cursor.getDouble(5)){
                if (cursor.getBlob(3) != null) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(cursor.getBlob(3), 0, cursor.getBlob(3).length);
                    imageView.setImageBitmap(bitmap);
                }
            } else {
                while (cursor.moveToNext()) {
                    if (latitude == cursor.getDouble(4) && longitude == cursor.getDouble(5)) {
                        if (cursor.getBlob(3) != null) {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(cursor.getBlob(3), 0, cursor.getBlob(3).length);
                            imageView.setImageBitmap(bitmap);
                        }
                    }
                }
            }
            mclassDB1.close();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap){
        mMap3 = googleMap;
        SQLiteDatabase mclassDB1 = this.openOrCreateDatabase("ItemList.db", MODE_PRIVATE, null);
        Cursor cursor =mclassDB1.rawQuery("SELECT * FROM ITEMLIST", null);
        cursor.moveToFirst();
        if(position != 1000) {
            for (int i = 0; i < position; i++) {
                cursor.moveToNext();
            }
            LatLng latLng2 = new LatLng(cursor.getDouble(4), cursor.getDouble(5));
            mMap3.addMarker(new MarkerOptions()
                    .position(latLng2)
                    .draggable(false));
            mMap3.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng2, 10));
        }
        else if(position == 1000) {
            LatLng latLng3 = new LatLng(latitude, longitude);
            mMap3.addMarker(new MarkerOptions()
            .position(latLng3)
            .draggable(false));
            mMap3.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng3,10));


        }
        mclassDB1.close();
        }


    }
