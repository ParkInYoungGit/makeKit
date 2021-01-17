package com.example.makekit.makekit_fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.makekit.R;
import com.example.makekit.makekit_activity.ProductList;

public class CategoryFragment extends Fragment {
    String pType;

    ListView listView;
    String macIP, email;
    ImageView imageView1;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;
    ImageView imageView5;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Fragment는 Activity가 아니기때문에 리턴값과 레이아웃을 변수로 정해준다.
        View v = inflater.inflate(R.layout.fragment_category, container, false);

        email = getArguments().getString("useremail");
        macIP = getArguments().getString("macIP");
//        listView = v.findViewById(R.id.lv_rec);
        imageView1 = v.findViewById(R.id.korean);
        imageView2 = v.findViewById(R.id.american);
        imageView3 = v.findViewById(R.id.chinese);
        imageView4 = v.findViewById(R.id.etc);
        imageView5 = v.findViewById(R.id.set);

        imageView1.setOnClickListener(onClickListener);
        imageView2.setOnClickListener(onClickListener);
        imageView3.setOnClickListener(onClickListener);
        imageView4.setOnClickListener(onClickListener);
        imageView5.setOnClickListener(onClickListener);
        return v;
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.korean:
                    pType = "korea";
                    Intent intent = new Intent(getContext(), ProductList.class);
                    intent.putExtra("pType", pType);
                    intent.putExtra("macIP", macIP);
                    intent.putExtra("useremail", email);

                    startActivity(intent);
                    break;

                case R.id.american:
                    Intent intent1 = new Intent(getContext(), ProductList.class);
                    pType = "american";
                    intent1.putExtra("pType", pType);
                    intent1.putExtra("macIP", macIP);
                    intent1.putExtra("useremail", email);

                    startActivity(intent1);
                    break;

                case R.id.etc:
                    Intent intent2 = new Intent(getContext(), ProductList.class);
                    pType = "etc";
                    intent2.putExtra("pType", pType);
                    intent2.putExtra("macIP", macIP);
                    intent2.putExtra("useremail", email);

                    startActivity(intent2);
                    break;

                case R.id.set:
                    Intent intent3 = new Intent(getContext(), ProductList.class);
                    pType = "set";
                    intent3.putExtra("pType", pType);
                    intent3.putExtra("macIP", macIP);
                    intent3.putExtra("useremail", email);

                    startActivity(intent3);
                    break;

                case R.id.chinese:
                    Intent intent4 = new Intent(getContext(), ProductList.class);
                    pType = "chinese";
                    intent4.putExtra("pType", pType);
                    intent4.putExtra("macIP", macIP);
                    intent4.putExtra("useremail", email);

                    startActivity(intent4);
                    break;
            }

        }
    };

}

