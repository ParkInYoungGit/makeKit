package com.example.makekit.makekit_adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.makekit.R;
import com.example.makekit.makekit_activity.ChatcontentActivity;
import com.example.makekit.makekit_bean.ChattingBean;

import java.util.ArrayList;

public class ChattingContentsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<ChattingBean> data = null;
    private AdapterView.OnItemClickListener mListener = null;
    String email, receiver;
    ChatcontentActivity chatcontentActivity;


    public ChattingContentsAdapter(ChatcontentActivity chatcontentActivity, int layout, ArrayList<ChattingBean> mDataset, String email, String receiver){
        this.chatcontentActivity = chatcontentActivity;
        this.data = mDataset;
        this.email = email;
        this.receiver = receiver;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;

        switch(viewType){
            case 0:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chatting_layout_rigth, parent,false);
                return new MyViewHolder(view);
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chatting_layout, parent,false);
                return new MyViewHolder2(view);
        }
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chatting_layout_rigth, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (data.get(position).getUserinfo_userEmail_sender().equals(email)){       // 오른쪽에 띄운다
            MyViewHolder holder1 = (MyViewHolder) holder;
            holder1.tv_sendid_right.setText("나");
            holder1.tv_sendid_right.setPaintFlags(holder1.tv_sendid_right.getPaintFlags()| Paint.FAKE_BOLD_TEXT_FLAG);
            holder1.tv_sendContent_right.setText(data.get(position).getChattingContents());
            holder1.tv_sendContent_right.setBackground(chatcontentActivity.getResources().getDrawable(R.drawable.kakaotalkchatting_sender));
        }else {
            MyViewHolder2 holder2 = (MyViewHolder2) holder;
            holder2.tv_sendid.setText(data.get(position).getUserinfo_userEmail_sender());
            holder2.tv_sendContent.setBackground(chatcontentActivity.getResources().getDrawable(R.drawable.kakaotalkchatting_receiver));
            holder2.tv_sendContent.setText(data.get(position).getChattingContents());
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {             // 오른쪽에 띄운다
        TextView tv_sendid_right;
        TextView tv_sendContent_right;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_sendid_right = itemView.findViewById(R.id.sendId_right);
            tv_sendContent_right = itemView.findViewById(R.id.sendContent_right);
        }
    }
    public class MyViewHolder2 extends RecyclerView.ViewHolder{
        TextView tv_sendid;
        TextView tv_sendContent;
        public MyViewHolder2(@NonNull View itemView) {
            super(itemView);
            tv_sendid = itemView.findViewById(R.id.sendId);
            tv_sendContent = itemView.findViewById(R.id.sendContent);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (data.get(position).getUserinfo_userEmail_sender().equals(email)){       // 오른쪽에 띄운다
            return 0;
        }else {
            return 1;
        }
    }
}
