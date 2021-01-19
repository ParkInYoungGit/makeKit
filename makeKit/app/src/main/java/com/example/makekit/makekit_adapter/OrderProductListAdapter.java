package com.example.makekit.makekit_adapter;

import android.content.Context;
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
import com.example.makekit.makekit_bean.Cart;
import com.example.makekit.makekit_bean.Order;
import com.example.makekit.makekit_bean.Payment;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class OrderProductListAdapter extends RecyclerView.Adapter<OrderProductListAdapter.MyViewHolder> {
    /////////////////////////////////////////////////////
    // 1/14 Kyeongmi 추가
    /////////////////////////////////////////////////////
    private ArrayList<Cart> mDataset;

    /////////////////////////////////////////////////////
    // 기존 사용 메소드
    /////////////////////////////////////////////////////
    //private ArrayList<Payment> mDataset;

    private AdapterView.OnItemClickListener mListener = null;
    private String urlImage;
    private String urlImageReal;
    private int layout = 0;
    private Context orderActivity;
    private Context mContext = null;
    private LayoutInflater inflater = null;

    public OrderProductListAdapter(OrderActivity orderActivity, int layout, ArrayList<Cart> payments, String urlimage){
        this.orderActivity = orderActivity;
        this.layout = layout;
        this.mDataset = payments;
        this.urlImage = urlimage;
        this.inflater = (LayoutInflater) orderActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        // 빈에서 이미지를 가져옴 getImage
        if(mDataset.get(position).getProductImage().equals("null")){           // getImage가 Null 일 경우 ic_default.jpg 넣어줌
            urlImageReal = urlImage+"ic_default.jpg";
        }else {
            urlImageReal = urlImage+mDataset.get(position).getProductImage();  // getImage가 Null 아닐 경우 가져온 이미지 이름 넣어줌
        }
        holder.order_productImage.loadUrl(urlImageReal);
        // 이미지 주소를 업로드해줌 urlImageReal(http://192.168.200.193:8080/makeKit/image/)+getImage(ic_default or 이미지명)


        holder.order_productName.setText(mDataset.get(position).getProductName());      // 빈에 담긴 정보를 넣어줌
        DecimalFormat myFormatter = new DecimalFormat("###,###");
        String formattedStringPrice = myFormatter.format(Integer.parseInt(mDataset.get(position).getProductPrice()));
        holder.order_productPrice.setText(formattedStringPrice+"원");    // 빈에 담긴 정보를 넣어줌
        holder.order_productCount.setText(mDataset.get(position).getCartQuantity()+"개");    // 빈에 담긴 정보를 넣어줌


        holder.order_productImage.setWebViewClient(new WebViewClient());        // 이미지 세팅을 위해 클라이언트 사용



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
        holder.order_productImage.loadUrl(urlImageReal); // 접속 URL      // 마지막으로 세팅까지 끝낸 이미지 재로딩
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        this.mListener = listener ;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView order_productName, order_productPrice, order_productCount;
        WebView order_productImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            order_productImage = itemView.findViewById(R.id.order_productImage);
            order_productName = itemView.findViewById(R.id.order_productName);
            order_productPrice = itemView.findViewById(R.id.order_productPrice);
            order_productCount = itemView.findViewById(R.id.order_productQnt);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

}
