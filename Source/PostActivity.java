package com.example.myproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Blob;

public class PostActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    PickImageHelper ViewHelper;
    ImageButton imageButton;
    Uri imageUri;
    LatLng point;
    Bitmap bm;
    EditText editText11, editText12;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        imageButton = (ImageButton) findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewHelper.selectImage(PostActivity.this);

            }
        });
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng start = new LatLng(37.566535,126.977969);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(start,5));
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            Marker marker = null;
            @Override
            public void onMapClick(LatLng latLng) {
                point = latLng;
                if(marker != null) {
                    marker.remove();
                }
                marker= mMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .draggable(false)
                );
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            imageUri = ViewHelper.getPickImageResultUri(this,data);
           imageButton.setImageURI(imageUri);
        }

    }

public void PostContent(View v){
   ContentFragment fragment=(ContentFragment) getSupportFragmentManager().findFragmentById(R.id.content_fragment);
    String title1 = fragment.title;
    String content1 = fragment.content;
    Uri imageUri1 = imageUri;
    try {
         bm = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri1);
    } catch (FileNotFoundException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    ByteArrayOutputStream stream = new ByteArrayOutputStream();
    bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
    byte[] data = stream.toByteArray();
    SQLiteDatabase db = this.openOrCreateDatabase("ItemList.db", MODE_PRIVATE, null);
    SQLiteStatement dbs = db.compileStatement("INSERT INTO ITEMLIST VALUES(NULL,?,?,?,?,?);");
    dbs.bindString(1,title1);
    dbs.bindString(2,content1);
    if(data == null){
        Log.e("PostActivity", "NULL IMAGE");
        Toast.makeText(this,"Image is null.",Toast.LENGTH_SHORT).show();
        finish();
    }
    else {
        dbs.bindBlob(3,data);
    }

    if(point == null) {
        Log.e("PostActivity", "NULL LATLNG");
        Toast.makeText(this,"LatLng is null.",Toast.LENGTH_SHORT).show();
     finish();
    }
     else{
            dbs.bindDouble(4, point.latitude);
            dbs.bindDouble(5, point.longitude);
        }

    dbs.execute();
    db.close();
    setResult(RESULT_OK);


    finish();

}

}
