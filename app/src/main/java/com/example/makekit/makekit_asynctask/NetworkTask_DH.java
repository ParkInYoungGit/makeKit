package com.example.makekit.makekit_asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.makekit.makekit_bean.ChattingBean;
import com.example.makekit.makekit_bean.Product;

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
    String chattingNumber;
    String where = null;

    public NetworkTask_DH(Context context, String mAddr, String where) {
        this.context = context;
        this.mAddr = mAddr;
        this.products = new ArrayList<Product>();
        this.productsName = new ArrayList<String>();
        this.chattingContents = new ArrayList<ChattingBean>();
        this.chattingList = new ArrayList<ChattingBean>();
        this.where = where;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("Dialogue");
        progressDialog.setMessage("Get ....");
        progressDialog.show();
    }

    @Override
    protected Object doInBackground(Integer... integers) {
        StringBuffer stringBuffer = new StringBuffer();
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;

        String result = null;


        try {
            URL url = new URL(mAddr);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(10000);

            if(httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                inputStream = httpURLConnection.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream);
                bufferedReader = new BufferedReader(inputStreamReader);

                while (true){
                    String strline = bufferedReader.readLine();
                    if(strline == null) break;
                    stringBuffer.append(strline + "\n");
                }

                if(where.equals("search")){
                    parserSelect(stringBuffer.toString());
                }else if(where.equals("productName")){
                    parserProductName(stringBuffer.toString());
                }else if (where.equals("chattingContents")){
                    parserChattingContents(stringBuffer.toString());
                }else if (where.equals("inputChatting")){
                    result = parserAction(stringBuffer.toString());
                }else if (where.equals("getChattingList")){
                    parserChattingList(stringBuffer.toString());
                }else if (where.equals("getChattingNumber")){
                    parserChattingNumber(stringBuffer.toString());
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
        }else if(where.equals("inputChatting")){
            return chattingList;
        }else if (where.equals("getChattingNumber")){
            return chattingNumber;
        }
        else {
            return result;
        }

    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        progressDialog.dismiss();
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
                String productType = jsonObject1.getString("productType");
                String productPrice = jsonObject1.getString("productPrice");
                String productStock = jsonObject1.getString("productStock");
                String productContent = jsonObject1.getString("productContent");
                String productFilename = jsonObject1.getString("productFilename");
                String productDFilename = jsonObject1.getString("productDFilename");
                String productAFilename = jsonObject1.getString("productAFilename");
                String productInsertDate = jsonObject1.getString("productInsertDate");
                String productDeleteDate = jsonObject1.getString("productDeleteDate");

                Product product = new Product(productNo, productName, productType, productPrice, productStock, productContent, productFilename, productDFilename, productAFilename, productInsertDate, productDeleteDate);
                products.add(product);
            }
        }catch (Exception e){
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

}
