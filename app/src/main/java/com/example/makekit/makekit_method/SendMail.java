package com.example.makekit.makekit_method;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;

public class SendMail extends AppCompatActivity {

    String user = "2bbeen@gmail.com";
    String password = "93elsl211!";

    static String pwCode = null;
    static String emailCode = null;

    public String sendSecurityCode(Context context, String sendTo) {
        String code = "";
        try {
            GMailSender gMailSender = new GMailSender(user, password);
            code = gMailSender.getEmailCode();
            String body = "[Make Kit 인증 메일] 인증번호는 "  + code +" 입니다." ;
            gMailSender.sendMail("이메일 인증코드 발송 메일입니다.", body, sendTo);
            Toast.makeText(context, "이메일을 성공적으로 보냈습니다.", Toast.LENGTH_SHORT).show();
        } catch (SendFailedException e) {
            Toast.makeText(context, "이메일 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show();
        } catch (MessagingException e) {
            Toast.makeText(context, "인터넷 연결을 확인해주십시오", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return code;
    }


    public void sendSecurityCode2(Context context, String sendTo) {

        try {
            Log.v("여기","SenMailClass1");
            GMailSender gMailSender2 = new GMailSender(user, password);
            Log.v("여기","SenMailClass2");
            String code = gMailSender2.getEmailCode2();
            Log.v("여기","SenMailClass3");
            emailCode = code;
            Log.v("여기","SenMailClass4");
            gMailSender2.sendMail("이메일인증", "이메일인증코드는 : " + code + " 입니다.", sendTo);
            Log.v("여기","SenMailClass5");
            //Toast.makeText(context, "이메일을 성공적으로 보냈습니다.", Toast.LENGTH_SHORT).show();
            Log.v("여기","SenMailClass6");
        } catch (SendFailedException e) {
            Log.v("여기","SenMailClass7");
            Toast.makeText(context, "이메일 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show();
        } catch (MessagingException e) {
            Log.v("여기","SenMailClass8");
            Toast.makeText(context, "인터넷 연결을 확인해주십시오", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.v("여기","SenMailClass9");
            e.printStackTrace();
        }
    }
}


