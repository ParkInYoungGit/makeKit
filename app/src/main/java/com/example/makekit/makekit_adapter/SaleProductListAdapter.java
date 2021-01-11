package com.example.makekit.makekit_adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.makekit.R;
import com.example.makekit.makekit_activity.SaleProductListActivity;
import com.example.makekit.makekit_bean.Order;

import java.util.ArrayList;

public class SaleProductListAdapter extends RecyclerView.Adapter<SaleProductListAdapter.MyViewHolder> {

    private ArrayList<Order> mDataset;
    private AdapterView.OnItemClickListener mListener = null;
    private String urlImage;
    private String urlImageReal;

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
        holder.productQuantity.setText(mDataset.get(position).getProductStock());
        holder.productPrice.setText("가격(1개) : "+ mDataset.get(position).getProductPrice());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        this.mListener = listener ;
    }
}
