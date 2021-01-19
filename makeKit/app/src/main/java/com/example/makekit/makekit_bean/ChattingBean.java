package com.example.makekit.makekit_bean;

public class ChattingBean {

    String userinfo_userEmail_sender;
    String userinfo_userEmail_receiver;
    String chattingContents;
    String chattingInsertDate;
    String chattingNumber;

    public ChattingBean(String userinfo_userEmail_sender, String userinfo_userEmail_receiver, String chattingContents, String chattingInsertDate, String chattingNumber) {
        this.userinfo_userEmail_sender = userinfo_userEmail_sender;
        this.userinfo_userEmail_receiver = userinfo_userEmail_receiver;
        this.chattingContents = chattingContents;
        this.chattingInsertDate = chattingInsertDate;
        this.chattingNumber = chattingNumber;
    }

    public String getUserinfo_userEmail_sender() {
        return userinfo_userEmail_sender;
    }

    public void setUserinfo_userEmail_sender(String userinfo_userEmail_sender) {
        this.userinfo_userEmail_sender = userinfo_userEmail_sender;
    }

    public String getUserinfo_userEmail_receiver() {
        return userinfo_userEmail_receiver;
    }

    public void setUserinfo_userEmail_receiver(String userinfo_userEmail_receiver) {
        this.userinfo_userEmail_receiver = userinfo_userEmail_receiver;
    }


    public String getChattingContents() {
        return chattingContents;
    }

    public void setChattingContents(String chattingContents) {
        this.chattingContents = chattingContents;
    }

    public String getChattingInsertDate() {
        return chattingInsertDate;
    }

    public void setChattingInsertDate(String chattingInsertDate) {
        this.chattingInsertDate = chattingInsertDate;
    }

    public String getChattingNumber() {
        return chattingNumber;
    }

    public void setChattingNumber(String chattingNumber) {
        this.chattingNumber = chattingNumber;
    }
}
