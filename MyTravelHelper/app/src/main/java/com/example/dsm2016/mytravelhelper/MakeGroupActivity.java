package com.example.dsm2016.mytravelhelper;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MakeGroupActivity extends AppCompatActivity {

    EditText input_groupName;
    EditText input_placeName;
    Button btn_makeGroup;
    DatePicker dp_startDay;
    DatePicker dp_endDay;
    AlertDialog.Builder alert;
    Date date;
    SimpleDateFormat nowTime;

    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_group);

        input_groupName = (EditText) findViewById(R.id.input_groupName);
        input_placeName = (EditText) findViewById(R.id.input_placeName);
        btn_makeGroup = (Button) findViewById(R.id.btn_makeGroup);
        dp_startDay = (DatePicker) findViewById(R.id.date_picker);
        dp_endDay = (DatePicker) findViewById(R.id.date_picker2);

        alert = new AlertDialog.Builder(this);
        alert.setPositiveButton("확인", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){
                dialog.dismiss();
            }
        });

        date = new Date();
        nowTime = new SimpleDateFormat("yyyy-MM-dd");
    }

    public void makeGroup(View view) {
        //형식에 맞나 체크
        if(TextUtils.isEmpty(input_groupName.getText())){
            alert.setMessage("방 이름을 입력해주세요");
            alert.show();
            return;
        }else if(TextUtils.isEmpty(input_placeName.getText())){
            alert.setMessage("여행 장소를 입력해주세요.");
            alert.show();
            return;
        }else if(DateCheck()){
            alert.setMessage("과거로 여행할 수 없습니다..");
            alert.show();
            return;
        }else if(DateCheck2()){
            alert.setMessage("여행 날짜가 알맞지 않습니다.");
            alert.show();
            return;
        }

        //코드발급
        //데이터베이스에 default 추가
        String key = mDatabase.child("room").push().getKey();

        StringBuilder start = new StringBuilder();
        start.append(dp_startDay.getYear()+"/");
        start.append((dp_startDay.getMonth()+1)+"/");
        start.append(dp_startDay.getDayOfMonth());
        String startDate = start.toString();
        start.setLength(0);
        start.append(dp_endDay.getYear() + "/");
        start.append((dp_endDay.getMonth()+1)+"/");
        start.append(dp_endDay.getDayOfMonth());
        String endDate = start.toString();

        DataOne dataOne = new DataOne(input_groupName.getText().toString(), input_placeName.getText().toString(),startDate, endDate);
        mDatabase.child("room").child(key).child("default").setValue(dataOne);

        //유저에 룸 코드 등록
        String s = mDatabase.child("user").child("localkey").child("myGroup").push().getKey();
        mDatabase.child("user").child("localkey").child("myGroup").child(s).child("roomcode").setValue(key);

        //DB에 유저등록
        String kes = mDatabase.child("room").child(key).child("user").push().getKey();
        mDatabase.child("room").child(key).child("user").child(kes).child("usercode").setValue("localkey");

        //초대코드 팝업 띄움
        Intent intent = new Intent(this, CodePopupActivity.class);
        intent.putExtra("code",key);
        startActivity(intent);
        finish();

    }

    private boolean DateCheck(){ //과거일경우
        String time = nowTime.format(date);
        String times[] = time.split("-");

        if(Integer.parseInt(times[0]) > dp_startDay.getYear()){
            //년도가 과거
           return true;
        }else if(Integer.parseInt(times[0]) == dp_startDay.getYear()) {
            if(Integer.parseInt(times[1]) > dp_startDay.getMonth()+1){
                return true;
            }else if(Integer.parseInt(times[1]) == dp_startDay.getMonth()+1) {
                if (Integer.parseInt(times[2]) > dp_startDay.getDayOfMonth()) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean DateCheck2(){
        if(dp_startDay.getYear() > dp_endDay.getYear()){
            //끝나는 연도가 빠를경우
            return true;
        }else if(dp_startDay.getYear() == dp_endDay.getYear()){
            //년도가 같을때
            if(dp_startDay.getMonth() > dp_endDay.getMonth() ){
                //끝나는 월이 빠를경우
                return true;
            }else if(dp_startDay.getMonth() == dp_endDay.getMonth()){
                //월이 같을때
                if(dp_startDay.getDayOfMonth() > dp_endDay.getDayOfMonth()){
                    //끝나는 일이 빠를경우
                    return true;
                }
            }
        }
        return false;
    }






}
