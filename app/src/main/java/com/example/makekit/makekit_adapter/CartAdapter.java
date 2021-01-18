package com.example.makekit.makekit_adapter;

import android.annotation.SuppressLint;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.makekit.R;
import com.example.makekit.makekit_activity.OnChangedPrice;
import com.example.makekit.makekit_activity.ProdutctViewActivity;
import com.example.makekit.makekit_asynctask.CartNetworkTask;
import com.example.makekit.makekit_bean.Cart;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {

    private OnChangedPrice onChangedPrice;

    final static String TAG = "CartAdapter";

    String result = null;
    String urlAddr, macIP;
    private Context mContext = null;
    private int layout = 0;
    private ArrayList<Cart> data = null;
    private ArrayList<Cart> checkBoxChecked;
    private LayoutInflater inflater = null;
    private String urlImage;
    private String urlImageReal;
    private DecimalFormat myFormatter;
    private int price = 0;
    private int count = 1;
    private AdapterView.OnItemClickListener mListener = null;
    List<CheckBox> checkboxsList = new ArrayList<>();
    List<TextView> textviewList = new ArrayList<>();


    public CartAdapter(Context mContext, int layout, ArrayList<Cart> data, String urlImage, String macIP, OnChangedPrice onChangedPrice) {
        this.mContext = mContext;
        this.layout = layout;
        this.data = data;
        this.urlImage = urlImage;
        this.macIP = macIP;
        this.onChangedPrice = onChangedPrice;
        this.inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }




    @Override
    public CartAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.custom_cart_layout, parent, false);
        CartAdapter.MyViewHolder myViewHolder = new CartAdapter.MyViewHolder(v);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(CartAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {




        //urlAddr = urlAddrBase + "jsp/update_cart_change.jsp?cartno=" + data.get(position).getCartNo() + "&productno=" + data.get(position).getProductNo() + "&cartquantity=" + data.get(position).getCartQuantity();
        Log.v(TAG, "주소" + urlAddr);

        //Log.v(TAG, data.get(position).get());
        Log.v(TAG, urlImage);

            Log.v(TAG, urlImage + "image/" + data.get(position).getProductImage());
            urlImageReal = urlImage + "image/" + data.get(position).getProductImage();

        // Initial webview
        holder.img_productImage.setWebViewClient(new WebViewClient());



        // Enable JavaScript
        holder.img_productImage.getSettings().setJavaScriptEnabled(true);
        holder.img_productImage.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        // Enable Zoom
        holder.img_productImage.getSettings().setBuiltInZoomControls(true);
        holder.img_productImage.getSettings().setSupportZoom(true);
        holder.img_productImage.getSettings().setSupportZoom(true); //zoom mode 사용.
        holder.img_productImage.getSettings().setDisplayZoomControls(false); //줌 컨트롤러를 안보이게 셋팅.


        // Adjust web display
        holder.img_productImage.setBackgroundColor(0);
        holder.img_productImage.getSettings().setLoadWithOverviewMode(true);
        holder.img_productImage.getSettings().setUseWideViewPort(true);
        holder.img_productImage.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
        holder.img_productImage.setInitialScale(5);

        // url은 알아서 설정 예) http://m.naver.com/
        holder.img_productImage.loadUrl(urlImageReal); // 접속 URL

        int total = Integer.parseInt(data.get(position).getProductPrice()) * Integer.parseInt(data.get(position).getCartQuantity());
        myFormatter = new DecimalFormat("###,###");
        String formattedStringPrice = myFormatter.format(total);
        holder.tv_productPrice.setText(formattedStringPrice + "원");
        //holder.tv_productPrice.setText(Integer.toString(total));
        holder.tv_purchaseNum.setText(data.get(position).getCartQuantity());
        holder.tv_productName.setText(data.get(position).getProductName());
        holder.tv_productDeliveryPrice.setText("2,500원");

//        // 체크박스 체크되어 있는 상태 설정
//        holder.cb_productName.setChecked(true);

        // 체크박스 리스트에 전부 추가하기
        checkboxsList.add(holder.cb_productName);

        // 구매 수량 리스트에 전부 추가하기
        textviewList.add(holder.tv_purchaseNum);

        // 제목 클릭 시 상세페이지 이동
        holder.tv_productName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ProdutctViewActivity.class);
//                            intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("productNo", data.get(position).getProductNo());
                intent.putExtra("macIP", macIP);
                v.getContext().startActivity(intent);
            }
        });


        holder.btn_MinusProudct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                count = Integer.parseInt(data.get(position).getCartQuantity());
                if(holder.tv_purchaseNum.getText().toString().equals("1")){
                    count = 1;
                    holder.tv_purchaseNum.setText("1");
                    Toast.makeText(v.getContext(),"최수 수량은 1개입니다.", Toast.LENGTH_SHORT).show();
                    String urlAddr = urlImage + "jsp/update_cart_change.jsp?cartno=" + data.get(position).getCartNo() + "&productno=" + data.get(position).getProductNo() + "&cartquantity=" +count;
                    connectUpdateData(urlAddr);
                    myFormatter = new DecimalFormat("###,###");
                    String formattedStringPrice = myFormatter.format(Integer.parseInt(data.get(position).getProductPrice()));
                    holder.tv_productPrice.setText(formattedStringPrice + "원");

                } else {

                    count = Integer.parseInt(holder.tv_purchaseNum.getText().toString());
                    count--;
                    String urlAddr = urlImage + "jsp/update_cart_change.jsp?cartno=" + data.get(position).getCartNo() + "&productno=" + data.get(position).getProductNo() + "&cartquantity=" +count;
                    connectUpdateData(urlAddr);
                    holder.tv_purchaseNum.setText("" + count);
                    int totalPrice = count * Integer.parseInt(data.get(position).getProductPrice());
                    myFormatter = new DecimalFormat("###,###");
                    String formattedStringPrice = myFormatter.format(totalPrice);
                    holder.tv_productPrice.setText(formattedStringPrice + "원");
//                    notifyItemChanged(position);

                }
                updateTotalPrice();
            }
        });

        holder.btn_PlusProudct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = Integer.parseInt(holder.tv_purchaseNum.getText().toString());
                count = count + 1;
                    holder.tv_purchaseNum.setText("" + count);

                    String urlAddr = urlImage + "jsp/update_cart_change.jsp?cartno=" + data.get(position).getCartNo() + "&productno=" + data.get(position).getProductNo() + "&cartquantity=" +count;
                    connectUpdateData(urlAddr);

                    int totalPrice = count * Integer.parseInt(data.get(position).getProductPrice());
                    myFormatter = new DecimalFormat("###,###");
                    String formattedStringPrice = myFormatter.format(totalPrice);
                    holder.tv_productPrice.setText(formattedStringPrice + "원");
//                    holder.tv_price.setText("값 변화");

                   // notifyItemChanged(position);

                    //Toast.makeText(v.getContext(), "추가", Toast.LENGTH_SHORT).show();
                updateTotalPrice();
                data.get(position).setCartQuantity(textviewList.get(position).getText().toString());
                Log.v(TAG, "변경된 값 : " + data.get(position).getCartQuantity());
            }
        });


    }

    @Override
    public int getItemCount() {
//        Log.v("AddressAdapter", "data.size = " + String.valueOf(data.size()));
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        WebView img_productImage;
        TextView tv_productPrice, tv_purchaseNum, tv_productDeliveryPrice, tv_price, tv_productName;
        Button btn_MinusProudct, btn_PlusProudct;
        CheckBox cb_productName;

        public MyViewHolder(View itemView) {
            super(itemView);

            img_productImage = itemView.findViewById(R.id.productImage_cart);
            tv_productPrice = itemView.findViewById(R.id.productPrice_cart);
            tv_productName = itemView.findViewById(R.id.tv_productName_cart);
            btn_MinusProudct = itemView.findViewById(R.id.btnMinusProudct_cart);
            tv_purchaseNum = itemView.findViewById(R.id.purchaseNum_cart);
            btn_PlusProudct = itemView.findViewById(R.id.btnPlusProudct_cart);
            cb_productName = itemView.findViewById(R.id.cb_productName_cart);
            tv_productDeliveryPrice = itemView.findViewById(R.id.productDeliveryPrice_cart);
            btn_MinusProudct = itemView.findViewById(R.id.btnMinusProudct_cart);
            btn_PlusProudct = itemView.findViewById(R.id.btnPlusProudct_cart);
            /////////////////////
            tv_price = itemView.findViewById(R.id.tv_total_payment_cart);

            checkBoxChecked = new ArrayList<Cart>();


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //cb_productName.isChecked();
                }
            });

            cb_productName.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    checkBoxChecked.clear();

                        for (int i = 0; i < checkboxsList.size(); i ++){
                            if (checkboxsList.get(i).isChecked() == true){
                                checkBoxChecked.add(data.get(i));

                            }
                        }
                    for (int i = 0; i < checkBoxChecked.size(); i ++) {
                        Log.v(TAG, i + "번째 : " + checkBoxChecked.get(i).getProductNo());
                    }
                    updateTotalPrice();
                }
            });

//            cb_productName.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    checkBoxChecked.clear();
//
//                        for (int i = 0; i < checkboxsList.size(); i ++){
//                            if (checkboxsList.get(i).isChecked() == true){
//                                checkBoxChecked.add(data.get(i));
//                            }
//                        }
//                    for (int i = 0; i < checkBoxChecked.size(); i ++) {
//                        Log.v(TAG, i + "번째 : " + checkBoxChecked.get(i).getProductNo());
//                    }
//
//                }
//            });

        }

    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        this.mListener = listener ;
    }

    // select cart
    private void connectUpdateData(String urlAddr) {
        try {
            CartNetworkTask cartNetworkTask = new CartNetworkTask(mContext, urlAddr, "update");

            Object object = cartNetworkTask.execute().get();
            result = (String) object;

//            cartAdapter = new CartAdapter(mContext, R.layout.custom_cart_layout, data, urlAddrBase);
//            recyclerView.setAdapter(cartAdapter);
//            recyclerView.setHasFixedSize(true); // 리사이클러뷰 기존성능 강화
//            layoutManager = new LinearLayoutManager(CartActivity.this);
//            recyclerView.setLayoutManager(layoutManager);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void checkBoxOperation(boolean what){
        for (CheckBox checkBox : checkboxsList ){
            checkBox.setChecked(what);
        }
    }


    public ArrayList<Cart> checkBoxCheckedReturn(){

        return checkBoxChecked;

    }



    // 장바구니 선택 제품 삭제
    public void connectDeleteData() {

        if (checkBoxChecked.size() == 0) {
            Toast.makeText(mContext, "삭제할 제품을 선택해주세요", Toast.LENGTH_SHORT).show();
        } else {
            String urlAddrDelete;

            for (int i = 0; i < checkBoxChecked.size(); i++) {
                String urlAddr = urlImage + "jsp/delete_cart_product.jsp?cartno=" + checkBoxChecked.get(i).getCartNo() + "&productno=" + checkBoxChecked.get(i).getProductNo();

                try {
                    CartNetworkTask networkTask = new CartNetworkTask(mContext, urlAddr, "delete");
                    networkTask.execute().get();
                    //Log.v(TAG, "체크된 제품의 위치 INDEX == " + selectedPosition.get(i));


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public void updateTotalPrice(){
        Log.v(TAG, "**viewholder 안 updateTotalPrice 들어옴 **");
        int price_each;
        int price_total = 0;
        int delivery_price = 0;
        int productTotalPrice = 0;


        // 체크된 상품 값 더하기
        for (int i = 0; i < checkboxsList.size(); i++) {
            if (checkboxsList.get(i).isChecked() == true) {
                price_each = Integer.parseInt(textviewList.get(i).getText().toString()) * Integer.parseInt(data.get(i).getProductPrice());
                price_total += price_each;

            }
        }
        if (onChangedPrice != null){
            Log.v(TAG, "**onChangeCheckedPrice != null **");
            Log.v(TAG, "**전체 가격은 **" + price_total);

            delivery_price = checkBoxChecked.size() * 2500;
            productTotalPrice = price_total + delivery_price;
            onChangedPrice.changedPrice(price_total, delivery_price, productTotalPrice);
        } else {
            price_total=0;
            delivery_price=0;
            productTotalPrice=0;
            onChangedPrice.changedPrice(price_total, delivery_price, productTotalPrice);
        }
    }


}
