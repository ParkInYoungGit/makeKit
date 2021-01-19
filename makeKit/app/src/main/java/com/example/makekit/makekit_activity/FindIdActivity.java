package com.example.makekit.makekit_activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.makekit.R;
import com.example.makekit.makekit_asynctask.UserNetworkTask;
import com.example.makekit.makekit_bean.User;
import com.example.makekit.makekit_sharVar.SharVar;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class FindIdActivity extends AppCompatActivity {

    final static String TAG = "FindIDActivity";

    private int _beforeLenght = 0;
    private int _afterLenght = 0;

    public static final String pattern2 = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$";

    LinearLayout layoutSMS;
    EditText phone, name, codeNum;
    TextView fieldCheck;
    String macIP, urlJsp, useremail;
    ArrayList<User> users;
    //String SMSContents = "1234";
    String smsCode = createSMSCode();


    /*카운트 다운 타이머에 관련된 필드*/

    TextView time_counter; //시간을 보여주는 TextView
    EditText emailAuth_number; //인증 번호를 입력 하는 칸
    Button emailAuth_btn; // 인증버튼
    CountDownTimer countDownTimer;
    final int MILLISINFUTURE = 180 * 1000; //총 시간 (300초 = 5분)
    final int COUNT_DOWN_INTERVAL = 1000; //onTick 메소드를 호출할 간격 (1초)


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_find_id);

        Intent intent = getIntent(); /*데이터 수신*/

       macIP = intent.getStringExtra("macIP");
       useremail = intent.getStringExtra("useremail");

       urlJsp = SharVar.urlAddrBase + "jsp/";
//        urlJsp = "http://" + macIP + ":8080/makeKit/jsp/";


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            Log.d(TAG, "=== sms전송을 위한 퍼미션 확인 ===" );

            // For device above MarshMallow
            boolean permission = getWritePermission();
            if(permission) {
                // If permission Already Granted
                // Send You SMS here
                Log.d(TAG, "=== 퍼미션 허용 ===" );
            }
        }
        else{
            // Send Your SMS. You don't need Run time permission
            Log.d(TAG, "=== 퍼미션 필요 없는 버전임 ===" );
        }



        name = findViewById(R.id.name_findId);
        phone = findViewById(R.id.phone_findId);
        fieldCheck = findViewById(R.id.tv_fieldCheck_findId);
        layoutSMS = findViewById(R.id.linearSMS_findId);
        codeNum = findViewById(R.id.sendNum_findId);

        findViewById(R.id.btnSendMsg_findId).setOnClickListener(mClickListener);
        findViewById(R.id.btnFindId_findId).setOnClickListener(mClickListener);
        phone.addTextChangedListener(changeListener);
        name.addTextChangedListener(changeListener1);

    } // on Create End -------------------------------------------------------------------

    // 문자 인증
    public boolean getWritePermission(){
        boolean hasPermission = (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED);
        if (!hasPermission) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 10);
        }
        return hasPermission;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case 10: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    // Permission is Granted
                    // Send Your SMS here
                }
            }
        }
    }


    View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                    case R.id.btnSendMsg_findId:
                    userInfoCheck();
                    break;

                case R.id.btnFindId_findId:
                    String code = codeNum.getText().toString();
                    checkCode(code);
                    break;
            }
        }
    };

    // name text
    TextWatcher changeListener1 = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            fieldCheck.setText("");
        }
    };

    // phone text
    TextWatcher changeListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            fieldCheck.setText("");
            _beforeLenght = s.length();
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //fieldCheck.setText("");
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


    // user 정보 확인
    private void userInfoCheck(){
        int count = 0;

        String userName = name.getText().toString().trim();
        String userTel = phone.getText().toString().trim();

        if(userName.length() == 0){
            fieldCheck.setText("이름을 입력해주세요");
            name.setFocusableInTouchMode(true);
            name.requestFocus();

        } else if(userTel.length() == 0){
            fieldCheck.setText("휴대폰 번호을 입력해주세요");
            phone.setFocusableInTouchMode(true);
            phone.requestFocus();

        } else{
            //fieldCheck.setText("");
            String phoneCheck =phone.getText().toString().trim();
            boolean flag = Pattern.matches(pattern2, phoneCheck);

            if(flag == false) {
                fieldCheck.setText("휴대폰 번호을 다시 입력해주세요");
                phone.setFocusableInTouchMode(true);
                phone.requestFocus();

            } else {
                String urlAddr1 = "";
                urlAddr1 = urlJsp + "userFind.jsp";
                users = connectSelectData(urlAddr1);

                for (int i = 0; i < users.size(); i++) {
                    if (userName.equals(users.get(i).getName()) && userTel.equals(users.get(i).getTel())) {
                        useremail = users.get(i).getEmail();
                        count++;
                    }
                }
                Log.v(TAG, Integer.toString(count));

                if (count == 0) {
                    name.setText("");
                    phone.setText("");
                    fieldCheck.setText("일치하는 정보가 없습니다. \n이름 또는 휴대폰 번호를 다시 입력해주세요");

                } else {
                    //sendSMS(userPhone, "[1234] 발송");
                    sendMessage(userTel);
                    countDownTimer();
                    //fieldCheck.setText("");
                    layoutSMS.setVisibility(View.VISIBLE);
                    Toast.makeText(FindIdActivity.this, "문자를 발송하였습니다.", Toast.LENGTH_SHORT).show();

                }
            }
        }

    }

    // user Info 검색
    private ArrayList<User> connectSelectData(String urlAddr1){
        ArrayList<User> user = null;

        try{
            UserNetworkTask selectNetworkTask = new UserNetworkTask(FindIdActivity.this, urlAddr1, "select");
            Object obj = selectNetworkTask.execute().get();
            user = (ArrayList<User>) obj;

        } catch (Exception e){
            e.printStackTrace();

        }
        return user;
    }


    // 문자 발송
    private void sendMessage(String phoneNo){
        try {
            Log.d(TAG, "=== 문자 전송 시작 ===" );

            //전송
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, "[MakeKit]의 인증번호는 "+getSMSCode() +"입니다.", null, null); //SMSContents앞서 전역변수로 입력한, 번호 [랜덤숫자 생성] 포스팅의 메서드를 활용하여 넣으면, 랜덤으로 숫자가 보내진다.
            //
            Log.d(TAG, "=== 문자 전송 완료 ===" );

            //countDownTimer(); [카운트다운 시간재기]포스팅에서 확인할 수 있다.


        } catch (Exception e) {
            Log.d(TAG, "=== 문자 전송 실패 === 에러코드 e : "+e );
            e.printStackTrace();

//            sendCan=false;
//            Log.d(TAG, "=== sendCan === :" +sendCan );
        }
    }


    // 문자 랜덤 코드
    public String getSMSCode() {
        return smsCode;
    } //생성된 인증코드 반환

    private String createSMSCode() { // 인증코드 생성
        String[] str = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0"};
        String newCode = new String();

        for (int x = 0; x < 6; x++) {
            int random = (int) (Math.random() * str.length);
            newCode += str[random];
        }

        return newCode;
    }

    private void checkCode(String code){
        if(code.equals(smsCode)){
            Toast.makeText(FindIdActivity.this, "일치", Toast.LENGTH_SHORT).show();
            alertCheck();

        } else{
            codeNum.setText("");
            codeNum.setFocusableInTouchMode(true);
            codeNum.requestFocus();
            Toast.makeText(FindIdActivity.this, "인증코드 다시 입력해주세요.", Toast.LENGTH_SHORT).show();

        }
    }

    // id 알람 발생
    private void alertCheck(){
        new androidx.appcompat.app.AlertDialog.Builder(FindIdActivity.this)
                .setTitle("MakeKit 서비스 안내")
                .setMessage( name.getText().toString().trim() + "님의 Email은 \n" + useremail + " 입니다.")
                .setIcon(R.drawable.alert)
                .setCancelable(false) // 버튼으로만 대화상자 닫기가 된다. (미작성 시 다른부분 눌러도 대화상자 닫힌다)
                .setPositiveButton("닫기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 닫기 시 FindIDActivity 종료
                        FindIdActivity.this.finish();
                    }
                })  // 페이지 이동이 없으므로 null
                .show();
    }


    //카운트 다운 메소드
    public void countDownTimer() {

        time_counter = findViewById(R.id.time_findId);
        //줄어드는 시간을 나타내는 TextView


        countDownTimer = new CountDownTimer(MILLISINFUTURE, COUNT_DOWN_INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) { //(300초에서 1초 마다 계속 줄어듬)

                long emailAuthCount = millisUntilFinished / 1000;
                Log.d("Alex", emailAuthCount + "");

                if ((emailAuthCount - ((emailAuthCount / 60) * 60)) >= 10) { //초가 10보다 크면 그냥 출력
                    time_counter.setText((emailAuthCount / 60) + " : " + (emailAuthCount - ((emailAuthCount / 60) * 60)));
                } else { //초가 10보다 작으면 앞에 '0' 붙여서 같이 출력. ex) 02,03,04...
                    time_counter.setText((emailAuthCount / 60) + " : 0" + (emailAuthCount - ((emailAuthCount / 60) * 60)));
                }

                //emailAuthCount은 종료까지 남은 시간임. 1분 = 60초 되므로,
                // 분을 나타내기 위해서는 종료까지 남은 총 시간에 60을 나눠주면 그 몫이 분이 된다.
                // 분을 제외하고 남은 초를 나타내기 위해서는, (총 남은 시간 - (분*60) = 남은 초) 로 하면 된다.

            }


            @Override
            public void onFinish() { //시간이 다 되면 다이얼로그 종료

                finish();

            }
        }.start();
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

} // End -------------------------------------------------------------------
