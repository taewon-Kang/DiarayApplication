package com.example.myproject;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;


public class DashboardFragment extends Fragment implements OnMapReadyCallback {
    private GoogleMap mMap2;
    private MapView mapView = null;
    String class_id = "Dashboard";
    public LatLng start;
    Cursor cursor;

    public DashboardFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, null);
        // Inflate the layout for this fragment
        FloatingActionButton fab = view.findViewById(R.id.floatingActionButton3);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), PostActivity.class);
                startActivityForResult(intent, 4000);
            }
        });
        SupportMapFragment fm =(SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map2);
        fm.getMapAsync(this);



        return view;

    }

    // TODO: Rename method, update argument and hook method into UI event


@Override
public void onMapReady(GoogleMap googleMap){
    mMap2 = googleMap;
    start = new LatLng(37.566535,126.977969);

    SQLiteDatabase mclassDB1 = this.getActivity().openOrCreateDatabase("ItemList.db", MODE_PRIVATE, null);
    Cursor cursor =mclassDB1.rawQuery("SELECT * FROM ITEMLIST", null);
    cursor.moveToFirst();
    if (cursor != null && cursor.getCount() != 0) {
        LatLng latLng2 = new LatLng(cursor.getDouble(4), cursor.getDouble(5));
        mMap2.addMarker(new MarkerOptions()
                .position(latLng2)
                .draggable(false)
                .title(cursor.getString(1))
        );


        while (cursor.moveToNext()) {
            LatLng latLng3 = new LatLng(cursor.getDouble(4), cursor.getDouble(5));
            mMap2.addMarker(new MarkerOptions()
                    .position(latLng3)
                    .draggable(false)
                    .title(cursor.getString(1))
            );


        }
    }
    mMap2.moveCamera(CameraUpdateFactory.newLatLngZoom(start,5));
    mMap2.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
        Marker marker = null;
        @Override
        public void onMapClick(LatLng latLng) {
           /* point = latLng;
            if(marker != null) {
                marker.remove();
            }
            marker= mMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .draggable(false)
            );
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));*/

        }
    });

    mMap2.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(Marker marker) {
           Marker marker1 = marker;

            Intent intent = new Intent(getActivity(), ViewDetails.class);
            intent.putExtra("ClassID",class_id);
            intent.putExtra("Latitude",marker1.getPosition().latitude);
            intent.putExtra("Longitude",marker1.getPosition().longitude);
            startActivity(intent);

            return false;
        }
    });
        mclassDB1.close();
}
@Override
    public void onResume(){
        super.onResume();

}

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            switch (requestCode){


                case 4000:
                    SQLiteDatabase mclassDB1 = this.getActivity().openOrCreateDatabase("ItemList.db", MODE_PRIVATE, null);
                    cursor =mclassDB1.rawQuery("SELECT * FROM ITEMLIST", null);
                    cursor.moveToLast();
                    LatLng latLng = new LatLng(cursor.getDouble(4), cursor.getDouble(5));
                    mMap2.addMarker(new MarkerOptions()
                            .position(latLng)
                            .draggable(false)
                            .title(cursor.getString(1))
                    );
                    mclassDB1.close();
                    break;
            }

        }
    }
}

