package com.example.dsm2016.mytravelhelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SignInActivity extends AppCompatActivity {

    TextView textView2;
    Button btn_signInActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        textView2 = (TextView) findViewById(R.id.textView2);
        btn_signInActivity = (Button) findViewById(R.id.btn_signInActivity);
    }

    public void SighInActivity(View view){
      //  Intent intent = new Intent(this, );
      //  startActivity(intent);
    }
}
