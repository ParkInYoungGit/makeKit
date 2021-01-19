package com.example.makekit.makekit_adapter;


import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.makekit.R;
import com.example.makekit.makekit_activity.ProdutctViewActivity;
import com.example.makekit.makekit_activity.SearchActivity;
import com.example.makekit.makekit_bean.Product;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> implements OnSearchItemClickListener {

    String TAG = "SearchActivity";
    private ArrayList<Product> items = new ArrayList<Product>();
    private ArrayList<String> imagedata = null;
    OnSearchItemClickListener listener;
    String urlBase = null;
    private String urlImageReal;

    public SearchAdapter(String url, ArrayList<Product> items){
        this.urlBase = url;
        this.items = items;
    }

    @NonNull
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.search_layout, parent, false);
        return new ViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.ViewHolder holder, int position) {
        if(items.get(position).getProductAFilename().equals("null")){
            urlImageReal = urlBase+"image/20210104114390.jpg";
        }else {
            urlImageReal = urlBase+"image/"+items.get(position).getProductFilename();
        }
        Product item = new Product(items.get(position).getProductNo(), items.get(position).getProductName(), items.get(position).getProductSubTitle(), items.get(position).getProductType(), items.get(position).getProductPrice(), items.get(position).getProductStock(), items.get(position).getProductContent(), items.get(position).getProductFilename(), items.get(position).getProductDfilename(), urlImageReal, items.get(position).getProductInsertDate(), items.get(position).getProductDeleteDate());
        holder.setItem(item);
        holder.iv_productLeft.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent intent = new Intent(v.getContext(), ProdutctViewActivity.class);
                intent.putExtra("productNo", items.get(position).getProductNo());
                v.getContext().startActivity(intent);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    public void addItem(ArrayList<Product> item){
        items.addAll(item);
    }
    public void setItems(ArrayList<Product> items){
        this.items = items;
    }
    public Product getItem(int position){
        return items.get(position);
    }
    public void setItem(int position,Product item){
        items.set(position,item);
    }

    @Override
    public void onItemClick(ViewHolder holder, View view, int position) {
        if(listener != null){
            listener.onItemClick(holder, view, position);
        }
    }
    public void setOnItemClickListener(OnSearchItemClickListener listener){
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        WebView iv_productLeft;
        TextView tv_productNameLeft;
        TextView tv_productPriceLeft;
        private DecimalFormat myFormatter;

        public ViewHolder(@NonNull View itemView, final OnSearchItemClickListener listener) {
            super(itemView);
            iv_productLeft =  itemView.findViewById(R.id.searchImageView);
            tv_productNameLeft = itemView.findViewById(R.id.searchTextViewName);
            tv_productPriceLeft = itemView.findViewById(R.id.searchTextViewPrice);

        }

        public void setItem(Product item){
            iv_productLeft.loadUrl(item.getProductAFilename());
            tv_productNameLeft.setText(item.getProductName());
            myFormatter = new DecimalFormat("###,###");
            String formattedStringPrice = myFormatter.format(Integer.parseInt(item.getProductPrice()));
            tv_productPriceLeft.setText(formattedStringPrice+" 원");
            iv_productLeft.setWebChromeClient(new WebChromeClient());//웹뷰에 크롬 사용 허용//이 부분이 없으면 크롬에서 alert가 뜨지 않음
            iv_productLeft.setWebViewClient(new WebViewClient());//새창열기 없이 웹뷰 내에서 다시 열기//페이지 이동 원활히 하기위해 사용

            iv_productLeft.setFocusable(false);
            iv_productLeft.setClickable(false);
            iv_productLeft.getSettings().setJavaScriptEnabled(false);
            WebSettings webSettings = iv_productLeft.getSettings();
            webSettings.setUseWideViewPort(true);       // wide viewport를 사용하도록 설정
            webSettings.setLoadWithOverviewMode(true);  // 컨텐츠가 웹뷰보다 클 경우 스크린 크기에 맞게 조정
            iv_productLeft.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
            iv_productLeft.setHorizontalScrollBarEnabled(false); //가로 스크롤
            iv_productLeft.setVerticalScrollBarEnabled(false);   //세로 스크롤

            iv_productLeft.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY); // 스크롤 노출 타입
            iv_productLeft.setScrollbarFadingEnabled(false);


            WebSettings wsetting = iv_productLeft.getSettings();
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            {// https 이미지.
                wsetting.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            }
            iv_productLeft.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            iv_productLeft.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
            iv_productLeft.setWebViewClient(new WebViewClient());
            iv_productLeft.setWebChromeClient(new WebChromeClient());
            iv_productLeft.setNetworkAvailable(true);

            //// Sets whether the DOM storage API is enabled.
            iv_productLeft.getSettings().setDomStorageEnabled(true);
            // 웹뷰 멀티 터치 가능하게 (줌기능)
            webSettings.setBuiltInZoomControls(false);   // 줌 아이콘 사용
            webSettings.setSupportZoom(false);
        }
    }
}
