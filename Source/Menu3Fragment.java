package com.example.myproject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;



public class Menu3Fragment extends Fragment {

    LatLng latLng;
    String string1,notice;
    EditText editText;
    Cursor cursor;
    List<Integer> find;
    CustomAdapter adapter;
    public  List<Item> items;
    ListView listView;
    TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
           string1 = editText.getText().toString();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    public Menu3Fragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu3, null);
        // Inflate the layout for this fragment
        SQLiteDatabase mclassDB1 = this.getActivity().openOrCreateDatabase("ItemList.db", MODE_PRIVATE, null);
         cursor = mclassDB1.rawQuery("SELECT * FROM ITEMLIST", null);
        Spinner spinner = (Spinner) view.findViewById(R.id.spinner1);

        listView = (ListView) view.findViewById(R.id.listView2);

        Button btn = (Button) view.findViewById(R.id.button3);

        final String selected = spinner.getSelectedItem().toString();
       editText = (EditText) view.findViewById(R.id.editt);
        editText.addTextChangedListener(watcher);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cursor.moveToFirst();
                switch (selected){
                    case "제목":
                        items =  new ArrayList<Item>();
                        adapter = new CustomAdapter(getActivity(), items);
                        listView.setAdapter(adapter);
                        cursor.moveToFirst();
                        if (cursor.getString(1).equals(string1)){
                            latLng = new LatLng(cursor.getDouble(4), cursor.getDouble(5));
                            items.add(new Item(cursor.getString(1), cursor.getString(2), cursor.getBlob(3), latLng));
                        }

                            while(cursor.moveToNext()){
                                if (cursor.getString(1).equals(string1)){
                                    latLng = new LatLng(cursor.getDouble(4), cursor.getDouble(5));
                                    items.add(new Item(cursor.getString(1), cursor.getString(2), cursor.getBlob(3), latLng));
                                }
                            }
                        notice = Integer.toString(items.size());
                        Toast.makeText(getActivity(),"총 "+notice+"개를 검색하였습니다!",Toast.LENGTH_LONG).show();

                        adapter.notifyDataSetChanged();
                        break;
                        case "내용":
                            items =  new ArrayList<Item>();
                            adapter = new CustomAdapter(getActivity(), items);
                            listView.setAdapter(adapter);
                            cursor.moveToFirst();
                            if (cursor.getString(2).equals(string1)){
                                latLng = new LatLng(cursor.getDouble(4), cursor.getDouble(5));
                                items.add(new Item(cursor.getString(1), cursor.getString(2), cursor.getBlob(3), latLng));
                            } else{
                                while(cursor.moveToNext()){
                                    if (cursor.getString(2).equals(string1)){
                                        latLng = new LatLng(cursor.getDouble(4), cursor.getDouble(5));
                                        items.add(new Item(cursor.getString(1), cursor.getString(2), cursor.getBlob(3), latLng));
                                    }
                                }
                            }
                            notice = Integer.toString(items.size());
                            Toast.makeText(getActivity(),"총 "+notice+"개를 검색하였습니다!",Toast.LENGTH_LONG).show();

                            adapter.notifyDataSetChanged();
                            break;
                    case "위도":
                        items =  new ArrayList<Item>();
                        adapter = new CustomAdapter(getActivity(), items);
                        listView.setAdapter(adapter);
                        cursor.moveToFirst();

                        if (Double.toString(cursor.getDouble(4)).equals(string1)){
                            latLng = new LatLng(cursor.getDouble(4), cursor.getDouble(5));
                            items.add(new Item(cursor.getString(1), cursor.getString(2), cursor.getBlob(3), latLng));
                        } else{
                            while(cursor.moveToNext()){
                                if (Double.toString(cursor.getDouble(4)).equals(string1)){
                                    latLng = new LatLng(cursor.getDouble(4), cursor.getDouble(5));
                                    items.add(new Item(cursor.getString(1), cursor.getString(2), cursor.getBlob(3), latLng));
                                }
                            }
                        }
                        notice = Integer.toString(items.size());
                        Toast.makeText(getActivity(),"총 "+notice+"개를 검색하였습니다!",Toast.LENGTH_LONG).show();

                        adapter.notifyDataSetChanged();
                        break;
                    case "경도":
                        items =  new ArrayList<Item>();
                        adapter = new CustomAdapter(getActivity(), items);
                        listView.setAdapter(adapter);
                        cursor.moveToFirst();
                        if (Double.toString(cursor.getDouble(5)).equals(string1)){
                            latLng = new LatLng(cursor.getDouble(4), cursor.getDouble(5));
                            items.add(new Item(cursor.getString(1), cursor.getString(2), cursor.getBlob(3), latLng));
                        } else{
                            while(cursor.moveToNext()){
                                if (Double.toString(cursor.getDouble(5)).equals(string1)){
                                    latLng = new LatLng(cursor.getDouble(4), cursor.getDouble(5));
                                    items.add(new Item(cursor.getString(1), cursor.getString(2), cursor.getBlob(3), latLng));
                                }
                            }
                        }
                        notice = Integer.toString(items.size());
                        Toast.makeText(getActivity(),"총 "+notice+"개를 검색하였습니다!",Toast.LENGTH_LONG).show();

                        adapter.notifyDataSetChanged();
                        break;
                }

            }
        });

        return view;
    }





}
