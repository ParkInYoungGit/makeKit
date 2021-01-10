package com.example.makekit.makekit_fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.makekit.R;
import com.example.makekit.makekit_asynctask.ProductNetworkTask;
import com.example.makekit.makekit_bean.Product;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductDetailFragment extends Fragment {

    View v;
    String macIP, productNo, urlAddrBase, urlAddr, urlImageReal;
    WebView productAFilename;
    ArrayList<Product> products;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProductDetailFragment(String macIP, String productNo) {
        // Required empty public constructor
        this.macIP = macIP;
        this.productNo = productNo;
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
        ProductDetailFragment fragment = new ProductDetailFragment("macIP", "productNo");
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
        urlAddr = urlAddrBase + "jsp/product_productview_content.jsp?productno=" + productNo;

        productAFilename  = v.findViewById(R.id.productDetailImage_productviewdetail);

        connectSelectData();

        urlImageReal = urlAddrBase+ "image/" + products.get(0).getProductAFilename();

        // Initial webview
        productAFilename.setWebViewClient(new WebViewClient());

        // Enable JavaScript
        productAFilename.getSettings().setJavaScriptEnabled(true);
        productAFilename.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        // WebView 세팅
        WebSettings webSettings = productAFilename.getSettings();
        webSettings.setUseWideViewPort(true);       // wide viewport를 사용하도록 설정
        webSettings.setLoadWithOverviewMode(true);  // 컨텐츠가 웹뷰보다 클 경우 스크린 크기에 맞게 조정
        //iv_viewPeople.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        productAFilename.setBackgroundColor(0); //배경색
        productAFilename.setHorizontalScrollBarEnabled(false); //가로 스크롤
        productAFilename.setVerticalScrollBarEnabled(false);   //세로 스크롤
        productAFilename.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY); // 스크롤 노출 타입
        productAFilename.setScrollbarFadingEnabled(false);
        productAFilename.setInitialScale(25);

        // 웹뷰 멀티 터치 가능하게 (줌기능)
        webSettings.setBuiltInZoomControls(false);   // 줌 아이콘 사용
        webSettings.setSupportZoom(false);

        // url은 알아서 설정 예) http://m.naver.com/
        productAFilename.loadUrl(urlImageReal); // 접속 URL

        return v;
    }

    // select detail
    private void connectSelectData() {
        try {
            ProductNetworkTask productNetworkTask = new ProductNetworkTask(getActivity(), urlAddr, "select");

            Object object = productNetworkTask.execute().get();
            products = (ArrayList<Product>) object;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}