package com.example.makekit.makekit_activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.makekit.R;
import com.example.makekit.makekit_bean.User;

import java.util.ArrayList;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.CustomViewHolder> {

    Context mContext = null;
    int layout = 0;
    ArrayList<ProductData> data = null;
    LayoutInflater inflater = null;
    String urlImage;

    public ProductListAdapter(Context mContext, int layout, ArrayList<ProductData> data, String urlImage) {
        this.mContext = mContext;
        this.layout = layout;
        this.data = data;
        this.urlImage = urlImage;
        this.inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    private ArrayList<ProductData> arrayList;

    public ProductListAdapter(ProductList mContext, int productitem_layout, ArrayList<ProductData> arrayList, String urlAddrBase) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ProductListAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.productitem_layout,parent,false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductListAdapter.CustomViewHolder holder, int position) {
//실제 추가될때 생명주기

        holder.product_image.setImageResource(arrayList.get(position).getProduct_image());
        holder.product_title.setText(arrayList.get(position).getProduct_title());
        holder.product_subtitle.setText(arrayList.get(position).getSub_title());
        holder.product_price.setText(arrayList.get(position).getProduct_price());

        holder.itemView.setTag(position);//클릭했을때
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String curTitle = holder.product_title.getText().toString();
                Toast.makeText(v.getContext(),curTitle,Toast.LENGTH_SHORT).show();
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {


                return true;
            }
        });


    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0 );
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected ImageView product_image;
        protected TextView product_title;
        protected TextView product_subtitle;
        protected TextView product_price;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.product_image = (ImageView) itemView.findViewById(R.id.product_image);
            this.product_title = (TextView) itemView.findViewById(R.id.product_title);
            this.product_subtitle = (TextView) itemView.findViewById(R.id.product_subtitle);
            this.product_price = (TextView) itemView.findViewById(R.id.product_price);
        }
    }
}
