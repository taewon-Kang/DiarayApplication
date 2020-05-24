package com.example.myproject;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.kakao.kakaolink.v2.KakaoLinkResponse;
import com.kakao.kakaolink.v2.KakaoLinkService;
import com.kakao.message.template.ButtonObject;
import com.kakao.message.template.ContentObject;
import com.kakao.message.template.LinkObject;
import com.kakao.message.template.ListTemplate;
import com.kakao.message.template.LocationTemplate;
import com.kakao.network.ErrorResult;
import com.kakao.network.callback.ResponseCallback;
import com.kakao.util.helper.log.Logger;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class Details extends Fragment {
    TextView textView,textView1;
    Button button, button1;
    Cursor cursor;
    SQLiteDatabase mclassDB1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.activity_details, null);
        mclassDB1 = this.getActivity().openOrCreateDatabase("ItemList.db", MODE_PRIVATE, null);
        cursor =mclassDB1.rawQuery("SELECT * FROM ITEMLIST", null);
        textView = (TextView) view.findViewById(R.id.textView);
        textView1 = (TextView) view.findViewById(R.id.textView3);

        Button button = (Button) view.findViewById(R.id.button2);
         Button button2 = (Button) view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String findF = textView.getText().toString();
                String findC = textView1.getText().toString();
                cursor.moveToFirst();
                boolean ComF = findF.equals(cursor.getString(1));
                boolean ComC = findC.equals(cursor.getString(2));
                String string = Integer.toString(cursor.getPosition());
                String string2 = Integer.toString(cursor.getInt(0));
                Intent intent = new Intent(getContext(), MainActivity.class);

                int id;
                int id2 = cursor.getInt(0);
                //String st = Integer.toString(id);
               // String st2 = Integer.toString(id2);
                if (findF.equals(cursor.getString(1))&&findC.equals(cursor.getString(2))) {
                    id = cursor.getInt(0);
                  //  Log.e("Detailsa",st);
                   // Log.e("Detailsa",st2);
                    mclassDB1.delete("ITEMLIST",  "_id" + "=" + id, null);
                    intent.putExtra("position",500);
                    startActivity(intent);
                }
                else {
                    while (cursor.moveToNext()) {
                    //    Log.e("a",findF +" / "+cursor.getString(1));

                        if (findF.equals(cursor.getString(1))&&findC.equals(cursor.getString(2))) {
                            id = cursor.getInt(0);
                         //   Log.e("Details",st);
                          //  Log.e("Details",st2);
                            mclassDB1.delete("ITEMLIST",  "_id" + "=" + id, null);
                            intent.putExtra("position",500);
                            startActivity(intent);
                        }

                    }
                }
                ;

                mclassDB1.close();
                getActivity().finish();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = textView.getText().toString();
                String content = textView1.getText().toString();
                String address, ctitle;
                Double lat1, lng1;
                cursor.moveToFirst();
                if (title.equals(cursor.getString(1)) && content.equals(cursor.getString(2))) {
                    ctitle = title;
                    lat1 = cursor.getDouble(4);
                    lng1 = cursor.getDouble(5);
                    address = getAddress(lat1, lng1);
                    onClickSendLink(address, ctitle);
                } else {
                    while (cursor.moveToNext()) {
                        //    Log.e("a",findF +" / "+cursor.getString(1));

                        if (title.equals(cursor.getString(1)) && content.equals(cursor.getString(2))) {
                            ctitle = title;
                            lat1 = cursor.getDouble(4);
                            lng1 = cursor.getDouble(5);
                            address = getAddress(lat1, lng1);
                            onClickSendLink(address, ctitle);
                        }
                    }
                }
                mclassDB1.close();
                getActivity().finish();
            }

        });

        return view;


    }

    public void SetText(int position){
        cursor.moveToPosition(position);
        textView.setText(cursor.getString(1));
        textView1.setText(cursor.getString(2));

    }

    public void SetTextlatlng(double latitude, double longitude) {
        cursor.moveToFirst();
        if (latitude == cursor.getDouble(4) && longitude == cursor.getDouble(5)) {
            textView.setText(cursor.getString(1));
            textView1.setText(cursor.getString(2));
        } else {
            while (cursor.moveToNext()) {
                if (latitude == cursor.getDouble(4) && longitude == cursor.getDouble(5)) {
                    textView.setText(cursor.getString(1));
                    textView1.setText(cursor.getString(2));
                }
            }
        }
    }

  /*  public void deleteItem(View v) {
        String findF = textView.getText().toString();
        String findC = textView1.getText().toString();
        Intent intent = new Intent(getContext(), MainActivity.class);
        cursor.moveToFirst();
        boolean ComF = findF.equals(cursor.getString(1));
        boolean ComC = findC.equals(cursor.getString(2));
        if (ComF && ComC) {
            int id = cursor.getCount();
            mclassDB1.execSQL("DELETE FROM ITEMLIST WHERE id=" + id + ";");
            intent.putExtra("position",id);
            startActivity(intent);
        } else {
            while (cursor.moveToNext()) {
                if (ComF && ComC) {
                    int id = cursor.getCount();
                    mclassDB1.execSQL("DELETE FROM ITEMLIST WHERE id=" + id + ";");

                    intent.putExtra("position",id);
                    startActivity(intent);
                }

            }
        }



    }*/


    public static Fragment newInstance(int param1) {
        Details fragment = new Details();
        Bundle args = new Bundle();
        args.putInt("position",param1);
        fragment.setArguments(args);
        return fragment;
    }


    public void onClickSendLink(String address,String title) {
        LocationTemplate params = LocationTemplate.newBuilder(address,
                ContentObject.newBuilder(title,
                        "http://www.kakaocorp.com/images/logo/og_daumkakao_151001.png",
                        LinkObject.newBuilder()
                                .setWebUrl("https://developers.kakao.com")
                                .setMobileWebUrl("https://developers.kakao.com")
                                .build())
                        .setDescrption(title+" 위치입니다.")
                        .build())
                .setAddressTitle(title)
                .build();

        Map<String, String> serverCallbackArgs = new HashMap<String, String>();
        serverCallbackArgs.put("user_id", "${current_user_id}");
        serverCallbackArgs.put("product_id", "${shared_product_id}");

        KakaoLinkService.getInstance().sendDefault(getContext(), params, serverCallbackArgs, new ResponseCallback<KakaoLinkResponse>() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                Logger.e(errorResult.toString());
            }

            @Override
            public void onSuccess(KakaoLinkResponse result) {
                // 템플릿 밸리데이션과 쿼터 체크가 성공적으로 끝남. 톡에서 정상적으로 보내졌는지 보장은 할 수 없다. 전송 성공 유무는 서버콜백 기능을 이용하여야 한다.
            }
        });
    }

public String getAddress(double lat, double lng){
        String address =null;
    Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
    List<Address> list = null;
    try{
        list = geocoder.getFromLocation(lat, lng, 1);
    } catch (IOException e){
        e.printStackTrace();
    }
    if(list == null){
        Log.e("getAddress","주소데이터 에러");
        return null;
    }
    if (list.size() > 0){
        Address addr = list.get(0);
        address = addr.getCountryName() + " "
                + addr.getAdminArea() + " "
                + addr.getLocality() + " "
                + addr.getThoroughfare() + " "
                + addr.getFeatureName();

    }
    return address;
    }



}

