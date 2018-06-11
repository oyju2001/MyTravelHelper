package com.example.dsm2016.mytravelhelper;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomShareListViewAdapter extends BaseAdapter implements View.OnClickListener {

    public interface ListBtnClickListener2{
        void onListBtnClick(int position);
    }

    public interface ListBtnClickListener3{
        void onListBtnClick2(int position);
    }

    private ListBtnClickListener2 listBtnClickListener2;
    public ListBtnClickListener3 listBtnClickListener3;

    public CustomShareListViewAdapter (ListBtnClickListener2 clickListener2, ListBtnClickListener3 listBtnClickListener3){
        this.listBtnClickListener2 = clickListener2;
        this.listBtnClickListener3 = listBtnClickListener3;
    }

    public ArrayList<DataTwo> listviewItemList = new ArrayList<DataTwo>();
    @Override
    public int getCount() {
        return listviewItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return listviewItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_share, parent, false);
        }

        final TextView itemText = (TextView) convertView.findViewById(R.id.textview_shareitem);
        final TextView userText = (TextView) convertView.findViewById(R.id.textView_user);

        //아이템 반영
        DataTwo listviewItem = listviewItemList.get(position);
        itemText.setText(listviewItem.getName());
        userText.setText(listviewItem.getUser());

        Button updateButton = (Button)convertView.findViewById(R.id.updateshare);
        Button deleteButton = (Button)convertView.findViewById(R.id.deleteshare);
        deleteButton.setTag(position);
        deleteButton.setOnClickListener(this);
        updateButton.setTag(position);
        updateButton.setOnClickListener(this);

        return convertView;
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(String text, String text2) {
        DataTwo item = new DataTwo();
        item.setName(text);
        item.setUser(text2);

        listviewItemList.add(item);
    }

    public String deleteItem(int position){
        String arr = listviewItemList.get(position).getName();
        listviewItemList.remove(position);
        return arr;
    }

    public String checkItem(int position){
        String arr = listviewItemList.get(position).getName();
        return arr;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.deleteshare :
                if(this.listBtnClickListener2 != null){
                    this.listBtnClickListener2.onListBtnClick((int)v.getTag());
                }
                break;
            case R.id.updateshare :
                if(this.listBtnClickListener3 != null){
                    this.listBtnClickListener3.onListBtnClick2((int)v.getTag());
                }
                break;
        }

    }

    public void clear(){
        listviewItemList.clear();
    }
}
