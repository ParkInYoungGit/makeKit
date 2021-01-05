package com.example.makekit.makekit_fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.makekit.R;
import com.example.makekit.makekit_activity.BuyListActivity;
import com.example.makekit.makekit_activity.LikeProductActivity;
import com.example.makekit.makekit_activity.ReviewListActivity;
import com.example.makekit.makekit_activity.SaleListActivity;
import com.example.makekit.makekit_activity.UserModifyActivity;


public class MypageFragment extends Fragment {

    Button setting_btn, notice_btn, chatlist_btn;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Fragment는 Activity가 아니기때문에 리턴값과 레이아웃을 변수로 정해준다.
        View v = inflater.inflate(R.layout.fragment_mypage, container, false);

        setting_btn = v.findViewById(R.id.setting_btn);
        setting_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), UserModifyActivity.class);
                startActivity(intent);
            }
        });



        v.findViewById(R.id.buylist_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), BuyListActivity.class);
                startActivity(intent);
            }
        });

        v.findViewById(R.id.salelist_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SaleListActivity.class);
                startActivity(intent);
            }
        });

        v.findViewById(R.id.likelist_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LikeProductActivity.class);
                startActivity(intent);
            }
        });

        v.findViewById(R.id.reviewlist_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ReviewListActivity.class);
                startActivity(intent);
            }
        });


        return v;
    }

}