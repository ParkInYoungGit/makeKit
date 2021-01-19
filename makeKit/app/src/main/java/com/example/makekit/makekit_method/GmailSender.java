package com.example.makekit.makekit_method;


import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.Properties;
import java.util.Random;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class GmailSender extends javax.mail.Authenticator {
    private String mailhost = "smtp.gmail.com";
    private String user;
    private String password;
    private Session session;
    private String emailCode; // 변경된 비밀번호
    private String emailCode2; // 이메일 인증코드

    public GmailSender(String user, String password) {
        Log.v("여기","GmailSenderClass1");
        this.user = user;
        this.password = password;
        emailCode = createEmailCode();
        emailCode2 = createEmailCode2();
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.host", mailhost);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.quitwait", "false"); //구글에서 지원하는 smtp 정보를 받아와 MimeMessage 객체에 전달해준다.
        session = Session.getDefaultInstance(props, this);
    }

    public String getEmailCode() {
        Log.v("여기","GmailSenderClass2");
        return emailCode;
    } //변경된 비밀번호 반환

    public String getEmailCode2() {
        Log.v("여기","GmailSenderClass2");
        return emailCode2;
    } //생성된 이메일 인증코드 반환



    private String createEmailCode() {
        //변경된 비밀번호
        String[] str = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        String newCode = new String();
        for (int x = 0; x < 8; x++) {
            int random = (int) (Math.random() * str.length);
            newCode += str[random];
        }
        String [] str1 = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
        String [] str2 = {"!","@"};
        Random random1 = new Random();

        newCode = newCode+str1[random1.nextInt(str1.length)];
        newCode = newCode+str2[random1.nextInt(str2.length)];


        return newCode;
    }
    private String createEmailCode2() {
        //이메일 인증코드 생성
        String[] str = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        String newCode = new String();
        for (int x = 0; x < 8; x++) {
            int random = (int) (Math.random() * str.length);
            newCode += str[random];
        }
        return newCode;
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        Log.v("여기","GmailSenderClass3");
        //해당 메서드에서 사용자의 계정(id & password)을 받아 인증받으며 인증 실패시 기본값으로 반환됨.
        return new PasswordAuthentication(user, password);
    }

    public synchronized void sendMail(String subject, String body, String recipients) throws Exception {
        Log.v("여기","GmailSenderClass4");
        MimeMessage message = new MimeMessage(session);
        Log.v("여기","GmailSenderClass5");
        DataHandler handler = new DataHandler(new ByteArrayDataSource(body.getBytes(), "text/plain")); //본문 내용을 byte단위로 쪼개어 전달
        Log.v("여기","GmailSenderClass6");
        message.setSender(new InternetAddress(user)); //본인 이메일 설정
        Log.v("여기","GmailSenderClass7");
        message.setSubject(subject); //해당 이메일의 본문 설정
        Log.v("여기","GmailSenderClass8");
        message.setDataHandler(handler);
        Log.v("여기","GmailSenderClass9");
        if (recipients.indexOf(',') > 0) {
            Log.v("여기", "GmailSenderClass10");
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipients));
            Log.v("여기", "GmailSenderClass11");
        }
        else{
            Log.v("여기","GmailSenderClass12");
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipients));
        }
        Log.v("여기","GmailSenderClass13");
        try {

            Transport.send(message); //메시지 전달

        }catch (Exception e){
            Log.v("여기","try : " +e);

        }


    }

    public class ByteArrayDataSource implements DataSource {
        private byte[] data;
        private String type;

        public ByteArrayDataSource(byte[] data, String type) {
            super();
            this.data = data;
            this.type = type;
        }

        public ByteArrayDataSource(byte[] data) {
            super();
            this.data = data;
        }

        public void setType(String type) {

            this.type = type;

        }

        public String getContentType() {
            if (type == null) return "application/octet-stream";
            else return type;
        }

        public InputStream getInputStream() throws IOException {
            return new ByteArrayInputStream(data);
        }

        public String getName() {
            return "ByteArrayDataSource";
        }

        public OutputStream getOutputStream() throws IOException {
            throw new IOException("Not Supported");
        }
    }
}


