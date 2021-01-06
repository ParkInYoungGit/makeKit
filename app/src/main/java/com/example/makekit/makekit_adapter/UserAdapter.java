package com.example.makekit.makekit_adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.makekit.R;
import com.example.makekit.makekit_bean.User;

import java.util.ArrayList;

public class UserAdapter extends BaseAdapter {
    Context mContext = null;
    int layout = 0;
    ArrayList<User> data = null;
    LayoutInflater inflater = null;

    public UserAdapter(Context mContext, int layout, ArrayList<User> data) {
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
        return data.get(position).getEmail();
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
        TextView user_email = convertView.findViewById(R.id.user_email);
        TextView user_pw = convertView.findViewById(R.id.user_pw);
        TextView user_name = convertView.findViewById(R.id.user_name);
        TextView user_address = convertView.findViewById(R.id.user_address);
        TextView user_addressdetail = convertView.findViewById(R.id.user_addressdetail);
        TextView user_phone = convertView.findViewById(R.id.user_tel);
        TextView user_birth = convertView.findViewById(R.id.user_birth);


        return convertView;
    }
}
