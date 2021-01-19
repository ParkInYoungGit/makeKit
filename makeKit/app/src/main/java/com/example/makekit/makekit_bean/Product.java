package com.example.makekit.makekit_bean;

public class Product {
    String sellerEmail;
    String productNo;
    String productName;
    String productType;
    String productPrice;
    String productStock;
    String productContent;
    String productFilename;
    String productDfilename;
    String productAFilename;
    String productInsertDate;
    String productDeleteDate;
    String sellerImage;
    String productSubTitle;




    ////////////////////////////////////////////
    // 01/09 kyeongmi 추가 product view
    public Product(String sellerEmail, String productNo, String productName, String productPrice, String productContent, String productFilename, String productDfilename, String productAFilename, String sellerImage) {
        this.sellerEmail = sellerEmail;
        this.productNo = productNo;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productContent = productContent;
        this.productFilename = productFilename;
        this.productDfilename = productDfilename;
        this.productAFilename = productAFilename;
        this.sellerImage = sellerImage;
    }

    ////////////////////////////////////////////

    public Product(String productNo, String productName, String productSubTitle, String productType, String productPrice, String productStock, String productContent, String productFilename, String productDfilename, String productAFilename, String productInsertDate, String productDeleteDate) {
        this.productNo = productNo;
        this.productName = productName;
        this.productSubTitle = productSubTitle;
        this.productType = productType;
        this.productPrice = productPrice;
        this.productStock = productStock;
        this.productContent = productContent;
        this.productFilename = productFilename;
        this.productDfilename = productDfilename;
        this.productAFilename = productAFilename;
        this.productInsertDate = productInsertDate;
        this.productDeleteDate = productDeleteDate;
    }

    ///////// Ria 추가 //////////////////////////////////








    //getter setter /////////////////////////////////


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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
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

    public String getProductContent() {
        return productContent;
    }

    public void setProductContent(String productContent) {
        this.productContent = productContent;
    }

    public String getProductFilename() {
        return productFilename;
    }

    public void setProductFilename(String productFilename) {
        this.productFilename = productFilename;
    }

    public String getProductDfilename() {
        return productDfilename;
    }

    public void setProductDfilename(String productDfilename) {
        this.productDfilename = productDfilename;
    }

    public String getProductAFilename() {
        return productAFilename;
    }

    public void setProductAFilename(String productAFilename) {
        this.productAFilename = productAFilename;
    }

    public String getProductInsertDate() {
        return productInsertDate;
    }

    public void setProductInsertDate(String productInsertDate) {
        this.productInsertDate = productInsertDate;
    }

    public String getProductDeleteDate() {
        return productDeleteDate;
    }

    public void setProductDeleteDate(String productDeleteDate) {
        this.productDeleteDate = productDeleteDate;
    }

    public String getSellerImage() {
        return sellerImage;
    }

    public void setSellerImage(String sellerImage) {
        this.sellerImage = sellerImage;
    }

    public String getProductSubTitle() {
        return productSubTitle;
    }

    public void setProductSubTitle(String productSubTitle) {
        this.productSubTitle = productSubTitle;
    }
}

