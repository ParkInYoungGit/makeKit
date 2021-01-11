package com.example.makekit.makekit_fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.makekit.R;
import com.example.makekit.makekit_bean.Product;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReviewListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReviewListFragment extends Fragment {

    TextView productName, productPrice, productContent, productTotalPrice, purchaseNumInput;
    WebView productFilename, productDfilename, productAFilename;
    Button btnPlus, btnMinus;
    int count = 1;

    View v;
    String urlAddr, urlAddrBase, urlImageReal1, urlImageReal2, urlImageReal3, orderNo, macIP, productNo;
    ArrayList<Product> products;
    final static String TAG = "ReviewListFragment";


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ReviewListFragment(String macIP, String productNo, String orderNo) {
        // Required empty public constructor
        this.macIP = macIP;
        this.productNo = productNo;
        this.orderNo = orderNo;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReviewListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReviewListFragment newInstance(String param1, String param2) {
        ReviewListFragment fragment = new ReviewListFragment("macIP", "productNo", "orderNo");
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
            mParam2 = getArguments().getString("orderNo");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_product_content, container, false);
        Log.v(TAG, "onCreateView content" + getArguments());




        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_review_list, container, false);
    }
}