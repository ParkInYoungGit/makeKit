package com.example.makekit.makekit_activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.makekit.R;
import com.example.makekit.makekit_adapter.PurchaseListAdapter;
import com.example.makekit.makekit_asynctask.NetworkTask;
import com.example.makekit.makekit_asynctask.NetworkTask_DH;
import com.example.makekit.makekit_bean.Order;
import com.example.makekit.makekit_sharVar.SharVar;

import java.util.ArrayList;

public class PurchaseListActivity extends AppCompatActivity {

    String TAG = "PurchaseReal";
    ArrayList<Order> orders;
    RecyclerView recyclerView = null;
    RecyclerView.Adapter mAdapter = null;
    RecyclerView.LayoutManager layoutManager = null;
    String email, macIP, urlAddrBase, url2,result;
    String urlAddr, orderNo;
    Button buyCheck_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_purchase_list);
        Log.v(TAG, "onCreat");
        orders = new ArrayList<Order>();

        Intent intent = getIntent();
//        orderNo = intent.getStringExtra("orderNo");

        result = intent.getStringExtra("result");

        macIP = SharVar.macIP;
        email = SharVar.userEmail;
        urlAddrBase = SharVar.urlAddrBase;

        recyclerView = findViewById(R.id.PurchaseList_RecyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        buyCheck_btn = findViewById(R.id.buycheck_btn);

//        View.OnClickListener mClickListener = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                switch (v.getId()) {
//                    case R.id.buycheck_btn:
//
//                        macIP = "192.168.35.251";
//                        email = SharVar.userEmail;
//
//                        Intent intent = getIntent();
//                        orderNo = intent.getStringExtra("orderNo");
//                        Log.v(TAG, orderNo);
//
//                        urlAddrBase = "http://" + macIP + ":8080/makeKit/";
//                        urlAddr = urlAddrBase + "jsp/buy_check?orderNo=" + orderNo;
//
//                        Log.v(TAG, urlAddr);
//                        String result = connectUpdateData();
//
//                        if (result.equals("1")) {
//                            Toast.makeText(PurchaseListActivity.this, "구매확정완료 !", Toast.LENGTH_SHORT).show();
//                        } else {
//                            Toast.makeText(PurchaseListActivity.this, "구매확정실패 !", Toast.LENGTH_SHORT).show();
//                        }
//
//                }
//            }
//
//
//        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        connectUpdateData();
        connectGetData();
        mAdapter = new PurchaseListAdapter(PurchaseListActivity.this, R.layout.purchase_list_layout, orders, urlAddrBase + "image/", email, macIP);
        recyclerView.setAdapter(mAdapter);
    }

    private void connectGetData() {
        try {
            Log.v(TAG, "test");
            NetworkTask_DH networkTask = new NetworkTask_DH(PurchaseListActivity.this, urlAddrBase + "jsp/purchase_list.jsp?userEmail=" + email, "getRealSalesList");
            Object obj = networkTask.execute().get();
            orders = (ArrayList<Order>) obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String connectUpdateData() {

        String result = null;
        try {
            ///////////////////////////////////////////////////////////////////////////////////////
            // Date : 2020.12.25
            //
            // Description:
            //  - NetworkTask를 한곳에서 관리하기 위해 기존 CUDNetworkTask 삭제
            //  - NetworkTask의 생성자 추가 : where <- "update"
            //
            ///////////////////////////////////////////////////////////////////////////////////////
            Log.v("dd","dddddd");
            NetworkTask_DH networkTask2 = new NetworkTask_DH(PurchaseListActivity.this, urlAddr, "updatebuy");
            ///////////////////////////////////////////////////////////////////////////////////////

            //CUDNetworkTask updnetworkTask = new CUDNetworkTask(UpdateActivity.this, urlAddr);
            ///////////////////////////////////////////////////////////////////////////////////////
            // Date : 2020.12.24
            //
            // Description:
            // - 수정 결과 값을 받기 위해 Object로 return후에 String으로 변환 하여 사용
            //
            ///////////////////////////////////////////////////////////////////////////////////////

            Object obj = networkTask2.execute().get();
            result = (String) obj;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


}