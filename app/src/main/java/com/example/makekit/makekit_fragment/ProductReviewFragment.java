package com.example.makekit.makekit_fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.makekit.R;
import com.example.makekit.makekit_adapter.ProductReviewAdapter;
import com.example.makekit.makekit_asynctask.ReviewNetworkTask;
import com.example.makekit.makekit_bean.Review;
import com.example.makekit.makekit_sharVar.SharVar;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductReviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

//////////////////////////////////////////
// jsp makekit 부분 makeKit 으로 변경 필요!!!
//////////////////////////////////////////

public class ProductReviewFragment extends Fragment {

    View v;
    String urlAddr;
    String urlAddrBase = null;
    String macIP, productNo;
    String where;

    ArrayList<Review> reviews;
    ProductReviewAdapter productReviewAdapter;

    private RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;

    final static String TAG = "ProductReviewFragment";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProductReviewFragment(String macIP, String productNo) {
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
     * @return A new instance of fragment ProductReviewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductReviewFragment newInstance(String param1, String param2) {
        ProductReviewFragment fragment = new ProductReviewFragment("macIP", "productNo");
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG, "onCreate REVIEW" + getArguments());
        if (getArguments() != null) {
            mParam1 = getArguments().getString("macIP");
            mParam2 = getArguments().getString("productNo");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_product_review, container, false);
        Log.v(TAG, "onCreateView REVIEW" + getArguments());

        recyclerView = v.findViewById(R.id.reviewList_productview);

//        if (getArguments() != null) {
//            mParam1 = getArguments().getString("macIP");
//            mParam2 = getArguments().getString("productNo");
//        }
//        mParam1 = "192.168.219.164";
//        mParam2 = "44";
        urlAddrBase = SharVar.urlAddrBase;
        //urlAddrBase = "http://" + macIP + ":8080/makeKit/";
        urlAddr = urlAddrBase + "jsp/review_productview_all.jsp?productno=" + productNo;
        Log.v(TAG, "주소" + urlAddr);
        connectSelectData();
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.v(TAG, "onResume REVIEW");
        connectSelectData();
    }

    // select review
    private void connectSelectData() {
        try {
            ReviewNetworkTask reviewNetworkTask = new ReviewNetworkTask(getActivity(), urlAddr, "selectProduct");

            Object object = reviewNetworkTask.execute().get();
            reviews = (ArrayList<Review>) object;

            productReviewAdapter = new ProductReviewAdapter(getActivity(), R.layout.custom_productview_review, reviews, urlAddrBase);
            recyclerView.setAdapter(productReviewAdapter);
            recyclerView.setHasFixedSize(true); // 리사이클러뷰 기존성능 강화
            layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}