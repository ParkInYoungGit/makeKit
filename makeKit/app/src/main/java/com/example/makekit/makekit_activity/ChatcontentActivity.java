package com.example.makekit.makekit_activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.makekit.R;
import com.example.makekit.makekit_adapter.ChattingContentsAdapter;
import com.example.makekit.makekit_asynctask.NetworkTask_DH;
import com.example.makekit.makekit_bean.ChattingBean;
import com.example.makekit.makekit_sharVar.SharVar;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;

public class ChatcontentActivity extends AppCompatActivity {

    String TAG = "ChatContents";
    String macIP, email, chattingNumber, receiver, urlAddrBase, searchAddress;
    int intChattingNumber = 0;
    ArrayList<ChattingBean> chattingContents;
    RecyclerView recyclerView = null;
    RecyclerView.Adapter mAdapter = null;
    RecyclerView.LayoutManager layoutManager = null;
    SlidingUpPanelLayout slidingUpPanelLayout;
    TextView IDTextView, gpsTextView_chat;
    EditText editText;
    ImageButton insertButton, plusButton, gpsButton_chat;
    Handler handler;
    Thread thread;
    ArrayList<ChattingBean> chattingJudge;
    boolean isRun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_chat_content);
        searchAddress = "";
        IDTextView = findViewById(R.id.receiverID);
        editText = findViewById(R.id.chattingContents_ET);
        insertButton = findViewById(R.id.chattingContents_Btn);
        plusButton = findViewById(R.id.plusButton_chat);
        recyclerView = findViewById(R.id.chattingContents_LV);
        gpsButton_chat = findViewById(R.id.gpsButton_chat);
        gpsTextView_chat = findViewById(R.id.gpsTextView_chat);

        Intent intent = getIntent();
        macIP = intent.getStringExtra("macIP");
        email = intent.getStringExtra("useremail");
        chattingNumber = intent.getStringExtra("chattingNumber");
        receiver = intent.getStringExtra("receiver");
        searchAddress = intent.getStringExtra("searchAddress");

        IDTextView.setText(receiver);

        urlAddrBase = SharVar.urlAddrBase;
        //urlAddrBase = "http://" + macIP + ":8080/makeKit/";

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        chattingJudge = new ArrayList<ChattingBean>();
        chattingContents = new ArrayList<ChattingBean>();

        plusButton.setOnClickListener(mClickListener);
        editText.setOnClickListener(mClickListener);
        gpsButton_chat.setOnClickListener(mClickListener);

        slidingUpPanelLayout = findViewById(R.id.chatContent_slidingup);

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

        editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                editText.requestFocus();
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(searchAddress.equals("")){

        }else{
            NetworkTask_DH networkTask = new NetworkTask_DH(ChatcontentActivity.this, urlAddrBase+"jsp/insertChatting.jsp?chattingNumber="+chattingNumber+"&userinfo_userEmail_sender="+email+"&userinfo_userEmail_receiver="+receiver+"&chattingContents=여기서 만나요~ : "+searchAddress, "inputChatting");
            networkTask.execute();
        }
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

    View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.plusButton_chat:
                    if(slidingUpPanelLayout.getPanelState().toString().equalsIgnoreCase("COLLAPSED")) {
                        slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
                        plusButton.setBackground(ContextCompat.getDrawable(ChatcontentActivity.this, R.drawable.minus_chat));
                        gpsButton_chat.setVisibility(View.VISIBLE);
                        gpsTextView_chat.setVisibility(View.VISIBLE);
                    }else{
                        slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);

                        plusButton.setBackground(ContextCompat.getDrawable(ChatcontentActivity.this, R.drawable.add));
                        gpsButton_chat.setVisibility(View.INVISIBLE);
                        gpsTextView_chat.setVisibility(View.INVISIBLE);

                    }

                    break;
//                case R.id.chattingContents_ET:
//
//
//                    break;
                case R.id.gpsButton_chat:
                    Intent intent = new Intent(ChatcontentActivity.this, MapChattingActivity.class);
                    intent.putExtra("useremail", email);
                    intent.putExtra("macIP", macIP);
                    intent.putExtra("receiver", receiver);
                    intent.putExtra("chattingNumber", chattingNumber);
                    startActivity(intent);
                    break;
            }
        }
    };

}