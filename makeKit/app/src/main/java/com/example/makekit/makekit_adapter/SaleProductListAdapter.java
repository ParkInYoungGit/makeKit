package com.example.makekit.makekit_adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
import com.example.makekit.makekit_activity.ProdutctViewActivity;
import com.example.makekit.makekit_activity.SaleProductListActivity;
import com.example.makekit.makekit_bean.Order;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SaleProductListAdapter extends RecyclerView.Adapter<SaleProductListAdapter.MyViewHolder> {

    private ArrayList<Order> mDataset;
    private AdapterView.OnItemClickListener mListener = null;
    private String urlImage;
    private String urlImageReal;
    DecimalFormat myFormatter;

    public SaleProductListAdapter(SaleProductListActivity saleListActivity, int layout, ArrayList<Order> orders, String urlimage){
        this.mDataset = orders;
        this.urlImage = urlimage;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView orderDate, productName, productQuantity, productPrice;
        WebView webView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            orderDate = itemView.findViewById(R.id.salelist_orderDate_TV);
            webView = itemView.findViewById(R.id.salelist_WebView);
            productName = itemView.findViewById(R.id.salelist_ProductName_TV);
            productQuantity = itemView.findViewById(R.id.salelist_orderQuantity_TV);
            productPrice = itemView.findViewById(R.id.salelist_orderPrice_TV);

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
                .inflate(R.layout.sales_product_list_layout, parent, false);
        //     반복할 xml 파일
        SaleProductListAdapter.MyViewHolder vh = new SaleProductListAdapter.MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if(mDataset.get(position).getProductAFilename().equals("null")){
            urlImageReal = urlImage+"ic_default.jpg";
        }else {
            urlImageReal = urlImage+mDataset.get(position).getProductAFilename();
        }
        holder.orderDate.setText("상품 번호 : " + mDataset.get(position).getProductNo());
        holder.webView.loadUrl(urlImageReal);
        holder.productName.setText(mDataset.get(position).getProductName());
        holder.productQuantity.setText("재고 : "+mDataset.get(position).getProductStock());
        myFormatter = new DecimalFormat("###,###");
        String formattedStringPrice = myFormatter.format(Integer.parseInt(mDataset.get(position).getProductPrice()));
        holder.productPrice.setText("가격(1개) : "+ formattedStringPrice+" 원");

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

        holder.webView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent intent = new Intent(v.getContext(), ProdutctViewActivity.class);
                intent.putExtra("productNo", mDataset.get(position).getProductNo());
                v.getContext().startActivity(intent);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        this.mListener = listener ;
    }
}
