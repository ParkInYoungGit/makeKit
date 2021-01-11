package com.example.makekit.makekit_bean;

public class Favorite {
    String userEamil;
    String favoriteSellerEmail;
    String sellerEmail;
    String productNo;
    String sellerName;
    String sellerImage;

    public Favorite(String userEamil, String favoriteSellerEmail, String sellerEmail, String productNo, String sellerName, String sellerImage) {
        this.userEamil = userEamil;
        this.favoriteSellerEmail = favoriteSellerEmail;
        this.sellerEmail = sellerEmail;
        this.productNo = productNo;
        this.sellerName = sellerName;
        this.sellerImage = sellerImage;
    }

    public String getUserEamil() {
        return userEamil;
    }

    public void setUserEamil(String userEamil) {
        this.userEamil = userEamil;
    }

    public String getFavoriteSellerEmail() {
        return favoriteSellerEmail;
    }

    public void setFavoriteSellerEmail(String favoriteSellerEmail) {
        this.favoriteSellerEmail = favoriteSellerEmail;
    }

    public String getSellerEmail() {
        return sellerEmail;
    }

    public void setSellerEmail(String sellerEmail) {
        this.sellerEmail = sellerEmail;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getSellerImage() {
        return sellerImage;
    }

    public void setSellerImage(String sellerImage) {
        this.sellerImage = sellerImage;
    }
}
