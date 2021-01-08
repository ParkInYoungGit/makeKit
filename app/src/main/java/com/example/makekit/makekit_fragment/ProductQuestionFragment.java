package com.example.makekit.makekit_fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.QuickContactBadge;

import com.example.makekit.R;
import com.example.makekit.makekit_activity.ChatcontentActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductQuestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductQuestionFragment extends Fragment {

    View v;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProductQuestionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProductQuestionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductQuestionFragment newInstance(String param1, String param2) {
        ProductQuestionFragment fragment = new ProductQuestionFragment();
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

        Button btnQuestion = v.findViewById(R.id.btnChattingQuestion_productview);

        btnQuestion.setOnClickListener(mClickListener);

        return v;
    }

    View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){

                // 1:1 문의 클릭 시 판매자 대화창 이동
                case R.id.btnChattingQuestion_productview:
                    Intent intent = new Intent(getActivity(), ChatcontentActivity.class );
                    startActivity(intent);
                    break;

            }
        }
    };

}