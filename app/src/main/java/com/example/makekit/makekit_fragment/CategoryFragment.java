package com.example.makekit.makekit_fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.makekit.R;
import com.example.makekit.makekit_activity.ProductList;

public class CategoryFragment extends Fragment {
    String pType;

    ListView listView;
    String macIP, email;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Fragment는 Activity가 아니기때문에 리턴값과 레이아웃을 변수로 정해준다.
        View v = inflater.inflate(R.layout.fragment_category, container, false);

        email = getArguments().getString("useremail");
        macIP = getArguments().getString("macIP");
//        listView = v.findViewById(R.id.lv_rec);

v.findViewById(R.id.korean).setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getContext(), ProductList.class);

        intent.putExtra(pType,"dd");
        intent.putExtra("macIP",macIP);
        intent.putExtra("useremail",email);

        startActivity(intent);
    }
});

        return v;
    }
}
