package com.example.makekit.makekit_bean;

public class ChattingBean {

    String userinfo_userEmail_sender;
    String userinfo_userEmail_receiver;
    String product_prdNo;
    String chattingContents;
    String chattingInsertDate;

    public ChattingBean(String userinfo_userEmail_sender, String userinfo_userEmail_receiver, String product_prdNo, String chattingContents, String chattingInsertDate) {
        this.userinfo_userEmail_sender = userinfo_userEmail_sender;
        this.userinfo_userEmail_receiver = userinfo_userEmail_receiver;
        this.product_prdNo = product_prdNo;
        this.chattingContents = chattingContents;
        this.chattingInsertDate = chattingInsertDate;
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

    public String getProduct_prdNo() {
        return product_prdNo;
    }

    public void setProduct_prdNo(String product_prdNo) {
        this.product_prdNo = product_prdNo;
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
}
