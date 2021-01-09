package com.example.makekit.makekit_adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.makekit.R;
import com.example.makekit.makekit_bean.Product;
import com.example.makekit.makekit_bean.Review;
import com.example.makekit.makekit_fragment.ProductReviewFragment;

import java.util.ArrayList;

// review 리스트
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder>{

    final static String TAG = "ProductAdapter";

    private Context mContext = null;
    private int layout = 0;
    private ArrayList<Review> data = null;
    private LayoutInflater inflater = null;
    private String urlImage;
    private String urlImageReal;

    public ProductAdapter(Context mContext, int layout, ArrayList<Review> data, String urlImage) {
        this.mContext = mContext;
        this.layout = layout;
        this.data = data;
        this.urlImage = urlImage;
        this.inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.custom_productview_review, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

//        // 프로필 사진에 아무것도 설정 안해놨을 경우. 그냥 mipmap 사진 갖다 씀
//        if (data.get(position).getAddressImage().contains("baseline_account_circle_black_48")){
//            holder.img.setImageResource(R.mipmap.baseline_account_circle_black_48);
//        }else{ // 사진을 설정해 놓은 경우. network 통해 다운받아서 보여줌. 이때 이미 폰에 존재하면 중복생성X
//
//            urlAddr = "http://" + ShareVar.macIP + ":8080/test/";
//            urlAddr = urlAddr + data.get(position).getAddressImage();
//            Log.v("AddressAdapter", "urlAddr = " + urlAddr);
//            ViewImageNetworkTask networkTask = new ViewImageNetworkTask(mContext, urlAddr, holder.img);
//            networkTask.execute(100); // 10초. 이것만 쓰면 pre post do back 등 알아서 실행
//        }

        Log.v(TAG, data.get(position).getReviewImage());
        Log.v(TAG, urlImage);

        if(data.get(position).getReviewImage().equals("null")){
            holder.img_reviewImage.setVisibility(View.INVISIBLE);
        }else {

            Log.v(TAG, urlImage + "image/" + data.get(position).getReviewImage());
            urlImageReal = urlImage+ "image/" + data.get(position).getReviewImage();
            holder.img_reviewImage.loadUrl(urlImageReal);

            WebSettings webSettings = holder.img_reviewImage.getSettings();

            webSettings.setUseWideViewPort(true);       // wide viewport를 사용하도록 설정
            webSettings.setLoadWithOverviewMode(true);  // 컨텐츠가 웹뷰보다 클 경우 스크린 크기에 맞게 조정
        }


        holder.img_reviewImage.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        holder.img_reviewImage.setHorizontalScrollBarEnabled(false); //가로 스크롤
        holder.img_reviewImage.setVerticalScrollBarEnabled(false);   //세로 스크롤

        holder.img_reviewImage.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY); // 스크롤 노출 타입
        holder.img_reviewImage.setScrollbarFadingEnabled(false);


        holder.tv_reviewContent.setText(data.get(position).getReviewContent());
        holder.tv_reviewWriter.setText(data.get(position).getReviewWriterName());
        holder.tv_reviewWriteDate.setText(data.get(position).getReviewDate());
        holder.tv_reviewStar.setText(data.get(position).getReviewStar() + " 점");


    }

    @Override
    public int getItemCount() {
//        Log.v("AddressAdapter", "data.size = " + String.valueOf(data.size()));
        return data.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        WebView img_reviewImage;
        TextView tv_reviewWriter, tv_reviewStar, tv_reviewContent, tv_reviewWriteDate;

        public MyViewHolder(View itemView) {
            super(itemView);

            img_reviewImage =  itemView.findViewById(R.id.imgReview_productcview);
            tv_reviewWriter =  itemView.findViewById(R.id.writerReview_productview);
            tv_reviewStar =  itemView.findViewById(R.id.StarReview_productview);
            tv_reviewContent =  itemView.findViewById(R.id.contentReview_productview);
            tv_reviewWriteDate =  itemView.findViewById(R.id.writerDateReview_productview);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

//    static class WebViewClientClass extends WebViewClient {//페이지 이동
//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            view.loadUrl(url);
//            return true;
//        }
//    }

}
