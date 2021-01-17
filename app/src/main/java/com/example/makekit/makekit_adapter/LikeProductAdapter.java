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
import com.example.makekit.makekit_activity.LikeProductActivity;
import com.example.makekit.makekit_activity.ProdutctViewActivity;
import com.example.makekit.makekit_bean.Product;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class LikeProductAdapter extends RecyclerView.Adapter<LikeProductAdapter.MyViewHolder> {

    private ArrayList<Product> mDataset;
    private String urlImage;
    private String urlImageReal;
    private AdapterView.OnItemClickListener mListener = null;
    DecimalFormat myFormatter;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        WebView webViewLeft;
        TextView productNameLeft;
        TextView productPriceLeft;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            webViewLeft = itemView.findViewById(R.id.likeProduct_WebView);
            productNameLeft = itemView.findViewById(R.id.likeProduct_ProductName_TV);
            productPriceLeft = itemView.findViewById(R.id.likeProduct_ProductPrice_TV);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
                    //////// 클릭했을 때 상품 상세페이지로 이동할 수 있도록 Intent 적용

                }
            });
        }


    }

    public LikeProductAdapter(LikeProductActivity likeProductActivity, int layout, ArrayList<Product> myDataset, String urlimage){
        mDataset = myDataset;
        urlImage = urlimage;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.likeproduct_layout, parent, false);
        //     반복할 xml 파일
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        //데이터를 받은걸 올리기
        if(mDataset.get(position).getProductAFilename().equals("null")){
            urlImageReal = urlImage+"ic_default.jpg";
        }else {
            urlImageReal = urlImage+mDataset.get(position).getProductFilename();
        }
        myFormatter = new DecimalFormat("###,###");
        String formattedStringPrice = myFormatter.format(Integer.parseInt(mDataset.get(position).getProductPrice()));
        holder.webViewLeft.loadUrl(urlImageReal);
        holder.productNameLeft.setText("["+mDataset.get(position).getProductType()+"]"+ mDataset.get(position).getProductName());
        holder.productPriceLeft.setText(formattedStringPrice+" 원");

        // Initial webview
        holder.webViewLeft.setWebViewClient(new WebViewClient());



        // Enable JavaScript
        holder.webViewLeft.getSettings().setJavaScriptEnabled(true);
        holder.webViewLeft.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        // Enable Zoom
        holder.webViewLeft.getSettings().setBuiltInZoomControls(true);
        holder.webViewLeft.getSettings().setSupportZoom(true);
        holder.webViewLeft.getSettings().setSupportZoom(true); //zoom mode 사용.
        holder.webViewLeft.getSettings().setDisplayZoomControls(false); //줌 컨트롤러를 안보이게 셋팅.


        // Adjust web display
        holder.webViewLeft.setBackgroundColor(0);
        holder.webViewLeft.getSettings().setLoadWithOverviewMode(true);
        holder.webViewLeft.getSettings().setUseWideViewPort(true);
        holder.webViewLeft.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
        holder.webViewLeft.setInitialScale(15);
        holder.webViewLeft.setOnTouchListener(new View.OnTouchListener() {
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
