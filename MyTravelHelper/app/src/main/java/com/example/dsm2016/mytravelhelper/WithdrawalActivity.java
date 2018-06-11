package com.example.dsm2016.mytravelhelper;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class WithdrawalActivity extends Activity {
    Button btn_withdrawalOk;
    Button btn_withdrawalNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_withdrawal);

        btn_withdrawalNo = (Button) findViewById(R.id.btn_withdrawalNo);
        btn_withdrawalNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_withdrawalOk = (Button) findViewById(R.id.btn_withdrawalOk);
        btn_withdrawalOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //DB에서 사용자 계정삭제
                //Room에서 사용자 있는거 삭제
                //LocalDB 사용자 삭제
            }
        });

    }

}
