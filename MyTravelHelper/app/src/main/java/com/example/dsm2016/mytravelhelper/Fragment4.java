package com.example.dsm2016.mytravelhelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

public class Fragment4 extends Fragment{

    View view;
    Button btn_deleteGroup;
    Button btn_showPopup;
    ListView listview_member;
    ArrayList<String> member;
    ArrayAdapter adapter;
    DatabaseReference mDatabase;
    DatabaseReference mDataRef;
    String localCode;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment4, container, false);

        btn_deleteGroup = (Button) view.findViewById(R.id.btn_deleteGroup);
        btn_showPopup = (Button) view.findViewById(R.id.btn_showPopup);
        listview_member = (ListView) view.findViewById(R.id.listview_member);

        btn_showPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CodePopupActivity.class);
                intent.putExtra("code",localCode);
                startActivity(intent);
            }
        });

        //Listview 설정
        member = new ArrayList<String>() ;
        adapter = new ArrayAdapter(getActivity() , android.R.layout.simple_list_item_1, member) ;
        listview_member.setAdapter(adapter);

        //DB 설정
        localCode = ((GroupActivity)getActivity()).getLocalGroupCode().toString();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDataRef = mDatabase.child("room").child(localCode).child("user");
        mDatabase.child("room").child(localCode).child("user").orderByChild("usercode").addValueEventListener(myEvent);

        adapter.notifyDataSetChanged();

        return view;
    }

    ValueEventListener myEvent = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            for(DataSnapshot d : dataSnapshot.getChildren()){
                String arr = d.getKey();
                mDataRef.child(arr).child("usercode").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String temp = dataSnapshot.getValue(String.class);
                        adapter.add(temp);
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
    };
}
