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

public class ChattingListAdapter extends BaseAdapter {

    Context mContext = null;
    int layout = 0;
    ArrayList<ChattingBean> data = null;
    LayoutInflater inflater = null;
    String email;

    public ChattingListAdapter(Context mContext, int layout, ArrayList<ChattingBean> data, String email) {
        this.mContext = mContext;
        this.layout = layout;
        this.data = data;
        this.email = email;
        this.inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position).getChattingNumber();
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
        TextView tv_sendID = convertView.findViewById(R.id.chattingListSendId_TV);
        TextView tv_sendContents = convertView.findViewById(R.id.chattingListSendContents_TV);

        if(email.equals(data.get(position).getUserinfo_userEmail_receiver())){
            tv_sendID.setText(data.get(position).getUserinfo_userEmail_sender());
        }else {
            tv_sendID.setText(data.get(position).getUserinfo_userEmail_receiver());
        }
        tv_sendContents.setText(data.get(position).getChattingContents());



        return convertView;
    }
}
