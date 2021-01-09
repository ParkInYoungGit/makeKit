package com.example.makekit.makekit_method;

import android.app.Application;

import com.kakao.sdk.common.KakaoSdk;

public class KakaoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        KakaoSdk.init(this, "f2d8fb4aeb0237f82647add92f60e987"); // 카카오 초기화
    }
}
