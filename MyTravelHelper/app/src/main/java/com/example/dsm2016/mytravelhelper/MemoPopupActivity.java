package com.example.dsm2016.mytravelhelper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by dsm2016 on 2018-05-21.
 */

public class MemoPopupActivity extends Activity {

    EditText editText;
    Button button;
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_memo_popup);
        Intent intent = getIntent();
        key = intent.getExtras().getString("key");

        editText = (EditText) findViewById(R.id.input_memo);
        button = (Button) findViewById(R.id.btn_okay);

        button.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(editText.getText())){
                    return;
                }
                String arr = editText.getText().toString();
                String local = mDatabase.child("room").child(key).child("memo").push().getKey();
                mDatabase.child("room").child(key).child("memo").child(local).child("text").setValue(arr);
                finish();
            }
        });


    }

}
