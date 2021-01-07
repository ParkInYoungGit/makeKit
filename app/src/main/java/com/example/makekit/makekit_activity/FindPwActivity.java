package com.example.makekit.makekit_activity;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.makekit.R;
import com.example.makekit.makekit_bean.User;

import java.util.ArrayList;


public class FindPwActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_pw);
    }



/*
===========================================================================================================================
===========================================================================================================================
===========================================================================================================================
======================                                                                              =======================
======================                                                                              =======================
======================                                 PW 찾기 화면                                   =======================
======================                               (결과창 Dialog)                                  =======================
======================                                                                              =======================
===========================================================================================================================
===========================================================================================================================
===========================================================================================================================
*/

    public class FindPWActivity extends Activity {

        final static String TAG = "FindPWActivity";

        ////////////////////////////////////////////
        ////////////////////////////////////////////
        /////////         계정 입력       ////////////
        ////////////////////////////////////////////
        ////////////////////////////////////////////

        String user = "@gmail.com"; // 보내는 계정의 id
        String password = ""; // 보내는 계정의 pw

        EditText name, email;
        TextView check;
        String macIP, urlAddr, pw, phone;
        ArrayList<User> users;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_find_pw);

            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .permitDiskReads()
                    .permitDiskWrites()
                    .permitNetwork().build());

//            SharedPreferences sf = getSharedPreferences("appData", MODE_PRIVATE);
//            // macIP = sf.getString("macIP","");
//            macIP = "192.168.35.113";
//            urlAddr = "http://" + macIP + ":8080/makeKit/";
//
//            name = findViewById(R.id.name_findPw);
//            email = findViewById(R.id.email_findPw);
//            check = findViewById(R.id.tv_fieldCheck_findPw);
//
//            findViewById(R.id.btnEmailAuth_findPw).setOnClickListener(mClickListener);
//            findViewById(R.id.btnPhoneAuth_findPw).setOnClickListener(mClickListener);
//
//            email.addTextChangedListener(changeListener1);

        }

//        View.OnClickListener mClickListener = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                switch (v.getId()){
//                    case R.id.btnEmailAuth_findPw:
//                        emailFieldCheck();
//                        break;
//                    case R.id.btnPhoneAuth_findPw:
//                        phoneFieldCheck();
//                        break;
//                }
//            }
//        };
//
//        // email text
//        TextWatcher changeListener1 = new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                // email 입력 시
//                if(email.getText().toString().trim().length() != 0) {
//                    validateEdit(s);
//                }
//            }
//        };
//
//        // email field check
//        private void emailFieldCheck(){
//            String userName = name.getText().toString().trim();
//            String userEmail = email.getText().toString().trim();
//            int count = 0;
//
//            if(userName.length() == 0){
//                check.setText("이름을 입력해주세요.");
//                name.setFocusableInTouchMode(true);
//                name.requestFocus();
//
//            } else if (userEmail.length() == 0){
//                check.setText("이메일을 입력해주세요.");
//                email.setFocusableInTouchMode(true);
//                email.requestFocus();
//                email.setError(null);
//
//            } else {
//
//                if(!android.util.Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
//                    check.setText("이메일을 다시 입력해주세요.");
//                    email.setFocusableInTouchMode(true);
//                    email.requestFocus();
//
//                } else {
//                    String urlAddr1 = "";
//                    urlAddr1 = urlAddr + "userFind.jsp";
//                    users = connectSelectData(urlAddr1);
//
//                    for (int i = 0; i < users.size(); i++) {
//                        if (userName.equals(users.get(i).getName()) && userEmail.equals(users.get(i).getEmail())) {
//                            pw = users.get(i).getPw();
//                            count++;
//                        }
//                    }
//
//                    Log.v(TAG, Integer.toString(count));
//
//                    if (count == 0) {
//                        check.setText("일치하는 정보가 없습니다. \n이름 또는 이메일을 다시 입력해주세요");
//                        name.setText("");
//                        email.setText("");
//                        name.setFocusableInTouchMode(true);
//                        name.requestFocus();
//
//                    } else {
//                        check.setText("");
//                        SendMail mailServer = new SendMail();
//
//                        String code = mailServer.sendSecurityCode(getApplicationContext(), email.getText().toString(), user, password);
//
//
//                        Intent intent = new Intent(FindPWActivity.this, EmailFindPWActivity.class);
//                        intent.putExtra("name", userName);
//                        intent.putExtra("user", user);
//                        intent.putExtra("password", password);
//                        intent.putExtra("pw", pw);
//                        intent.putExtra("codeAuth", code);
//                        finish();
//                        startActivity(intent);
//                    }
//                }
//            }
//
//        }
//
//        // phone field check
//        private void phoneFieldCheck(){
//            String userName = name.getText().toString().trim();
//            String userEmail = email.getText().toString().trim();
//            int count = 0;
//            check.setText("");
//
//            if(userName.length() == 0){
//                check.setText("이름을 입력해주세요.");
//                name.setFocusableInTouchMode(true);
//                name.requestFocus();
//
//            } else if (userEmail.length() == 0){
//                check.setText("이메일을 입력해주세요.");
//                email.setFocusableInTouchMode(true);
//                email.requestFocus();
//                email.setError(null);
//
//            } else {
//                if(!android.util.Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
//                    check.setText("이메일을 다시 입력해주세요.");
//                    email.setFocusableInTouchMode(true);
//                    email.requestFocus();
//
//                } else {
//                    urlAddr = urlAddr + "user_query_all.jsp?name=" + userName + "&email=" + userEmail;
//                    users = connectSelectData(urlAddr);
//
//                    for (int i = 0; i < users.size(); i++) {
//                        if (userName.equals(users.get(i).getUserName()) && userEmail.equals(users.get(i).getUserEmail())) {
//                            phone = users.get(i).getUserPhone();
//                            pw = users.get(i).getUserPW();
//                            count++;
//                        }
//                    }
//
//                    Log.v(TAG, Integer.toString(count));
//
//                    if (count == 0) {
//                        check.setText("일치하는 정보가 없습니다. \n이름 또는 이메일을 다시 입력해주세요");
//                        name.setText("");
//                        email.setText("");
//                        name.setFocusableInTouchMode(true);
//                        name.requestFocus();
//
//                    } else {
//                        check.setText("");
//
//                        Intent intent = new Intent(FindPWActivity.this, PhoneFindPWActivity.class);
//                        intent.putExtra("name", userName);
//                        intent.putExtra("pw", pw);
//                        intent.putExtra("phone", phone);
//                        finish();
//                        startActivity(intent);
//                    }
//                }
//            }
//
//        }
//
//
//        // user Info 검색
//        private ArrayList<User> connectSelectData(String urlAddr){
//            ArrayList<User> user = null;
//
//            try{
//                NetworkTask selectNetworkTask = new NetworkTask(FindPWActivity.this, urlAddr, "select");
//                Object obj = selectNetworkTask.execute().get();
//                user = (ArrayList<User>) obj;
//
//            } catch (Exception e){
//                e.printStackTrace();
//
//            }
//            return user;
//        }
//
//        // email 형식 일치 확인
//        private void validateEdit(Editable s){
//            if(!android.util.Patterns.EMAIL_ADDRESS.matcher(s.toString()).matches()){
//                email.setError("이메일 형식으로 입력해주세요.");
//            } else{
//                email.setError(null);         //에러 메세지 제거
//            }
//        }


        // 화면 touch 시 키보드 숨기
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
}