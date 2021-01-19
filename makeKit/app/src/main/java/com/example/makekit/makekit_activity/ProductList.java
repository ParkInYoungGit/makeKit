package com.example.makekit.makekit_activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.TextView;

import com.example.makekit.R;
import com.example.makekit.makekit_adapter.ProductListAdapter;
import com.example.makekit.makekit_asynctask.NetworkTask;
import com.example.makekit.makekit_bean.ProductData;
import com.example.makekit.makekit_sharVar.SharVar;

import java.util.ArrayList;

public class ProductList extends AppCompatActivity {

    private ArrayList<ProductData> arrayList;
    private ProductListAdapter productListAdapter;
    private RecyclerView recyclerView;
    private GridLayoutManager linearLayoutManager;

    TextView product_title;
    TextView product_subtitle;
    TextView product_price;
    WebView product_image;


    String title, subtitle, price, pType, pNo;

    String macIP, email, urlAddrBase, urlAddr1;
    private RecyclerView.LayoutManager layoutManager;



    ArrayList<ProductData> product;   // 빈, 어댑터
    ArrayList<ProductData> searchArr;   // 빈, 어댑터
    ProductListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_product_list);



//        macIP = "172.20.10.5";

        // 아이피와 이메일 받기
        Intent intent = getIntent();
        pType = intent.getStringExtra("pType");
        macIP = SharVar.macIP;
        email = SharVar.userEmail;
//        macIP = "192.168.2.14";



        recyclerView = (RecyclerView) findViewById(R.id.rv_product);
//        linearLayoutManager = new GridLayoutManager(ProductList.this,2);
//        recyclerView.setLayoutManager(linearLayoutManager);

        arrayList = new ArrayList<>();

        // productListAdapter = new ProductListAdapter(arrayList);
        //recyclerView.setAdapter(productListAdapter);
    }


    @Override
    public void onResume() {
        super.onResume();
        urlAddrBase = SharVar.urlAddrBase;
        //urlAddrBase = "http://" + macIP + ":8080/makeKit/jsp/";
        urlAddr1 = urlAddrBase + "product_category.jsp?pType="+pType;
        connectGetData(urlAddr1);

//        searchArr.addAll(product);

    }

    // NetworkTask에서 값을 가져오는 메소드
    private void connectGetData(String urlAddr) {
        try {
            urlAddrBase = SharVar.urlAddrBase;
            urlAddr1 = urlAddrBase + "jsp/product_category.jsp?pType="+pType;

            NetworkTask NetworkTask = new NetworkTask(ProductList.this, urlAddr1, "productSelect");
            Object obj = NetworkTask.execute().get();
            product = (ArrayList<ProductData>) obj;
            Log.v("no", product.get(0).getProductNo());
            pNo = product.get(0).getProductNo();
            Log.v("pno",pNo);
            adapter = new ProductListAdapter(ProductList.this, R.layout.productitem_layout, product, urlAddrBase); // 아댑터에 값을 넣어준다.
            recyclerView.setAdapter(adapter);  // 리스트뷰에 어탭터에 있는 값을 넣어준다.
            recyclerView.setHasFixedSize(true); // 리사이클러뷰 기존성능 강화
            layoutManager = new GridLayoutManager(ProductList.this,2);
            SnapHelper snapHelper = new PagerSnapHelper();
            recyclerView.setLayoutManager(layoutManager);
            snapHelper.attachToRecyclerView(recyclerView);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public void search(String charText) {
//        product.clear();
//        if (charText.length() == 0) {
//            product.addAll(searchArr);
//        } else {
//            for (int i = 0; i < searchArr.size(); i++) {
//                if (searchArr.get(i).getProduct_title().contains(charText)) {
//                    product.add(searchArr.get(i));
//                }
//            }
//        }
//        adapter.notifyDataSetChanged();
//    }
}
