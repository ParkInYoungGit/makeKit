package com.example.makekit.makekit_adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.makekit.R;
import com.example.makekit.makekit_activity.LikeProductActivity;
import com.example.makekit.makekit_bean.Product;

import java.util.ArrayList;

public class LikeProductAdapter extends RecyclerView.Adapter<LikeProductAdapter.MyViewHolder> {

    private ArrayList<Product> mDataset;

    public class MyViewHolder extends RecyclerView.ViewHolder {



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }

    public LikeProductAdapter(LikeProductActivity likeProductActivity, int layout, ArrayList<Product> myDataset){
        mDataset = myDataset;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
//        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.member, parent, false);
//        //     반복할 xml 파일
//        MyViewHolder vh = new MyViewHolder(v);
//        return vh;
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        //데이터를 받은걸 올리기
//        holder.mName.setText(mDataset.get(position).getMember());
//        holder.mPicture.setImageResource(mDataset.get(position).getIcon());
//        holder.mNumber.setText(mDataset.get(position).getNumber());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
