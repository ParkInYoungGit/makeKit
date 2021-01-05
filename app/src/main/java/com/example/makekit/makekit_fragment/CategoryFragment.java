package com.example.makekit.makekit_fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.makekit.R;
import com.example.makekit.makekit_bean.Product;

public class CategoryFragment extends Fragment {


    ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Fragment는 Activity가 아니기때문에 리턴값과 레이아웃을 변수로 정해준다.
        View v = inflater.inflate(R.layout.fragment_category, container, false);


//        listView = v.findViewById(R.id.lv_rec);



        return v;
    }
}
