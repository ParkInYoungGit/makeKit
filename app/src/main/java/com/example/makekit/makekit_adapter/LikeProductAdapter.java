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
import com.example.makekit.makekit_activity.LikeProductActivity;
import com.example.makekit.makekit_bean.Product;
import com.example.makekit.makekit_fragment.ProductAdapter;

import java.util.ArrayList;

public class LikeProductAdapter extends RecyclerView.Adapter<LikeProductAdapter.MyViewHolder> {

    private ArrayList<Product> mDataset;
    private String urlImage;
    private String urlImageReal;
    private AdapterView.OnItemClickListener mListener = null;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        WebView webViewLeft;
        WebView webViewRight;
        TextView productNameLeft;
        TextView productNameRight;
        TextView productPriceLeft;
        TextView productPriceRight;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            webViewLeft = itemView.findViewById(R.id.searchImageViewLeft);
            productNameLeft = itemView.findViewById(R.id.searchTextViewNameLeft);
            productPriceLeft = itemView.findViewById(R.id.searchTextViewPriceLeft);

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
                .inflate(R.layout.search_layout, parent, false);
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
            urlImageReal = urlImage+mDataset.get(position).getProductAFilename();
        }

        if((position % 2) == 1){
            holder.webViewLeft.loadUrl(urlImageReal);
            holder.productNameLeft.setText("["+mDataset.get(position).getProductType()+"]"+ mDataset.get(position).getProductName());
            holder.productPriceLeft.setText(mDataset.get(position).getProductPrice()+" 원");

        }else{
            holder.webViewRight.loadUrl(urlImageReal);
            holder.productNameRight.setText("["+mDataset.get(position).getProductType()+"]"+ mDataset.get(position).getProductName());
            holder.productPriceRight.setText(mDataset.get(position).getProductPrice()+" 원");
        }

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        this.mListener = listener ;
    }

}
