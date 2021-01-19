package com.example.makekit.makekit_activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.example.makekit.R;
import com.example.makekit.makekit_sharVar.SharVar;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.kakao.sdk.auth.LoginClient;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

public class LoginActivity extends AppCompatActivity {

    final static String TAG = "LoginActivity";
    private View btnLogin, btnLogout, login_google;
    private TextView nickName, tv1, tv2;
    private ImageView profileIMG, logoIMG;
    SignInButton google;
    Button login_join_btn, GotoMain;
    TextView non_members, login_members;
    String macIP;
    String username;
    String userfamilyname;
    String useremail;
    String userid;
    Uri userphoto;

    private FirebaseAuth mAuth = null;
    private GoogleSignInClient mGoogleSignInClient;
    public static final int RC_SIGN_IN = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MODE_PRIVATE);
        //macIP = "192.168.0.81";
        macIP = SharVar.macIP;


        btnLogin = findViewById(R.id.login_kakao);
        btnLogout = findViewById(R.id.btn_logout);
        nickName = findViewById(R.id.nickname);
        profileIMG = findViewById(R.id.profile);
        GotoMain = findViewById(R.id.GotoMain);
        logoIMG = findViewById(R.id.makekitLogo);
        login_join_btn = findViewById(R.id.login_join_btn);

        GotoMain = findViewById(R.id.GotoMain);
        non_members = findViewById(R.id.non_members);
        login_members = findViewById(R.id.login_members);

        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);

        login_join_btn.setOnClickListener(mOnclickListener);
        GotoMain.setOnClickListener(mOnclickListener);
        non_members.setOnClickListener(mOnclickListener);
        login_members.setOnClickListener(mOnclickListener);



        // 구글 로그인 세팅
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        login_google = findViewById(R.id.login_google);
        login_google.setOnClickListener(mOnclickListener);

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            Intent intent = new Intent(getApplication(), MainActivity.class);

            startActivity(intent);
        }


        // 카카오 > 별도의 콜백 객체 선언
        Function2<OAuthToken, Throwable, Unit> callback = new Function2<OAuthToken, Throwable, Unit>() {
            @Override
            public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {

                if (oAuthToken != null) {
                    // 로그인 성공시 처리해야하는 것들 요기에

//                    Intent intentk = new Intent(getApplicationContext(), JoinActivity.class);
//                    intentk.putExtra("useremail", user.getKakaoAccount().getEmail().toString());
//                    intentk.putExtra("username", result.getNickname().toString());
//                    if(result.getKakaoAccount().getPhoneNumber()!=null) intentk.putExtra("userTel", result.getKakaoAccount().getPhoneNumber().toString());
//                    startActivity(intentk);
//                    finish();
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
                        signOut();
                        return null;
                    }
                });
            }
        });


        updateKakaoLoginUi();



    } // onCreate End -------------------------------------------------------------------

    View.OnClickListener mOnclickListener = new View.OnClickListener() {
        Intent intent;
        @Override
        public void onClick(View v) {
            Intent intent;
            switch (v.getId()) {
                case R.id.login_google:
                    signInGoogle();
                    break;
                case R.id.login_join_btn: //다른 방법으로 회원가입
                    intent = new Intent(LoginActivity.this, JoinActivity.class);
                    intent.putExtra("macIP", macIP);
                    startActivity(intent);
                    break;
                case R.id.non_members: // 비회원 둘러보기
                    intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("macIP", macIP);
                    startActivity(intent);
                    break;
                case R.id.login_members: // 기존 회원 로그인
                    intent = new Intent(LoginActivity.this, TempLogin.class);
                    intent.putExtra("macIP", macIP);
                    startActivity(intent);

                    break;
                case R.id.GotoMain: // 메인으로 가기
                    intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("useremail", useremail);
                    intent.putExtra("macIP", macIP);
                    startActivity(intent);
                    break;
            }
        }
    };


    // 구글 -----------------------------------------------------------------------------
    @Override
    public void onStart() {
        super.onStart();

        // [START on_start_sign_in]
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
    } // [END on_start_sign_in]

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {// [START onActivityResult]
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {  // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    } // [END onActivityResult]

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {// [START handleSignInResult]

        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            firebaseAuthWithGoogle(account);
            // Signed in successfully, show authenticated UI.
            if (account != null) {//google 인텐트 보내는 값 (유저 데이터)
               username = account.getGivenName();
               userfamilyname = account.getFamilyName();
               useremail = account.getEmail();
               userid = account.getId();
               userphoto = account.getPhotoUrl();

                updateUI(useremail);

//                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                intent.putExtra("useremail",useremail);//유저 이메일 주소 넘기기
//                intent.putExtra("userid",userid);//유저 이메일 주소 넘기기
//                startActivity(intent);

//                logoIMG.setVisibility(View.GONE);
//                btnLogin.setVisibility(View.GONE);
//                login_google.setVisibility(View.GONE);
//                btnLogout.setVisibility(View.VISIBLE);
//                GotoMain.setVisibility(View.VISIBLE);
            }
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
        }
    }
    // [END handleSignInResult]

    // [START signIn]
    private void signInGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    // [END signIn]

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Snackbar.make(findViewById(R.id.layout_main), "Authentication Successed.", Snackbar.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(userid);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Snackbar.make(findViewById(R.id.layout_main), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    private void updateUI(String userid) { //update ui code here
        if (userid != null) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("macIP", macIP);
            intent.putExtra("useremail", userid);
            startActivity(intent);
            finish();
        }
    }


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
            Intent intent;
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

//                    new AlertDialog.Builder(LoginActivity.this)
//                            .setIcon(R.drawable.img_logo)
//                            .setTitle("MakeKit 서비스 안내")
//                            .setMessage("대화 상자를 열었습니다.")
//                            // 아무곳이나 터치했을 때 alert 꺼지는 것을 막기 위해서
//                            .setCancelable(false)
//                            // 이제 닫기 눌러야만 꺼짐!
//                            .setPositiveButton("닫기", null)
//                            .show();

                    nickName.setText(user.getKakaoAccount().getProfile().getNickname());
                    Glide.with(profileIMG).load(user.getKakaoAccount().getProfile().getThumbnailImageUrl()).circleCrop().into(profileIMG);

                    // 버튼
//                    logoIMG.setVisibility(View.GONE);
                    btnLogin.setVisibility(View.GONE);
                    login_google.setVisibility(View.GONE);
                    btnLogout.setVisibility(View.VISIBLE);
                    GotoMain.setVisibility(View.VISIBLE);
                    logoIMG.setVisibility(View.INVISIBLE);
                    non_members.setVisibility(View.INVISIBLE);
                    login_members.setVisibility(View.INVISIBLE);
                    login_join_btn.setVisibility(View.INVISIBLE);
                    tv1.setVisibility(View.INVISIBLE);
                    tv2.setVisibility(View.INVISIBLE);

                } else {
                    nickName.setText(null);
                    profileIMG.setImageBitmap(null);

                    login_google.setVisibility(View.VISIBLE);
                    btnLogin.setVisibility(View.VISIBLE);
                    btnLogout.setVisibility(View.GONE);
                    GotoMain.setVisibility(View.GONE);
                    logoIMG.setVisibility(View.VISIBLE);
                    non_members.setVisibility(View.VISIBLE);
                    login_members.setVisibility(View.VISIBLE);
                    login_join_btn.setVisibility(View.VISIBLE);
                    tv1.setVisibility(View.VISIBLE);
                    tv2.setVisibility(View.VISIBLE);
                }
                return null;
            }
        });
    }




        private void signOut() {
        FirebaseAuth.getInstance().signOut();
    }



}// End -------------------------------------------------------------------