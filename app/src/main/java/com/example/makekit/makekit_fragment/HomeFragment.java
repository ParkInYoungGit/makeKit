package com.example.makekit.makekit_fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.makekit.R;
import com.example.makekit.makekit_adapter.SectionPageAdapter;

import com.example.makekit.makekit_bean.Product;

import me.relex.circleindicator.CircleIndicator;

public class HomeFragment extends Fragment {

    private ViewPager mViewPager;
//    ProductAdapter adapter = new ProductAdapter(getActivity().getSupportFragmentManager());
//    SectionPageAdapter adapter = new SectionPageAdapter(getChildFragmentManager());
    Button btnStart;
    RecyclerView recyclerView;
    ProductAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Fragment는 Activity가 아니기때문에 리턴값과 레이아웃을 변수로 정해준다.
        View v = inflater.inflate(R.layout.fragment_home, container, false);


        // 앱소개 뷰페이저
        mViewPager = (ViewPager) v.findViewById(R.id.container);
        mViewPager.setOffscreenPageLimit(3);

        setupViewPager(mViewPager);   // 뷰페이지 불러오기
        CircleIndicator indicator = (CircleIndicator) v.findViewById(R.id.indicator); // 인디케이터 불러오기
        indicator.setViewPager(mViewPager);  // 인디케이터 안에 페이저처리





        recyclerView = v.findViewById(R.id.recyclerView);

        //LinearLayoutManager.HORIZONTAL로 넘기는 방향설정 가능 (첫번쨰파라미터는 context 세번쨰파라미터는 아이템이 보이는 방향을 애기한다.)
        //세번쨰파라미터는 예를들어 채팅방같은 경우 메세지가 아래에서 위로 올라가는 방향같은거 설정
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false); //레이아웃매니저 생성
        recyclerView.setLayoutManager(layoutManager);//만든 레이아웃매니저 객체를(설정을) 리사이클러 뷰에 설정해줌

        adapter = new ProductAdapter(getContext());
        //아이템추가
        adapter.addItem(new Product("도시락", "5000" , R.drawable.img_product1));
        adapter.addItem(new Product("도시락2", "5000" , R.drawable.img_product1));
        adapter.addItem(new Product("도시락3", "5000" , R.drawable.img_product1));
        adapter.addItem(new Product("도시락4", "5000" , R.drawable.img_product1));
        adapter.addItem(new Product("도시락5", "5000" , R.drawable.img_product1));
        adapter.addItem(new Product("도시락6", "5000" , R.drawable.img_product1));



        //어댑터에 연결
        recyclerView.setAdapter(adapter);

        //어댑터클래스에 직접 이벤트처리관련 코드를 작성해줘야함 (리스트뷰처럼 구현되어있지않음 직접 정의해놔야한다.)
        //setOnItemClickListener라는 이름으로 이벤트 메소드 직접 정의한거임
        adapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(ProductAdapter.ViewHolder holder, View view, int position) {

                Product item = adapter.getItem(position);

            }
        });
        return v;
    }


    public void setupViewPager(ViewPager viewPager) {
        SectionPageAdapter adapter = new SectionPageAdapter(getFragmentManager());
        adapter.addFragment(new BannerViewFragmentFirst(), "1");
        adapter.addFragment(new BannerViewFragmentSecond(), "2");
        adapter.addFragment(new BannerViewFragmentThird(), "3");

        viewPager.setAdapter(adapter);
    }


}