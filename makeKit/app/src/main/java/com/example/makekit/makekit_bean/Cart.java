package com.example.makekit.makekit_bean;

import java.io.Serializable;

public class Cart implements Serializable {
    String cartNo;
    String cartDetailNo;
    String productNo;
    String userEmail;
    String cartQuantity;
    String productName;
    String productImage;
    String productPrice;
    String totalPrice;


    public Cart(String cartNo) {
        this.cartNo = cartNo;
    }

    public Cart(String cartNo, String productNo, String cartQuantity, String productName, String productImage, String productPrice) {
        this.cartNo = cartNo;
        this.productNo = productNo;
        this.cartQuantity = cartQuantity;
        this.productName = productName;
        this.productImage = productImage;
        this.productPrice = productPrice;

    }

    public String getCartNo() {
        return cartNo;
    }

    public void setCartNo(String cartNo) {
        this.cartNo = cartNo;
    }

    public String getCartDetailNo() {
        return cartDetailNo;
    }

    public void setCartDetailNo(String cartDetailNo) {
        this.cartDetailNo = cartDetailNo;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getCartQuantity() {
        return cartQuantity;
    }

    public void setCartQuantity(String cartQuantity) {
        this.cartQuantity = cartQuantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }
}
