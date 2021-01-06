package com.example.makekit.makekit_fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.makekit.R;
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
        WebView iv_productRight =  (WebView) convertView.findViewById(R.id.searchImageViewRight);
        TextView tv_productNameRight = (TextView) convertView.findViewById(R.id.searchTextViewNameRight);
        TextView tv_productPriceRight = (TextView) convertView.findViewById(R.id.searchTextViewPriceRight);
        if(data.get(position).getProductAFilename().equals("null")){
            urlImageReal = urlImage+"ic_default.jpg";
        }else {
            urlImageReal = urlImage+data.get(position).getProductAFilename();
        }

        if((position % 2) == 1){
            iv_productLeft.loadUrl(urlImageReal);
            tv_productNameLeft.setText("["+data.get(position).getProductType()+"]"+ data.get(position).getProductName());
            tv_productPriceLeft.setText(data.get(position).getProductPrice()+" 원");

        }else{
            iv_productRight.loadUrl(urlImageReal);
            tv_productNameRight.setText("["+data.get(position).getProductType()+"]"+ data.get(position).getProductName());
            tv_productPriceRight.setText(data.get(position).getProductPrice()+" 원");
        }

        return convertView;
    }
}
