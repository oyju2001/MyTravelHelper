package com.example.dsm2016.mytravelhelper;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by dsm2016 on 2018-05-10.
 */

public class Fragment2 extends Fragment implements CustomShareListViewAdapter.ListBtnClickListener2, CustomShareListViewAdapter.ListBtnClickListener3 {
    View view;
    ListView listview_share;
    CustomShareListViewAdapter adapter;
    DatabaseReference mConditionRef;
    DatabaseReference mConditionRef2, mConditionRef3;
    DatabaseReference mDatabase;
    DatabaseReference mDatabase2;
    String GroupCode;
    Button addButton;
    EditText addText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment2, container, false);
        GroupCode = ((GroupActivity)getActivity()).getLocalGroupCode();
        addText = (EditText) view.findViewById(R.id.input_addshareitem);
        addButton = (Button) view.findViewById(R.id.btn_shareadd);
        mConditionRef = FirebaseDatabase.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase2 = mDatabase.child("room").child(GroupCode).child("share");
        mConditionRef2 = FirebaseDatabase.getInstance().getReference();
        mConditionRef3 = mDatabase.child("room").child(GroupCode).child("share");

        adapter = new CustomShareListViewAdapter(this, this);
        listview_share = (ListView) view.findViewById(R.id.listview_share);
        listview_share.setAdapter(adapter);

        //데이터 불러와서 전체출력

        mDatabase.child("room").child(GroupCode).child("share").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                adapter.clear();
                for(DataSnapshot shareData : dataSnapshot.getChildren()){
                    String arr = shareData.getKey();
                    mDatabase2.child(arr).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                DataTwo temp = dataSnapshot.getValue(DataTwo.class);
                                adapter.addItem(temp.getName(), temp.getUser());
                            }

                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        //addButtonEvent
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(addText.getText())){
                    return;
                }
                //db에 넣어준다.
                adapter.addItem(addText.getText().toString(),"X");
                DataTwo temp = new DataTwo(addText.getText().toString(), "X");
                String key = mConditionRef.child("room").child(GroupCode).child("share").push().getKey();
                mConditionRef.child("room").child(GroupCode).child("share").child(key).setValue(temp);

                adapter.notifyDataSetChanged();
                addText.setText("");
            }
        });

        return view;
    }

    @Override
    public void onListBtnClick(int position) {
        //리스트 버튼 누르면 삭제해주는 버튼
        int count = adapter.getCount();
        if(position > -1 && position < count){
            String arr = adapter.deleteItem(position);
            //db에서도 삭제
            mConditionRef2.child("room").child(GroupCode).child("share")
                    .orderByChild("name").equalTo(arr).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String keyvalue="";
                    for(DataSnapshot child : dataSnapshot.getChildren()){
                        keyvalue = child.getKey();
                    }
                    Log.w("aaaa",keyvalue);
                    mConditionRef3.child(keyvalue).removeValue();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onListBtnClick2(int position) {
        //updatd button
        int count = adapter.getCount();
        String arr = null;
        if(position > -1 && position < count) {
            arr = adapter.checkItem(position);
        }
        Intent intent = new Intent(getActivity(), SharePopupActivity.class);
        String[] temp = {GroupCode, arr};
        intent.putExtra("code",temp);
        startActivity(intent);
    }
}
