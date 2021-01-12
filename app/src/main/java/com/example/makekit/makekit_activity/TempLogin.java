package com.example.makekit.makekit_activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.makekit.R;
import com.example.makekit.makekit_adapter.SectionPageAdapter;
import com.example.makekit.makekit_asynctask.UserNetworkTask;

public class TempLogin extends AppCompatActivity {

    final static String TAG = "TempLogin";

    TextView findidpw;
    Button login, join;
    private boolean saveLoginData;
    String urlJsp;
    EditText loginId;
    EditText loginPw;
    String useremail, userpw, macIP, urlAddr;
    String urlJspLoginCheck = null;
    CheckBox savechb;
    int count = 0;
    private SharedPreferences appData;
    SharedPreferences setting;
    SharedPreferences.Editor editor;
    private Context mContext;
    private ViewPager mViewPager;
    SectionPageAdapter adapter = new SectionPageAdapter(getSupportFragmentManager());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_login);

        ActivityCompat.requestPermissions(TempLogin.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MODE_PRIVATE);
        Intent intent = getIntent(); /*데이터 수신*/
        macIP = intent.getStringExtra("macIP");

        appData = getSharedPreferences("appData", MODE_PRIVATE);
        load();
        ActivityCompat.requestPermissions(TempLogin.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MODE_PRIVATE);


        if (saveLoginData) {
            loginId.setText(useremail);
            loginPw.setText(userpw);
            savechb.setChecked(saveLoginData);
        }


        login = findViewById(R.id.templogin_btn);
        join = findViewById(R.id.tempjoin_btn);
        findidpw = findViewById(R.id.temp_findIDPW);
        findidpw.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        findidpw.setOnClickListener(mOnClickListener);
        login.setOnClickListener(mOnClickListener);
        join.setOnClickListener(mOnClickListener);

    }  // onCrearte End  -----------------------------------------------------------------------------

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        Intent intent;
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.templogin_btn: // 로그인
                    loginCheck();
                    break;
                case R.id.temp_findIDPW: // id/pw찾기

                    new AlertDialog.Builder(TempLogin.this, android.R.style.Theme_DeviceDefault_Light_Dialog)
                            .setIcon(R.drawable.alert)
                            .setTitle("MakeKit 서비스 안내")
                            .setMessage("찾고자 하는 서비스를 선택해주세요!")
                            // 버튼명   // 버튼 리스너명
                            .setNegativeButton("ID 찾기", aClick)
                            .setPositiveButton("PW 찾기", aClick)
                            .show();

                    break;
                case R.id.tempjoin_btn:
                    intent = new Intent(TempLogin.this, JoinActivity.class);
                    intent.putExtra("macIP", macIP);
                    startActivity(intent);
                    break;
            }

        }
    };

    // find ID PW
    DialogInterface.OnClickListener aClick = new DialogInterface.OnClickListener() {
        Intent intent;
        @Override
        public void onClick(DialogInterface dialog, int which) {

            if (which == DialogInterface.BUTTON_NEGATIVE) {
                intent = new Intent(TempLogin.this, FindIdActivity.class);
                intent.putExtra("macIP", macIP);
                startActivity(intent);
            } else {
                intent = new Intent(TempLogin.this, FindPwActivity.class);
                intent.putExtra("macIP", macIP);
                startActivity(intent);
            }
        }
    };

    private void loginCheck(){
        urlJsp = "http://" + macIP + ":8080/makeKit/jsp/logincheck.jsp?";
        useremail = loginId.getText().toString();
        userpw = loginPw.getText().toString();

        urlAddr = urlJsp + "useremail=" + useremail + "&userpw=" + userpw;

        count = loginCount();

        Log.v("여기 >>>>>>>>>>", "" + loginCount());
        Log.v("아이디 >>>>>>>>>", "login : " + useremail + userpw);

        if (count == 1) {
            save();
            Toast.makeText(TempLogin.this, "로그인 완료!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(TempLogin.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(TempLogin.this, "아이디와 비밀번호를 확인하세요!", Toast.LENGTH_SHORT).show();
        }
    }


    private int loginCount() {
        try {
            UserNetworkTask networkTask = new UserNetworkTask(TempLogin.this, urlAddr, "loginCount");
            Object obj = networkTask.execute().get();

            count = (int) obj;
            Log.v("여기", "loginCount : " + count);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }



    // 설정값을 저장하는 함수
    private void save() {
        // SharedPreferences 객체만으론 저장 불가능 Editor 사용
        SharedPreferences.Editor editor = appData.edit();
        Log.v(TAG, loginId.getText().toString().trim());
        // 에디터객체.put타입( 저장시킬 이름, 저장시킬 값 )
        // 저장시킬 이름이 이미 존재하면 덮어씌움
        editor.putBoolean("SAVE_LOGIN_DATA", savechb.isChecked());
        editor.putString("useremail", loginId.getText().toString().trim());
        editor.putString("userpw", loginPw.getText().toString().trim());
        editor.putString("macIP", macIP);



        // apply, commit 을 안하면 변경된 내용이 저장되지 않음
        editor.apply();
    }

    // 설정값을 불러오는 함수
    private void load() {
        // SharedPreferences 객체.get타입( 저장된 이름, 기본값 )
        // 저장된 이름이 존재하지 않을 시 기본값
        saveLoginData = appData.getBoolean("SAVE_LOGIN_DATA", false);
        useremail = appData.getString("useremail", "");
        userpw = appData.getString("userpw", "");


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

} // End  -----------------------------------------------------------------------------