package com.example.makekit.makekit_adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.makekit.R;
import com.example.makekit.makekit_bean.Order;
import com.example.makekit.makekit_bean.Review;

import java.util.ArrayList;

// review 리스트
public class WriteReviewAdapter extends RecyclerView.Adapter<WriteReviewAdapter.MyViewHolder> {

    final static String TAG = "ReviewAdapter";

    private Context mContext = null;
    private int layout = 0;
    private ArrayList<Order> data = null;
    private LayoutInflater inflater = null;
    private String urlImage;
    private String urlImageReal;

    public WriteReviewAdapter(Context mContext, int layout, ArrayList<Order> data, String urlImage) {
        this.mContext = mContext;
        this.layout = layout;
        this.data = data;
        this.urlImage = urlImage;
        this.inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.custom_write_review, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        // 1. FileName
        Log.v(TAG, data.get(position).getProductFilename());
        Log.v(TAG, urlImage);

        // if(data.get(position).getProductFilename().equals("null")){
//        if(data.get(position).getProductFilename().equals("null")){
//            holder.img_ProductImage.setVisibility(View.INVISIBLE);
        holder.img_ProductImage.setVisibility(View.VISIBLE);

        holder.reviewlist_productName.setText(data.get(position).getProductName());
        holder.reviewlist_productQuantity.setText(data.get(position).getOrderQuantity());

        Log.v(TAG, urlImage + "image/" + data.get(position).getProductFilename());
        urlImageReal = urlImage + "image/" + data.get(position).getProductFilename();
        // holder.img_reviewImage.loadUrl(urlImageReal);

        // Initial webview
        holder.img_ProductImage.setWebViewClient(new WebViewClient());


        // Enable JavaScript
        holder.img_ProductImage.getSettings().setJavaScriptEnabled(true);
        holder.img_ProductImage.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        // Enable Zoom
        holder.img_ProductImage.getSettings().setBuiltInZoomControls(true);
        holder.img_ProductImage.getSettings().setSupportZoom(true);
        holder.img_ProductImage.getSettings().setSupportZoom(true); //zoom mode 사용.
        holder.img_ProductImage.getSettings().setDisplayZoomControls(false); //줌 컨트롤러를 안보이게 셋팅.


        // Adjust web display
        holder.img_ProductImage.setBackgroundColor(0);
        holder.img_ProductImage.getSettings().setLoadWithOverviewMode(true);
        holder.img_ProductImage.getSettings().setUseWideViewPort(true);
        holder.img_ProductImage.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
        holder.img_ProductImage.setInitialScale(15);

        // url은 알아서 설정 예) http://m.naver.com/
        holder.img_ProductImage.loadUrl(urlImageReal); // 접속 URL


    }

    @Override
    public int getItemCount() {
//        Log.v("AddressAdapter", "data.size = " + String.valueOf(data.size()));
        return data.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        WebView img_ProductImage;
        TextView reviewlist_productName, tv_productQuantity, reviewlist_productQuantity;
        TextView Textview_RegisterReview;
//        LinearLayout li_reviewImage, li_reviewNonImage;

        public MyViewHolder(View itemView) {
            super(itemView);
//            li_reviewImage = itemView.findViewById(R.id.linearImage_reviewlist);
//            li_reviewNonImage = itemView.findViewById(R.id.linearNonImage_reviewlist);

            img_ProductImage = itemView.findViewById(R.id.reviewlist_sales_productImage);
            reviewlist_productName = itemView.findViewById(R.id.reviewlist_productName);
            tv_productQuantity = itemView.findViewById(R.id.tv_productQuantity);
            reviewlist_productQuantity = itemView.findViewById(R.id.reviewlist_productQuantity);
            Textview_RegisterReview = itemView.findViewById(R.id.Textview_RegisterReview);

        }

//    @Override
//    public long getItemId(int position) {
//        return Integer.parseInt(data.get(position).getOrderDetailNo());
//    }

    }
}