package com.example.makekit.makekit_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.makekit.R;
import com.example.makekit.makekit_adapter.NoticeAdapter;
import com.example.makekit.makekit_bean.User;

import java.util.ArrayList;
import java.util.List;

public class NoticeActivity extends AppCompatActivity {

//    ListView lv_notice;
    ArrayList<User> lv_notice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_notice);

//        lv_notice = (ListView)findViewById(R.id.lv_notice);
//
//        List<String> data = new ArrayList<>();
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,data);
//        lv_notice.setAdapter(adapter);
//
//        data.add("홍드로이드", "", "");
//        data.add("안드로이드");
//        data.add("사과");
//        adapter.notifyDataSetChanged();


        this.InitializeMovieData();

        ListView listView = (ListView)findViewById(R.id.lv_notice);
        final NoticeAdapter noticeAdapter = new NoticeAdapter(this,lv_notice);

        listView.setAdapter(noticeAdapter);

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()){
//            @Override
//            public void onItemClick(AdapterView parent, View v, int position, long id){
//                Toast.makeText(getApplicationContext(),
//                        noticeAdapter.getItem(position).getContent(),
//                        Toast.LENGTH_LONG).show();
//            }
//        });

    }

    public void InitializeMovieData()
    {
        lv_notice = new ArrayList<User>();

        lv_notice.add(new User("[MakeKit] 신규기능 및 체험단 모집! ","2021.01.15"));
        lv_notice.add(new User("[MakeKit] 2차 체험단 모집!","2021.01.17"));
        lv_notice.add(new User("[MakeKit] MakeKit을 통한 지역 활성 기획","2021.01.21"));
        lv_notice.add(new User("[MakeKit] MakeKit을 통한 불우이웃 돕기 자선행사 진행","2021.02.25"));
        lv_notice.add(new User("[MakeKit] 'MakeKit' 지역 사회에 큰 공헌","2021.03.10"));
        lv_notice.add(new User("[MakeKit] 월간 신문 발간! MakeKit 1호!","2021.04.01"));
    }
}