package com.example.makekit.makekit_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.makekit.R;

public class SplashActivity extends AppCompatActivity {

    ImageView MakeKit_Logo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        // 애니메이션에 쓸 이미지
        MakeKit_Logo = findViewById(R.id.MakeKit_Logo);





        // 애니메이션 읽어오는 법
        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_translate_alpha);
//        Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
//        Animation animation3 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_translate_alpha2);

        // 애니메이션 리스너
        animation1.setAnimationListener(new Animation.AnimationListener() {

            Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_translate_alpha);  // 측에서 중앙
//            Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);  // 회전
//            Animation animation3 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_translate_alpha2);  // 우측에서 중앙

            @Override
            public void onAnimationStart(Animation animation) {    // 애니메이션 시작시
//                trans(500,0,0,0);
//                splash_3.startAnimation(animation3);
//                splash_2.startAnimation(animation2);

            }

            @Override
            public void onAnimationEnd(Animation animation) {  // 애니메이션 끝나고

            }

            @Override
            public void onAnimationRepeat(Animation animation) {  // 애니메이션 하는중
//                splash_1.getAnimation().cancel();
//                splash_1.clearAnimation();

            }
        });

        // 애니메이션 스타트
        MakeKit_Logo.startAnimation(animation1);
//        splash_2.startAnimation(animation2);
//        splash_3.startAnimation(animation3);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {   // 스플래시는 바로 메인으로 넘어가기위해 온 크리에이트 안에서 바로 스타트 인텐트 해준다

                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
            }
        },3000);   // 시작 전 애니메이션을 위한 딜레이를 준다 3.0초 = 3000
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }


    // 애니메이션 자바에서 쓰는 법 (좀 더 자유로운 조정이 가능)

//    public void trans(float formXDelta,float toXDelta,float fromYDelta, float toYDelta){
//        TranslateAnimation translateAnimation = new TranslateAnimation(formXDelta,toXDelta,fromYDelta,toYDelta);
//        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
//            Animation animation3 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });
//    }

}