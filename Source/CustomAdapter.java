package com.example.myproject;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class CustomAdapter extends BaseAdapter {

    private List<Item> items;
    private Activity context;
    public CustomAdapter(Activity _context, List<Item> items2){
        context = _context;
        items = items2;
    }
    @Override
    public int getCount() {
        return items.size();
    }
    @Override
    public Object getItem(int position) {
        return items.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.itemlist, parent, false);
        }
        TextView title1=(TextView) convertView.findViewById(R.id.textView1);
        TextView content1=(TextView) convertView.findViewById(R.id.textView2);
        ImageView image1=(ImageView) convertView.findViewById(R.id.imageView);
        title1.setText(items.get(position).title);
        content1.setText(items.get(position).content);
        if(items.get(position).image != null)
        {Bitmap bitmap = BitmapFactory.decodeByteArray(items.get(position).image, 0 ,items.get(position).image.length);
        image1.setImageBitmap(bitmap);}
        return convertView;
    }
}
