package com.example.makekit.makekit_bean;

public class Cart {
    String cartNo;
    String cartDetailNo;
    String productNo;
    String userEmail;
    String cartQuantity;


    public Cart(String cartNo) {
        this.cartNo = cartNo;
    }

    public Cart(String cartNo, String cartDetailNo, String productNo, String userEmail, String cartQuantity) {
        this.cartNo = cartNo;
        this.cartDetailNo = cartDetailNo;
        this.productNo = productNo;
        this.userEmail = userEmail;
        this.cartQuantity = cartQuantity;
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
}
