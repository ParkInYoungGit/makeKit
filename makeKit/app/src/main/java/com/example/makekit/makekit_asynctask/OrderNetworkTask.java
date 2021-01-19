package com.example.makekit.makekit_asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.makekit.makekit_bean.Order;
import com.example.makekit.makekit_bean.Payment;
import com.example.makekit.makekit_bean.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class OrderNetworkTask extends AsyncTask<Integer, String, Object> {

    final static String TAG = "OrderNetworkTask";
    Context context = null;
    String mAddr = null;
    String where = null;
    ArrayList<Order> order = null;
    ArrayList<Payment> payment = null;
    int loginCheck = 0;


    public OrderNetworkTask(Context context, String mAddr) {
        this.context = context;
        this.mAddr = mAddr;
        this.payment = new ArrayList<Payment>();
        Log.v(TAG, "Start : " + mAddr);
    }
    public OrderNetworkTask(Context context, String mAddr, String where) {
        this.context = context;
        this.mAddr = mAddr;
        this.where = where;
        this.order = new ArrayList<Order>();
        this.payment = new ArrayList<Payment>();

        Log.v(TAG, "Start : " + mAddr);
    }


    @Override
    protected Object doInBackground(Integer... integers) {
        Log.v(TAG, "doInBackground()");

        StringBuffer stringBuffer = new StringBuffer();

        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;

        BufferedReader bufferedReader = null;
        String result = null;
        Log.v(TAG, "before try");


        try{
            Log.v(TAG, "after try");
            URL url = new URL(mAddr);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(10000);
            Log.v(TAG, "Accept : "+httpURLConnection.getResponseCode());
            if(httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                inputStream = httpURLConnection.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream);
                bufferedReader = new BufferedReader(inputStreamReader);

                while (true){
                    String strline = bufferedReader.readLine();
                    if(strline == null) break;
                    stringBuffer.append(strline + "\n");
                }
                Log.v(TAG, "StringBuffer : "+stringBuffer.toString());


                if (where.equals("selectOrder")) {
                    parserOrderSelect(stringBuffer.toString());

                } else if (where.equals("selectProductOrder")){
                    parserOrderProductSelect(stringBuffer.toString());

                } else if(where.equals("select")){
                    result = parserOrderNoSelect(stringBuffer.toString());

                } else if (where.equals("selectProduct")){
                    parserOrderProduct(stringBuffer.toString());

                } else {
                    result = parserInsert(stringBuffer.toString());

                }


            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                if(bufferedReader != null) bufferedReader.close();
                if(inputStreamReader != null) inputStreamReader.close();
                if(inputStream != null) inputStream.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        if (where.equals("selectOrder")) {
            return order;

        } else if (where.equals("selectProductOrder")){
            return payment;

        } else if(where.equals("select")) {
            return result;

        } else if (where.equals("selectProduct")) {
            return payment;

        } else {
            return result;

        }
    }




    @Override
    protected void onProgressUpdate(String... values) {
        Log.v(TAG, "doProgressUpdate()");
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Object o) {
        Log.v(TAG, "doPostExecute()");
        super.onPostExecute(o);
//        progressDialog.dismiss();
    }

    @Override
    protected void onCancelled() {
        Log.v(TAG, "onCancelled()");
        super.onCancelled();
    }

    // OrderActivity 주문자 기본 정보
    private void parserOrderSelect(String s) {
        Log.v(TAG, "Parser()");

        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("user_info"));
            order.clear();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                String name = jsonObject1.getString("userName");
                String tel = jsonObject1.getString("userTel");
                String address = jsonObject1.getString("userAddress");
                String addressdetail = jsonObject1.getString("userAddressDetail");

                Order orders = new Order(name, tel, address, addressdetail);
                order.add(orders);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // OrderProductSelect
    private void parserOrderProduct(String s) {
        Log.v(TAG, "Parser in()");

        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("orderdetail_info"));
            Log.v(TAG, "Parser : ");
            payment.clear();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                String productNo = jsonObject1.getString("productNo");
                String productName = jsonObject1.getString("productName");
                String productImage = jsonObject1.getString("productImage");
                String productPrice = jsonObject1.getString("productPrice");
                String orderQuantity = jsonObject1.getString("orderQuantity");
                String orderDate = jsonObject1.getString("orderDate");

                Payment payments = new Payment(productImage, productName, productPrice, orderQuantity, orderDate);

                payment.add(payments);
                Log.v(TAG, "payment : " + payments);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // OrderActivity 제품 정보
    private void parserOrderProductSelect(String s) {
        Log.v(TAG, "Parser in()");

        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("product_info"));
            Log.v(TAG, "Parser : ");
            payment.clear();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                String productImage = jsonObject1.getString("productImage");
                String productName = jsonObject1.getString("productName");
                String productPrice = jsonObject1.getString("productPrice");
                String cartQuantity = jsonObject1.getString("cartQuantity");
                Log.v(TAG, "productImage : " + productImage);
                Log.v(TAG, "productName : " + productName);
                Log.v(TAG, "productPrice : " + productPrice);
                Log.v(TAG, "cartQuantity : " + cartQuantity);

                Payment payments = new Payment(productImage, productName, productPrice, cartQuantity);

                payment.add(payments);
                Log.v(TAG, "payment : " + payments);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // OrderNo select
    private String parserOrderNoSelect(String s) {
        Log.v(TAG, "Parser in()");
        String orderNo ="";

        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("order_info"));
            Log.v(TAG, "Parser : ");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                orderNo = jsonObject1.getString("orderNo");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orderNo;
    }

    // insert/update action
    private String parserInsert(String s) {
        Log.v(TAG, "parserInsert()");
        String returnResult = null;

        try {
            JSONObject jsonObject = new JSONObject(s);
            returnResult = jsonObject.getString("result");
            Log.v(TAG, returnResult);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return returnResult;
    }


}
