package com.example.makekit.makekit_adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.makekit.R;
import com.example.makekit.makekit_bean.User;

import java.util.ArrayList;

public class NoticeAdapter extends BaseAdapter {

    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<User> sample;

    public NoticeAdapter(Context context, ArrayList<User> data) {
        mContext = context;
        sample = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return sample.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public User getItem(int position) {
        return sample.get(position);
    }

    @Override
    public View getView(int position, View converView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.custom_notice, null);


        TextView content = (TextView)view.findViewById(R.id.content_notice);
        TextView date = (TextView)view.findViewById(R.id.date_notice);


        content.setText(sample.get(position).getContent());
        date.setText(sample.get(position).getDate());

        return view;
    }
}
