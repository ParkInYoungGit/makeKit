package com.example.makekit.makekit_adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.makekit.R;
import com.example.makekit.makekit_bean.ChattingBean;

import java.util.ArrayList;

public class ChattingContentsAdapter extends BaseAdapter {

    Context mContext = null;
    int layout = 0;
    ArrayList<ChattingBean> data = null;
    LayoutInflater inflater = null;

    public ChattingContentsAdapter(Context mContext, int layout, ArrayList<ChattingBean> data) {
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
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = inflater.inflate(this.layout, parent, false);
        }
        TextView tv_sendid = convertView.findViewById(R.id.sendId);
        TextView tv_sendContent = convertView.findViewById(R.id.sendContent);

       // tv_sendid.setText(data.get(position).getSendid());
      //  tv_sendContent.setText(data.get(position).getSendContents());

        if ((position % 2)==1){
            convertView.setBackgroundColor(0x50000000);
        }else {
            convertView.setBackgroundColor(0x50dddddd);
        }

        return convertView;
    }
}
