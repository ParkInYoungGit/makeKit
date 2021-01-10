package com.example.makekit.makekit_activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.makekit.R;
import com.example.makekit.makekit_adapter.UserAdapter;
import com.example.makekit.makekit_asynctask.NetworkTask;
import com.example.makekit.makekit_bean.Product;
import com.example.makekit.makekit_bean.User;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ProductList extends AppCompatActivity {

    private ArrayList<ProductData> arrayList;
    private ProductListAdapter productListAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    TextView product_title;
    TextView product_subtitle;
    TextView product_price;
    WebView product_image;


    String title, subtitle, price, pType;
    String macIP, urlAddrBase, urlAddr1, email;
    private RecyclerView.LayoutManager layoutManager;




    ArrayList<ProductData> product;   // 빈, 어댑터
    ArrayList<ProductData> searchArr;   // 빈, 어댑터
    ProductListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);



        macIP = "172.20.10.8";

        // 아이피와 이메일 받기
        Intent intent = getIntent();
        macIP = intent.getStringExtra("macIP");
        email = intent.getStringExtra("useremail");
//        macIP = "192.168.2.14";



        recyclerView = (RecyclerView) findViewById(R.id.rv_product);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        arrayList = new ArrayList<>();

        // productListAdapter = new ProductListAdapter(arrayList);
        recyclerView.setAdapter(productListAdapter);
    }


    @Override
    public void onResume() {
        super.onResume();
        Intent intent = new Intent();
        pType =  getIntent().getStringExtra("pType");
        urlAddrBase = "http://" + macIP + ":8080/makeKit/jsp/";
        urlAddr1 = urlAddrBase + "product_category.jsp?pType="+"dd";
        connectGetData(urlAddr1);

//        searchArr.addAll(product);

    }

    // NetworkTask에서 값을 가져오는 메소드
    private void connectGetData(String urlAddr) {
        try {
            urlAddrBase = "http://" + macIP + ":8080/makeKit/";
            urlAddr1 = urlAddrBase + "jsp/product_category.jsp?pType="+"dd";

            NetworkTask NetworkTask = new NetworkTask(ProductList.this, urlAddr1, "productSelect");
            Object obj = NetworkTask.execute().get();
            product = (ArrayList<ProductData>) obj;
            adapter = new ProductListAdapter(ProductList.this, R.layout.productitem_layout, product, urlAddrBase); // 아댑터에 값을 넣어준다.
            recyclerView.setAdapter(adapter);  // 리스트뷰에 어탭터에 있는 값을 넣어준다.
            recyclerView.setHasFixedSize(true); // 리사이클러뷰 기존성능 강화
            layoutManager = new LinearLayoutManager(ProductList.this);
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
