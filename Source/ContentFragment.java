package com.example.myproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import static android.content.Context.MODE_PRIVATE;


public class ContentFragment extends Fragment {
private EditText editText11,editText12;
    View view2,view;
  String title, content;
    Context mContext3;
    TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            ;
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            title= editText11.getText().toString();
            content=editText12.getText().toString();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    public ContentFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       view = inflater.inflate(R.layout.fragment_content, null);
        view2 = view.findViewById(R.id.content_fragment2);
        editText11 = (EditText) view2.findViewById(R.id.editText1);
        editText12 = (EditText) view2.findViewById(R.id.editText2);
        editText11.addTextChangedListener(watcher);
        editText12.addTextChangedListener(watcher);
        // Inflate the layout for this fragment
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event

    @Override
    public void onResume(){
        super.onResume();

    }

    @Override
    public void onPause(){
        super.onPause();


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onStart(){
        super.onStart();

    }

    @Override
    public void onDestroy(){
        super.onDestroy();

    }

}
