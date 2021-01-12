package com.example.makekit.makekit_adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.makekit.R;
import com.example.makekit.makekit_activity.OrderActivity;
import com.example.makekit.makekit_activity.SaleProductListActivity;
import com.example.makekit.makekit_bean.Order;
import com.example.makekit.makekit_bean.Payment;

import java.util.ArrayList;

public class OrderProductListAdapter extends RecyclerView.Adapter<OrderProductListAdapter.MyViewHolder> {

    private ArrayList<Payment> mDataset;
    private AdapterView.OnItemClickListener mListener = null;
    private String urlImage;
    private String urlImageReal;

    public OrderProductListAdapter(OrderActivity orderActivity, int layout, ArrayList<Payment> payments, String urlimage){
        this.mDataset = payments;
        this.urlImage = urlimage;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView order_productName, order_productPrice, order_productCount;
        WebView order_productImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            order_productImage = itemView.findViewById(R.id.order_productImage);
            order_productName = itemView.findViewById(R.id.order_productName);
            order_productPrice = itemView.findViewById(R.id.order_productPrice);
            order_productCount = itemView.findViewById(R.id.order_productCount);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_order_product, parent, false);
        //     반복할 xml 파일
        OrderProductListAdapter.MyViewHolder vh = new OrderProductListAdapter.MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if(mDataset.get(position).getImage().equals("null")){
            urlImageReal = urlImage+"ic_default.jpg";
        }else {
            urlImageReal = urlImage+mDataset.get(position).getImage();
        }
        holder.order_productImage.loadUrl(urlImageReal);
        holder.order_productName.setText(mDataset.get(position).getProductName());
        holder.order_productPrice.setText(mDataset.get(position).getProductPrice());
        holder.order_productCount.setText(mDataset.get(position).getCartQuantity());


        holder.order_productImage.setWebViewClient(new WebViewClient());



        // Enable JavaScript
        holder.order_productImage.getSettings().setJavaScriptEnabled(true);
        holder.order_productImage.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        // Enable Zoom
        holder.order_productImage.getSettings().setBuiltInZoomControls(true);
        holder.order_productImage.getSettings().setSupportZoom(true);
        holder.order_productImage.getSettings().setSupportZoom(true); //zoom mode 사용.
        holder.order_productImage.getSettings().setDisplayZoomControls(false); //줌 컨트롤러를 안보이게 셋팅.


        // Adjust web display
        holder.order_productImage.setBackgroundColor(0);
        holder.order_productImage.getSettings().setLoadWithOverviewMode(true);
        holder.order_productImage.getSettings().setUseWideViewPort(true);
        holder.order_productImage.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
        holder.order_productImage.setInitialScale(15);

        // url은 알아서 설정 예) http://m.naver.com/
        holder.order_productImage.loadUrl(urlImageReal); // 접속 URL
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        this.mListener = listener ;
    }
}
