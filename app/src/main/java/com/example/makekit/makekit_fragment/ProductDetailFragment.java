package com.example.makekit.makekit_fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.example.makekit.R;
import com.example.makekit.makekit_activity.LoginActivity;
import com.example.makekit.makekit_asynctask.ProductNetworkTask;
import com.example.makekit.makekit_asynctask.WishlistNetworkTask;
import com.example.makekit.makekit_bean.Product;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductDetailFragment extends Fragment {

    View v;
    String macIP, productNo, urlAddrBase, urlAddr, urlImageReal, result, userEmail, sellerFavoriteCheck, urlAddr2, urlAddr3, sellerEmail;
    WebView sellerImage;
    TextView sellerName;
    WebView productAFilename;
    ArrayList<Product> products;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProductDetailFragment(String macIP, String productNo, String userEmail) {
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
     * @return A new instance of fragment ProductDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductDetailFragment newInstance(String param1, String param2) {
        ProductDetailFragment fragment = new ProductDetailFragment("macIP", "productNo", "userEmail");
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
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_product_detail, container, false);
        urlAddrBase = "http://" + macIP + ":8080/makekit/";
        urlAddr = urlAddrBase + "jsp/sellerfavorite_productview_check.jsp?useremail=" + userEmail + "&productno=" + productNo;

        sellerImage = v.findViewById(R.id.sellerImage_productviewdetail);
        sellerName = v.findViewById(R.id.sellerName_productdetail);
        v.findViewById(R.id.btnSellerFavorite_productdetail).setOnClickListener(mClickListener);
        v.findViewById(R.id.btnSellerStory_productdetail).setOnClickListener(mClickListener);

        connectSelectSellerFavoriteData(urlAddr);

//        urlAddr = urlAddrBase + "jsp/product_productview_content.jsp?productno=" + productNo;
//
//        productAFilename  = v.findViewById(R.id.productDetailImage_productviewdetail);
//
//        connectSelectData();
//
//        urlImageReal = urlAddrBase+ "image/" + products.get(0).getProductAFilename();
//
//        // Initial webview
//        productAFilename.setWebViewClient(new WebViewClient());
//
//        // Enable JavaScript
//        productAFilename.getSettings().setJavaScriptEnabled(true);
//        productAFilename.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//
//        // WebView 세팅
//        WebSettings webSettings = productAFilename.getSettings();
//        webSettings.setUseWideViewPort(true);       // wide viewport를 사용하도록 설정
//        webSettings.setLoadWithOverviewMode(true);  // 컨텐츠가 웹뷰보다 클 경우 스크린 크기에 맞게 조정
//        //iv_viewPeople.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//
//        productAFilename.setBackgroundColor(0); //배경색
//        productAFilename.setHorizontalScrollBarEnabled(false); //가로 스크롤
//        productAFilename.setVerticalScrollBarEnabled(false);   //세로 스크롤
//        productAFilename.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY); // 스크롤 노출 타입
//        productAFilename.setScrollbarFadingEnabled(false);
//        productAFilename.setInitialScale(25);
//
//        // 웹뷰 멀티 터치 가능하게 (줌기능)
//        webSettings.setBuiltInZoomControls(false);   // 줌 아이콘 사용
//        webSettings.setSupportZoom(false);
//
//        // url은 알아서 설정 예) http://m.naver.com/
//        productAFilename.loadUrl(urlImageReal); // 접속 URL

        return v;
    }

//    // select detail
//    private void connectSelectData() {
//        try {
//            ProductNetworkTask productNetworkTask = new ProductNetworkTask(getActivity(), urlAddr, "select");
//
//            Object object = productNetworkTask.execute().get();
//            products = (ArrayList<Product>) object;
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btnSellerFavorite_productdetail:
                    if(loginCheck() == true) {
                        if (sellerEmail == null) {
                            urlAddr2 = urlAddrBase + "jsp/insert_wishlistproduct_productview.jsp?useremail=" + userEmail + "&sellereamil=" +sellerEmail;
                            insertSellerFavorite(urlAddr2);
                            if (result.equals("1")) {
                               // favoriteStatus.setImageResource(R.drawable.ic_favorite);
                            } else {
                                Toast.makeText(getContext(), "입력에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            urlAddr3 = urlAddrBase + "jsp/delete_sellerwishlistproduct_productview.jsp?useremail=" + userEmail + "&sellereamil=" +sellerEmail;
                            deleteSellerFavorite(urlAddr3);
                            if (result.equals("1")) {
                               // favoriteStatus.setImageResource(R.drawable.ic_nonfavorite);
                            } else {
                                Toast.makeText(getContext(), "입력에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    break;

                case R.id.btnSellerStory_productdetail:


                    break;
            }
        }
    };

    // select 판매자 찜
    private void connectSelectSellerFavoriteData(String urlAddr) {
        try {
            WishlistNetworkTask wishlistNetworkTask = new WishlistNetworkTask(getActivity(), urlAddr, "selectseller");

            Object object = wishlistNetworkTask.execute().get();
            sellerEmail = (String) object;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String insertSellerFavorite(String urlAddr){
        try {
            WishlistNetworkTask wishlistNetworkTask = new WishlistNetworkTask(getActivity(), urlAddr, "insert");

            Object object = wishlistNetworkTask.execute().get();
            result = (String) object;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private String deleteSellerFavorite(String urlAddr){
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

        if(userEmail == null || userEmail.equals("")){
            Toast.makeText(getContext(), "로그인이 필요합니다. \n로그인 후 이용해주세요.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            return false;
        }
        return true;
    }

}