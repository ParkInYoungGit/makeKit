package com.example.makekit.makekit_activity;

import android.content.Intent;
import android.os.Bundle;
import android.content.Intent;
import com.example.makekit.R;
import com.example.makekit.makekit_adapter.SectionPageAdapter;
import com.example.makekit.makekit_fragment.BannerViewFragmentFirst;
import com.example.makekit.makekit_fragment.BannerViewFragmentSecond;
import com.example.makekit.makekit_fragment.BannerViewFragmentThird;
import com.example.makekit.makekit_fragment.CategoryFragment;
import com.example.makekit.makekit_fragment.ChatListFragment;
import com.example.makekit.makekit_fragment.HomeFragment;
import com.example.makekit.makekit_fragment.MypageFragment;
import com.example.makekit.makekit_fragment.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private static final int SEARCH_ADDRESS_ACTIVITY = 10000;
    Fragment fragment = new Fragment();
    private EditText et_address;
    private BottomNavigationView mBottomNV;
    private ViewPager mViewPager;
    SectionPageAdapter adapter = new SectionPageAdapter(getSupportFragmentManager());
    Button btnStart;
    ActionBar actionBar;
    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setBackground(null);
        bottomNavigationView.getMenu().getItem(2).isEnabled();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.getBackground().setAlpha(0);
//        bottomNavigationView.setBackground(null);
        FloatingActionButton fab = findViewById(R.id.fab_search);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, SearchFragment.class);
//                startActivity(intent);

                String tag = String.valueOf(R.id.fab_search);

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                Fragment currentFragment = fragmentManager.getPrimaryNavigationFragment();
                fragment = new SearchFragment();
                fragmentTransaction.add(R.id.search_fragment, fragment, tag);
            }
        });

        actionBar = getSupportActionBar();
        actionBar.setLogo(R.drawable.img_logo);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_USE_LOGO);


//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//                // 물건 판매글 작성으로
////                Intent intent = new Intent(MainActivity.this, RegisterPeopleActivity.class);
////                startActivity(intent);
//            }
//        });


    
        mBottomNV = findViewById(R.id.nav_view);

        mBottomNV.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() { //NavigationItemSelecte
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                // 하단 탭 선택시 아이템 아이디 가져온다.!
                BottomNavigate(menuItem.getItemId());

                return true;
            }
        });
        mBottomNV.setSelectedItemId(R.id.navigation_1);










    }


    private void BottomNavigate(int id) {  //BottomNavigation 페이지 변경 (하단 탭 3개 선택)
        String tag = String.valueOf(id+R.id.fab_search);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment currentFragment = fragmentManager.getPrimaryNavigationFragment();
        if (currentFragment != null) {
            fragmentTransaction.hide(currentFragment);
        }

        Fragment fragment = fragmentManager.findFragmentByTag(tag);

        if (fragment == null) {
            if (id == R.id.navigation_1) {  // 메뉴 아이템 1번 선택

                fragment = new HomeFragment();  // 프래그먼트 1번으로 이동
//                Bundle bundle = new Bundle(1);
//                bundle.putString("useremail", email);
//                bundle.putString("macIP", macIP);
//                fragment.setArguments(bundle);

            } else if (id == R.id.navigation_2) {

                fragment = new CategoryFragment();
//                Bundle bundle = new Bundle(2);
//                bundle.putString("useremail", email);
//                bundle.putString("macIP", macIP);
//                fragment.setArguments(bundle);
            } else if (id == R.id.fab_search) {

                fragment = new ChatListFragment();
//                Bundle bundle = new Bundle(2);
//                bundle.putString("useremail", email);
//                bundle.putString("macIP", macIP);
//                fragment.setArguments(bundle);
            }

            else if (id == R.id.navigation_4) {

                fragment = new ChatListFragment();
//                Bundle bundle = new Bundle(2);
//                bundle.putString("useremail", email);
//                bundle.putString("macIP", macIP);
//                fragment.setArguments(bundle);
            } else if (id == R.id.navigation_5) {

                fragment = new MypageFragment();
//                Bundle bundle = new Bundle(2);
//                bundle.putString("useremail", email);
//                bundle.putString("macIP", macIP);
//                fragment.setArguments(bundle);

            }

            fragmentTransaction.add(R.id.content_layout, fragment, tag);
        } else {
            fragmentTransaction.show(fragment);
        }

        fragmentTransaction.setPrimaryNavigationFragment(fragment);
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.commitNow();
    }



    //=========================================================================상단 메뉴
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.top_list, menu);
        return true;
    }

    //추가된 소스, ToolBar에 추가된 항목의 select 이벤트를 처리하는 함수
    public boolean onOptionsItemSelected(MenuItem item) {
        //return super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.menu_gps:

                // GPS로 가는 버튼
//                Intent intent = new Intent(MainActivity.this, MypagePWActivity01.class);
//                startActivity(intent);
                return true;

            case R.id.menu_cart:

                // 장바구니로 가는 버튼
//                Intent intent = new Intent(MainActivity.this, MypagePWActivity01.class);
//                startActivity(intent);
                return true;


            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
//                Intent intent3 = new Intent(MainActivity.this, MainActivity.class);
//
//                startActivity(intent3);
                return super.onOptionsItemSelected(item);
//                return true;
        //-----------------------------------------------------------
        }
    }
}
