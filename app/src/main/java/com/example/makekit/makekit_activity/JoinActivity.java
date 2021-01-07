package com.example.makekit.makekit_activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.StrictMode;
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
import com.example.makekit.makekit_asynctask.UserNetworkTask;
import com.example.makekit.makekit_bean.User;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JoinActivity extends AppCompatActivity {

    final static String TAG = "JoinActivity";
    private static final int SEARCH_ADDRESS_ACTIVITY = 10000;
    public static final String pattern1 = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,20}$"; // 영문, 숫자, 특수문자
    public static final String pattern2 = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$";

    String macIP, urlJsp, urlImage;
    EditText email, name, pw, pwCheck, phone, address, addressDetail;
    String emailInput = null;
    TextView pwCheckMsg;
    CheckBox checkAgree;
    int btnCheck = 0;

    private int _beforeLenght = 0;
    private int _afterLenght = 0;
    Matcher match;


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

        // 권한
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .permitDiskReads()
                .permitDiskWrites()
                .permitNetwork().build());


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
        addressDetail = findViewById(R.id.et_address_detail);

        address = findViewById(R.id.et_address); // 주소 검색

        findViewById(R.id.btnEmailCheck_join).setOnClickListener(mClickListener);
        findViewById(R.id.submitBtn_join).setOnClickListener(mClickListener);

        address.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {//클릭했을 경우 발생할 이벤트 작성
                    Intent i = new Intent(JoinActivity.this, WebViewActivity.class);
                    startActivityForResult(i, SEARCH_ADDRESS_ACTIVITY);
                }
                return false;
            }
        });




    } // onCrearte End  -----------------------------------------------------------------------------

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case SEARCH_ADDRESS_ACTIVITY:
                if (resultCode == RESULT_OK) {
                    String data = intent.getExtras().getString("data");
                    if (data != null) {
                        address.setText(data);
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
            switch (v.getId()) {
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
            if (email.getText().toString().trim().length() != 0) {
                validateEdit(s);
            }
        }
    };

    // email 형식 일치 확인
    private void validateEdit(Editable s) {
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(s.toString()).matches()) {
            email.setError("이메일 형식으로 입력해주세요!");
        } else {
            email.setError(null);
        }
    }

    // email 중복 체크
    private void emailCheck(String emailInput) {
        int count = 0;

        if (emailInput.length() == 0) {
            Toast.makeText(JoinActivity.this, "Email을 입력해주세요.", Toast.LENGTH_SHORT).show();

        } else {
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
                Toast.makeText(JoinActivity.this, "이메일 형식으로 입력해주세요.", Toast.LENGTH_SHORT).show();

            } else {
                String urlAddr2 = "";
                urlAddr2 = urlJsp + "user_query_all.jsp?email=" + emailInput;

                Log.v(TAG, "email : " + emailInput);

                ArrayList<User> result = connectSelectData(urlJsp);

                for (int i = 0; i < result.size(); i++) {
                    if (emailInput.equals(result.get(i).getEmail())) {
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
            String pwCheck = pw.getText().toString().trim();
            Boolean check = pwdRegularExpressionChk(pwCheck);

            if (pwCheck.length() == 0) {
                pw.setError(null);

            } else {
                if (check == false) {
                    pw.setError("비밀번호는 영문, 특수문자 포함하여 최소 8자 이상 입력해주세요.");
                }
            }
        }
    };

    // 비밀번호 영/숫/특 포함 설정
    public boolean pwdRegularExpressionChk(String newPwd) {
        boolean chk = false;  // 특수문자, 영문, 숫자 조합 (8~10 자리)
        match = Pattern.compile(pattern1).matcher(newPwd);
        if (match.find()) {
            chk = true;
        }
        return chk;
    }

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

        @Override
        public void afterTextChanged(Editable s) {
            String phoneCheck =phone.getText().toString().trim();
            boolean flag = Pattern.matches(pattern2, phoneCheck);

            if(phoneCheck.length() == 0){
                phone.setError(null);
            }
            else {
                if(flag == false) {
                    phone.setError("휴대폰 번호를 다시 입력해주세요.");
                }
            }
        }
    };






        // 입력란 field check
        private void checkField() {
            if (name.getText().toString().trim().length() == 0) {
                alertCheck("이름을");
                name.setFocusableInTouchMode(true);
                name.requestFocus();

            } else if (email.getText().toString().trim().length() == 0) {
                alertCheck("이메일을");
                email.setFocusableInTouchMode(true);
                email.requestFocus();

            } else if (pw.getText().toString().trim().length() == 0) {
                alertCheck("비밀번호를");
                pw.setFocusableInTouchMode(true);
                pw.requestFocus();

            } else if (pwCheck.getText().toString().trim().length() == 0) {
                alertCheck("비밀번호 확인을");
                pwCheck.setFocusableInTouchMode(true);
                pwCheck.requestFocus();

            } else if (phone.getText().toString().trim().length() == 0) {
                alertCheck("전화번호를");
                phone.setFocusableInTouchMode(true);
                phone.requestFocus();

            } else if (checkAgree.isChecked() != true) {
                Toast.makeText(JoinActivity.this, "약관 동의 체크해주세요.", Toast.LENGTH_SHORT).show();

            } else {
                String userName = name.getText().toString().trim();
                String userEmail = email.getText().toString().trim();
                String userPW = pw.getText().toString().trim();
                String userTel = phone.getText().toString().trim();
                String Address = address.getText().toString().trim();
                String AddressDetail = addressDetail.getText().toString().trim();


                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
                    Toast.makeText(JoinActivity.this, "이메일 형식으로 입력해주세요.", Toast.LENGTH_SHORT).show();
                    btnCheck = 0;

                } else {

                    if (btnCheck == 1) {
                        Boolean check = pwdRegularExpressionChk(userPW);

                        if (check == false) {
                            alertCheck("비밀번호를 영문, 특수문자 포함하여 최소 8자 이상");
                        } else {

                            String phoneCheck = phone.getText().toString().trim();
                            boolean flag = Pattern.matches(pattern2, phoneCheck);

                            if (flag == false) {
                                alertCheck("휴대폰 번호 확인 후 다시");

                            } else {

                                if ((pwCheck.getText().toString().trim()).equals(pw.getText().toString().trim())) {
                                    insertUser(userEmail, userName, userPW, userTel, Address, AddressDetail);

                                } else {
                                    pwCheck.setText("");
                                    Toast.makeText(JoinActivity.this, "비밀번호가 일치하지 않습니다. \n다시 확인해주세요.", Toast.LENGTH_SHORT).show();

                                }

                            }
                        }

                    } else {
                        Toast.makeText(JoinActivity.this, "Email 중복 채크 해주세요.", Toast.LENGTH_SHORT).show();

                    }
                }

            }
        }

        // 미입력 시 알람 발생
        private void alertCheck(String field) {
            new AlertDialog.Builder(JoinActivity.this)
                    .setTitle("MakeKit 서비스 안내")
                    .setMessage(field + " 입력해주세요.")
                    .setIcon(R.mipmap.ic_launcher)
                    .setCancelable(false) // 버튼으로만 대화상자 닫기가 된다. (미작성 시 다른부분 눌러도 대화상자 닫힌다)
                    .setPositiveButton("닫기", null)  // 페이지 이동이 없으므로 null
                    .show();
        }

        // user 입력 data 송부
        private void insertUser(String userEmail, String userName, String userPW, String userTel, String Address, String AddressDetail) {
            String urlAddr1 = "";
            urlAddr1 = urlJsp + "userInfoInsert.jsp?email" + userEmail + "&name=" + userName + "&pw=" + userPW + "&phone=" + userTel + "&address=" + Address + "&addressDetail=" + AddressDetail;

            String result = connectInsertData(urlAddr1);

            if (result.equals("1")) {
                Toast.makeText(JoinActivity.this, userName + "님 회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(JoinActivity.this, userName + "님 회원가입 실패하였습니다.", Toast.LENGTH_SHORT).show();

            }

            finish();

        }

        //connection Insert
        private String connectInsertData(String urlJsp) {
            String result = null;

            try {
                UserNetworkTask insertNetworkTask = new UserNetworkTask(JoinActivity.this, urlJsp, "insert");
                Object obj = insertNetworkTask.execute().get();
                result = (String) obj;

            } catch (Exception e) {
                e.printStackTrace();

            }
            return result;
        }

        //connection Select
        private ArrayList<User> connectSelectData(String urlJsp) {
            ArrayList<User> result1 = null;

            try {
                UserNetworkTask selectNetworkTask = new UserNetworkTask(JoinActivity.this, urlJsp, "select");
                Object obj = selectNetworkTask.execute().get();
                result1 = (ArrayList<User>) obj;

            } catch (Exception e) {
                e.printStackTrace();

            }
            return result1;
        }


} // End  -----------------------------------------------------------------------------