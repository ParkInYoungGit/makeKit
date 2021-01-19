package com.example.makekit.makekit_fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.makekit.R;
import com.example.makekit.makekit_activity.LoginActivity;
import com.example.makekit.makekit_activity.ProdutctViewActivity;
import com.example.makekit.makekit_adapter.ProductReviewAdapter;
import com.example.makekit.makekit_asynctask.ProductNetworkTask;
import com.example.makekit.makekit_asynctask.ReviewNetworkTask;
import com.example.makekit.makekit_asynctask.WishlistNetworkTask;
import com.example.makekit.makekit_bean.Product;
import com.example.makekit.makekit_bean.Review;
import com.example.makekit.makekit_sharVar.SharVar;
import com.sun.mail.imap.protocol.INTERNALDATE;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductContentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */


//////////////////////////////////////////
// jsp makekit 부분 makeKit 으로 변경 필요!!!
// 싱픔 찜 추가하기!
//////////////////////////////////////////


public class ProductContentFragment extends Fragment {

    TextView productName, productPrice, productContent, productTotalPrice, purchaseNumInput;
    WebView productFilename, productDfilename, productAFilename;
    ImageView favoriteStatus;
    int purchaseNum;
    int count = 1;

    View v;
    String urlAddr, urlAddrBase, urlImageReal1, urlImageReal2, urlImageReal3, price, macIP, productNo, urlAddr1, userEmail, result, urlAddr2, urlAddr3;
    ArrayList<Product> products;
    String favoriteCheck = null;

    final static String TAG = "ProductContentFragment";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProductContentFragment(String macIP, String productNo, String userEmail) {
        // Required empty public constructor
        this.macIP = macIP;
        this.productNo = productNo;
        this.userEmail = userEmail;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProductContentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductContentFragment newInstance(String param1, String param2) {
        ProductContentFragment fragment = new ProductContentFragment("macIP", "productNo", "userEmail");
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString("macIP");
            mParam2 = getArguments().getString("productNo");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_product_content, container, false);
        Log.v(TAG, "onCreateView content" + getArguments());


        if (getArguments() != null) {
            mParam1 = getArguments().getString("macIP");
            mParam2 = getArguments().getString("productNo");
        }
//        mParam1 = "192.168.219.164";
//        mParam2 = "44";

        urlAddrBase = SharVar.urlAddrBase;
//        urlAddrBase = "http://" + macIP + ":8080/makeKit/";
        urlAddr = urlAddrBase + "jsp/product_productview_content.jsp?productno=" + productNo;
        Log.v(TAG, "주소" + urlAddr);
        urlAddr1 = urlAddrBase + "jsp/wishlist_productview_check.jsp?productno=" + productNo + "&useremail=" + userEmail;

        favoriteStatus = v.findViewById(R.id.favorite_productviewcontent);
        productName = v.findViewById(R.id.productNmae_prodcutviewcontent);
        productPrice  = v.findViewById(R.id.productPrice_productviewcontent);
        productContent = v.findViewById(R.id.productContent_productviewcontent);
        productFilename  = v.findViewById(R.id.productImage_productviewcontent);
        productDfilename = v.findViewById(R.id.prdouctdetail_productviewcontent);
        productAFilename = v.findViewById(R.id.prdouctdetailSecond_productviewcontent);


        connectSelectData();
        connectSelectFavoriteData();

        if(favoriteCheck.equals("1")){
            favoriteStatus.setImageResource(R.drawable.ic_favorite);
        } else {
            favoriteStatus.setImageResource(R.drawable.ic_nonfavorite);
        }

        // , 표시 하기
        DecimalFormat myFormatter = new DecimalFormat("###,###");
        String formattedStringPrice = myFormatter.format(Integer.parseInt(products.get(0).getProductPrice()));

        productName.setText(products.get(0).getProductName());
        productContent.setText(products.get(0).getProductContent());
        productPrice.setText(formattedStringPrice + "원");



//        int total = Integer.parseInt(products.get(0).getProductPrice()) + 2500;
//        Log.v(TAG, String.valueOf(total));
//        productTotalPrice.setText(Integer.toString(total) + "원");

        urlImageReal1 = urlAddrBase+ "image/" + products.get(0).getProductFilename();
        urlImageReal2 = urlAddrBase+ "image/" + products.get(0).getProductDfilename();
        urlImageReal3 = urlAddrBase+ "image/" + products.get(0).getProductAFilename();

        // 썸네일 이미지
        // Initial webview
        productFilename.setWebViewClient(new WebViewClient());

        // Enable JavaScript
        productFilename.getSettings().setJavaScriptEnabled(true);
        productFilename.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        // Enable Zoom
        productFilename.getSettings().setBuiltInZoomControls(true);
        productFilename.getSettings().setSupportZoom(true);
        productFilename.getSettings().setSupportZoom(true); //zoom mode 사용.
        productFilename.getSettings().setDisplayZoomControls(false); //줌 컨트롤러를 안보이게 셋팅.


        // Adjust web display
        productFilename.getSettings().setLoadWithOverviewMode(true);
        productFilename.getSettings().setUseWideViewPort(true);
        productFilename.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
        productFilename.setBackgroundColor(0);
        productFilename.setInitialScale(5);

        // url은 알아서 설정 예) http://m.naver.com/
        productFilename.loadUrl(urlImageReal1); // 접속 URL

        // 설명 이미지
        // Initial webview
        productDfilename.setWebViewClient(new WebViewClient());
        // Enable JavaScript
        productDfilename.getSettings().setJavaScriptEnabled(true);
        productDfilename.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        // Enable Zoom
        productDfilename.getSettings().setBuiltInZoomControls(true);
        productDfilename.getSettings().setSupportZoom(true);
        productDfilename.getSettings().setSupportZoom(true); //zoom mode 사용.
        productDfilename.getSettings().setDisplayZoomControls(false); //줌 컨트롤러를 안보이게 셋팅.


        // Adjust web display
        productDfilename.getSettings().setLoadWithOverviewMode(true);
        productDfilename.getSettings().setUseWideViewPort(true);
        productDfilename.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
        productDfilename.setBackgroundColor(0);
        productDfilename.setInitialScale(5);
//
//            // url은 알아서 설정 예) http://m.naver.com/
        productDfilename.loadUrl(urlImageReal2); // 접속 URL

//            btnMinus.setOnClickListener(mClickListener);
//            btnPlus.setOnClickListener(mClickListener);

        // 원산지 이미지
        // Initial webview
        productAFilename.setWebViewClient(new WebViewClient());

        // Enable JavaScript
        productAFilename.getSettings().setJavaScriptEnabled(true);
        productAFilename.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        // Enable Zoom
        productAFilename.getSettings().setBuiltInZoomControls(true);
        productAFilename.getSettings().setSupportZoom(true);
        productAFilename.getSettings().setSupportZoom(true); //zoom mode 사용.
        productAFilename.getSettings().setDisplayZoomControls(false); //줌 컨트롤러를 안보이게 셋팅.


        // Adjust web display
        productAFilename.getSettings().setLoadWithOverviewMode(true);
        productAFilename.getSettings().setUseWideViewPort(true);
        productAFilename.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
        productAFilename.setBackgroundColor(0);
        productAFilename.setInitialScale(30);

        // url은 알아서 설정 예) http://m.naver.com/
        productAFilename.loadUrl(urlImageReal3); // 접속 URL

        favoriteStatus.setOnClickListener(mClickListener);

        return v;
    }
//
//    View.OnClickListener mClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            switch (v.getId()){
//                case R.id.btnMinusProudct_productview:
//                    if(purchaseNumInput.getText().toString().equals("1")){
//                        count = 1;
//                        Toast.makeText(getContext(), "최수 수량은 1개입니다.", Toast.LENGTH_SHORT).show();
//                        purchaseNumInput.setText("1");
//
//                    } else {
//                        count--;
//                        purchaseNumInput.setText(""+count);
//
//                        int total = (Integer.parseInt(products.get(0).getProductPrice()) * count) + 2500;
//                        productTotalPrice.setText(Integer.toString(total) + " 원");
//
//                    }
//                    break;
//
//                case R.id.btnPlusProudct_productview:
//
//                    count++;
//                    purchaseNumInput.setText(""+count);
//
//                    int total = (Integer.parseInt(products.get(0).getProductPrice()) * count) + 2500;
//                    productTotalPrice.setText(Integer.toString(total) + " 원");
//                    break;
//            }
//        }
//    };


    @Override
    public void onResume() {
        super.onResume();
        Log.v(TAG, "onResume PRODUCT");
        connectSelectData();
    }

    // select content
    private void connectSelectData() {
        try {
            ProductNetworkTask productNetworkTask = new ProductNetworkTask(getActivity(), urlAddr, "select");

            Object object = productNetworkTask.execute().get();
            products = (ArrayList<Product>) object;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // select 상품 찜
    private void connectSelectFavoriteData() {
        try {
            WishlistNetworkTask wishlistNetworkTask = new WishlistNetworkTask(getActivity(), urlAddr1, "selectdate");

            Object object = wishlistNetworkTask.execute().get();
            favoriteCheck = (String) object;
            Log.v(TAG, "favoriteCheck : " + favoriteCheck);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.favorite_productviewcontent:
                    if(loginCheck() == true) {
                        if (favoriteCheck.equals("0")) {
                            urlAddr2 = urlAddrBase + "jsp/insert_wishlistproduct_productview.jsp?productno=" + productNo + "&useremail=" + userEmail;
                            Log.v(TAG, "urlAddr2" + urlAddr2);
                            insertFavorite(urlAddr2);
                            favoriteCheck = "1";;
                            if (result.equals("1")) {
                                favoriteStatus.setImageResource(R.drawable.ic_favorite);
                                Toast.makeText(getContext(), "상품 찜 하셨습니다.", Toast.LENGTH_SHORT).show();
                            } else {
                                favoriteStatus.setImageResource(R.drawable.ic_nonfavorite);
                                Toast.makeText(getContext(), "입력에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                            }

                        } else if(favoriteCheck.equals("1")){
                            urlAddr3 = urlAddrBase + "jsp/delete_wishlistproduct_productview.jsp?productno=" + productNo + "&useremail=" + userEmail;
                            deleteFavorite(urlAddr3);
                            favoriteCheck = "0";;
                            if (result.equals("1")) {
                                favoriteStatus.setImageResource(R.drawable.ic_nonfavorite);
                                Toast.makeText(getContext(), "상품 찜 취소되었습니다.", Toast.LENGTH_SHORT).show();
                            } else {
                                favoriteStatus.setImageResource(R.drawable.ic_favorite);
                            }
                        }
                    }

                    break;
            }
        }
    };

    private String insertFavorite(String urlAddr){
        try {
            WishlistNetworkTask wishlistNetworkTask = new WishlistNetworkTask(getActivity(), urlAddr, "insert");

            Object object = wishlistNetworkTask.execute().get();
            result = (String) object;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private String deleteFavorite(String urlAddr){
        try {
            WishlistNetworkTask wishlistNetworkTask = new WishlistNetworkTask(getActivity(), urlAddr, "delete");

            Object object = wishlistNetworkTask.execute().get();
            result = (String) object;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private boolean loginCheck(){
        ///////////////////////////////
        // email 받아오는 값 확인해서 수정하기


        if(userEmail == null || userEmail.equals("")){
            Toast.makeText(getContext(), "로그인이 필요합니다. \n로그인 후 이용해주세요.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            return false;
        }
        return true;
    }

}