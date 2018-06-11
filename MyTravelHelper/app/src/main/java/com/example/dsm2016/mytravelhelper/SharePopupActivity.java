package com.example.dsm2016.mytravelhelper;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SharePopupActivity extends Activity {

    String []code;
    Button btn_shareOkay;
    EditText input_share;
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mConditionRef = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_popup);
        Intent intent = getIntent();
        code = intent.getStringArrayExtra("code");

        btn_shareOkay = (Button) findViewById(R.id.btn_shareOkay);
        input_share = (EditText) findViewById(R.id.input_share);

        btn_shareOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(input_share.getText())){
                    return;
                }
                mDatabase.child("room").child(code[0]).child("share").orderByChild("name").equalTo(code[1]).
                        addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String keyvalue="";
                        for(DataSnapshot child : dataSnapshot.getChildren()){
                            keyvalue = child.getKey();
                        }
                        Log.w("aaaa",keyvalue);
                        mConditionRef.child("room").child(code[0]).child("share").child(keyvalue).child("user").setValue(input_share.getText().toString());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                finish();
            }
        });


    }
}
