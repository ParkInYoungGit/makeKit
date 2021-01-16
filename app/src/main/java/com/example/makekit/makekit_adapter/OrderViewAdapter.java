package com.example.makekit.makekit_adapter;

import android.content.Context;
import android.util.Log;
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
import com.example.makekit.makekit_bean.Payment;
import com.example.makekit.makekit_sharVar.SharVar;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class OrderViewAdapter extends BaseAdapter {

    final static String TAG = "OrderViewAdapter";

    Context mContext = null;
    int layout = 0;
    //ArrayList<Order> data = null;
    ///////////////////////////////
    // 1/15 경미 추가
    ///////////////////////////////
    ArrayList<Payment> data = null;
    ///////////////////////////////
    LayoutInflater inflater = null;
    String image, realImage;

//    public OrderViewAdapter(Context mContext, int layout, ArrayList<Order> data, String image) {
//        this.mContext = mContext;
//        this.layout = layout;
//        this.data = data;
//        this.image = image;
//        this.inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//    }

    ///////////////////////////////
    // 1/15 경미 추가
    ///////////////////////////////
    public OrderViewAdapter(Context mContext, int layout, ArrayList<Payment> data, String image) {
        this.mContext = mContext;
        this.layout = layout;
        this.data = data;
        this.image = image;
        this.inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    ///////////////////////////////


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position).getProductName();
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
        TextView order_productPrice = convertView.findViewById(R.id.order_productPrice_view);

        //int divTotalPrice = Integer.parseInt(data.get(position).getOrderQuantity())*Integer.parseInt(data.get(position).getProductPrice());

//        order_productName.setText(data.get(position).getUserName());
//        order_productQuantity.setText(data.get(position).getUserTel());
//        order_productTotalPrice.setText(data.get(position).getUserAddress());
        DecimalFormat myFormatter = new DecimalFormat("###,###");
        String formattedStringPrice = myFormatter.format(Integer.parseInt(data.get(position).getProductPrice()));
        String formattedStringPrice1 = myFormatter.format((Integer.parseInt(data.get(position).getProductPrice()) * Integer.parseInt(data.get(position).getCartQuantity())+2500));
        order_productName.setText(data.get(position).getProductName());
        order_productQuantity.setText(data.get(position).getCartQuantity() + "개");
        order_productPrice.setText(formattedStringPrice + "원");
        order_productTotalPrice.setText(formattedStringPrice1 + "원");
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
        realImage = SharVar.urlAddrBase +"image/"+data.get(position).getImage();
        Log.v(TAG, "realImag : " + realImage);
        order_productImage.loadUrl(realImage); // 접속 URL




        return convertView;
    }
}
