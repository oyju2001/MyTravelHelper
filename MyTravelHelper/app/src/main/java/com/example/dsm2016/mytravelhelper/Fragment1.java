package com.example.dsm2016.mytravelhelper;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by dsm2016 on 2018-05-10.
 */

public class Fragment1 extends Fragment {
    View view;
    TextView groupName, placeName, startDate, endDate;
    String GroupCode;
    DatabaseReference mConditionRef;
    ArrayList<String> items;
    ArrayAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment1, container, false);
        GroupCode = ((GroupActivity)getActivity()).getLocalGroupCode();

        groupName = (TextView) view.findViewById(R.id.groupName );
        placeName = (TextView) view.findViewById(R.id.placeName);
        startDate = (TextView) view.findViewById(R.id.startDate );
        endDate = (TextView) view.findViewById(R.id.endDate);

        items = new ArrayList<String>() ;
        adapter = new ArrayAdapter(getActivity() , android.R.layout.simple_list_item_single_choice, items) ;

        final ListView listview = (ListView) view.findViewById(R.id.listview_memo) ;
        listview.setAdapter(adapter) ;

        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mConditionRef = mDatabase.child("room").child(GroupCode).child("memo");
        mConditionRef.addValueEventListener(memoEvent);

        Button addButton = (Button)view.findViewById(R.id.btn_add) ;
        addButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MemoPopupActivity.class);
                intent.putExtra("key",GroupCode);
                startActivity(intent);
            }
        }) ;

        // delete button에 대한 이벤트 처리.
        Button deleteButton = (Button)view.findViewById(R.id.btn_delete) ;
        deleteButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                int checked = listview.getCheckedItemPosition();
                String gets = items.get(checked);
                listview.clearChoices();

                mDatabase.child("room").child(GroupCode).child("memo").orderByChild("text")
                        .equalTo(gets).addListenerForSingleValueEvent(eee);

            }
        }) ;

        groupName.setText(((GroupActivity)getActivity()).localGroupName);
        placeName.setText(((GroupActivity)getActivity()).localPlaceName);
        startDate.setText(((GroupActivity)getActivity()).localStartDate);
        endDate.setText(((GroupActivity)getActivity()).localEndDate);


        return view;
    }

    ValueEventListener eee = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            String keyvalue="";
            for(DataSnapshot child : dataSnapshot.getChildren()){
                keyvalue = child.getKey();
            }
            mConditionRef.child(keyvalue).child("text").removeValue();


        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    ValueEventListener memoEvent = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            adapter.clear();
            if(dataSnapshot.exists()){
                for(DataSnapshot messageData : dataSnapshot.getChildren()){
                    String arr = messageData.child("text").getValue().toString();
                    items.add(arr);
                }
            }
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };


}
