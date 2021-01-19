package com.example.makekit.makekit_fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.makekit.makekit_activity.AboutMakeKitActivity;
import com.example.makekit.makekit_activity.LoginActivity;
import com.example.makekit.makekit_activity.PurchaseListActivity;
import com.example.makekit.R;
import com.example.makekit.makekit_activity.BuyListActivity;
import com.example.makekit.makekit_activity.LikeProductActivity;
import com.example.makekit.makekit_activity.NoticeActivity;
import com.example.makekit.makekit_activity.ReviewListActivity;
import com.example.makekit.makekit_activity.SaleProductListActivity;
import com.example.makekit.makekit_activity.SalesListActivity;
import com.example.makekit.makekit_activity.UserModifyActivity;
import com.example.makekit.makekit_sharVar.SharVar;


public class MypageFragment extends Fragment {

    TextView setting_btn, buylist_btn, salelist_btn, likelist_btn, reviewlist_btn;  // 회원정보 수정, 구매내역, 판매내역, 찜목록, 후기목록
    Button notice_btn, aboutMakeKit_btn, Alarm_btn, btn_logout, btn_view_dial;  // 공지사항, 채팅목록, 어바웃메이킷, 알람설정, 로그아웃, 전화문의
    TextView user_mypage_name;
    String macIP, email;    // 이메일과 아이피

    String phonetel;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Fragment는 Activity가 아니기때문에 리턴값과 레이아웃을 변수로 정해준다.
        View v = inflater.inflate(R.layout.fragment_mypage, container, false);

        macIP = SharVar.macIP;
        email = SharVar.userEmail;

        setting_btn = v.findViewById(R.id.setting_btn);
        buylist_btn = v.findViewById(R.id.buylist_btn);
        salelist_btn = v.findViewById(R.id.salelist_btn);
        likelist_btn = v.findViewById(R.id.likelist_btn);
        reviewlist_btn = v.findViewById(R.id.reviewlist_btn);
        notice_btn = v.findViewById(R.id.notice_btn);
        aboutMakeKit_btn = v.findViewById(R.id.aboutMakeKit_btn);
        Alarm_btn = v.findViewById(R.id.Alarm_btn);
        user_mypage_name = v.findViewById(R.id.user_mypage_name);
        user_mypage_name.setText(email+"님 환영합니다.");


        btn_logout = v.findViewById(R.id.btn_logout);
        btn_view_dial = v.findViewById(R.id.btn_view_dial);

        setting_btn.setOnClickListener(onClickListener);         // 회원정보수정
        buylist_btn.setOnClickListener(onClickListener);         // 구매내역
        salelist_btn.setOnClickListener(onClickListener);        // 판매내역
        likelist_btn.setOnClickListener(onClickListener);        // 찜 목록
        reviewlist_btn.setOnClickListener(onClickListener);      // 후기 목록
        notice_btn.setOnClickListener(onClickListener);          // 공지사항
        aboutMakeKit_btn.setOnClickListener(onClickListener);    // 메이킷설명
        Alarm_btn.setOnClickListener(onClickListener);           // 후기 목록
        user_mypage_name.setOnClickListener(onClickListener);           // 후기 목록


        btn_logout.setOnClickListener(onClickListener);     // 로그아웃
        btn_view_dial.setOnClickListener(onClickListener);  // 전화문의

        return v;
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.user_mypage_name:              // 회원정보 수정 버튼
                    Intent intentname = new Intent(getActivity(), UserModifyActivity.class);
                    startActivity(intentname);
                    break;
                case R.id.setting_btn:              // 회원정보 수정 버튼
                    Intent intent = new Intent(getActivity(), UserModifyActivity.class);
                    startActivity(intent);
                    break;
                case R.id.buylist_btn:              // 구매내역 버튼
                    Intent intent1 = new Intent(getContext(), PurchaseListActivity.class);
                    startActivity(intent1);
                    break;
                case R.id.salelist_btn:             // 판매내역 버튼
                    Intent intent2 = new Intent(getContext(), SalesListActivity.class);
                    startActivity(intent2);
                    break;
                case R.id.likelist_btn:             // 찜 목록 버튼
                    Intent intent3 = new Intent(getActivity(), LikeProductActivity.class);
                    startActivity(intent3);
                    break;
                case R.id.reviewlist_btn:           // 후기 목록 버튼
                    Intent intent4 = new Intent(getActivity(), ReviewListActivity.class);
                    startActivity(intent4);
                    break;
                case R.id.notice_btn:               // 공지 사항 버튼
                    Intent intent5 = new Intent(getActivity(), NoticeActivity.class);
                    startActivity(intent5);
                    break;
                case R.id.aboutMakeKit_btn:         // 브랜드 설명 버튼
                    Intent intent7 = new Intent(getActivity(), AboutMakeKitActivity.class);
                    startActivity(intent7);
                    break;
                case R.id.Alarm_btn:                // 알람설정 버튼
//                    Intent intent8 = new Intent(getActivity(), ReviewListActivity.class);
//                    startActivity(intent8);
                    break;
                case R.id.btn_logout:               // 메인 phoneNumber 기준 전화로 이동
                    Intent intent9 = new Intent(getActivity(), LoginActivity.class);
                    SharVar.userEmail ="";
                    startActivity(intent9);
                    break;
                case R.id.btn_view_dial:            // 메인 phoneNumber 기준 전화로 이동
                    Intent intent10 = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "03-728-2293"));
                    startActivity(intent10);
                    break;
            }


        }
    };

}