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
    TextView textView7;
    TextView textView8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_withdrawal);

        btn_withdrawalOk = (Button) findViewById(R.id.btn_withdrawalOk);
        btn_withdrawalNo = (Button) findViewById(R.id.btn_withdrawalNo);
        textView7 = (TextView) findViewById(R.id.textView7);
        textView8 = (TextView) findViewById(R.id.textView8);
    }

    public void WithdrawalOk() {

    }

    public void WithdrawalNo(){

    }
}
