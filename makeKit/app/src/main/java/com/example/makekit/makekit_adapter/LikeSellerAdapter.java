package com.example.makekit.makekit_adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.makekit.R;
import com.example.makekit.makekit_activity.LikeSellerActivity;
import com.example.makekit.makekit_activity.ProdutctViewActivity;
import com.example.makekit.makekit_activity.SaleProductListActivity;
import com.example.makekit.makekit_bean.User;

import java.util.ArrayList;

public class LikeSellerAdapter extends RecyclerView.Adapter<LikeSellerAdapter.MyViewHolder> {

    private ArrayList<User> users;
    private AdapterView.OnItemClickListener mListener = null;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView, goSeller;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.likeSellerID_TV);
            goSeller = itemView.findViewById(R.id.goLiekSeller);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ////////////////////////////////////////////
                    ///// 클릭했을 떄 판매자의 판매목록으로 가기
                }
            });
        }
    }

    public LikeSellerAdapter(LikeSellerActivity likeSellerActivity, int layout, ArrayList<User> myDataset){
        users = myDataset;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.likeseller_layout, parent, false);
        //     반복할 xml 파일
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textView.setText(users.get(position).getEmail());
        holder.goSeller.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent intent = new Intent(v.getContext(), SaleProductListActivity.class);
                intent.putExtra("seller", users.get(position).getEmail());
                v.getContext().startActivity(intent);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        this.mListener = listener ;
    }
}
