package com.example.makekit.makekit_fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.makekit.R;
import com.example.makekit.makekit_activity.BuyListActivity;
import com.example.makekit.makekit_activity.LikeProductActivity;
import com.example.makekit.makekit_activity.NoticeActivity;
import com.example.makekit.makekit_activity.ReviewListActivity;
import com.example.makekit.makekit_activity.SaleListActivity;
import com.example.makekit.makekit_activity.UserModifyActivity;


public class MypageFragment extends Fragment {

    Button setting_btn, buylist_btn, salelist_btn, likelist_btn, reviewlist_btn;  // 회원정보 수정, 구매내역, 판매내역, 찜목록, 후기목록

    String macIP, email;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Fragment는 Activity가 아니기때문에 리턴값과 레이아웃을 변수로 정해준다.
        View v = inflater.inflate(R.layout.fragment_mypage, container, false);

        email = getArguments().getString("useremail");
        macIP = getArguments().getString("macIP");

        setting_btn = v.findViewById(R.id.setting_btn);
        buylist_btn = v.findViewById(R.id.buylist_btn);
        salelist_btn = v.findViewById(R.id.salelist_btn);
        likelist_btn = v.findViewById(R.id.likelist_btn);
        reviewlist_btn = v.findViewById(R.id.reviewlist_btn);

        setting_btn.setOnClickListener(onClickListener);
        buylist_btn.setOnClickListener(onClickListener);
        salelist_btn.setOnClickListener(onClickListener);
        likelist_btn.setOnClickListener(onClickListener);
        reviewlist_btn.setOnClickListener(onClickListener);



        setting_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), UserModifyActivity.class);
                intent.putExtra("macIP", macIP);
                intent.putExtra("useremail", email);
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
                Intent intent = new Intent(getContext(), SaleListActivity.class);
                intent.putExtra("macIP", macIP);
                intent.putExtra("useremail", email);
                startActivity(intent);
            }
        });

        v.findViewById(R.id.likelist_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LikeProductActivity.class);
                intent.putExtra("macIP", macIP);
                intent.putExtra("useremail", email);
                startActivity(intent);
            }
        });

        v.findViewById(R.id.reviewlist_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ReviewListActivity.class);
                startActivity(intent);
            }
        });

        v.findViewById(R.id.notice_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), NoticeActivity.class);
                startActivity(intent);
            }
        });

        return v;
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.setting_btn:   // 회원정보 수정 버튼
                    Intent intent = new Intent(getActivity(), UserModifyActivity.class);
                    intent.putExtra("useremail", email);
                    intent.putExtra("macIP", macIP);
                    startActivity(intent);
                    break;
                case R.id.buylist_btn:  // 구매내역 버튼
                    Intent intent1 = new Intent(getActivity(), BuyListActivity.class);
                    startActivity(intent1);
                    break;
                case R.id.salelist_btn: // 판매내역 버튼
                    Intent intent2 = new Intent(getActivity(), SaleListActivity.class);
                    startActivity(intent2);
                    break;
                case R.id.likelist_btn: // 찜 목록 버튼
                    Intent intent3 = new Intent(getActivity(), LikeProductActivity.class);
                    startActivity(intent3);
                    break;
                case R.id.reviewlist_btn:// 후기 목록 버튼
                    Intent intent4 = new Intent(getActivity(), ReviewListActivity.class);
                    startActivity(intent4);
                    break;
            }

        }
    };

}