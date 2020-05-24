package com.example.myproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;
import static android.webkit.ConsoleMessage.MessageLevel.LOG;


public class HomeFragment extends Fragment {
     List<String> titles = new ArrayList<String>();
     List<String> contents = new ArrayList<String>();
     List<byte[]> images = new ArrayList<byte[]>();
     List<LatLng> latlngs = new ArrayList<LatLng>();
    int count =0;
    public  List<Item> items = new ArrayList<Item>();

    CustomAdapter adapter;
    Cursor cursor;
    String class_id = "Home";
    public static Context mContext2;
    String title1, content1;
    SQLiteDatabase mclassDB1;
    public ListView listView;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Activity activity = (Activity)context;
        try{
            mItemClickListener = (AdapterView.OnItemClickListener) activity;
        }catch(ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, null);

        LatLng latLng1;
        mclassDB1 = this.getActivity().openOrCreateDatabase("ItemList.db", MODE_PRIVATE, null);
        cursor = mclassDB1.rawQuery("SELECT * FROM ITEMLIST", null);
        int a=items.size();
        int b = cursor.getCount();
        Log.e("Home","abc2");
        adapter = new CustomAdapter(getActivity(), items);
        listView = (ListView) view.findViewById(R.id.listView);
        listView.setAdapter(adapter);
        FloatingActionButton fab = view.findViewById(R.id.floatingActionButton2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent = new Intent(getActivity(), PostActivity.class);
               startActivityForResult(intent,3000);
            }
        });



        if (cursor != null && cursor.getCount() != 0 && count == 0){

            cursor.moveToFirst();
            if(items.size() == 0) {
                    latLng1 = new LatLng(cursor.getDouble(4), cursor.getDouble(5));
                    titles.add(cursor.getString(1));
                    contents.add(cursor.getString(2));
                    images.add(cursor.getBlob(3));
                    latlngs.add(latLng1);
                    addItem(cursor.getString(1), cursor.getString(2), cursor.getBlob(3), latLng1);
                    count++;
                  //  Log.e("Home", "abc1");


                    while (cursor.moveToNext()) {

                        LatLng latlngg = new LatLng(cursor.getDouble(4), cursor.getDouble(5));
                        titles.add(cursor.getString(1));
                        contents.add(cursor.getString(2));
                        images.add(cursor.getBlob(3));
                        latlngs.add(latLng1);
                        addItem(cursor.getString(1), cursor.getString(2), cursor.getBlob(3), latlngg);

                    }
                   //a = cursor.getCount();
                }


        }
            /*for (int i = 0; i < titles.size(); i++) {
                addItem(titles.get(i), contents.get(i), images.get(i), latlngs.get(i));
                Log.e("aa","3");
            }*/

        Log.e("HomeE",Integer.toString(a)+"/"+Integer.toString(b));
        if(a != b) {
        items.clear();
            if (cursor != null && cursor.getCount() != 0 ){

                cursor.moveToFirst();
                if(items.size() == 0) {
                    latLng1 = new LatLng(cursor.getDouble(4), cursor.getDouble(5));
                    titles.add(cursor.getString(1));
                    contents.add(cursor.getString(2));
                    images.add(cursor.getBlob(3));
                    latlngs.add(latLng1);
                    addItem(cursor.getString(1), cursor.getString(2), cursor.getBlob(3), latLng1);

                    //  Log.e("Home", "abc1");


                    while (cursor.moveToNext()) {

                        LatLng latlngg = new LatLng(cursor.getDouble(4), cursor.getDouble(5));
                        titles.add(cursor.getString(1));
                        contents.add(cursor.getString(2));
                        images.add(cursor.getBlob(3));
                        latlngs.add(latLng1);
                        addItem(cursor.getString(1), cursor.getString(2), cursor.getBlob(3), latlngg);

                    }
                    //a = cursor.getCount();
                }


            }



        }
        adapter.notifyDataSetChanged();
            listView.setOnItemClickListener(mItemClickListener);
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
     /*   String title2 = getActivity().getIntent().getExtras().getString("title");
        String content2 = getActivity().getIntent().getExtras().getString("content");
        Uri imageUri2 = getActivity().getIntent().getExtras().getParcelable("image");
        items.add(new Item(title2,content2,imageUri2));
        adapter.notifyDataSetChanged();*/

    }
    public void addItem(String title, String content, byte[] image, LatLng latlng){
       items.add(new Item(title,content,image, latlng));
        adapter.notifyDataSetChanged();
    }

    private AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long l_position) {
            Intent intent2 = new Intent(getActivity(), ViewDetails.class);
            /*Bundle bundle = new Bundle();
           bundle.putString("ClassID","Home");
           bundle.putInt("position",position);
           intent2.putExtras(bundle);*/
            // intent2.putExtra("position",position);
         //   intent2.putExtra("ClassID",class_id);
         //   Toast.makeText(getActivity(),class_id+"1",Toast.LENGTH_SHORT).show();
          //  startActivity(intent2);



        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            switch (requestCode){


                case 3000:
                    SQLiteDatabase mclassDB1 = this.getActivity().openOrCreateDatabase("ItemList.db", MODE_PRIVATE, null);
                    cursor =mclassDB1.rawQuery("SELECT * FROM ITEMLIST", null);
                    cursor.moveToLast();
                    titles.add(cursor.getString(1));
                    contents.add(cursor.getString(2));
                    images.add(cursor.getBlob(3));
                    LatLng lat1 = new LatLng(cursor.getDouble(4),cursor.getDouble(5));
                    latlngs.add(lat1);
                    int k = titles.size();
                    addItem(titles.get(k-1),contents.get(k-1),images.get(k-1),latlngs.get(k-1));
                    count++;
                    Log.e("Home",Integer.toString(cursor.getInt(0)));
                    mclassDB1.close();
                    break;



            }

        }
    }

    public void DeleteList(int position){
        Log.e("a","1");
        items.remove(position) ;
        Log.e("a","12");
        // listview 선택 초기화.
       listView.clearChoices();

        // listview 갱신.
      adapter.notifyDataSetChanged();

    }

}
