package com.example.dsm2016.mytravelhelper;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by dsm2016 on 2018-05-10.
 */

public class GroupActivity extends AppCompatActivity implements Button.OnClickListener{
    //http://wimir-dev.tistory.com/13

    DatabaseReference mConditionRef;
    private String localGroupCode;
    public String localGroupName;
    public String localPlaceName;
    public String localStartDate;
    public String localEndDate;

    Button button[];
    Button bt1,bt2,bt3,bt4;
    View view[];
    View v1,v2,v3,v4;
    FragmentManager fm;
    FragmentTransaction tran;
    Fragment1 frag1;
    Fragment2 frag2;
    Fragment3 frag3;
    Fragment4 frag4;

    public String getLocalGroupCode(){
        return localGroupCode;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticvity_group);

        //groupActivity는 intent 될때 무조건 code를 받아온다.

        Intent intent = getIntent();
        localGroupCode =  intent.getExtras().getString("GroupCode");

        bt1 = (Button) findViewById(R.id.bt1);
        bt2 = (Button) findViewById(R.id.bt2);
        bt3 = (Button) findViewById(R.id.bt3);
        bt4 = (Button) findViewById(R.id.bt4);
        button = new Button[]{bt1, bt2, bt3, bt4};
        button[0].setOnClickListener(this);
        button[1].setOnClickListener(this);
        button[2].setOnClickListener(this);
        button[3].setOnClickListener(this);
        frag1 = new Fragment1();
        frag2 = new Fragment2();
        frag3 = new Fragment3();
        frag4 = new Fragment4();
        v1 = findViewById(R.id.view1);
        v2 = findViewById(R.id.view2);
        v3 = findViewById(R.id.view3);
        v4 = findViewById(R.id.view4);
        view = new View[]{v1,v2,v3,v4};

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mConditionRef = mDatabase.child("room").child(localGroupCode).child("default");
        mConditionRef.addValueEventListener(defaultEvent);

    }

    ValueEventListener defaultEvent = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            DataOne please = dataSnapshot.getValue(DataOne.class);
            localGroupName = please.getName();
            localPlaceName = please.getPlace();
            localStartDate = please.getStartDate();
            localEndDate = please.getEndDate();
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    public void onClick(View v){
        switch (v.getId()){
            case R.id.bt1:
                setDesign(0);
                setFrag(0);
                break;
            case R.id.bt2:
                setDesign(1);
                setFrag(1);
                break;
            case R.id.bt3:
                setDesign(2);
                setFrag(2);
                break;
            case R.id.bt4:
                setDesign(3);
                setFrag(3);
                break;
            default :
                setDesign(0);
                setFrag(0);
        }
    }
    public void setFrag(int n){    //프래그먼트를 교체하는 작업을 하는 메소드
        fm = getFragmentManager();
        tran = fm.beginTransaction();
        switch (n){
            case 0:
                tran.replace(R.id.main_frame, frag1);
                tran.commit();
                break;
            case 1:
                tran.replace(R.id.main_frame, frag2);
                tran.commit();
                break;
            case 2:
                tran.replace(R.id.main_frame, frag3);
                tran.commit();
                break;
            case 3:
                tran.replace(R.id.main_frame, frag4);
                tran.commit();
                break;
        }
    }

    public void setDesign(int i){
        for(int a=0; a<4; a++){
            if(a == i){
                button[a].setTextColor(Color.BLACK);
                view[a].setVisibility(View.VISIBLE);
            }else {
                button[a].setTextColor(Color.GRAY);
                view[a].setVisibility(View.INVISIBLE);
            }
        }
    }



}
