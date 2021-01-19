package com.example.makekit.makekit_asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.makekit.makekit_bean.ChattingBean;
import com.example.makekit.makekit_bean.Order;
import com.example.makekit.makekit_bean.Product;
import com.example.makekit.makekit_bean.Review;
import com.example.makekit.makekit_bean.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class NetworkTask_DH extends AsyncTask<Integer, String, Object> {

    Context context = null;
    String mAddr = null;
    ProgressDialog progressDialog = null;
    ArrayList<Product> products;
    ArrayList<String> productsName;
    ArrayList<ChattingBean> chattingContents;
    ArrayList<ChattingBean> chattingList;
    ArrayList<User> users;
    ArrayList<Order> orders;
    ArrayList<Order> salesList;
    ArrayList<Order> purchaseList;
    ArrayList<Order> reviewList;
//    ArrayList<Order> reviewCheck;
    String reviewCheck = null;

    String chattingNumber;
    String where = null;

    public NetworkTask_DH(Context context, String mAddr, String where) {
        this.context = context;
        this.mAddr = mAddr;
        this.products = new ArrayList<Product>();
        this.productsName = new ArrayList<String>();
        this.chattingContents = new ArrayList<ChattingBean>();
        this.chattingList = new ArrayList<ChattingBean>();
        this.users = new ArrayList<User>();
        this.orders = new ArrayList<Order>();
        this.salesList = new ArrayList<Order>();
        this.purchaseList = new ArrayList<Order>();
        this.reviewList = new ArrayList<Order>();
        this.where = where;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected Object doInBackground(Integer... integers) {
        Log.v("NetworkTask", "doInBackground");
        StringBuffer stringBuffer = new StringBuffer();
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;

        String result = null;


        try {
            URL url = new URL(mAddr);
            Log.v("NetworkTask", mAddr);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(10000);
            Log.v("NetworkTask", "Accept : "+httpURLConnection.getResponseCode());
            if(httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = httpURLConnection.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream);
                bufferedReader = new BufferedReader(inputStreamReader);
                Log.v("NetworkTask", "doInBackground, if");
                while (true) {
                    String strline = bufferedReader.readLine();
                    if (strline == null) break;
                    stringBuffer.append(strline + "\n");
                }

                if (where.equals("search")) {
                    parserSelect(stringBuffer.toString());
                } else if (where.equals("productName")) {
                    parserProductName(stringBuffer.toString());
                } else if (where.equals("chattingContents")) {
                    parserChattingContents(stringBuffer.toString());
                } else if (where.equals("inputChatting")) {
                    result = parserAction(stringBuffer.toString());
                } else if (where.equals("getChattingList")) {
                    parserChattingList(stringBuffer.toString());
                } else if (where.equals("getChattingNumber")) {
                    parserChattingNumber(stringBuffer.toString());
                } else if (where.equals("LikeSeller")) {
                    parserLikeSeller(stringBuffer.toString());
                } else if (where.equals("getSalesList")) {
                    parserSalesList(stringBuffer.toString());
                } else if (where.equals("getRealSalesList")) {
                    Log.v("NetworkTask", "if chattingContents");
                    parserRealSalesList(stringBuffer.toString());
                } else if (where.equals("purchaseList")) {
                    parserRealSalesList2(stringBuffer.toString());
                    Log.v("NetworkTask", "parserselect2");
                } else if (where.equals("writeReviewList")) {
                    parserWriteReviewList(stringBuffer.toString());
                } else if (where.equals("updatebuy")) {
                    result = parserAction(stringBuffer.toString());
                    Log.v("NetworkTask", "result : "+result);
                }
                else if (where.equals("reviewCheck")){
                    reviewCheck = parserReviewCheck(stringBuffer.toString());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(bufferedReader != null) bufferedReader.close();
                if(inputStreamReader != null) inputStreamReader.close();
                if(inputStream != null) inputStream.close();

            }catch (Exception e2){
                e2.printStackTrace();
            }
        }

        if(where.equals("search")){
            return products;
        }else if (where.equals("productName")){
            return productsName;
        }else if(where.equals("chattingContents")){
            return chattingContents;
        }else if(where.equals("getChattingList")){
            return chattingList;
        }else if (where.equals("getChattingNumber")){
            return chattingNumber;
        }else if (where.equals("LikeSeller")){
            return users;
        }else if (where.equals("getSalesList")){
            return orders;
        }else if (where.equals("getRealSalesList")){
            return salesList;
        }else if (where.equals("purchaseList")){
            return purchaseList;
        }else if (where.equals("writeReviewList")){
            return reviewList;
        }else if(where.equals("update")){
            return result;
        }else if(where.equals("updatebuy")){
            return result;

        }else if (where.equals("reviewCheck")){
            return reviewCheck;

        }
        else {
            return result;
        }

    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }


    private void parserSelect(String s){
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("makekit_info"));
            products.clear();
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                String productNo = jsonObject1.getString("productNo");
                String productName = jsonObject1.getString("productName");
                String productSubTitle = jsonObject1.getString("productSubTitle");
                String productType = jsonObject1.getString("productType");
                String productPrice = jsonObject1.getString("productPrice");
                String productStock = jsonObject1.getString("productStock");
                String productContent = jsonObject1.getString("productContent");
                String productFilename = jsonObject1.getString("productFilename");
                String productDFilename = jsonObject1.getString("productDFilename");
                String productAFilename = jsonObject1.getString("productAFilename");
                String productInsertDate = jsonObject1.getString("productInsertDate");
                String productDeleteDate = jsonObject1.getString("productDeleteDate");
                Product product = new Product(productNo, productName, productSubTitle, productType, productPrice, productStock, productContent, productFilename, productDFilename, productAFilename, productInsertDate, productDeleteDate);
                products.add(product);
            }
        }catch (Exception e){
            Log.v("NetworkTask", "parserselect, catch");
            e.printStackTrace();
        }
    }

    private void parserProductName(String s){
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("makekit_info"));
            productsName.clear();
            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                String productName = jsonObject1.getString("productName");

                productsName.add(productName);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void parserChattingContents(String s){
        try {
            Log.v("NetworkTask", "parserselect, tryin");
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("makekit_info"));
            chattingContents.clear();
            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                String userinfo_userEmail_sender = jsonObject1.getString("userinfo_userEmail_sender");
                String userinfo_userEmail_receiver = jsonObject1.getString("userinfo_userEmail_receiver");
                String chattingContent = jsonObject1.getString("chattingContents");
                String chattingInsertDate = jsonObject1.getString("chattingInsertDate");
                String chattingNumber = jsonObject1.getString("chattingNumber");

                ChattingBean chattingBean = new ChattingBean(userinfo_userEmail_sender, userinfo_userEmail_receiver, chattingContent, chattingInsertDate, chattingNumber);

                chattingContents.add(chattingBean);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private String parserAction(String s){
        String returnValue = null;
        try {
            JSONObject jsonObject = new JSONObject(s);
            returnValue = jsonObject.getString("result");
        }catch (Exception e){
            e.printStackTrace();
        }
        return returnValue;
    }

    private void parserChattingList(String s){
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("makekit_info"));
            chattingList.clear();
            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                String userinfo_userEmail_sender = jsonObject1.getString("userinfo_userEmail_sender");
                String userinfo_userEmail_receiver = jsonObject1.getString("userinfo_userEmail_receiver");
                String chattingContent = jsonObject1.getString("chattingContents");
                String chattingInsertDate = jsonObject1.getString("chattingInsertDate");
                String chattingNumber = jsonObject1.getString("chattingNumber");

                ChattingBean chattingBean = new ChattingBean(userinfo_userEmail_sender, userinfo_userEmail_receiver, chattingContent, chattingInsertDate, chattingNumber);

                chattingList.add(chattingBean);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void parserChattingNumber(String s){
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("makekit_info"));
            chattingNumber = null;
            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                chattingNumber = jsonObject1.getString("chattingNumber");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void parserLikeSeller(String s){
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("makekit_info"));
            users.clear();
            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                String userinfo_like_userEmail = jsonObject1.getString("userinfo_like_userEmail");
                User user = new User(userinfo_like_userEmail);

                users.add(user);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void parserSalesList(String s){
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("makekit_info"));
            orders.clear();
            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                String productNo = jsonObject1.getString("productNo");
                String productAFilename = jsonObject1.getString("productAFilename");
                String productName = jsonObject1.getString("productName");
                String productStock = jsonObject1.getString("productStock");
                String productPrice = jsonObject1.getString("productPrice");

                Order order = new Order(productNo, productName, productPrice, productStock, productAFilename);

                orders.add(order);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void parserRealSalesList(String s){
        Log.v("NetworkTask", "parserselect");
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("makekit_info"));
            salesList.clear();
            for(int i = 0; i < jsonArray.length(); i++){
                Log.v("NetworkTask", "parserselect, for");
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                String userName = jsonObject1.getString("userName");
                String orderNo = jsonObject1.getString("orderNo");
                String userinfo_userEmail = jsonObject1.getString("userinfo_userEmail");
                String orderReceiver = jsonObject1.getString("orderReceiver");
                String orderRcvAddress = jsonObject1.getString("orderRcvAddress");
                String orderRcvAddressDetail = jsonObject1.getString("orderRcvAddressDetail");
                String orderRcvPhone = jsonObject1.getString("orderRcvPhone");
                String orderTotalPrice = jsonObject1.getString("orderTotalPrice");
                String orderBank = jsonObject1.getString("orderBank");
                String orderCardNo = jsonObject1.getString("orderCardNo");
                String orderDate = jsonObject1.getString("orderDate");
                String orderDelivery = jsonObject1.getString("orderDelivery");
                String orderDeliveryDate = jsonObject1.getString("orderDeliveryDate");
                String orderDetailNo = jsonObject1.getString("orderDetailNo");
                String orderinfo_orderNo = jsonObject1.getString("orderinfo_orderNo");
                String goods_productNo = jsonObject1.getString("goods_productNo");
                String orderQuantity = jsonObject1.getString("orderQuantity");
                String orderConfirm = jsonObject1.getString("orderConfirm");
                String orderRefund = jsonObject1.getString("orderRefund");
                String orderStar = jsonObject1.getString("orderStar");
                String orderReview = jsonObject1.getString("orderReview");
                String orderReviewImg = jsonObject1.getString("orderReviewImg");
                String orderReviewInsertDate = jsonObject1.getString("orderReviewInsertDate");
                String productName = jsonObject1.getString("productName");
                String productPrice = jsonObject1.getString("productPrice");
                String productAFilename = jsonObject1.getString("productAFilename");
                Log.v("NetworkTask", productName);
                Order order = new Order(orderNo, userinfo_userEmail, orderDate, orderReceiver, orderRcvAddress, orderRcvAddressDetail, orderRcvPhone, orderTotalPrice, orderBank, orderCardNo, userName, orderDelivery,orderDeliveryDate,orderDetailNo,goods_productNo,orderQuantity,orderConfirm,orderRefund,orderStar,orderReview,orderReviewImg,orderinfo_orderNo, productName, productPrice,orderReviewInsertDate,productAFilename);
                salesList.add(order);
                Log.v("NetworkTask", salesList.get(0).getOrderCardPw());
            }
        }catch (Exception e){
            Log.v("NetworkTask", "parserselect, catch");
            e.printStackTrace();
        }
    }
    private void parserRealSalesList2(String r){

        try {
            JSONObject jsonObject2 = new JSONObject(r);
            JSONArray jsonArray2 = new JSONArray(jsonObject2.getString("makekit_info"));
            purchaseList.clear();
            for(int i = 0; i < jsonArray2.length(); i++){

                JSONObject jsonObject3 = (JSONObject) jsonArray2.get(i);
                String userName = jsonObject3.getString("userName");
                String orderNo = jsonObject3.getString("orderNo");
                String userinfo_userEmail = jsonObject3.getString("userinfo_userEmail");
                String orderReceiver = jsonObject3.getString("orderReceiver");
                String orderRcvAddress = jsonObject3.getString("orderRcvAddress");
                String orderRcvAddressDetail = jsonObject3.getString("orderRcvAddressDetail");
                String orderRcvPhone = jsonObject3.getString("orderRcvPhone");
                String orderTotalPrice = jsonObject3.getString("orderTotalPrice");
                String orderBank = jsonObject3.getString("orderBank");
                String orderCardNo = jsonObject3.getString("orderCardNo");
                String orderDate = jsonObject3.getString("orderDate");
                String orderDelivery = jsonObject3.getString("orderDelivery");
                String orderDeliveryDate = jsonObject3.getString("orderDeliveryDate");
                String orderDetailNo = jsonObject3.getString("orderDetailNo");
                String orderinfo_orderNo = jsonObject3.getString("orderinfo_orderNo");
                String goods_productNo = jsonObject3.getString("goods_productNo");
                String orderQuantity = jsonObject3.getString("orderQuantity");
                String orderConfirm = jsonObject3.getString("orderConfirm");
                String orderRefund = jsonObject3.getString("orderRefund");
                String orderStar = jsonObject3.getString("orderStar");
                String orderReview = jsonObject3.getString("orderReview");
                String orderReviewImg = jsonObject3.getString("orderReviewImg");
                String orderReviewInsertDate = jsonObject3.getString("orderReviewInsertDate");
                String productName = jsonObject3.getString("productName");
                String productPrice = jsonObject3.getString("productPrice");
                String productAFilename = jsonObject3.getString("productAFilename");
                Log.v("NetworkTask", productName);
                Order order = new Order(orderNo, userinfo_userEmail, orderDate, orderReceiver, orderRcvAddress, orderRcvAddressDetail, orderRcvPhone, orderTotalPrice, orderBank, orderCardNo, userName, orderDelivery,orderDeliveryDate,orderDetailNo,goods_productNo,orderQuantity,orderConfirm,orderRefund,orderStar,orderReview,orderReviewImg,orderinfo_orderNo, productName, productPrice,orderReviewInsertDate,productAFilename);
                purchaseList.add(order);
                Log.v("NetworkTask", purchaseList.get(0).getOrderCardPw());
            }
        }catch (Exception e){
            Log.v("NetworkTask", "parserselect, catch");
            e.printStackTrace();
        }
    }


    private void parserWriteReviewList(String s){
        Log.v("NetworkTask", "parserWriteReviewList");
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("makekit_info"));
            reviewList.clear();
            for(int i = 0; i < jsonArray.length(); i++) {
                Log.v("NetworkTask", "parserWriteReviewList, for");
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                String orderDetailNo = jsonObject1.getString("orderDetailNo");
                String goods_productNo = jsonObject1.getString("goods_productNo");
                String productFilename = jsonObject1.getString("productFilename");
                String productName = jsonObject1.getString("productName");
                String orderQuantity = jsonObject1.getString("orderQuantity");
                String orderConfirm = jsonObject1.getString("orderConfirm");

                Order order = new Order(orderDetailNo, goods_productNo , productFilename, productName, orderQuantity, orderConfirm);
                reviewList.add(order);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private String parserReviewCheck(String s) {

        String reviewCheck = null;

        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("makekit_info"));

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);

                reviewCheck = jsonObject1.getString("check");

                Log.v("여기", "parserLoginCheck : " + reviewCheck);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reviewCheck;
    }



} // end
