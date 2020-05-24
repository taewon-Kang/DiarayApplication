package com.example.myproject;

import android.net.Uri;

import com.google.android.gms.maps.model.LatLng;

public class Item {
public String title, content;
public byte[] image;
public LatLng latlng;
//public Uri imageUri;

public Item(String title1, String content1, byte[] image1, LatLng latlng1){
    title = title1;
    content = content1;
    image = image1;
    latlng= latlng1;
    //imageUri = uri1;

}
public String getTitle() {return title;}
public String getContent() {return  content;}
public byte[] getImage() {return image;}
public LatLng getLatlng( ) {return latlng;}


}
