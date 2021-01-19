package com.example.makekit.makekit_bean;

public class Order {

    //  order에 들어가는 userinfo
    String userName;
    String userTel;
    String userAddress;
    String userAddressDetail;


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
    String orderinfo_orderNo;
    String orderReviewInsertDate;


    public Order(String orderNo) {
        this.orderNo = orderNo;
    }

    public Order(String userName, String orderNo, String userinfo_userEmail, String orderDate, String orderReceiver, String orderRcvAddress,
                 String orderRcvAddressDetail, String orderRcvPhone, String orderTotalPrice, String orderBank, String orderCardNo, String orderCardPw,
                 String orderDelivery, String orderDeliveryDate, String orderDetailNo, String orderinfo_orderNo, String goods_productNo,
                 String orderQuantity, String orderConfirm, String orderReviewInsertDate,
                 String orderRefund, String orderStar, String orderReview,
                 String orderReviewImg, String productName, String productPrice, String productAFilename) {
        this.userName = userName;
        this.orderNo = orderNo;
        this.userinfo_userEmail = userinfo_userEmail;
        this.orderDate = orderDate;
        this.orderReceiver = orderReceiver;
        this.orderRcvAddress = orderRcvAddress;
        this.orderRcvAddressDetail = orderRcvAddressDetail;
        this.orderRcvPhone = orderRcvPhone;
        this.orderTotalPrice = orderTotalPrice;
        this.orderBank = orderBank;
        this.orderCardNo = orderCardNo;
        this.orderCardPw = orderCardPw;
        this.orderDelivery = orderDelivery;
        this.orderDeliveryDate = orderDeliveryDate;
        this.orderDetailNo = orderDetailNo;
        this.orderinfo_orderNo = orderinfo_orderNo;
        this.goods_productNo = goods_productNo;
        this.orderQuantity = orderQuantity;
        this.orderConfirm = orderConfirm;
        this.orderReviewInsertDate = orderReviewInsertDate;
        this.orderRefund = orderRefund;
        this.orderStar = orderStar;
        this.orderReview = orderReview;
        this.orderReviewImg = orderReviewImg;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productAFilename = productAFilename;
    }

    public String getOrderinfo_orderNo() {
        return orderinfo_orderNo;
    }

    public void setOrderinfo_orderNo(String orderinfo_orderNo) {
        this.orderinfo_orderNo = orderinfo_orderNo;
    }

    public String getOrderReviewInsertDate() {
        return orderReviewInsertDate;
    }

    public void setOrderReviewInsertDate(String orderReviewInsertDate) {
        this.orderReviewInsertDate = orderReviewInsertDate;
    }

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
    String productFilename;

    // constructor는 원하는 만큼 생성


    // Ria 추가 // Write review List에 들어가는 product관련 변수
    public Order(String orderDetailNo, String goods_productNo , String productFilename, String productName, String orderQuantity, String orderConfirm) {
        this.orderDetailNo = orderDetailNo;
        this.goods_productNo = goods_productNo;
        this.productFilename = productFilename;
        this.productName = productName;
        this.orderQuantity = orderQuantity;
        this.orderConfirm = orderConfirm;
    }


    //  order에 들어가는 productInfo




    //  order에 들어가는 userinfo
    public Order(String userName, String userTel, String userAddress, String userAddressDetail) {
        this.userName = userName;
        this.userTel = userTel;
        this.userAddress = userAddress;
        this.userAddressDetail = userAddressDetail;
    }

    public Order(String orderTotalPrice, String orderQuantity, String productName) {
        this.orderTotalPrice = orderTotalPrice;
        this.orderQuantity = orderQuantity;
        this.productName = productName;
    }

    // CardPw로 주문자 이름으로 받음 ,productNo로 orderinfo_orderNo 받음, productStock로 orderReviewInsertDate를 받음
    public Order(String orderNo, String userinfo_userEmail, String orderDate, String orderReceiver, String orderRcvAddress, String orderRcvAddressDetail, String orderRcvPhone, String orderTotalPrice, String orderBank, String orderCardNo, String orderCardPw, String orderDelivery, String orderDeliveryDate, String orderDetailNo, String goods_productNo, String orderQuantity, String orderConfirm, String orderRefund, String orderStar, String orderReview, String orderReviewImg, String productNo, String productName, String productPrice, String productStock, String productAFilename) {
        this.orderNo = orderNo;
        this.userinfo_userEmail = userinfo_userEmail;
        this.orderDate = orderDate;
        this.orderReceiver = orderReceiver;
        this.orderRcvAddress = orderRcvAddress;
        this.orderRcvAddressDetail = orderRcvAddressDetail;
        this.orderRcvPhone = orderRcvPhone;
        this.orderTotalPrice = orderTotalPrice;
        this.orderBank = orderBank;
        this.orderCardNo = orderCardNo;
        this.orderCardPw = orderCardPw;
        this.orderDelivery = orderDelivery;
        this.orderDeliveryDate = orderDeliveryDate;
        this.orderDetailNo = orderDetailNo;
        this.goods_productNo = goods_productNo;
        this.orderQuantity = orderQuantity;
        this.orderConfirm = orderConfirm;
        this.orderRefund = orderRefund;
        this.orderStar = orderStar;
        this.orderReview = orderReview;
        this.orderReviewImg = orderReviewImg;
        this.productNo = productNo;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productStock = productStock;
        this.productAFilename = productAFilename;
    }

    public Order(String productNo, String productName, String productPrice, String productStock, String productAFilename) {
        this.productNo = productNo;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productStock = productStock;
        this.productAFilename = productAFilename;
    }



    // getter setter


    public String getProductFilename() {
        return productFilename;
    }

    public void setProductFilename(String productFilename) {
        this.productFilename = productFilename;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserTel() {
        return userTel;
    }

    public void setUserTel(String userTel) {
        this.userTel = userTel;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserAddressDetail() {
        return userAddressDetail;
    }

    public void setUserAddressDetail(String userAddressDetail) {
        this.userAddressDetail = userAddressDetail;
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
