package com.example.dsm2016.mytravelhelper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by dsm2016 on 2018-05-22.
 */

public class CustomMainListViewAdapter  extends BaseAdapter {

    private ArrayList<DataOne> listViewItemList = new ArrayList<DataOne>();

    public CustomMainListViewAdapter (){}

    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position);
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
            convertView = inflater.inflate(R.layout.listview_main, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView groupName = (TextView) convertView.findViewById(R.id.groupName) ;
        TextView placeName = (TextView) convertView.findViewById(R.id.placeName) ;
        TextView startDate = (TextView) convertView.findViewById(R.id.startDate) ;
        TextView endDate = (TextView) convertView.findViewById(R.id.endDate) ;

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        DataOne listViewItem = listViewItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        groupName.setText(listViewItem.getName());
        placeName.setText(listViewItem.getPlace());
        startDate.setText(listViewItem.getStartDate());
        endDate.setText(listViewItem.getEndDate());

        return convertView;
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(String a, String b, String c, String d,String f) {
        DataOne item = new DataOne();

        item.setName(a);
        item.setPlace(b);
        item.setStartDate(c);
        item.setEndDate(d);
        item.setCode(f);

        listViewItemList.add(item);
    }

    public void addItem(DataOne a){
        listViewItemList.add(a);
    }

    //리스트 초기화해주는 함수
    public void clear(){
        listViewItemList.clear();
    }



}
