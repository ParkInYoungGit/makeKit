package com.example.makekit.makekit_bean;

public class Review {

    String orderDetailNo;
    String reviewContent;
    String reviewImage;
    String reviewDate;
    String reviewWriterName;
    String reviewStar;

    public Review(String orderDetailNo, String reviewWriterName, String reviewContent, String reviewImage, String reviewDate, String reviewStar) {
        this.orderDetailNo = orderDetailNo;
        this.reviewWriterName = reviewWriterName;
        this.reviewContent = reviewContent;
        this.reviewImage = reviewImage;
        this.reviewDate = reviewDate;
        this.reviewStar = reviewStar;
    }


    public String getOrderDetailNo() {
        return orderDetailNo;
    }

    public void setOrderDetailNo(String orderDetailNo) {
        this.orderDetailNo = orderDetailNo;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    public String getReviewImage() {
        return reviewImage;
    }

    public void setReviewImage(String reviewImage) {
        this.reviewImage = reviewImage;
    }

    public String getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(String reviewDate) {
        this.reviewDate = reviewDate;
    }

    public String getReviewWriterName() {
        return reviewWriterName;
    }

    public void setReviewWriterName(String reviewWriterName) {
        this.reviewWriterName = reviewWriterName;
    }

    public String getReviewStar() {
        return reviewStar;
    }

    public void setReviewStar(String reviewStar) {
        this.reviewStar = reviewStar;
    }
}
