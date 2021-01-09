package com.example.makekit.makekit_fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.example.makekit.R;
import com.example.makekit.makekit_adapter.ProductReviewAdapter;
import com.example.makekit.makekit_asynctask.ProductNetworkTask;
import com.example.makekit.makekit_asynctask.ReviewNetworkTask;
import com.example.makekit.makekit_bean.Product;
import com.example.makekit.makekit_bean.Review;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductContentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */


//////////////////////////////////////////
// jsp makekit 부분 makeKit 으로 변경 필요!!!
//////////////////////////////////////////


public class ProductContentFragment extends Fragment {

    TextView productName, productPrice, productContent;
    WebView productFilename, productDfilename, productAFilename;

    View v;
    String urlAddr, urlAddrBase, urlImageReal1, urlImageReal2;
    ArrayList<Product> products;
    final static String TAG = "ProductContentFragment";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProductContentFragment() {
        // Required empty public constructor
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
        ProductContentFragment fragment = new ProductContentFragment();
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
        mParam1 = "192.168.219.164";
        mParam2 = "44";

        urlAddrBase = "http://" + mParam1 + ":8080/makekit/";
        urlAddr = urlAddrBase + "jsp/product_productview_content.jsp?productno=" + mParam2;
        Log.v(TAG, "주소" + urlAddr);

        productName = v.findViewById(R.id.productNmae_prodcutviewcontent);
        productPrice  = v.findViewById(R.id.productPrice_productviewcontent);
        productContent = v.findViewById(R.id.productContent_productviewcontent);
        productFilename  = v.findViewById(R.id.productImage_productviewcontent);
        productDfilename = v.findViewById(R.id.prdouctdetail_productviewcontent);
        //productAFilename

        connectSelectData();

        productName.setText(products.get(0).getProductName());
        productContent.setText(products.get(0).getProductContent());
        productPrice.setText(products.get(0).getProductPrice() + "원");

            urlImageReal1 = urlAddrBase+ "image/" + products.get(0).getProductFilename();
            urlImageReal2 = urlAddrBase+ "image/" + products.get(0).getProductDfilename();

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
            productFilename.setInitialScale(30);

            // url은 알아서 설정 예) http://m.naver.com/
             productFilename.loadUrl(urlImageReal1); // 접속 URL


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
            productDfilename.setInitialScale(30);
//
//            // url은 알아서 설정 예) http://m.naver.com/
            productDfilename.loadUrl(urlImageReal2); // 접속 URL


        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.v(TAG, "onResume PRODUCT");
        connectSelectData();
    }

    // select review
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