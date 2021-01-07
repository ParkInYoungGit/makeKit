package com.example.makekit.makekit_adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.makekit.makekit_bean.ChattingBean;

import java.util.ArrayList;

public class ChattingListAdapter extends BaseAdapter {

    Context mContext = null;
    int layout = 0;
    ArrayList<ChattingBean> data = null;
    LayoutInflater inflater = null;

    public ChattingListAdapter(Context mContext, int layout, ArrayList<ChattingBean> data) {
        this.mContext = mContext;
        this.layout = layout;
        this.data = data;
        this.inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position); ///////////////////////////////////////
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(this.layout, parent, false);
        }



        return convertView;
    }
}
