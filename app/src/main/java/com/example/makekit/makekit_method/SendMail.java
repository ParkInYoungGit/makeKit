package com.example.makekit.makekit_method;

import android.content.Context;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;

public class SendMail extends AppCompatActivity {

    public String sendSecurityCode(Context context, String sendTo, String user, String password) {
        String code = "";
        try {
            GMailSender gMailSender = new GMailSender(user, password);
            code = gMailSender.getEmailCode();
            String body = "[사람들의 소중한 주소록] 인증번호는 "  + code +" 입니다." ;
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
}

