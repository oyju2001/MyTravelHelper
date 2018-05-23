package com.example.dsm2016.mytravelhelper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by dsm2016 on 2018-05-21.
 */

public class CustomChoiceListViewAdapter extends BaseAdapter implements View.OnClickListener {

    public interface ListBtnClickListener{
        void onListBtnClick(int position);
    }

    private ListBtnClickListener listBtnClickListener;

    public CustomChoiceListViewAdapter(ListBtnClickListener clickListener){
        this.listBtnClickListener = clickListener;

    }

    public ArrayList<Listview_my> listviewItemList = new ArrayList<Listview_my>();



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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_my, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        final TextView itemText = (TextView) convertView.findViewById(R.id.textview_item);
        final TextView checkText = (TextView) convertView.findViewById(R.id.textView_isChecked);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        Listview_my listViewItem = listviewItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        itemText.setText(listViewItem.getText());
        checkText.setText(listViewItem.getIsCheckd());

        Button okButton = (Button)convertView.findViewById(R.id.check);
        okButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(checkText.getText().toString().equals("YES")){
                    Listview_my item = new Listview_my(itemText.getText().toString(),"NO");
                    listviewItemList.set(position, item);
                }else{
                    Listview_my item = new Listview_my(itemText.getText().toString(),"YES");
                    listviewItemList.set(position, item);
                }
                notifyDataSetChanged();
            }
        });

        Button deleteButton = (Button)convertView.findViewById(R.id.delete);
        deleteButton.setTag(position);
        deleteButton.setOnClickListener(this);

        return convertView;
        //출처: http://recipes4dev.tistory.com/68 [개발자를 위한 레시피]
    }
    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(String text) {
        Listview_my item = new Listview_my();
        item.setText(text);
        item.setIsCheckd("NO");

        listviewItemList.add(item);
    }

    public void deleteItem(int position){
        listviewItemList.remove(position);
    }

    public void onClick(View v){
        // ListBtnClickListener(MainActivity)의 onListBtnClick() 함수 호출.
        if (this.listBtnClickListener != null) {
            this.listBtnClickListener.onListBtnClick((int)v.getTag()) ;
        }

    }
}
