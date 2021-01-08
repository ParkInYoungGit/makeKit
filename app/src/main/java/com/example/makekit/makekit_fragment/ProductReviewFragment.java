package com.example.makekit.makekit_fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.makekit.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductReviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductReviewFragment extends Fragment {

    View v;

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

    public ProductReviewFragment() {
        // Required empty public constructor
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
        ProductReviewFragment fragment = new ProductReviewFragment();
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

        v = inflater.inflate(R.layout.fragment_product_question,container,false);
        Log.v(TAG, "onCreate" + getArguments());

        if (getArguments() != null) {
            mParam1 = getArguments().getString("sellerEmail");
            mParam2 = getArguments().getString("productNo");
        }


        return v;
    }

    // select review
    private void connectGetData() {
        try {
//            AddressNetworkTask networkTask = new AddressNetworkTask(getActivity(), urlAddr); //onCreate 에 urlAddr 이 선언된것이 들어옴
//
//            // object 에서 선언은 되었지만 실질적으로 리턴한것은 arraylist
//            Object object = networkTask.execute().get();
//            addresses = (ArrayList<Address>) object;
//            Log.v(TAG, "addresses size = " + String.valueOf(addresses.size()));
//            //StudentAdapter.java 의 생성자를 받아온다.
//            adapter = new AddressAdapter(getActivity(), R.layout.item_contact, addresses);
//            recyclerView.setAdapter(adapter);
//            recyclerView.setHasFixedSize(true); // 리사이클러뷰 기존성능 강화
//            layoutManager = new LinearLayoutManager(getContext());
//            recyclerView.setLayoutManager(layoutManager);
//
s          e.printStackTrace();
        }
    }

}