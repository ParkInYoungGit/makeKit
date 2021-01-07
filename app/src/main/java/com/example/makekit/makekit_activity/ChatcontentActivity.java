package com.example.makekit.makekit_activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.makekit.R;
import com.example.makekit.makekit_adapter.ChattingContentsAdapter;
import com.example.makekit.makekit_asynctask.NetworkTask_DH;
import com.example.makekit.makekit_bean.ChattingBean;

import java.util.ArrayList;

public class ChatcontentActivity extends AppCompatActivity {

    String urlAddrBase = null;
    String macIP, email, chattingNumber, receiver;
    int intChattingNumber = 0;
    ArrayList<ChattingBean> chattingContents;
    ChattingContentsAdapter adapter;
    ListView listView;
    EditText editText;
    Button insertButton;
    Handler handler;
    Thread thread;
    ArrayList<ChattingBean> chattingJudge;
    boolean isRun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_content);
        editText = findViewById(R.id.chattingContents_ET);
        insertButton = findViewById(R.id.chattingContents_Btn);
        listView = findViewById(R.id.chattingContents_LV);
        Intent intent = getIntent();
        email = intent.getStringExtra("useremail");
        macIP = intent.getStringExtra("macIP");
        chattingNumber = intent.getStringExtra("chattingNumber");
        receiver = intent.getStringExtra("receiver");

        urlAddrBase = "http://" + macIP + ":8080/makeKit/";

        handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 0:
                        chattingContents.clear();
                        chattingJudge.clear();
                        connectGetData();
                        adapter = new ChattingContentsAdapter(ChatcontentActivity.this, R.layout.chatting_layout, chattingContents, email, receiver);
                        listView.setAdapter(adapter);
                        break;
                    case 1:
                        break;
                }
            }
        };

//        thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try{
//                    while (isRun){
//                        connectGetData();
//                        Thread.sleep(500);
//                        if(judgement()==chattingContents.size()){
//                            Message msg = handler.obtainMessage();
//                            msg.what = 1;
//                            handler.sendMessage(msg);
//                        }else {
//                            Message msg = handler.obtainMessage();
//                            msg.what = 0;
//                            handler.sendMessage(msg);
//                        }
//                    }
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//        });

        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NetworkTask_DH networkTask = new NetworkTask_DH(ChatcontentActivity.this, urlAddrBase+"/jsp/insertChatting.jsp?chattingNumber="+chattingNumber+"&userinfo_userEmail_sender="+email+"&userinfo_userEmail_receiver="+receiver+"&chattingContents="+editText.getText().toString(), "inputChatting");
                networkTask.execute();
                connectGetData();
                editText.setText("");
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        chattingJudge = new ArrayList<ChattingBean>();
        chattingContents = new ArrayList<ChattingBean>();
        isRun = true;
        // 첫 대화이면 가장 큰 채팅 번호를 불러와서 1 증가 시켜 채팅 방을 만든다.
        if(chattingNumber.equals(null)){
            connectGetChattingNumber();
        }else {
            connectGetData();
        }
        adapter = new ChattingContentsAdapter(ChatcontentActivity.this, R.layout.chatting_layout, chattingContents, email, receiver);
        listView.setAdapter(adapter);
        thread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        isRun = false;
        try {
            thread.join();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void connectGetData(){
        try {
            NetworkTask_DH networkTask = new NetworkTask_DH(ChatcontentActivity.this, urlAddrBase+"/jsp/chatting.jsp?userinfo_userEmail_sender="+email+"&userinfo_userEmail_receiver="+receiver, "chattingContents");
            Object obj = networkTask.execute().get();
            chattingContents = (ArrayList<ChattingBean>) obj;
            chattingJudge.addAll(chattingContents);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void connectGetChattingNumber(){
        try {
            NetworkTask_DH networkTask = new NetworkTask_DH(ChatcontentActivity.this, urlAddrBase+"/jsp/getChattingNumber.jsp?userinfo_userEmail_sender="+email, "getChattingNumber");
            Object obj = networkTask.execute().get();
            chattingNumber = (String) obj;
            intChattingNumber = Integer.parseInt(chattingNumber)+1;
            chattingNumber = Integer.toString(intChattingNumber);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
//
//    private int judgement(){
//        int j = 0;
//        for(int i =0 ; i<chattingContents.size(); i++){
//            int contents = chattingContents.get(i).getChattingNumber();
//            int judge = chattingJudge.get(i).getChattingNumber();
//            if(contents == judge){
//                j++;
//            }else {
//            }
//        }
//        return j;
//    }


}