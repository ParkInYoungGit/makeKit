package com.example.makekit.makekit_activity;

import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.makekit.R;
import com.kakao.sdk.auth.LoginClient;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

public class LoginActivity extends AppCompatActivity {

    final static String TAG = "LoginActivity";
    private View btnLogin, btnLogout;
    private TextView nickName;
    private ImageView profileIMG;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.login_kakao);
        btnLogout = findViewById(R.id.btn_logout);
        nickName = findViewById(R.id.nickname);
        profileIMG = findViewById(R.id.profile);

        // 별도의 콜백 객체 선언
        Function2<OAuthToken, Throwable, Unit> callback = new Function2<OAuthToken, Throwable, Unit>() {
            @Override
            public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {

                if (oAuthToken != null) {
                    // 로그인 성공시 처리해야하는 것들 요기에

                }
                if (throwable != null) {
                    // 오류값을 핸들링 해주는 곳
                }
                updateKakaoLoginUi();
                return null;
            }
        };


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 폰에 카톡 설치 되어 있는 경우 -> 카톡으로 로그인
                if (LoginClient.getInstance().isKakaoTalkLoginAvailable(LoginActivity.this)) {
                    LoginClient.getInstance().loginWithKakaoTalk(LoginActivity.this, callback);

                } else { // 폰에 카톡 설치 되어있지 않은 경우 -> 카카오 계정으로 로그인
                    LoginClient.getInstance().loginWithKakaoAccount(LoginActivity.this, callback);
                }
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserApiClient.getInstance().logout(new Function1<Throwable, Unit>() {
                    @Override
                    public Unit invoke(Throwable throwable) {
                        updateKakaoLoginUi();
                        return null;
                    }
                });
            }
        });


        updateKakaoLoginUi();



    } // onCreate End -------------------------------------------------------------------
    // 화면 touch 시 키보드 숨기기
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View focusView = getCurrentFocus();
        if (focusView != null) {
            Rect rect = new Rect();
            focusView.getGlobalVisibleRect(rect);
            int x = (int) ev.getX(), y = (int) ev.getY();
            if (!rect.contains(x, y)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                if (imm != null)
                    imm.hideSoftInputFromWindow(focusView.getWindowToken(), 0);
                focusView.clearFocus();
            }
        }
        return super.dispatchTouchEvent(ev);
    }


    // 카카오 로그인 여부 확인해 화면 갱신
    private void updateKakaoLoginUi(){
        UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
            @Override  // 여부확인하여 invoke method로 callback
            public Unit invoke(User user, Throwable throwable) {
                if(user != null){

                    // 정보
                    Log.i(TAG, "invoke: id =" + user.getId());
                    Log.i(TAG, "invoke: email =" + user.getKakaoAccount().getEmail());
                    Log.i(TAG, "invoke: profile =" + user.getKakaoAccount().getProfile().getThumbnailImageUrl());
                    Log.i(TAG, "invoke: nickname =" + user.getKakaoAccount().getProfile().getNickname());
                    Log.i(TAG, "invoke: gender =" + user.getKakaoAccount().getGender());
                    Log.i(TAG, "invoke: age =" + user.getKakaoAccount().getAgeRange());

                    nickName.setText(user.getKakaoAccount().getProfile().getNickname());
                    Glide.with(profileIMG).load(user.getKakaoAccount().getProfile().getThumbnailImageUrl()).circleCrop().into(profileIMG);

                    // 버튼
                    btnLogin.setVisibility(View.GONE);
                    btnLogout.setVisibility(View.VISIBLE);
                } else {
                    nickName.setText(null);
                    profileIMG.setImageBitmap(null);

                    btnLogin.setVisibility(View.VISIBLE);
                    btnLogout.setVisibility(View.GONE);
                }
                return null;
            }
        });
    }



}// End -------------------------------------------------------------------