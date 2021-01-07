package com.example.makekit.makekit_fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.makekit.R;
import com.example.makekit.makekit_bean.ChattingBean;

import java.util.ArrayList;


public class ChatListFragment extends Fragment {

    String urlAddrBase = null;
    String macIP, email;
    ArrayList<ChattingBean> chattingBeanArrayList;

    TextView sendID, sendContents;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Fragment는 Activity가 아니기때문에 리턴값과 레이아웃을 변수로 정해준다.
        View v = inflater.inflate(R.layout.fragment_chat, container, false);



        return v;
    }
}