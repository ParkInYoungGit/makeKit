package com.example.makekit.makekit_adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.makekit.R;
import com.example.makekit.makekit_activity.ProdutctViewActivity;
import com.example.makekit.makekit_bean.ProductData;

import java.util.ArrayList;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.CustomViewHolder> {
    ArrayList<ProductData> product;

    private String email;
    private String macIP;
    String pNo;
    final static String TAG = "ProductListAdapter";

    Context mContext = null;
    int layout = 0;
    ArrayList<ProductData> data = null;
    LayoutInflater inflater = null;
    String urlImage;

    private String urlImageReal;

    public ProductListAdapter(Context mContext, int layout, ArrayList<ProductData> data, String urlImage) {
        this.mContext = mContext;
        this.layout = layout;
        this.data = data;
        this.urlImage = urlImage;
        this.inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

//    private ArrayList<ProductData> arrayList;
//
//    public ProductListAdapter(ProductList mContext, int productitem_layout, ArrayList<ProductData> arrayList, String urlAddrBase) {
//        this.arrayList = arrayList;
//    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.productitem_layout, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(ProductListAdapter.CustomViewHolder holder, int position) {
//실제 추가될때 생명주기


        if (data.get(position).getProduct_image().equals("null")) {
            holder.product_image.setVisibility(View.INVISIBLE);
        } else {

            Log.v(TAG, urlImage + "image/" + data.get(position).getProduct_image());
            urlImageReal = urlImage + "image/" + data.get(position).getProduct_image();
            holder.product_image.loadUrl(urlImageReal);

            WebSettings webSettings = holder.product_image.getSettings();

            webSettings.setUseWideViewPort(true);       // wide viewport를 사용하도록 설정
            webSettings.setLoadWithOverviewMode(true);  // 컨텐츠가 웹뷰보다 클 경우 스크린 크기에 맞게 조정
            // Initial webview
            holder.product_image.setWebViewClient(new WebViewClient());

            // Enable JavaScript
            holder.product_image.getSettings().setJavaScriptEnabled(true);
            holder.product_image.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

            // WebView 세팅
//            holder.product_image.setBackgroundColor(R.color.white); //배경
            holder.product_image.setHorizontalScrollBarEnabled(false); //가로 스크롤
            holder.product_image.setVerticalScrollBarEnabled(false);   //세로 스크롤
            holder.product_image.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY); // 스크롤 노출 타입
            holder.product_image.setScrollbarFadingEnabled(false);
            holder.product_image.setInitialScale(30);

            // 웹뷰 멀티 터치 가능하게 (줌기능)
            webSettings.setBuiltInZoomControls(false);   // 줌 아이콘 사용
            webSettings.setSupportZoom(false);

        }


        holder.product_image.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        holder.product_image.setHorizontalScrollBarEnabled(false); //가로 스크롤
        holder.product_image.setVerticalScrollBarEnabled(false);   //세로 스크롤

        holder.product_image.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY); // 스크롤 노출 타입
        holder.product_image.setScrollbarFadingEnabled(false);


        holder.product_title.setText(data.get(position).getProduct_title());
        holder.product_subtitle.setText(data.get(position).getSub_title());
        holder.product_price.setText(data.get(position).getProduct_price());


        holder.itemView.setTag(position);//클릭했을때
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, ProdutctViewActivity.class);

                pNo = data.get(position).getProductNo();
                Log.v("pNo",pNo);
                intent.putExtra("macIP", macIP);
                intent.putExtra("useremail", email);
                intent.putExtra("productNo",pNo);

                context.startActivity(intent);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {


                return true;
            }
        });


    }

    @Override
    public int getItemCount() {
        return (null != data ? data.size() : 0);
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder {

        protected WebView product_image;
        protected TextView product_title;
        protected TextView product_subtitle;
        protected TextView product_price;


        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.product_image = itemView.findViewById(R.id.product_image);
            this.product_title = itemView.findViewById(R.id.product_title);
            this.product_subtitle = itemView.findViewById(R.id.product_subtitle);
            this.product_price = itemView.findViewById(R.id.product_price);
        }
    }
}
