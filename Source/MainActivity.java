package com.example.myproject;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import static java.sql.Types.NULL;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    public static Context mContext;

    // FrameLayout에 각 메뉴의 Fragment를 바꿔 줌
    private FragmentManager fragmentManager = getSupportFragmentManager();
    // 4개의 메뉴에 들어갈 Fragment들
    private HomeFragment HomeFragment1 = new HomeFragment();
    private DashboardFragment DashboardFragment1 = new DashboardFragment();
    private Menu3Fragment menu3Fragment = new Menu3Fragment();
    SQLiteDatabase db;
    int position2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        DBHelper dbHelper = new DBHelper(getApplicationContext(), "ItemList.db", null, 1);
        db = dbHelper.getWritableDatabase();
       // db.execSQL("DELETE FROM ITEMLIST");
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        // 첫 화면 지정
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_layout, HomeFragment1).commitAllowingStateLoss();

        // bottomNavigationView의 아이템이 선택될 때 호출될 리스너 등록
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                switch (item.getItemId()) {
                    case R.id.navigation_menu1: {
                        transaction.replace(R.id.frame_layout, HomeFragment1).commitAllowingStateLoss();
                        break;
                    }
                    case R.id.navigation_menu2: {
                        transaction.replace(R.id.frame_layout, DashboardFragment1).commitAllowingStateLoss();
                        break;
                    }
                    case R.id.navigation_menu3: {
                        transaction.replace(R.id.frame_layout, menu3Fragment).commitAllowingStateLoss();
                        break;
                    }
                }

                return true;
            }
        });


    }

    @Override
    public void onItemClick(AdapterView<?> parent,
                            View view, int position, long id){

        position2 = position;
      Intent intent2 = new Intent(this, ViewDetails.class);
       Bundle bundle = new Bundle();
       bundle.putInt("position", position);
       bundle.putString("ClassID","Home");
       intent2.putExtras(bundle);
       startActivity(intent2);


    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        db.close();
    }

    @Override
    public void onResume(){
        super.onResume();
        Intent intent = getIntent();
        int position = intent.getIntExtra("position",1000);


        HomeFragment fragment = (HomeFragment) getSupportFragmentManager().findFragmentById(R.id.home_frag);

        if(position == 500) {


      /*  Intent intent3 = new Intent(this, HomeFragment.class);
        intent3.putExtra("data",500);
        startActivity(intent3);*/

        }
    }

}
