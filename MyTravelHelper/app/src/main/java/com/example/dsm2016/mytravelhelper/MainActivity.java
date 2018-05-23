package com.example.dsm2016.mytravelhelper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DatabaseReference mConditionRef;
    CustomMainListViewAdapter adapter;
    ArrayList<String> userRoom;
    ListView myGroupListview;
    Button btn_inGroupActivity;
    Button btn_makeGroupActivity;
    String localUserCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //위는 navigationbar 설정

        btn_inGroupActivity = (Button) findViewById(R.id.btn_inGroupActivity);
        btn_makeGroupActivity = (Button) findViewById(R.id.btn_makeGroupActivity);
        userRoom = new ArrayList<String>();

        //final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        //mConditionRef = mDatabase.child("user").child(localUserCode).child("myGroup").;
        //mConditionRef.addValueEventListener(mylistEvent);


        adapter = new CustomMainListViewAdapter();
        myGroupListview = (ListView) findViewById(R.id.myGroupListview);
        myGroupListview.setAdapter(adapter);

        adapter.addItem("1","2","3","4","f");
        adapter.addItem("a","b","c","d","-LD3SFEcBZpvcZiZyV97");

        adapter.notifyDataSetChanged();

        //리스트뷰 눌렀을때
        myGroupListview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DataOne item = (DataOne) parent.getItemAtPosition(position);
                String arr = item.getCode();
                Intent intent = new Intent(MainActivity.this, GroupActivity.class);
                intent.putExtra("GroupCode",arr);
                startActivity(intent);
            }
        });

    }

//
/*
    ValueEventListener mylistEvent = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            userRoom.clear();
            if(dataSnapshot.exists()){
                for(DataSnapshot messageData : dataSnapshot.getChildren()){
                    String arr = messageData.getValue().toString();
                    //어찌저찌해서 code를 불러온다.
                    userRoom.add(arr);
                }
            }
            //list의 값을 바꿔주는 함수를 불러온다.
            changeListview();
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };*/
/*
    public void changeListview(){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        for(String localGroupCode : userRoom){
            mConditionRef = mDatabase.child("room").child(localGroupCode).child("default");
            mConditionRef.addValueEventListener(defaultEvent);
            //한번만 불러오면 되서 valueEventListener 필요x... 위에 mylistEvent에서 변경 모두 감지 (default는 변경하지 않으니까)
        }
        adapter.notifyDataSetChanged();
    }

    ValueEventListener defaultEvent = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            adapter.clear();
            if (dataSnapshot.exists()) {
                DataOne please = dataSnapshot.getValue(DataOne.class);
                please.setCode(localGroupCode);
                adapter.addItem(please);
                //DataOne에 code에 localcode를 넣어주고
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };*/
//

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_my_data) {
            //내 정보보기 눌렀을 때
        } else if (id == R.id.nav_withdrawal ) {
            //탈퇴하기 눌렀을 때
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void inGroupActivity(View view){
        Intent intent = new Intent(this, GroupActivity.class);
        intent.putExtra("GroupCode","-LD3SFEcBZpvcZiZyV97");
        startActivity(intent);
    }

    public void makeGroupActivity(View view){
        Intent intent = new Intent(this, MakeGroupActivity.class);
        startActivity(intent);

    }
}
