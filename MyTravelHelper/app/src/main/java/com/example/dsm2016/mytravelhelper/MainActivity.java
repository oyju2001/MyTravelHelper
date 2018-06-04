package com.example.dsm2016.mytravelhelper;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DatabaseReference mConditionRef;
    CustomMainListViewAdapter adapter;
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

        localUserCode = "localkey";
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("user").child(localUserCode).child("myGroup").orderByChild("roomcode").startAt("-").addValueEventListener(mylistEvent);
        mConditionRef = FirebaseDatabase.getInstance().getReference();

        adapter = new CustomMainListViewAdapter();
        myGroupListview = (ListView) findViewById(R.id.myGroupListview);
        myGroupListview.setAdapter(adapter);

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

    ValueEventListener mylistEvent = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            //어찌저찌해서 code를 불러온다.
            if(dataSnapshot.exists()){
                adapter.clear();
                for(DataSnapshot messageData : dataSnapshot.getChildren()){
                    String a = messageData.getKey();
                    mConditionRef.child("user").child("localkey").child("myGroup").child(a).child("roomcode")
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            final String real = dataSnapshot.getValue(String.class).toString();

                            mConditionRef.child("room").child(real).child("default")
                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            DataOne a = dataSnapshot.getValue(DataOne.class);
                                            Log.w("messaga", a.getName());
                                            adapter.addItem(a.getName(), a.getPlace(), a.getStartDate(), a.getEndDate(), real);
                                            adapter.notifyDataSetChanged();
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

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
        Intent intent = new Intent(this, InGroupActivity.class);
        startActivity(intent);
    }

    public void makeGroupActivity(View view){
        Intent intent = new Intent(this, MakeGroupActivity.class);
        startActivity(intent);

    }
}
