package com.example.makekit.makekit_fragment;

import android.content.Intent;
import android.graphics.Rect;
import android.opengl.Visibility;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.makekit.R;
import com.example.makekit.makekit_adapter.WriteReviewAdapter;
import com.example.makekit.makekit_asynctask.NetworkTask_DH;
import com.example.makekit.makekit_bean.Order;
import com.example.makekit.makekit_sharVar.SharVar;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WriteReviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WriteReviewFragment extends Fragment {


    final static String TAG = "WriteReviewFragment";
    View v;
    String urlAddr, urlJsp;
    String urlAddrBase = null;
    String macIP, productNo, email, orderDetailNo, orderConfirm;
    TextView btn_Register_Review;
    String reviewCheck = null;
    LinearLayout you,meiyou;

    ArrayList<Order> reviewList;
    WriteReviewAdapter writeReviewAdapter;

    private RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public WriteReviewFragment(String macIP, String email) {
        // Required empty public constructor
        this.macIP = macIP;
        this.email = email;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.

     * @return A new instance of fragment WriteReviewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WriteReviewFragment newInstance(String param1, String param2, String param3) {
        WriteReviewFragment fragment = new WriteReviewFragment("macIP", "email");
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
            mParam2 = getArguments().getString("email");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_write_review, container, false);
        Log.v(TAG, "onCreateView WRITE REVIEW >>>>>>>" + getArguments());

        recyclerView = v.findViewById(R.id.recyclerView_writeReview);
        you = v.findViewById(R.id.you);
        meiyou = v.findViewById(R.id.meiyou);

        urlAddrBase = SharVar.urlAddrBase;
        urlAddr = urlAddrBase + "jsp/write_reviewlist_all.jsp?email=" + email;
        Log.v(TAG, "주소 >>>>>>>>>> " + urlAddr);
        // 초기 init

//        // 넘겨줄 값 지정
//        productNo = reviewList.get(0).getGoods_productNo();
//        orderDetailNo = reviewList.get(0).getOrderDetailNo();
//        orderConfirm = reviewList.get(0).getOrderConfirm();


        return v;
    } // onCreate END -----------------------------------------------------------------

    @Override
    public void onResume() {
        super.onResume();
        Log.v(TAG, "onResume REVIEW");
        check();
        urlAddr = urlAddrBase + "jsp/write_reviewlist_all.jsp?email=" + email;
        connectSelectData();
        recyclerView.setAdapter(writeReviewAdapter);
        recyclerView.setHasFixedSize(true); // 리사이클러뷰 기존성능 강화
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
    }

    // select review
    private void connectSelectData() {
        Log.v(TAG, "Before Try --------------------------! ");
        try {
            Log.v(TAG, "After Try --------------------------! ");
            NetworkTask_DH networkTaskDh = new NetworkTask_DH(getActivity(), urlAddr,"writeReviewList");

            Object object = networkTaskDh.execute().get();
            reviewList = (ArrayList<Order>) object;

            Log.v(TAG, "reviewList ----------- " + reviewList);

            writeReviewAdapter = new WriteReviewAdapter(getActivity(), R.layout.custom_write_review, reviewList, urlAddrBase, email, macIP);


            Log.v(TAG, "reviewList ----------- " + reviewList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void check(){
        urlJsp =  SharVar.urlAddrBase + "jsp/reviewlist_empty_check.jsp?";
        urlAddr = urlJsp + "email=" + email;

        reviewCheck = reviewListCheck();

        if (reviewCheck.equals("0")){
            meiyou.setVisibility(View.VISIBLE);
            you.setVisibility(View.INVISIBLE);

        }else {
            meiyou.setVisibility(View.INVISIBLE);
            you.setVisibility(View.VISIBLE);
        }
    }


    private String reviewListCheck() {
        try {
            NetworkTask_DH checkTask = new NetworkTask_DH(getActivity(), urlAddr, "reviewCheck");
            Object obj = checkTask.execute().get();

            reviewCheck = (String) obj;

        }catch (Exception e){
            e.printStackTrace();
        }
        return reviewCheck;
    }

}  // END ------------------------------------------------------------------------------