package com.example.makekit.makekit_activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.makekit.R;
import com.example.makekit.makekit_asynctask.NetworkTask_DH;
import com.example.makekit.makekit_bean.Product;
import com.example.makekit.makekit_adapter.SearchAdapter;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    String TAG = "SearchActivity";
    String urlAddrBase = null;
    String urlAddrGetData = null;
    ArrayList<Product> products;
    ArrayList<String> productsName;
    SearchAdapter adapter;
    ListView listViewLeft, listViewRight;
    String macIP, email;
    AutoCompleteTextView search_EdT;
    ImageView search_Iv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Log.v(TAG, "onCreate");
        listViewLeft = findViewById(R.id.searchListViewLeft);
        listViewRight = findViewById(R.id.searchListViewRigt);
        search_EdT = findViewById(R.id.search_ET);
        search_Iv = findViewById(R.id.search_Iv);

        Intent intent = getIntent();
        email = intent.getStringExtra("useremail");
        macIP = intent.getStringExtra("macIP");
        urlAddrBase = "http://" + macIP + ":8080/makeKit/";
        Log.v(TAG, "urlAddrBase : "+urlAddrBase);
        products = new ArrayList<Product>();

        search_Iv.setOnClickListener(mClickListener);
        listViewLeft.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        listViewRight.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }
    View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.v(TAG, "onClick");
            urlAddrGetData = null;
            products.clear();
            connectGetSearchLeftData();
            urlAddrGetData = null;
            products.clear();
            connectGetSearchRightData();

        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        Log.v(TAG, "onResume");
        connectGetProductName();

    }

    private void connectGetSearchLeftData() {
        try {
            urlAddrGetData = urlAddrBase+"jsp/getProductAll_Infromation.jsp?search="+search_EdT.getText().toString()+"&number=1";
            NetworkTask_DH networkTask = new NetworkTask_DH(SearchActivity.this, urlAddrGetData, "search");
            Object obj = networkTask.execute().get();
            products = (ArrayList<Product>) obj;
            Log.v(TAG, products.get(0).getProductName());
            adapter = new SearchAdapter(SearchActivity.this, R.layout.search_layout, products, urlAddrBase+"image/"); // 아댑터에 값을 넣어준다.
            listViewLeft.setAdapter(adapter);  // 리스트뷰에 어탭터에 있는 값을 넣어준다.
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void connectGetSearchRightData() {
        try {
            urlAddrGetData = urlAddrBase+"jsp/getProductAll_Infromation.jsp?search="+search_EdT.getText().toString()+"&number=0";
            NetworkTask_DH networkTask = new NetworkTask_DH(SearchActivity.this, urlAddrGetData, "search");
            Object obj = networkTask.execute().get();
            products = (ArrayList<Product>) obj;
            adapter = new SearchAdapter(SearchActivity.this, R.layout.search_layout, products, urlAddrBase+"image/"); // 아댑터에 값을 넣어준다.
            listViewRight.setAdapter(adapter);  // 리스트뷰에 어탭터에 있는 값을 넣어준다.
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void connectGetProductName() {
        try {
            urlAddrGetData = urlAddrBase+"jsp/getProductName.jsp";
            NetworkTask_DH networkTask = new NetworkTask_DH(SearchActivity.this, urlAddrGetData, "productName");
            Object obj = networkTask.execute().get();
            productsName = (ArrayList<String>) obj;
            search_EdT.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, productsName));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}