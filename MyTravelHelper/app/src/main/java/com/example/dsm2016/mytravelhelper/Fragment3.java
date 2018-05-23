package com.example.dsm2016.mytravelhelper;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

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
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment3, container, false);
        addText = (EditText) view.findViewById(R.id.input_additem);

        adapter = new CustomChoiceListViewAdapter(this);

        listView = (ListView) view.findViewById(R.id.listview_my);
        listView.setAdapter(adapter);

        //addButton event
        addButton = (Button) view.findViewById(R.id.btn_add);
        addButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(addText.getText())){
                    return;
                }
                adapter.addItem(addText.getText().toString());
                adapter.notifyDataSetChanged();
                addText.setText("");
            }
        });



        //아이템 추가
        adapter.addItem("바디워시");
        adapter.addItem("샴푸");
        adapter.addItem("돈");
        adapter.addItem("지도");

        return view;
    }

    @Override
    public void onListBtnClick(int position) {
        //Toast.makeText(view.getContext(), Integer.toString(position+1) + "item selected", Toast.LENGTH_SHORT).show();
        int count = adapter.getCount();
        if(position > -1 && position < count){
            adapter.deleteItem(position);
        }
        adapter.notifyDataSetChanged();
    }
}
