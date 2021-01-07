package com.example.makekit.makekit_activity;



public class ProductData {

    private int product_image;
    private  String product_title;
    private  String sub_title;
    private  String product_price;

    public ProductData(int product_image, String product_title, String sub_title, String product_price) {
        this.product_image = product_image;
        this.product_title = product_title;
        this.sub_title = sub_title;
        this.product_price = product_price;
    }


    public int getProduct_image() {
        return product_image;
    }

    public void setProduct_image(int product_image) {
        this.product_image = product_image;
    }

    public String getProduct_title() {
        return product_title;
    }

    public void setProduct_title(String product_title) {
        this.product_title = product_title;
    }

    public String getSub_title() {
        return sub_title;
    }

    public void setSub_title(String sub_title) {
        this.sub_title = sub_title;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }
}
