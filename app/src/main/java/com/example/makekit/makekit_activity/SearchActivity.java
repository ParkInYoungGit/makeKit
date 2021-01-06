package com.example.makekit.makekit_activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.makekit.R;
import com.example.makekit.makekit_asynctask.NetworkTask_DH;
import com.example.makekit.makekit_bean.Product;
import com.example.makekit.makekit_fragment.ProductAdapter;
import com.example.makekit.makekit_fragment.SearchAdapter;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    String urlAddrBase = null;
    String urlAddrGetData = null;
    ArrayList<Product> products;
    SearchAdapter adapter;
    ListView listView;
    String IP;
    String email;
    EditText search_EdT;
    ImageView search_Iv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        listView = findViewById(R.id.searchListView);
        search_EdT = findViewById(R.id.search_ET);
        search_Iv = findViewById(R.id.search_Iv);

        // ip와 email 받아오기
        //
        //
        //
        //
        //

        search_Iv.setOnClickListener(mClickListener);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }
    View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            connectGetData();

        }
    };

    private void connectGetData() {
        try {
            urlAddrBase = "http://" + IP + ":8080/makeKit/Image/";
            NetworkTask_DH networkTask = new NetworkTask_DH(SearchActivity.this, urlAddrGetData, "select");
            Object obj = networkTask.execute().get();
            products = (ArrayList<Product>) obj;
            adapter = new SearchAdapter(SearchActivity.this, R.layout.search_layout, products, urlAddrBase); // 아댑터에 값을 넣어준다.
            listView.setAdapter(adapter);  // 리스트뷰에 어탭터에 있는 값을 넣어준다.
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}