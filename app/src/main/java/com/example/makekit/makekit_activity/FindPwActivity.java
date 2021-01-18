package com.example.makekit.makekit_activity;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.makekit.R;
import com.example.makekit.makekit_asynctask.UserNetworkTask;
import com.example.makekit.makekit_bean.User;
import com.example.makekit.makekit_method.SendMail;
import com.example.makekit.makekit_sharVar.SharVar;

import java.util.ArrayList;


public class FindPwActivity extends AppCompatActivity {

    final static String TAG = "FindPWActivity";

    ////////////////////////////////////////////
    ////////////////////////////////////////////
    /////////         계정 입력       ////////////
    ////////////////////////////////////////////
    ////////////////////////////////////////////

    String user = ""; // 보내는 계정의 id
    String password = ""; // 보내는 계정의 pw

    EditText name, email;
    TextView check;
    String macIP, urlJsp, useremail, pw, usertel, urlAddr;
    ArrayList<User> users;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_find_pw);

        // Thread 설정
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .permitDiskReads()
                .permitDiskWrites()
                .permitNetwork().build());

        // intetent 및 변수
//        SharedPreferences sf = getSharedPreferences("appData", MODE_PRIVATE);
//        macIP = sf.getString("macIP","");


        Intent intent = getIntent(); /*데이터 수신*/
        macIP = intent.getStringExtra("macIP");

        urlJsp = SharVar.urlAddrBase + "jsp/";
//        urlJsp = "http://" + macIP + ":8080/makeKit/jsp/";
        Log.d(TAG, macIP );


        name = findViewById(R.id.name_findPw);
        email = findViewById(R.id.email_findPw);
        check = findViewById(R.id.tv_fieldCheck_findPw);

        findViewById(R.id.btnEmailAuth_findPw).setOnClickListener(mClickListener);
        findViewById(R.id.btnPhoneAuth_findPw).setOnClickListener(mClickListener);

        email.addTextChangedListener(changeListener1);


    } // onCreate End -------------------------------------------------------------------

    View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btnEmailAuth_findPw:
                    emailFieldCheck();
                    break;

                case R.id.btnPhoneAuth_findPw:
                    phoneFieldCheck();
                    break;

            }
        }
    };

    // email text
    TextWatcher changeListener1 = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            // email 입력 시
            if(email.getText().toString().trim().length() != 0) {
                validateEdit(s);
            }
        }
    };

    // email field check
    private void emailFieldCheck(){
        String userName = name.getText().toString().trim();
        String userEmail = email.getText().toString().trim();
        int count = 0;

        if(userName.length() == 0){
            check.setText("이름을 입력해주세요.");
            name.setFocusableInTouchMode(true);
            name.requestFocus();

        } else if (userEmail.length() == 0){
            check.setText("이메일을 입력해주세요.");
            email.setFocusableInTouchMode(true);
            email.requestFocus();
            email.setError(null);

        } else {

            if(!android.util.Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
                check.setText("이메일을 다시 입력해주세요.");
                email.setFocusableInTouchMode(true);
                email.requestFocus();

            } else {
                String urlAddr1 = "";
                urlAddr1 = urlJsp + "userFind.jsp";
                users = connectSelectData(urlAddr1);

                for (int i = 0; i < users.size(); i++) {
                    if (userName.equals(users.get(i).getName()) && userEmail.equals(users.get(i).getEmail())) {
                        pw = users.get(i).getPw();
                        count++;
                    }
                }

                Log.v(TAG, Integer.toString(count));

                if (count == 0) {
                    check.setText("일치하는 정보가 없습니다. \n이름 또는 이메일을 다시 입력해주세요");
                    name.setText("");
                    email.setText("");
                    name.setFocusableInTouchMode(true);
                    name.requestFocus();

                } else {
                    check.setText("");
                    SendMail mailServer = new SendMail();

                    String code = mailServer.sendSecurityCode2(getApplicationContext(), email.getText().toString(), user, password);


                    Intent intent = new Intent(FindPwActivity.this, EmailFindPwActivity.class);
                    intent.putExtra("name", userName);
                    intent.putExtra("user", user);
                    intent.putExtra("password", password);
                    intent.putExtra("pw", pw);
                    intent.putExtra("codeAuth", code);
//                    finish();
                    startActivity(intent);
                }
            }
        }

    }

    // phone field check
    private void phoneFieldCheck(){
        String userName = name.getText().toString().trim();
        String userEmail = email.getText().toString().trim();
        int count = 0;
        check.setText("");

        if(userName.length() == 0){
            check.setText("이름을 입력해주세요.");
            name.setFocusableInTouchMode(true);
            name.requestFocus();

        } else if (userEmail.length() == 0){
            check.setText("이메일을 입력해주세요.");
            email.setFocusableInTouchMode(true);
            email.requestFocus();
            email.setError(null);

        } else {
            if(!android.util.Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
                check.setText("이메일을 다시 입력해주세요.");
                email.setFocusableInTouchMode(true);
                email.requestFocus();

            } else {
                urlAddr = urlJsp + "user_query_all.jsp?name=" + userName + "&email=" + userEmail;
                users = connectSelectData(urlAddr);

                for (int i = 0; i < users.size(); i++) {
                    if (userName.equals(users.get(i).getName()) && userEmail.equals(users.get(i).getEmail())) {
                        usertel = users.get(i).getTel();
                        pw = users.get(i).getPw();
                        count++;
                    }
                }

                Log.v(TAG, Integer.toString(count));

                if (count == 0) {
                    check.setText("일치하는 정보가 없습니다. \n이름 또는 이메일을 다시 입력해주세요");
                    name.setText("");
                    email.setText("");
                    name.setFocusableInTouchMode(true);
                    name.requestFocus();

                } else {
                    check.setText("");

                    Intent intent = new Intent(FindPwActivity.this, PhoneFindPwActivity.class);
                    intent.putExtra("name", userName);
                    intent.putExtra("pw", pw);
                    intent.putExtra("usertel", usertel);
//                    finish();
                    startActivity(intent);
                }
            }
        }

    }


    // user Info 검색
    private ArrayList<User> connectSelectData(String urlAddr){
        ArrayList<User> user = null;

        try{
            UserNetworkTask selectNetworkTask = new UserNetworkTask(FindPwActivity.this, urlAddr, "select");
            Object obj = selectNetworkTask.execute().get();
            user = (ArrayList<User>) obj;

        } catch (Exception e){
            e.printStackTrace();

        }
        return user;
    }

    // email 형식 일치 확인
    private void validateEdit(Editable s){
        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(s.toString()).matches()){
            email.setError("이메일 형식으로 입력해주세요.");
        } else{
            email.setError(null);         //에러 메세지 제거
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

} // End —————————————————————————————————