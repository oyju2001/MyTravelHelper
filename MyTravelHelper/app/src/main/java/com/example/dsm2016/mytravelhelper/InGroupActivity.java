package com.example.dsm2016.mytravelhelper;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

public class InGroupActivity extends AppCompatActivity {

    String userID;
    Button btn_okay;
    EditText input_inviteCode;
    AlertDialog.Builder alert;
    DatabaseReference mDatabase;
    DatabaseReference sDatabase;
    DatabaseReference checkDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_group);

        btn_okay = (Button) findViewById(R.id.btn_okay);
        input_inviteCode = (EditText) findViewById(R.id.input_inviteCode);

        userID = "localkey";

        alert = new AlertDialog.Builder(this);
        alert.setPositiveButton("확인", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){
                dialog.dismiss();
            }
        });

        mDatabase = FirebaseDatabase.getInstance().getReference("room");
        sDatabase = FirebaseDatabase.getInstance().getReference();
        checkDatabase = FirebaseDatabase.getInstance().getReference().child("user").child(userID).child("myGroup");
    }

    public void inGroup(View v){
        if(TextUtils.isEmpty(input_inviteCode.getText())){
            alert.setMessage("코드를 입력해주세요");
            alert.show();
            return;
        }
        final String tempCode = input_inviteCode.getText().toString();
//
        //db에 tempCode가 있나 확인해주고
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> child = dataSnapshot.getChildren().iterator();

                while(child.hasNext()){
                    if(child.next().getKey().equals(tempCode)){
                        //tempcode가 있으면 user-myGroup에 있는지 확인
                        checkDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Iterator<DataSnapshot> aa = dataSnapshot.getChildren().iterator();
                                while (aa.hasNext()){
                                    Log.w("aaaaaa",aa.next().child("roomcode").getValue().toString());
                                    /*if(aa.next().child("roomcode").getValue().toString().equals(tempCode)){
                                        return;
                                    }*/
                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

/*
                        //localcode -myGroup과 room-tempcode-user에 넣어주기
                        String key = sDatabase.child("user").child(userID).child("myGroup").push().getKey();
                        sDatabase.child("user").child(userID).child("myGroup").child(key).child("roomcode").setValue(tempCode);
                        String kes = sDatabase.child("room").child(tempCode).child("user").push().getKey();
                        sDatabase.child("room").child(tempCode).child("user").child(kes).child("usercode").setValue(userID);

                        //그 방으로 이동하기
                        Intent intent = new Intent(InGroupActivity.this, GroupActivity.class);
                        intent.putExtra("GroupCode", tempCode);
                        startActivity(intent);
                        finish();
                        return;*/
                    }
                }
                alert.setMessage("일치하는 방이 없습니다");
                alert.show();
                return;

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
//


    }
}
