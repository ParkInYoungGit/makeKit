package com.example.makekit.makekit_activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.makekit.R;

import java.util.ArrayList;

public class ProductList extends AppCompatActivity {

private ArrayList<ProductData> arrayList;
private ProductListAdapter productListAdapter;
private RecyclerView recyclerView;
private LinearLayoutManager linearLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);





        recyclerView = (RecyclerView)findViewById(R.id.rv_product);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        arrayList = new ArrayList<>();

        productListAdapter = new ProductListAdapter(arrayList);
        recyclerView.setAdapter(productListAdapter);






    }
}
