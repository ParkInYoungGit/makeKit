package com.example.makekit.makekit_bean;

public class Order {

    // orderinfo
    String orderNo;
    String userinfo_userEmail;
    String orderDate;
    String orderReceiver;
    String orderRcvAddress;
    String orderRcvAddressDetail;
    String orderRcvPhone;
    String orderTotalPrice;
    String orderBank;
    String orderCardNo;
    String orderCardPw;
    String orderDelivery;
    String orderDeliveryDate;

    // orderdetail(위와 중복되는 orderinfo_orderNo, userinfo_userEmail은 뺌)
    String orderDetailNo;
    String goods_productNo;
    String orderQuantity;
    String orderConfirm;
    String orderRefund;
    String orderStar;
    String orderReview;
    String orderReviewImg;

    // 오더와 연결 되어있는 상품 관련
    String productNo;
    String productName;
    String productPrice;
    String productStock;
    String productAFilename;

    // constructor는 원하는 만큼 생성
    //


    public Order(String productNo, String productName, String productPrice, String productStock, String productAFilename) {
        this.productNo = productNo;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productStock = productStock;
        this.productAFilename = productAFilename;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getUserinfo_userEmail() {
        return userinfo_userEmail;
    }

    public void setUserinfo_userEmail(String userinfo_userEmail) {
        this.userinfo_userEmail = userinfo_userEmail;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderReceiver() {
        return orderReceiver;
    }

    public void setOrderReceiver(String orderReceiver) {
        this.orderReceiver = orderReceiver;
    }

    public String getOrderRcvAddress() {
        return orderRcvAddress;
    }

    public void setOrderRcvAddress(String orderRcvAddress) {
        this.orderRcvAddress = orderRcvAddress;
    }

    public String getOrderRcvAddressDetail() {
        return orderRcvAddressDetail;
    }

    public void setOrderRcvAddressDetail(String orderRcvAddressDetail) {
        this.orderRcvAddressDetail = orderRcvAddressDetail;
    }

    public String getOrderRcvPhone() {
        return orderRcvPhone;
    }

    public void setOrderRcvPhone(String orderRcvPhone) {
        this.orderRcvPhone = orderRcvPhone;
    }

    public String getOrderTotalPrice() {
        return orderTotalPrice;
    }

    public void setOrderTotalPrice(String orderTotalPrice) {
        this.orderTotalPrice = orderTotalPrice;
    }

    public String getOrderBank() {
        return orderBank;
    }

    public void setOrderBank(String orderBank) {
        this.orderBank = orderBank;
    }

    public String getOrderCardNo() {
        return orderCardNo;
    }

    public void setOrderCardNo(String orderCardNo) {
        this.orderCardNo = orderCardNo;
    }

    public String getOrderCardPw() {
        return orderCardPw;
    }

    public void setOrderCardPw(String orderCardPw) {
        this.orderCardPw = orderCardPw;
    }

    public String getOrderDelivery() {
        return orderDelivery;
    }

    public void setOrderDelivery(String orderDelivery) {
        this.orderDelivery = orderDelivery;
    }

    public String getOrderDeliveryDate() {
        return orderDeliveryDate;
    }

    public void setOrderDeliveryDate(String orderDeliveryDate) {
        this.orderDeliveryDate = orderDeliveryDate;
    }

    public String getOrderDetailNo() {
        return orderDetailNo;
    }

    public void setOrderDetailNo(String orderDetailNo) {
        this.orderDetailNo = orderDetailNo;
    }

    public String getGoods_productNo() {
        return goods_productNo;
    }

    public void setGoods_productNo(String goods_productNo) {
        this.goods_productNo = goods_productNo;
    }

    public String getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(String orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public String getOrderConfirm() {
        return orderConfirm;
    }

    public void setOrderConfirm(String orderConfirm) {
        this.orderConfirm = orderConfirm;
    }

    public String getOrderRefund() {
        return orderRefund;
    }

    public void setOrderRefund(String orderRefund) {
        this.orderRefund = orderRefund;
    }

    public String getOrderStar() {
        return orderStar;
    }

    public void setOrderStar(String orderStar) {
        this.orderStar = orderStar;
    }

    public String getOrderReview() {
        return orderReview;
    }

    public void setOrderReview(String orderReview) {
        this.orderReview = orderReview;
    }

    public String getOrderReviewImg() {
        return orderReviewImg;
    }

    public void setOrderReviewImg(String orderReviewImg) {
        this.orderReviewImg = orderReviewImg;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductStock() {
        return productStock;
    }

    public void setProductStock(String productStock) {
        this.productStock = productStock;
    }

    public String getProductAFilename() {
        return productAFilename;
    }

    public void setProductAFilename(String productAFilename) {
        this.productAFilename = productAFilename;
    }
}
