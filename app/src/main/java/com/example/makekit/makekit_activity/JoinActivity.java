package com.example.makekit.makekit_activity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.example.makekit.R;
import com.example.makekit.makekit_asynctask.UserNetworkTask;
import com.example.makekit.makekit_bean.User;
import com.example.makekit.makekit_method.SendMail;
import com.example.makekit.makekit_sharVar.SharVar;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JoinActivity extends AppCompatActivity {

    final static String TAG = "JoinActivity";
    private static final int SEARCH_ADDRESS_ACTIVITY = 10000;
    public static final String pattern1 = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,20}$"; // 영문, 숫자, 특수문자
    public static final String pattern2 = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$";

    String user = ""; // 보내는 계정의 id
    String password = ""; // 보내는 계정의 pw

    String macIP, urlJsp, urlImage, urlAddr, cartInsert, urlAddrBase;
    EditText email, name, pw, pwCheck, phone, address, addressDetail;
    String emailInput = null;
    TextView pwCheckMsg, termsOfService;
    CheckBox checkAgree;
    int btnCheck = 0;

    private int _beforeLenght = 0;
    private int _afterLenght = 0;
    Matcher match;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_join);

        // intent 및 받아오는 변수 정리
        // SharedPreferences sf = getSharedPreferences("appData", MODE_PRIVATE);
        //macIP = sf.getString("macIP","");

        macIP = SharVar.macIP;

        urlAddrBase = SharVar.urlAddrBase;
        urlJsp = urlAddrBase + "jsp/";
        urlImage = urlAddrBase + "image/";



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
        termsOfService = findViewById(R.id.terms_of_service);

        address = findViewById(R.id.et_address); // 주소 검색

        phone.addTextChangedListener(changeListener3);
        pw.addTextChangedListener(changeListener2);
        pwCheck.addTextChangedListener(changeListener1);
        email.addTextChangedListener(changeListener);

        findViewById(R.id.btnEmailCheck_join).setOnClickListener(mClickListener);
        findViewById(R.id.submitBtn_join).setOnClickListener(mClickListener);
        findViewById(R.id.terms_of_service).setOnClickListener(mClickListener);

        address.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {//클릭했을 경우 발생할 이벤트 작성
                    Intent intent = new Intent(JoinActivity.this, WebViewActivity.class);
                    intent.putExtra("macIP", macIP);
                    startActivityForResult(intent, SEARCH_ADDRESS_ACTIVITY);
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
                case R.id.btnEmailCheck_join: // email 인증
                    emailInput = email.getText().toString().trim();
                    emailCheck(emailInput);

                    SendMail sendMail = new SendMail();
                    String code = sendMail.sendSecurityCode2(getApplicationContext(), email.getText().toString(), user, password);

                    Intent intent = new Intent(JoinActivity.this, EmailCheckActivity.class);
                    intent.putExtra("codeAuth", code);
                    startActivity(intent);
                    break;
                case R.id.terms_of_service: // 이용 약관 동의
                    new AlertDialog.Builder(JoinActivity.this)
                            .setTitle("MakeKit 서비스 안내")
                            .setMessage("제 1 장 환영합니다!\n" +
                                    "제 1 조 (목적)\n" +
                                    "MakeKit(이하 ‘회사’)가 제공하는 서비스를 이용해 주셔서 감사합니다.\n회사는 여러분이 다양한 인터넷과 모바일 서비스를 좀 더 편리하게 이용할 수 있도록 회사 또는 관계사의 개별 서비스에 모두 접속 가능한 통합로그인계정 체계를 만들고 그에 적용되는 ‘MakeKit계정 약관(이하 '본 약관')을 마련하였습니다.\n본 약관은 여러분이 MakeKit계정 서비스를 이용하는 데 필요한 권리, 의무 및 책임사항, 이용조건 및 절차 등 기본적인 사항을 규정하고 있으므로 조금만 시간을 내서 주의 깊게 읽어주시기 바랍니다.\n" +
                                    "제 2 조 (약관의 효력 및 변경)\n" +
                                    "①본 약관의 내용은 MakeKit계정 웹사이트 또는 개별 서비스의 화면에 게시하거나 기타의 방법으로 공지하고, 본 약관에 동의한 여러분 모두에게 그 효력이 발생합니다.\n" +
                                    "②회사는 필요한 경우 관련법령을 위배하지 않는 범위 내에서 본 약관을 변경할 수 있습니다.\n본 약관이 변경되는 경우 회사는 변경사항을 시행일자 15일 전부터 여러분에게 서비스 공지사항에서 공지 또는 통지하는 것을 원칙으로 하며, 피치 못하게 여러분에게 불리한 내용으로 변경할 경우에는 그 시행일자 30일 전부터 MakeKit계정에 등록된 이메일 주소로 이메일(이메일주소가 없는 경우 서비스 내 전자쪽지 발송, 서비스 내 알림 메시지를 띄우는 등의 별도의 전자적 수단) 발송 또는 여러분이 등록한 휴대폰번호로 문자메시지 발송하는 방법 등으로 개별적으로 알려 드리겠습니다.\n" +
                                    "③회사가 전항에 따라 공지 또는 통지를 하면서 공지 또는 통지일로부터 개정약관 시행일 7일 후까지 거부의사를 표시하지 아니하면 승인한 것으로 본다는 뜻을 명확하게 고지하였음에도 여러분의 의사표시가 없는 경우에는 변경된 약관을 승인한 것으로 봅니다.\n여러분이 개정약관에 동의하지 않을 경우 여러분은 이용계약을 해지할 수 있습니다.\n" +
                                    "제 3 조 (약관 외 준칙)\n" +
                                    "본 약관에 규정되지 않은 사항에 대해서는 관련법령 또는 회사가 정한 개별 서비스의 이용약관, 운영정책 및 규칙 등(이하 ‘세부지침’)의 규정에 따릅니다.\n" +
                                    "제 4 조 (용어의 정의)\n" +
                                    "①본 약관에서 사용하는 용어의 정의는 다음과 같습니다.\n" +
                                    "1.MakeKit계정: 회사 또는 관계사가 제공하는 개별 서비스를 하나의 로그인계정과 비밀번호로 회원 인증, 회원정보 변경, 회원 가입 및 탈퇴 등을 관리할 수 있도록 회사가 정한 로그인계정 정책을 말합니다.\n" +
                                    "2.회원: MakeKit계정이 적용된 개별 서비스 또는 MakeKit계정 웹사이트에서 본 약관에 동의하고, MakeKit계정을 이용하는 자를 말합니다.\n" +
                                    "3.관계사: 회사와 제휴 관계를 맺고 카카오계정을 공동 제공하기로 합의한 법인을 말합니다.\n개별 관계사는 MakeKit 기업사이트에서 확인할 수 있고 추후 추가/변동될 수 있으며 관계사가 추가/변동될 때에는 MakeKit 기업사이트에 변경 사항을 게시합니다.\n" +
                                    "4.개별 서비스: MakeKit계정을 이용하여 접속 가능한 회사 또는 관계사가 제공하는 서비스를 말합니다.\n개별 서비스는 추후 추가/변동될 수 있으며 서비스가 추가/변동될 때에는 MakeKit 기업사이트에 변경 사항을 게시합니다.\n" +
                                    "5.MakeKit계정 웹사이트: 회원이 온라인을 통해 카카오계정 정보를 조회 및 수정할 수 있는 인터넷 사이트를 말합니다.\n" +
                                    "6.MakeKit계정 정보 : MakeKit계정 이용하기 위해 회사가 정한 필수 내지 선택 입력 정보로서 MakeKit계정 웹사이트 또는 개별 서비스 내 MakeKit계정 설정 화면을 통해 정보 확인, 변경 처리 등을 관리할 수 있는 회원정보 항목을 말합니다.\n" +
                                    "8.인증서 : 인증서라 함은 회사가 인증서비스를 통하여 발급하는 전자서명생성정보가 회원에게 유일하게 속한다는 사실 등을 확인하고 이를 증명하는 전자적 정보를 말합니다.\n" +
                                    "9.전자서명 : 서명자의 신원을 확인하고 서명자가 해당 전자문서에 서명하였다는 사실을 나타내는데 이용하기 위하여 전자문서에 첨부되거나 논리적으로 결합된 전자적 형태의 정보를 말합니다.\n" +
                                    "10.전자서명생성정보 : 전자서명을 생성하기 위하여 이용하는 전자적 정보를 말합니다.\n" +
                                    "11.인증회원 : 회사로부터 전자서명생성정보를 인증 받은 회원을 말합니다.\n" +
                                    "12.이용기관 : 인증회원의 전자서명 및 인증서를 바탕으로 한 거래 등을 위하여 인증서비스를 이용하려는 제3자를 말합니다.\n")
                            .setIcon(R.drawable.alert)
                            .setCancelable(false) // 버튼으로만 대화상자 닫기가 된다. (미작성 시 다른부분 눌러도 대화상자 닫힌다)
                            .setPositiveButton("닫기", null)  // 페이지 이동이 없으므로 null
                            .show();

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

    // pw 입력란 text 변경 시 listener
    TextWatcher changeListener1 = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            // pwcheck 입력 시 일치 여부 message
            if(pwCheck.getText().toString().trim().length() != 0){
                if((pwCheck.getText().toString().trim()).equals(pw.getText().toString().trim())){
                    pwCheckMsg.setTextColor(getResources().getColor(R.color.blue));
                    pwCheckMsg.setText("비밀번호 일치");

                } else {
                    pwCheckMsg.setTextColor(getResources().getColor(R.color.red));
                    pwCheckMsg.setText("비밀번호 불일치");
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
                                String userBirth = "1995-05-03";
                                insertUser(userEmail, userName, userPW, Address, AddressDetail, userTel, userBirth);

                            } else {
                                pwCheck.setText("");
                                Toast.makeText(JoinActivity.this, "비밀번호가 일치하지 않습니다. \n다시 확인해주세요.", Toast.LENGTH_SHORT).show();

                            }

                        }
                    }

                }
            }

        }
    }

    // 미입력 시 알람 발생
    private void alertCheck(String field) {
        new AlertDialog.Builder(JoinActivity.this)
                .setTitle("MakeKit 서비스 안내")
                .setMessage(field + " 입력해주세요.")
                .setIcon(R.drawable.alert)
                .setCancelable(false) // 버튼으로만 대화상자 닫기가 된다. (미작성 시 다른부분 눌러도 대화상자 닫힌다)
                .setPositiveButton("닫기", null)  // 페이지 이동이 없으므로 null
                .show();
    }

    // user 입력 data 송부
    private void insertUser(String userEmail, String userName, String userPW, String Address, String AddressDetail, String userTel, String userBirth) {
        String urlAddr1 = "";
        urlAddr1 = urlJsp + "userInfoInsert.jsp?email=" + userEmail + "&name=" + userName + "&pw=" + userPW + "&address=" + Address + "&addressDetail=" + AddressDetail + "&phone=" + userTel + "&birth=" + userBirth;

        String result = connectInsertData(urlAddr1);

        if (result.equals("1")) {
            Toast.makeText(JoinActivity.this, userName + "님 회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();
            insertCart(userEmail);
        } else {
            Toast.makeText(JoinActivity.this, userName + "님 회원가입 실패하였습니다.", Toast.LENGTH_SHORT).show();
        }
        finish();

    }


    // cart에 고유번호 추가
    private void insertCart(String userEmail){
        String urlCart;
        urlCart = urlJsp + "join_cartInsert.jsp?email=" + userEmail;
        connectInsertCart(urlCart);
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
                urlAddr2 = urlJsp + "user_query_all.jsp?email=" + emailInput;

                Log.v(TAG, "email : " + emailInput);

                ArrayList<User> result = connectSelectData(urlAddr2);

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

    //connection Select
    private ArrayList<User> connectSelectData(String urlAddr){
        ArrayList<User> result1 = null;

        try {
            UserNetworkTask selectNetworkTask = new UserNetworkTask(JoinActivity.this, urlAddr, "select");
            Object obj = selectNetworkTask.execute().get();
            result1 = (ArrayList<User>) obj;

        } catch (Exception e) {
            e.printStackTrace();

        }
        return result1;
    }

    // Insert Cart
    private String connectInsertCart(String urlCart) {
        try {
            UserNetworkTask insTelNonetworkTask = new UserNetworkTask(JoinActivity.this, urlCart, "insert");
            Object object = insTelNonetworkTask.execute().get();
            cartInsert = (String) object;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cartInsert;
    }




} // End  ——————————————————————————————————————