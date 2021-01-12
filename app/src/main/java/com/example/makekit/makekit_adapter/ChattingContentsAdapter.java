package com.example.makekit.makekit_adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.Gravity;
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
    String email, receiver;

    public ChattingContentsAdapter(Context mContext, int layout, ArrayList<ChattingBean> data, String email, String receiver) {
        this.mContext = mContext;
        this.layout = layout;
        this.data = data;
        this.email = email;
        this.receiver = receiver;
        this.inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {

        return data.get(position);//////////////////////////////////////////////////////////

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




        if (data.get(position).getUserinfo_userEmail_sender().equals(email)){
            tv_sendid.setText("나");
            tv_sendid.setPaintFlags(tv_sendid.getPaintFlags()| Paint.FAKE_BOLD_TEXT_FLAG);
            tv_sendContent.setText(data.get(position).getChattingContents());
            tv_sendContent.setBackground(mContext.getResources().getDrawable(R.drawable.chat_sender));
            tv_sendContent.setGravity(Gravity.RIGHT);
        }else {
            tv_sendid.setText(data.get(position).getUserinfo_userEmail_sender());
            tv_sendContent.setBackground(mContext.getResources().getDrawable(R.drawable.chat_receiver));
            tv_sendContent.setText(data.get(position).getChattingContents());
        }

        return convertView;
    }
}
