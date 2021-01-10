package com.example.makekit.makekit_adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.makekit.R;
import com.example.makekit.makekit_activity.SearchActivity;
import com.example.makekit.makekit_bean.Product;

import java.util.ArrayList;

public class SearchAdapter extends BaseAdapter {

    private Context mContext = null;
    private int layout = 0;
    private ArrayList<Product> data = null;
    private LayoutInflater inflater = null;
    private String urlImage;
    private String urlImageReal;

    public SearchAdapter(Context mContext, int layout, ArrayList<Product> data, String urlImage) {
        this.mContext = mContext;
        this.layout = layout;
        this.data = data;
        this.urlImage = urlImage;
        this.inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position).getProductNo();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = inflater.inflate(this.layout, parent, false);
        }
        WebView iv_productLeft =  (WebView) convertView.findViewById(R.id.searchImageViewLeft);
        TextView tv_productNameLeft = (TextView) convertView.findViewById(R.id.searchTextViewNameLeft);
        TextView tv_productPriceLeft = (TextView) convertView.findViewById(R.id.searchTextViewPriceLeft);
        if(data.get(position).getProductAFilename().equals("null")){
            urlImageReal = urlImage+"ic_default.jpg";
        }else {
            urlImageReal = urlImage+data.get(position).getProductAFilename();
        }

        iv_productLeft.loadUrl(urlImageReal);
        tv_productNameLeft.setText("["+data.get(position).getProductType()+"]"+ data.get(position).getProductName());
        tv_productPriceLeft.setText(data.get(position).getProductPrice()+" 원");
        iv_productLeft.setWebChromeClient(new WebChromeClient());//웹뷰에 크롬 사용 허용//이 부분이 없으면 크롬에서 alert가 뜨지 않음
        // iv_productLeft.setWebViewClient(new SearchActivity.We());//새창열기 없이 웹뷰 내에서 다시 열기//페이지 이동 원활히 하기위해 사용

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
//        img_peopleImg.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        iv_productLeft.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        iv_productLeft.setWebViewClient(new WebViewClient());
        iv_productLeft.setWebChromeClient(new WebChromeClient());
        iv_productLeft.setNetworkAvailable(true);

        //// Sets whether the DOM storage API is enabled.
        iv_productLeft.getSettings().setDomStorageEnabled(true);
        // 웹뷰 멀티 터치 가능하게 (줌기능)
        webSettings.setBuiltInZoomControls(false);   // 줌 아이콘 사용
        webSettings.setSupportZoom(false);

        return convertView;
    }
}
