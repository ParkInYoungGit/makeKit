package com.example.makekit.makekit_bean;

public class Payment {

    String image;
    String productName;
    String productPrice;
    String cartQuantity;
    String orderDate;

    public Payment(String image, String productName, String productPrice, String cartQuantity) {
        this.image = image;
        this.productName = productName;
        this.productPrice = productPrice;
        this.cartQuantity = cartQuantity;
    }

    public Payment(String image, String productName, String productPrice, String cartQuantity, String orderDate) {
        this.image = image;
        this.productName = productName;
        this.productPrice = productPrice;
        this.cartQuantity = cartQuantity;
        this.orderDate = orderDate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getCartQuantity() {
        return cartQuantity;
    }

    public void setCartQuantity(String cartQuantity) {
        this.cartQuantity = cartQuantity;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
}

