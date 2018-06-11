package com.example.dsm2016.mytravelhelper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DeleteRoomActivity extends AppCompatActivity {

    Button btn_deleteGroupOk;
    Button btn_deleteGroupNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_room);

        btn_deleteGroupNo = (Button) findViewById(R.id.btn_deleteGroupNo);
        btn_deleteGroupNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_deleteGroupOk = (Button) findViewById(R.id.btn_deleteGroupOk);
        btn_deleteGroupOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //localDB 개인준비물 삭제
                //DB 사용자들에 있는 room들 모두 삭제
                //room 삭제
                //
            }
        });
    }
}
