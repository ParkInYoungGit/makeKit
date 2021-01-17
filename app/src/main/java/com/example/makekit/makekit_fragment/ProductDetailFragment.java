package com.example.makekit.makekit_fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.makekit.R;
import com.example.makekit.makekit_activity.LoginActivity;
import com.example.makekit.makekit_activity.SaleProductListActivity;
import com.example.makekit.makekit_asynctask.ProductNetworkTask;
import com.example.makekit.makekit_asynctask.WishlistNetworkTask;
import com.example.makekit.makekit_bean.Favorite;
import com.example.makekit.makekit_bean.Product;
import com.example.makekit.makekit_sharVar.SharVar;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductDetailFragment extends Fragment {
    final static String TAG = "ProductDetailFragment";

    View v;
    String macIP, productNo,favoriteCheck, urlAddrBase, urlAddr, urlImageReal, result, userEmail, sellerFavoriteCheck, urlAddr2, urlAddr3, urlAddr4, sellerEmail, sellerNameSt, urlImageReal1;
    WebView sellerImage;
    TextView sellerName;
    WebView productAFilename;
    ArrayList<Product> products;
    ImageView sellerFavorite;
    ArrayList<Favorite> sellerInfo;

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
        urlAddrBase = SharVar.urlAddrBase;
//        urlAddrBase = "http://" + macIP + ":8080/makeKit/";
        urlAddr = urlAddrBase + "jsp/sellerfavorite_productview_check.jsp?useremail=" + userEmail + "&productno=" + productNo;
        urlAddr4 = urlAddrBase + "jsp/product_productview_content.jsp?productno=" + productNo;

        sellerImage = v.findViewById(R.id.sellerImage_productviewdetail);
        sellerName = v.findViewById(R.id.sellerName_productdetail);
        sellerFavorite = v.findViewById(R.id.sellerFavorite_productdetail);
        v.findViewById(R.id.sellerFavorite_productdetail).setOnClickListener(mClickListener);
        v.findViewById(R.id.btnSellerStory_productdetail).setOnClickListener(mClickListener);


        connectSelectData(urlAddr4);
        connectSelectSellerFavoriteData(urlAddr);

        if(favoriteCheck.equals("0")){
            sellerFavorite.setImageResource(R.drawable.seller_nonfavorite);

        } else {
            sellerFavorite.setImageResource(R.drawable.seller_favorite);


        }

        sellerEmail = products.get(0).getSellerEmail();
        sellerNameSt = products.get(0).getSellerEmail();
        sellerName.setText(sellerNameSt);

        urlImageReal = urlAddrBase+ "image/" +products.get(0).getSellerImage();
        urlImageReal1 = urlAddrBase+ "image/ic_defaultpeople.jpg";

        if(products.get(0).getSellerImage().equals("null")){
//            // Initial webview
//            sellerImage.setWebViewClient(new WebViewClient());
//
//            // Enable JavaScript
//            sellerImage.getSettings().setJavaScriptEnabled(true);
//            sellerImage.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//
//            // WebView 세팅
//            WebSettings webSettings = sellerImage.getSettings();
//            webSettings.setUseWideViewPort(true);       // wide viewport를 사용하도록 설정
//            webSettings.setLoadWithOverviewMode(true);  // 컨텐츠가 웹뷰보다 클 경우 스크린 크기에 맞게 조정
//            //iv_viewPeople.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//
//            sellerImage.setBackgroundColor(0); //배경색
//            sellerImage.setHorizontalScrollBarEnabled(false); //가로 스크롤
//            sellerImage.setVerticalScrollBarEnabled(false);   //세로 스크롤
//            sellerImage.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY); // 스크롤 노출 타입
//            sellerImage.setScrollbarFadingEnabled(false);
//            sellerImage.setInitialScale(15);
//
//            // 웹뷰 멀티 터치 가능하게 (줌기능)
//            webSettings.setBuiltInZoomControls(false);   // 줌 아이콘 사용
//            webSettings.setSupportZoom(false);
//
            // Web Setting
            WebSettings webSettings = sellerImage.getSettings();
            webSettings.setJavaScriptEnabled(true); // 자바 스크립트는 쓰겠다.
            webSettings.setBuiltInZoomControls(true); // 확대 축소 기능
            webSettings.setDisplayZoomControls(false); // 돋보기 없애기
            //sellerImage.setBackgroundColor(Color.TRANSPARENT);  // webview의 배경 투명으로 전환
            sellerImage.setBackgroundColor(0);  // webview의 배경 투명으로 전환
            sellerImage.setInitialScale(5);

            String URL = urlImageReal1;


            String htmlData = "<html>" +
                    "<head>" +
                    "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                    "</head>" +
                    "<body><center>" +
                    "<img src = \"" + URL + "\"style=\"width: auto; height: 30%;\">" +
                    "</center></body>" +
                    "</html>";
            sellerImage.loadData(htmlData,"text/html", "UTF-8");

            Log.v(TAG, htmlData);

            sellerImage.loadUrl(urlImageReal1); // 접속 URL

        } else {
            sellerImage.loadUrl(urlImageReal); // 접속 URL
            // Initial webview
            sellerImage.setWebViewClient(new WebViewClient());

            // Enable JavaScript
            sellerImage.getSettings().setJavaScriptEnabled(true);
            sellerImage.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

            // WebView 세팅
            WebSettings webSettings = sellerImage.getSettings();
            webSettings.setUseWideViewPort(true);       // wide viewport를 사용하도록 설정
            webSettings.setLoadWithOverviewMode(true);  // 컨텐츠가 웹뷰보다 클 경우 스크린 크기에 맞게 조정
            //iv_viewPeople.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

            sellerImage.setBackgroundColor(0); //배경색
            sellerImage.setHorizontalScrollBarEnabled(false); //가로 스크롤
            sellerImage.setVerticalScrollBarEnabled(false);   //세로 스크롤
            sellerImage.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY); // 스크롤 노출 타입
            sellerImage.setScrollbarFadingEnabled(false);
            sellerImage.setInitialScale(15);

            // 웹뷰 멀티 터치 가능하게 (줌기능)
            webSettings.setBuiltInZoomControls(false);   // 줌 아이콘 사용
            webSettings.setSupportZoom(false);

            // url은 알아서 설정 예) http://m.naver.com/
            sellerImage.loadUrl(urlImageReal); // 접속 URL
        }


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
                case R.id.sellerFavorite_productdetail:
                    if(loginCheck() == true) {
                        if (favoriteCheck.equals("0")) {
                            urlAddr2 = urlAddrBase + "jsp/insert_sellerwishlistproduct_productview.jsp?useremail=" + userEmail + "&selleremail=" +sellerEmail;
                            favoriteCheck = "1";
                            insertSellerFavorite(urlAddr2);

                            if (result.equals("1")) {
                                sellerFavorite.setImageResource(R.drawable.seller_favorite);
                                Toast.makeText(getContext(), "판매자 찜 하였습니다.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "입력에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                //sellerFavorite.setImageResource(R.drawable.seller_nonfavorite);
                            }

                        } else if(favoriteCheck.equals("1")){
                            urlAddr3 = urlAddrBase + "jsp/delete_sellerwishlistproduct_productview.jsp?useremail=" + userEmail + "&selleremail=" +sellerEmail;
                            deleteSellerFavorite(urlAddr3);
                            favoriteCheck = "0";
                            if (result.equals("1")) {
                                //sellerFavorite.setImageResource(R.drawable.seller_nonfavorite);
                                sellerFavorite.setImageResource(R.drawable.seller_nonfavorite);

                                Toast.makeText(getContext(), "판매자 찜 취소되었습니다.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "삭제에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                sellerFavorite.setImageResource(R.drawable.seller_favorite);
                            }
                        }
                    }
                    break;

                case R.id.btnSellerStory_productdetail:
                    Intent intent = new Intent(getActivity(), SaleProductListActivity.class);
                    intent.putExtra("seller", sellerEmail);
                    intent.putExtra("macIP", macIP);
                    intent.putExtra("userEmail", userEmail);
                    startActivity(intent);

                    break;
            }
        }
    };

    // select 판매자 찜
    private void connectSelectSellerFavoriteData(String urlAddr) {
        try {
            WishlistNetworkTask wishlistNetworkTask = new WishlistNetworkTask(getActivity(), urlAddr, "selectseller");

            Object object = wishlistNetworkTask.execute().get();
            favoriteCheck = (String) object;
            Log.v(TAG, "favorite : "+favoriteCheck);

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

    // select content
    private void connectSelectData(String urlAddr) {
        try {
            ProductNetworkTask productNetworkTask = new ProductNetworkTask(getActivity(), urlAddr, "select");

            Object object = productNetworkTask.execute().get();
            products = (ArrayList<Product>) object;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        connectSelectData(urlAddr4);
        connectSelectSellerFavoriteData(urlAddr);
    }
}