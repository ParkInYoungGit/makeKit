package com.example.makekit.makekit_activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.makekit.R;
import com.example.makekit.makekit_adapter.ChattingContentsAdapter;
import com.example.makekit.makekit_asynctask.NetworkTask_DH;
import com.example.makekit.makekit_bean.ChattingBean;

import java.util.ArrayList;

public class ChatcontentActivity extends AppCompatActivity {

    String urlAddrBase = null;
    String macIP, email;
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
//        SharedPreferences sf = getSharedPreferences("appData", MODE_PRIVATE);
//        email = sf.getString("useremail");
//        macIP = sf.getString("macIP");

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
                        adapter = new ChattingContentsAdapter(ChatcontentActivity.this, R.layout.chatting_layout, chattingContents);
                        listView.setAdapter(adapter);
                        break;
                    case 1:
                        break;
                }
            }
        };

        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    while (isRun){
                        connectGetData();
                        Thread.sleep(500);
                        if(judgement()==chattingContents.size()){
                            Message msg = handler.obtainMessage();
                            msg.what = 1;
                            handler.sendMessage(msg);
                        }else {
                            Message msg = handler.obtainMessage();
                            msg.what = 0;
                            handler.sendMessage(msg);
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NetworkTask_DH networkTask = new NetworkTask_DH(ChatcontentActivity.this, urlAddrBase+"/JSP/insertChatting.jsp?userid=??????????????&contents="+editText.getText().toString(), "inputChatting");
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
        connectGetData();
        adapter = new ChattingContentsAdapter(ChatcontentActivity.this, R.layout.chatting_layout, chattingContents);
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
            NetworkTask_DH networkTask = new NetworkTask_DH(ChatcontentActivity.this, urlAddrBase+"/JSP/chatting.jsp??????????????????????", "chattingContents");
            Object obj = networkTask.execute().get();
            chattingContents = (ArrayList<ChattingBean>) obj;
            chattingJudge.addAll(chattingContents);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private int judgement(){
        int j = 0;
        for(int i =0 ; i<chattingContents.size(); i++){
            int contents = chattingContents.get(i).getChattingNumber();
            int judge = chattingJudge.get(i).getChattingNumber();
            if(contents == judge){
                j++;
            }else {
            }
        }
        return j;
    }
}