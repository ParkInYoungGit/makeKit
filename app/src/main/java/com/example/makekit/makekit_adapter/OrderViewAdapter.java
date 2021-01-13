package com.example.makekit.makekit_adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.makekit.R;
import com.example.makekit.makekit_bean.Order;

import java.util.ArrayList;

public class OrderViewAdapter extends BaseAdapter {

    Context mContext = null;
    int layout = 0;
    ArrayList<Order> data = null;
    LayoutInflater inflater = null;
    String image, realImage;

    public OrderViewAdapter(Context mContext, int layout, ArrayList<Order> data, String image) {
        this.mContext = mContext;
        this.layout = layout;
        this.data = data;
        this.image = image;
        this.inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position).getGoods_productNo();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(this.layout, parent, false);
        }
        WebView order_productImage = convertView.findViewById(R.id.order_productImage);
        TextView order_productName = convertView.findViewById(R.id.order_productName);
        TextView order_productQuantity = convertView.findViewById(R.id.order_productQuantity);
        TextView order_productTotalPrice = convertView.findViewById(R.id.order_productTotalPrice);

        int divTotalPrice = Integer.parseInt(data.get(position).getOrderQuantity())*Integer.parseInt(data.get(position).getProductPrice());

        order_productName.setText(data.get(position).getProductName());
        order_productQuantity.setText(data.get(position).getOrderQuantity());
        order_productTotalPrice.setText(Integer.toString(divTotalPrice));

        order_productImage.setWebViewClient(new WebViewClient());



        // Enable JavaScript
        order_productImage.getSettings().setJavaScriptEnabled(true);
        order_productImage.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        // Enable Zoom
        order_productImage.getSettings().setBuiltInZoomControls(true);
        order_productImage.getSettings().setSupportZoom(true);
        order_productImage.getSettings().setSupportZoom(true); //zoom mode 사용.
        order_productImage.getSettings().setDisplayZoomControls(false); //줌 컨트롤러를 안보이게 셋팅.


        // Adjust web display
        order_productImage.setBackgroundColor(0);
        order_productImage.getSettings().setLoadWithOverviewMode(true);
        order_productImage.getSettings().setUseWideViewPort(true);
        order_productImage.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
        order_productImage.setInitialScale(15);

        // url은 알아서 설정 예) http://m.naver.com/
        realImage = image+"image/"+data.get(position).getProductFilename();
        order_productImage.loadUrl(realImage); // 접속 URL




        return convertView;
    }
}
