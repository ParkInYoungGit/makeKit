package com.example.makekit.makekit_asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

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
    String where = null;

    public NetworkTask_DH(Context context, String mAddr, String where) {
        this.context = context;
        this.mAddr = mAddr;
        this.products = new ArrayList<Product>();
        this.productsName = new ArrayList<String>();
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
        }else {
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
//            JSONArray jsonArray = new JSONArray(jsonObject.getString("makekit_info"));
//            products.clear();
//            for(int i = 0; i < jsonArray.length(); i++){
//                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
//                String productNo = jsonObject1.getString("productNo");
//                String productName = jsonObject1.getString("productName");
//                String productType = jsonObject1.getString("productType");
//                String productPrice = jsonObject1.getString("productPrice");
//                String productStock = jsonObject1.getString("productStock");
//                String productContent = jsonObject1.getString("productContent");
//                String productFilename = jsonObject1.getString("productFilename");
//                String productDFilename = jsonObject1.getString("productDFilename");
//                String productAFilename = jsonObject1.getString("productAFilename");
//                String productInsertDate = jsonObject1.getString("productInsertDate");
//                String productDeleteDate = jsonObject1.getString("productDeleteDate");

//
//                Product product = new Product(code, name, dept, phone);
//                products.add(product);
//                Log.v(TAG, member.toString());
//            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void parserProductName(String s){
        try {
            JSONObject jsonObject = new JSONObject(s);
//            JSONArray jsonArray = new JSONArray(jsonObject.getString("makekit_info"));
//            productsName.clear();
//            for(int i = 0; i < jsonArray.length(); i++){
//                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
//                String productName = jsonObject1.getString("productName");
//
//                productsName.add(productName);
//                Log.v(TAG, member.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}