package com.example.makekit.makekit_activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.makekit.R;
import com.example.makekit.makekit_adapter.OnSearchItemClickListener;
import com.example.makekit.makekit_asynctask.NetworkTask_DH;
import com.example.makekit.makekit_bean.Product;
import com.example.makekit.makekit_adapter.SearchAdapter;
import com.example.makekit.makekit_sharVar.SharVar;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    String TAG = "SearchActivity";
    String urlAddrBase = null;
    String urlAddrGetData = null;
    ArrayList<Product> products;
    ArrayList<String> productsName;
    SearchAdapter adapterLeft;
    String macIP, email;
    AutoCompleteTextView search_EdT;
    ImageView search_Iv;
    RecyclerView recyclerView;
    GridLayoutManager layoutManager;
    SearchAdapter searchAdapter = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_search);
        Log.v(TAG, "onCreate");
        search_EdT = findViewById(R.id.search_ET);
        search_Iv = findViewById(R.id.search_Iv);
        recyclerView = findViewById(R.id.searchRecyclerView);

        macIP = SharVar.macIP;
        email = SharVar.userEmail;
        urlAddrBase = SharVar.urlAddrBase;

        products = new ArrayList<Product>();
        layoutManager = new GridLayoutManager(this,2);

        search_Iv.setOnClickListener(mClickListener);

    }
    View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.v(TAG, "onClick");
            connectGetSearchData();
            searchAdapter = new SearchAdapter(urlAddrBase, products);

            recyclerView.setLayoutManager(layoutManager);

            recyclerView.setAdapter(searchAdapter);

            searchAdapter.setOnItemClickListener(new OnSearchItemClickListener() {
                @Override
                public void onItemClick(SearchAdapter.ViewHolder holder, View view, int position) {
                    Product item = searchAdapter.getItem(position);
                }
            });
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        Log.v(TAG, "onResume");
        connectGetProductName();

    }

    private void connectGetSearchData() {
        try {
            urlAddrGetData = null;
            products.clear();
            urlAddrGetData = urlAddrBase+"jsp/getProductAll_Infromation.jsp?search="+search_EdT.getText().toString();
            NetworkTask_DH networkTask = new NetworkTask_DH(SearchActivity.this, urlAddrGetData, "search");
            Object obj = networkTask.execute().get();
            products = (ArrayList<Product>) obj;
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