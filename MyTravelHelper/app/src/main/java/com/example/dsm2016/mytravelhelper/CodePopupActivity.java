package com.example.dsm2016.mytravelhelper;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CodePopupActivity extends Activity {
    TextView text_inviteCode;
    Button btn_okay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_code_popup);

        Intent intent = getIntent();
        String code = intent.getExtras().getString("code");

        text_inviteCode = (TextView) findViewById(R.id.text_inviteCode);
        btn_okay = (Button) findViewById(R.id.btn_okay);

        text_inviteCode.setText(code);


    }

    public void okButton(){

        // 방으로 이동

        Intent intent = new Intent(this, CodePopupActivity.class);
        intent.putExtra("code",text_inviteCode.getText().toString());
        startActivity(intent);

        /*ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("label", text_inviteCode.getText().toString());
        clipboardManager.setPrimaryClip(clipData);

        Toast.makeText(this,text_inviteCode.getText().toString(),Toast.LENGTH_SHORT).show();*/

    }
}



