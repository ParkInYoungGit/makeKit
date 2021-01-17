package com.example.makekit.makekit_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.makekit.R;
import com.example.makekit.makekit_method.GmailSender;

public class EmailFindPwActivity extends AppCompatActivity {

    EditText sendNum;
    TextView msg;
    String name, pw, code, user, password;

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
        setContentView(R.layout.activity_email_find_pw);


        Intent intent = getIntent();
        user = intent.getStringExtra("user");
        password = intent.getStringExtra("password");
        name = intent.getStringExtra("name");
        pw = intent.getStringExtra("pw");
        code = intent.getStringExtra("codeAuth");

        sendNum =findViewById(R.id.sendNum_emailfindPw);
        msg = findViewById(R.id.tv_msg_emailfindPw);


        findViewById(R.id.btnFindPw_emailfindPw).setOnClickListener(mClickListener);

        countDownTimer();


    } // onCreate End -------------------------------------------------------------------


    // Click 이벤트
    View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btnFindPw_emailfindPw:
                    String num = sendNum.getText().toString();
                    authCheck(num);
                    break;
            }
        }
    };

    // 코드 일치 여부 확인
    private void authCheck(String num){
        GmailSender gMailSender = new GmailSender(user, password);

        if(code.equals(num)){
            Toast.makeText(this, "인증이 완료되었습니다.", Toast.LENGTH_SHORT).show();
            alertCheck();
        } else {
            sendNum.setText("");
            sendNum.setFocusableInTouchMode(true);
            sendNum.requestFocus();
            msg.setText("인증번호가 다릅니다. \n다시 입력해주세요.");
            msg.setTextColor(getResources().getColor(R.color.red));
        }
    }

    //카운트 다운 메소드
    public void countDownTimer() {

        time_counter = findViewById(R.id.time_emailFindPw);
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

    // pw 알람 발생
    private void alertCheck(){
        new androidx.appcompat.app.AlertDialog.Builder(EmailFindPwActivity.this)
                .setTitle("MakeKit 서비스 안내")
                .setMessage( name + "님의 비밀번호는 \n" + pw + " 입니다.")
                .setIcon(R.drawable.alert)
                .setCancelable(false) // 버튼으로만 대화상자 닫기가 된다. (미작성 시 다른부분 눌러도 대화상자 닫힌다)
                .setPositiveButton("닫기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 닫기 시 FindIDActivity 종료
                        finish();
                    }
                })
                .show();
    }




} //  End -----------------------------------------------------------------------------------------------------
