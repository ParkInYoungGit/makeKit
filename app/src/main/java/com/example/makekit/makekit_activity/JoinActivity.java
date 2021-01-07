package com.example.makekit.makekit_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.makekit.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class JoinActivity extends AppCompatActivity {

    final static String TAG = "JoinActivity";
    private static final int SEARCH_ADDRESS_ACTIVITY = 10000;
    public static final String pattern1 = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,20}$"; // 영문, 숫자, 특수문자
    public static final String pattern2 = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$";

    String macIP, urlJsp, urlImage;
    EditText email, name, pw, pwCheck, phone, et_address, et_addressDetail;
    TextView pwCheckMsg;
    CheckBox checkAgree;

    private int _beforeLenght = 0;
    private int _afterLenght = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        // intent 및 받아오는 변수 정리
        SharedPreferences sf = getSharedPreferences("appData", MODE_PRIVATE);
        //macIP = sf.getString("macIP","");
        macIP = "192.168.35.133";

        urlJsp = "http://" + macIP + ":8080/makeKit/jsp/";
        urlImage = "http://" + macIP + ":8080/makeKit/image/";


        // 화면 구성
        TextInputLayout inputLayoutPW = findViewById(R.id.InputLayoutPw_join);
        TextInputLayout inputLayoutPWCheck = findViewById(R.id.InputLayoutPwCheck_join);

        inputLayoutPW.setPasswordVisibilityToggleEnabled(true);
        inputLayoutPWCheck.setPasswordVisibilityToggleEnabled(true);

        email = findViewById(R.id.email_join);
        name = findViewById(R.id.name_join);
        pw = findViewById(R.id.pw_join);
        pwCheck = findViewById(R.id.pwCheck_join);
        phone = findViewById(R.id.phone_join);
        pwCheckMsg = findViewById(R.id.tv_pwCheckMsg_join);
        checkAgree = findViewById(R.id.checkAgree_join);
        et_addressDetail = findViewById(R.id.et_address_detail);
        
        et_address = findViewById(R.id.et_address); // 주소 검색

        findViewById(R.id.btnEmailCheck_join).setOnClickListener(mClickListener);
        findViewById(R.id.submitBtn_join).setOnClickListener(mClickListener);

        et_address.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){//클릭했을 경우 발생할 이벤트 작성
                    Intent i = new Intent(JoinActivity.this, WebViewActivity.class);
                    startActivityForResult(i, SEARCH_ADDRESS_ACTIVITY);
                }
                return false;
            }
        });


    } // onCrearte End  -----------------------------------------------------------------------------


    public void onActivityResult ( int requestCode, int resultCode, Intent intent)
    {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case SEARCH_ADDRESS_ACTIVITY:
                if (resultCode == RESULT_OK) {
                    String data = intent.getExtras().getString("data");
                    if (data != null) {
                        et_address.setText(data);
                    }
                }
                break;
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

    // 버튼 클릭 이벤트 정리
    View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btnEmailCheck_join: // email 중복 체크
                    emailInput = email.getText().toString().trim();
                    emailCheck(emailInput);
                    break;

                case R.id.submitBtn_join:  // 가입 버튼 클릭 시
                    checkField();
                    break;
            }

        }
    };

    // email 입력란 text 변경 시 listener
    TextWatcher changeListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) { // email 입력 시 DB 드가기 전에 정리
            if(email.getText().toString().trim().length() != 0){
                validateEdit(s);
            }
        }
    };

    // email 형식 일치 확인
    private void validateEdit(Editable s){
        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(s.toString()).matches()){
            email.setError("이메일 형식으로 입력해주세요!");
        } else{
            email.setError(null);
        }
    }

    // email 중복 체크
    private void emailCheck(String emailInput){
        int count = 0;

        if (emailInput.length() == 0) {
            Toast.makeText(JoinActivity.this, "Email을 입력해주세요.", Toast.LENGTH_SHORT).show();

        } else {
            if(!android.util.Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
                Toast.makeText(JoinActivity.this, "이메일 형식으로 입력해주세요.", Toast.LENGTH_SHORT).show();

            } else {
                String urlAddr2 = "";
                urlAddr2 = urlAddr + "user_query_all.jsp?email=" + emailInput;

                Log.v(TAG, "email : " + emailInput);

                ArrayList<User> result = connectSelectData(urlAddr2);

                for (int i = 0; i < result.size(); i++) {
                    if (emailInput.equals(result.get(i).getUserEmail())) {
                        count++;
                    }
                }

                if (count == 0) {
                    email.setEnabled(false);
                    Toast.makeText(JoinActivity.this, "Email 사용이 가능합니다.", Toast.LENGTH_SHORT).show();
                    btnCheck = 1;
                } else {
                    Toast.makeText(JoinActivity.this, "동일한 Email이 존재합니다.", Toast.LENGTH_SHORT).show();
                    btnCheck = 0;
                }
            }
        }

    }

    // pw 입력란 text 변경 시 listener
    TextWatcher changeListener2 = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            // pw 입력 시
            String pwCheck =pw.getText().toString().trim();
            Boolean check = pwdRegularExpressionChk(pwCheck);

            if(pwCheck.length() == 0){
                pw.setError(null);

            } else {
                if (check == false) {
                    pw.setError("비밀번호는 영문, 특수문자 포함하여 최소 8자 이상 입력해주세요.");
                }
            }
        }
    };

    // phone 입력란 text 변경 시 listener
    TextWatcher changeListener3 = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            _beforeLenght = s.length();

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            _afterLenght = s.length();
            // 삭제 중
            if (_beforeLenght > _afterLenght) {
                // 삭제 중에 마지막에 -는 자동으로 지우기
                if (s.toString().endsWith("-")) {
                    phone.setText(s.toString().substring(0, s.length() - 1));
                }
            }
            // 입력 중
            else if (_beforeLenght < _afterLenght) {
                if (_afterLenght == 4 && s.toString().indexOf("-") < 0) {
                    phone.setText(s.toString().subSequence(0, 3) + "-" + s.toString().substring(3, s.length()));
                } else if (_afterLenght == 9) {
                    phone.setText(s.toString().subSequence(0, 8) + "-" + s.toString().substring(8, s.length()));
                } else if (_afterLenght == 14) {
                    phone.setText(s.toString().subSequence(0, 13) + "-" + s.toString().substring(13, s.length()));
                }
            }
            phone.setSelection(phone.length());


        }

} // End  -----------------------------------------------------------------------------