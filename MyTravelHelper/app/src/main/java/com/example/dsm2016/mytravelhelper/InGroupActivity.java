package com.example.dsm2016.mytravelhelper;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InGroupActivity extends AppCompatActivity {

    Button btn_okay;
    EditText input_inviteCode;
    AlertDialog.Builder alert;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_group);

        btn_okay = (Button) findViewById(R.id.btn_okay);
        input_inviteCode = (EditText) findViewById(R.id.input_inviteCode);

        alert = new AlertDialog.Builder(this);
        alert.setPositiveButton("확인", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){
                dialog.dismiss();
            }
        });

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void inGroup(View v){
        if(TextUtils.isEmpty(input_inviteCode.getText())){
            alert.setMessage("코드를 입력해주세요");
            alert.show();
            return;
        }
        String tempCode = input_inviteCode.getText().toString();
//
        //db에 tempCode가 있나 확인해주고 (자체적으로 있나 확인하거나 다 불러와서 하나씩 대조. 앞에것이 좋겠지)
        mDatabase.child("room").equalTo(tempCode);
        if(1-0 > 0){   //일치하는게 없을경우
            alert.setMessage("일치하는 방이 없습니다");
            alert.show();
            return;
        }

//
        //일치하는 방이 있을경우 user-그사용자키-myGroup에 그룹을 넣어주고 방 정보에도 유저 넣어준다.
        /*String key = mDatabase.child("user").child(앱사용자의유저ID).child("myGroup").push();
        mDatabase.child("user").child(앱사용자의유저ID).child("myGroup").child(key).child("roomcode").setValue(tempCode);
        String kes = mDatabase.child("room").child(tempCode).child("user").push().getKey();
        mDatabase.child("room").child(tempCode).child("user").child(kes).child("usercode").setValue(앱사용자의유저ID);*/

        //그 방으로 이동해준다.
        Intent intent = new Intent(this, GroupActivity.class);
        intent.putExtra("GroupCode", tempCode);
        startActivity(intent);
        finish();

    }
}
