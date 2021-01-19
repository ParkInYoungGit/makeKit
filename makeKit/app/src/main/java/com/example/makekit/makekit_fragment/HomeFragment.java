package com.example.makekit.makekit_fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.makekit.R;
import com.example.makekit.makekit_activity.ChatcontentActivity;
import com.example.makekit.makekit_adapter.ProductHotAdapter;
import com.example.makekit.makekit_adapter.ProductNewAdapter;
import com.example.makekit.makekit_adapter.ProductRecAdapter;
import com.example.makekit.makekit_adapter.SectionPageAdapter;
import com.example.makekit.makekit_asynctask.NetworkTask_DH;
import com.example.makekit.makekit_bean.ChattingBean;
import com.example.makekit.makekit_bean.Product;
import com.example.makekit.makekit_sharVar.SharVar;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;

public class HomeFragment extends Fragment {

    private ViewPager mViewPager;
//    ProductAdapter adapter = new ProductAdapter(getActivity().getSupportFragmentManager());
//    SectionPageAdapter adapter = new SectionPageAdapter(getChildFragmentManager());
    RecyclerView recyclerViewHot;
    RecyclerView recyclerViewRec;
    RecyclerView recyclerViewNew;
    LinearLayoutManager layoutManagerHot;
    LinearLayoutManager layoutManagerRec;
    LinearLayoutManager layoutManagerNew;
    ProductHotAdapter hotAdapter;
    ProductRecAdapter recAdapter;
    ProductNewAdapter newAdapter;

    ArrayList<Product> hotProduct;
    ArrayList<Product> recProduct;
    ArrayList<Product> newProduct;

    String email, macIP, urlAddrBase;


    ViewFlipper v_fllipper;


    int images[] = {
            R.drawable.img_banner1,
            R.drawable.img_banner2,
            R.drawable.img_banner3
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Fragment는 Activity가 아니기때문에 리턴값과 레이아웃을 변수로 정해준다.
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        // 앱소개 뷰페이저
//        mViewPager = (ViewPager) v.findViewById(R.id.container);
//        mViewPager.setOffscreenPageLimit(3);
//        setupViewPager(mViewPager);   // 뷰페이지 불러오기
//        CircleIndicator indicator = (CircleIndicator) v.findViewById(R.id.indicator); // 인디케이터 불러오기
//        indicator.setViewPager(mViewPager);  // 인디케이터 안에 페이저처리

        // 21/1/14 Min
        v_fllipper = v.findViewById(R.id.image_slide);

        for(int image : images) {
            fllipperImages(image);
        }

        hotProduct = new ArrayList<Product>();
        recProduct = new ArrayList<Product>();
        newProduct = new ArrayList<Product>();
        
        macIP = SharVar.macIP;
        email = SharVar.userEmail;
        urlAddrBase = SharVar.urlAddrBase;

        recyclerViewHot = v.findViewById(R.id.recyclerViewHot);
        recyclerViewRec = v.findViewById(R.id.recyclerViewRec);
        recyclerViewNew = v.findViewById(R.id.recyclerViewNew);

        recyclerViewNew.setHasFixedSize(true);
        recyclerViewRec.setHasFixedSize(true);
        recyclerViewHot.setHasFixedSize(true);

        //LinearLayoutManager.HORIZONTAL로 넘기는 방향설정 가능 (첫번쨰파라미터는 context 세번쨰파라미터는 아이템이 보이는 방향을 애기한다.)
        //세번쨰파라미터는 예를들어 채팅방같은 경우 메세지가 아래에서 위로 올라가는 방향같은거 설정
        layoutManagerHot = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false); //레이아웃매니저 생성
        layoutManagerRec = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false); //레이아웃매니저 생성
        layoutManagerNew = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false); //레이아웃매니저 생성

        recyclerViewHot.setLayoutManager(layoutManagerHot);//만든 레이아웃매니저 객체를(설정을) 리사이클러 뷰에 설정해줌
        recyclerViewNew.setLayoutManager(layoutManagerRec);//만든 레이아웃매니저 객체를(설정을) 리사이클러 뷰에 설정해줌
        recyclerViewRec.setLayoutManager(layoutManagerNew);//만든 레이아웃매니저 객체를(설정을) 리사이클러 뷰에 설정해줌

        //아이템추가
//        adapter.addItem(new Product("도시락", "5000" , R.drawable.img_product1));
//        adapter.addItem(new Product("도시락2", "5000" , R.drawable.img_product1));
//        adapter.addItem(new Product("도시락3", "5000" , R.drawable.img_product1));
//        adapter.addItem(new Product("도시락4", "5000" , R.drawable.img_product1));
//        adapter.addItem(new Product("도시락5", "5000" , R.drawable.img_product1));
//        adapter.addItem(new Product("도시락6", "5000" , R.drawable.img_product1));

        //어댑터에 연결
//        recyclerView.setAdapter(adapter);

        //어댑터클래스에 직접 이벤트처리관련 코드를 작성해줘야함 (리스트뷰처럼 구현되어있지않음 직접 정의해놔야한다.)
        //setOnItemClickListener라는 이름으로 이벤트 메소드 직접 정의한거임
        return v;
    }

// 뷰페이저 사용 세팅
//    public void setupViewPager(ViewPager viewPager) {
//        SectionPageAdapter adapter = new SectionPageAdapter(getFragmentManager());
//        adapter.addFragment(new BannerViewFragmentFirst(), "1");
//        adapter.addFragment(new BannerViewFragmentSecond(), "2");
//        adapter.addFragment(new BannerViewFragmentThird(), "3");
//
//        viewPager.setAdapter(adapter);
//    }

    @Override
    public void onResume() {
        super.onResume();
        //urlAddrBase = "http://" + macIP + ":8080/makeKit/";
        urlAddrBase = SharVar.urlAddrBase;

        connectGetHotData();
        connectGetRecData();
        connectGetNewData();

        hotAdapter = new ProductHotAdapter(getContext(), hotProduct, urlAddrBase);
        recAdapter = new ProductRecAdapter(getContext(), recProduct, urlAddrBase);
        newAdapter = new ProductNewAdapter(getContext(), newProduct, urlAddrBase);

        recyclerViewHot.setAdapter(hotAdapter);
        recyclerViewRec.setAdapter(recAdapter);
        recyclerViewNew.setAdapter(newAdapter);

    }

    private void connectGetHotData(){
        try {
            NetworkTask_DH networkTask = new NetworkTask_DH(getContext(), urlAddrBase+"jsp/hotProduct.jsp", "search");
            Object obj = networkTask.execute().get();
            hotProduct = (ArrayList<Product>) obj;
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void connectGetRecData(){
        try {
            NetworkTask_DH networkTask = new NetworkTask_DH(getContext(), urlAddrBase+"jsp/recProduct.jsp", "search");
            Object obj = networkTask.execute().get();
            recProduct = (ArrayList<Product>) obj;
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void connectGetNewData(){
        try {
            NetworkTask_DH networkTask = new NetworkTask_DH(getContext(), urlAddrBase+"jsp/newProduct.jsp", "search");
            Object obj = networkTask.execute().get();
            newProduct = (ArrayList<Product>) obj;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // 이미지 슬라이더 구현 메서드
    public void fllipperImages(int image) {
        ImageView imageView = new ImageView(getContext());
        imageView.setBackgroundResource(image);

        v_fllipper.addView(imageView);      // 이미지 추가
        v_fllipper.setFlipInterval(3000);       // 자동 이미지 슬라이드 딜레이시간(1000 당 1초)
        v_fllipper.setAutoStart(true);          // 자동 시작 유무 설정

        // animation
        v_fllipper.setInAnimation(getContext(),android.R.anim.slide_in_left);
        v_fllipper.setOutAnimation(getContext(),android.R.anim.slide_out_right);
    }

}