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

import com.example.makekit.makekit_activity.PurchaseListActivity;
import com.example.makekit.R;
import com.example.makekit.makekit_bean.Order;

import java.util.ArrayList;

public class PurchaseListAdapter extends RecyclerView.Adapter<PurchaseListAdapter.MyViewHolder> {
    private ArrayList<Order> mDataset;
    private AdapterView.OnItemClickListener mListener = null;
    private String urlImage;
    private String urlImageReal;

    public PurchaseListAdapter(PurchaseListActivity purchaseListActivity, int sales_list_layout, ArrayList<Order> orders, String urlimage) {
        this.mDataset = orders;
        this.urlImage = urlimage;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView orderDate, productName, productQuantity, productPrice;
        WebView webView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            orderDate = itemView.findViewById(R.id.Saleslist_orderDate_TV);
            productName = itemView.findViewById(R.id.Salelists_ProductName_TV);
            productQuantity = itemView.findViewById(R.id.Salelists_orderQuantity_TV);
            productPrice = itemView.findViewById(R.id.Salelists_orderPrice_TV);
            webView = itemView.findViewById(R.id.Salelists_WebView);

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
                .inflate(R.layout.purchase_list_layout, parent, false);
        //     반복할 xml 파일
        PurchaseListAdapter.MyViewHolder vh = new PurchaseListAdapter.MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull PurchaseListAdapter.MyViewHolder holder, int position) {

        if(mDataset.get(position).getProductAFilename().equals("null")){
            urlImageReal = urlImage+"ic_default.jpg";
        }else {
            urlImageReal = urlImage+mDataset.get(position).getProductAFilename();
        }
        holder.orderDate.setText("주문 번호 : " + mDataset.get(position).getOrderNo());
        holder.webView.loadUrl(urlImageReal);
        holder.productName.setText(mDataset.get(position).getProductName());
        holder.productQuantity.setText("수량 : "+ mDataset.get(position).getOrderQuantity());
        holder.productPrice.setText("총 가격 : "+mDataset.get(position).getOrderTotalPrice());

        holder.webView.setWebViewClient(new WebViewClient());



        // Enable JavaScript
        holder.webView.getSettings().setJavaScriptEnabled(true);
        holder.webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        // Enable Zoom
        holder.webView.getSettings().setBuiltInZoomControls(true);
        holder.webView.getSettings().setSupportZoom(true);
        holder.webView.getSettings().setSupportZoom(true); //zoom mode 사용.
        holder.webView.getSettings().setDisplayZoomControls(false); //줌 컨트롤러를 안보이게 셋팅.


        // Adjust web display
        holder.webView.setBackgroundColor(0);
        holder.webView.getSettings().setLoadWithOverviewMode(true);
        holder.webView.getSettings().setUseWideViewPort(true);
        holder.webView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
        holder.webView.setInitialScale(15);

        // url은 알아서 설정 예) http://m.naver.com/
        holder.webView.loadUrl(urlImageReal); // 접속 URL
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        this.mListener = listener ;
    }

}

