package com.example.dsm2016.mytravelhelper;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Created by dsm2016 on 2018-05-10.
 */

public class Fragment3 extends Fragment implements CustomChoiceListViewAdapter.ListBtnClickListener{

    View view;
    CustomChoiceListViewAdapter adapter;
    ListView listView;
    Button addButton;
    EditText addText;
    DBHelper dbHelper;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment3, container, false);
        addText = (EditText) view.findViewById(R.id.input_additem);

        adapter = new CustomChoiceListViewAdapter(this);
        listView = (ListView) view.findViewById(R.id.listview_my);
        listView.setAdapter(adapter);

        //local DB
        String localCode = ((GroupActivity)getActivity()).getLocalGroupCode().toUpperCase().substring(1);
        dbHelper = new DBHelper(getContext(), localCode+".db",null,1,localCode);

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String result = "";
        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM "+localCode, null);
        while (cursor.moveToNext()) {
            adapter.addItem(cursor.getString(1),cursor.getString(2));
        }
        adapter.notifyDataSetChanged();

        //addButton event
        addButton = (Button) view.findViewById(R.id.btn_add);
        addButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(addText.getText())){
                    return;
                }
                dbHelper.insert(addText.getText().toString(), "NO");
                adapter.addItem(addText.getText().toString(),"NO");
                adapter.notifyDataSetChanged();
                addText.setText("");
            }
        });

        return view;
    }

    @Override
    public void onListBtnClick(int position) {
        //Toast.makeText(view.getContext(), Integer.toString(position+1) + "item selected", Toast.LENGTH_SHORT).show();
        int count = adapter.getCount();
        if(position > -1 && position < count){
            String arr = adapter.deleteItem(position);
            dbHelper.delete(arr);
        }
        adapter.notifyDataSetChanged();
    }
}
