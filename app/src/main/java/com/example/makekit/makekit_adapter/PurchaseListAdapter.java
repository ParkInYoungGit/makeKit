package com.example.makekit.makekit_adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.makekit.makekit_activity.OrderViewActivity;
import com.example.makekit.makekit_activity.PurchaseListActivity;
import com.example.makekit.R;
import com.example.makekit.makekit_activity.RegisterReviewActivity;
import com.example.makekit.makekit_asynctask.NetworkTask_DH;
import com.example.makekit.makekit_bean.Order;
import com.example.makekit.makekit_sharVar.SharVar;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class PurchaseListAdapter extends RecyclerView.Adapter<PurchaseListAdapter.MyViewHolder> {
    private ArrayList<Order> mDataset;
    private AdapterView.OnItemClickListener mListener = null;
    private String urlImage;
    private String urlImageReal;
    private Context mContext;
    private String email;
    private String macIP;
    String orderNo, urlAddrBase, urlAddr;
    DecimalFormat myFormatter;

    public PurchaseListAdapter(Context context, int layout, ArrayList<Order> orders,  String urlImage, String email, String macIP) {
        this.mContext = context;
        this.mDataset = orders;
        this.urlImage = urlImage;
        this.email = email;
        this.macIP = macIP;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView orderDate, productName, productQuantity, productPrice;
        Button orderView, buy_check;
        WebView webView;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            orderDate = itemView.findViewById(R.id.purchaselist_orderDate_TV);
            productName = itemView.findViewById(R.id.purchaselist_ProductName_TV);
            productQuantity = itemView.findViewById(R.id.purchaselist_orderQuantity_TV);
            productPrice = itemView.findViewById(R.id.purchaselist_orderPrice_TV);
            webView = itemView.findViewById(R.id.purchaselist_WebView);
            orderView = itemView.findViewById(R.id.purchaseLists_Btn);
            buy_check = itemView.findViewById(R.id.buycheck_btn);

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

        if (mDataset.get(position).getProductAFilename().equals("null")) {
            urlImageReal = urlImage + "ic_default.jpg";
        } else {
            urlImageReal = urlImage + mDataset.get(position).getProductAFilename();
        }
        holder.orderDate.setText("주문 번호 : " + mDataset.get(position).getOrderNo());
        holder.webView.loadUrl(urlImageReal);
        holder.productName.setText(mDataset.get(position).getProductName());
        holder.productQuantity.setText("수량 : " + mDataset.get(position).getOrderQuantity());
        myFormatter = new DecimalFormat("###,###");
        String formattedStringPrice = myFormatter.format(Integer.parseInt(mDataset.get(position).getProductPrice()));
        holder.productPrice.setText("총 가격 : " + formattedStringPrice+" 원");

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

        holder.buy_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("here","test");
                macIP = SharVar.macIP;
                email = SharVar.userEmail;


                orderNo = mDataset.get(position).getOrderNo();
                Log.v("adapter", orderNo);

                urlAddrBase = "http://" + macIP + ":8080/makeKit/";
                urlAddr = urlAddrBase + "jsp/buy_check.jsp?orderNo=" + orderNo;

                Log.v("adapter", urlAddr);

                String result = connectUpdateData();

            }
        });
        holder.orderView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, OrderViewActivity.class);
                intent.putExtra("macIP", macIP);
                intent.putExtra("useremail", email);
                intent.putExtra("orderView_Date_TV", mDataset.get(position).getOrderDate());
                intent.putExtra("orderView_Number_TV", mDataset.get(position).getOrderNo());
                intent.putExtra("order_userName", mDataset.get(position).getOrderCardPw());
                intent.putExtra("order_userTel", mDataset.get(position).getOrderRcvPhone());
                intent.putExtra("order_userAddress", mDataset.get(position).getOrderRcvAddress());
                intent.putExtra("order_userAddressDetail", mDataset.get(position).getOrderRcvAddressDetail());
                intent.putExtra("order_productImage", mDataset.get(position).getProductAFilename());
                intent.putExtra("order_productName", mDataset.get(position).getProductName());
                intent.putExtra("order_productQuantity", mDataset.get(position).getOrderQuantity());
                intent.putExtra("order_productTotalPrice", mDataset.get(position).getOrderTotalPrice());
                intent.putExtra("orderView_orderBank", mDataset.get(position).getOrderBank());
                intent.putExtra("orderView_orderCardNo", mDataset.get(position).getOrderCardNo());
                intent.putExtra("orderView_orderDate", mDataset.get(position).getOrderDate());
                intent.putExtra("orderView_orderTotalPrice", mDataset.get(position).getOrderTotalPrice());
                context.startActivity(intent);
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
    private String connectUpdateData() {

        String result = null;
        try {
            ///////////////////////////////////////////////////////////////////////////////////////
            // Date : 2020.12.25
            //
            // Description:
            //  - NetworkTask를 한곳에서 관리하기 위해 기존 CUDNetworkTask 삭제
            //  - NetworkTask의 생성자 추가 : where <- "update"
            //
            ///////////////////////////////////////////////////////////////////////////////////////
            Log.v("dd","dddddd");
            NetworkTask_DH networkTask2 = new NetworkTask_DH(mContext, urlAddr, "updatebuy");
            ///////////////////////////////////////////////////////////////////////////////////////

            //CUDNetworkTask updnetworkTask = new CUDNetworkTask(UpdateActivity.this, urlAddr);
            ///////////////////////////////////////////////////////////////////////////////////////
            // Date : 2020.12.24
            //
            // Description:
            // - 수정 결과 값을 받기 위해 Object로 return후에 String으로 변환 하여 사용
            //
            ///////////////////////////////////////////////////////////////////////////////////////

            Object obj = networkTask2.execute().get();
            result = (String) obj;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }



}

