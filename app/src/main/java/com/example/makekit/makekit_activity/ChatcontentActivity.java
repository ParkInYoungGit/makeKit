package com.example.makekit.makekit_activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.makekit.R;
import com.example.makekit.makekit_adapter.ChattingContentsAdapter;
import com.example.makekit.makekit_asynctask.NetworkTask_DH;
import com.example.makekit.makekit_bean.ChattingBean;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;

public class ChatcontentActivity extends AppCompatActivity {

    String TAG = "ChatContents";
    String macIP, email, chattingNumber, receiver, urlAddrBase;
    int intChattingNumber = 0;
    ArrayList<ChattingBean> chattingContents;
    RecyclerView recyclerView = null;
    RecyclerView.Adapter mAdapter = null;
    RecyclerView.LayoutManager layoutManager = null;
    SlidingUpPanelLayout slidingUpPanelLayout;
    TextView IDTextView;
    EditText editText;
    Button insertButton, plusButton;
    Handler handler;
    Thread thread;
    ArrayList<ChattingBean> chattingJudge;
    boolean isRun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_content);

        IDTextView = findViewById(R.id.receiverID);
        editText = findViewById(R.id.chattingContents_ET);
        insertButton = findViewById(R.id.chattingContents_Btn);
        plusButton = findViewById(R.id.plusButton_chat);
        recyclerView = findViewById(R.id.chattingContents_LV);

        Intent intent = getIntent();
        email = intent.getStringExtra("useremail");
        macIP = intent.getStringExtra("macIP");
        chattingNumber = intent.getStringExtra("chattingNumber");
        receiver = intent.getStringExtra("receiver");

        IDTextView.setText(receiver);

        urlAddrBase = "http://" + macIP + ":8080/makeKit/";

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        chattingJudge = new ArrayList<ChattingBean>();
        chattingContents = new ArrayList<ChattingBean>();

        handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                Log.v(TAG, Integer.toString(msg.what));
                switch (msg.what){
                    case 0:
                        Log.v(TAG, "Thread 들어옴");
                        chattingContents.clear();
                        chattingJudge.clear();
                        connectGetData();
                        chattingJudge.addAll(chattingContents);
                        mAdapter = new ChattingContentsAdapter(ChatcontentActivity.this, R.layout.chatting_layout, chattingContents, email, receiver);
                        recyclerView.setAdapter(mAdapter);
                        recyclerView.post(new Runnable() {
                            @Override
                            public void run() {
                                recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount()-1);
                            }
                        });
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
                        Thread.sleep(1000);
                        if(judgement()==1){
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
                NetworkTask_DH networkTask = new NetworkTask_DH(ChatcontentActivity.this, urlAddrBase+"jsp/insertChatting.jsp?chattingNumber="+chattingNumber+"&userinfo_userEmail_sender="+email+"&userinfo_userEmail_receiver="+receiver+"&chattingContents="+editText.getText().toString(), "inputChatting");
                networkTask.execute();
                connectGetData();
                editText.setText("");
            }
        });

        setContentViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isRun = true;
        // 첫 대화이면 가장 큰 채팅 번호를 불러와서 1 증가 시켜 채팅 방을 만든다.
        if(chattingNumber.equals(null)){
            connectGetChattingNumber();
        }
        connectGetData();
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
            NetworkTask_DH networkTask = new NetworkTask_DH(ChatcontentActivity.this, urlAddrBase+"jsp/chatting.jsp?userinfo_userEmail_sender="+email+"&userinfo_userEmail_receiver="+receiver, "chattingContents");
            Object obj = networkTask.execute().get();
            chattingContents = (ArrayList<ChattingBean>) obj;
//            chattingJudge.addAll(chattingContents);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void connectGetChattingNumber(){
        try {
            NetworkTask_DH networkTask = new NetworkTask_DH(ChatcontentActivity.this, urlAddrBase+"jsp/getChattingNumber.jsp", "getChattingNumber");
            Object obj = networkTask.execute().get();
            chattingNumber = (String) obj;
            intChattingNumber = Integer.parseInt(chattingNumber)+1;
            chattingNumber = Integer.toString(intChattingNumber);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private int judgement(){
        int j = 0;
        int contents = chattingContents.size();
        int judge = chattingJudge.size();
        if(contents == judge){
            j++;
        }
        Log.v(TAG, "j : "+j);
        return j;
    }

    public void setContentViews(){
        slidingUpPanelLayout = findViewById(R.id.chatContent_slidingup);
        slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        slidingUpPanelLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {

            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {

            }
        });
    }

}