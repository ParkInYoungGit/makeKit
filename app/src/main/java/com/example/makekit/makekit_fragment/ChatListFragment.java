package com.example.makekit.makekit_fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.makekit.R;
import com.example.makekit.makekit_activity.ChatcontentActivity;
import com.example.makekit.makekit_adapter.ChattingListAdapter;
import com.example.makekit.makekit_asynctask.NetworkTask;
import com.example.makekit.makekit_asynctask.NetworkTask_DH;
import com.example.makekit.makekit_bean.ChattingBean;

import java.util.ArrayList;


public class ChatListFragment extends Fragment {

    String urlAddrBase, urlGetData;
    String macIP, email;
    ArrayList<ChattingBean> chattingBeanArrayList;
    ChattingListAdapter adapter;

    TextView sendID, sendContents;
    ListView listView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Fragment는 Activity가 아니기때문에 리턴값과 레이아웃을 변수로 정해준다.
        View v = inflater.inflate(R.layout.fragment_chat, container, false);

        listView = v.findViewById(R.id.myChattingList);
        sendID = v.findViewById(R.id.chattingListSendId_TV);
        sendContents= v.findViewById(R.id.chattingListSendContents_TV);

        email = getArguments().getString("useremail");
        macIP = getArguments().getString("macIP");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ChatcontentActivity.class);
                intent.putExtra("useremail", email);
                intent.putExtra("macIP", macIP);
            }
        });

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        urlAddrBase = "http://" + macIP + ":8080/makeKit/jsp";
        connectGetData();
    }

    private void connectGetData(){
        urlGetData = urlAddrBase+"???????????????.jsp";
        try{
            NetworkTask_DH networkTask_dh = new NetworkTask_DH(getContext(), urlGetData, "getChattingList");
            Object obj = networkTask_dh.execute().get();
            chattingBeanArrayList = (ArrayList<ChattingBean>) obj;
            adapter = new ChattingListAdapter(getContext(), R.layout.chatting_list_layout, chattingBeanArrayList);
            listView.setAdapter(adapter);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}