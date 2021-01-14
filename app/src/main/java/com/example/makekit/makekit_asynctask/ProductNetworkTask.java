package com.example.makekit.makekit_asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.makekit.makekit_bean.Product;
import com.example.makekit.makekit_bean.Review;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ProductNetworkTask extends AsyncTask<Integer, String, Object> {

    final static String TAG = "ProductNetworkTask";
    Context context = null;
    String mAddr = null;
    ProgressDialog progressDialog = null;
    ArrayList<Product> products;
    String where = null;

    public ProductNetworkTask(Context context, String mAddr) {
        this.context = context;
        this.mAddr = mAddr;
        this.products = new ArrayList<Product>();
        Log.v(TAG, "Start : "+ mAddr);
    }
    public ProductNetworkTask(Context context, String mAddr, String where) {
        this.context = context;
        this.mAddr = mAddr;
        this.where = where;
        this.products = new ArrayList<Product>();
        Log.v(TAG, "Start : "+ mAddr);
    }

    @Override
    protected void onPreExecute() {
        Log.v(TAG, "onPreExecute()");
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
    }

    @Override
    protected void onCancelled() {
        Log.v(TAG, "onCancelled()");
        super.onCancelled();
    }

    @Override
    protected Object doInBackground(Integer... integers) {
        Log.v(TAG, "doInBackground()");

        StringBuffer stringBuffer = new StringBuffer();
        String result = null;
        String resultAddr = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;

        BufferedReader bufferedReader = null;
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

                if(where.equals("select")) {
                    productParser(stringBuffer.toString());
                } else {
                    result = parserInsert(stringBuffer.toString());  // 제품 등록
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

        if (where.equals("select")) {
            return products;
        } else {
            return result;  // 제품 등록
        }

    }

    private void productParser(String s){
        Log.v(TAG, "parser()");
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("product_info"));
            Log.v(TAG, "parser() in");
            products.clear();
            for(int i = 0 ; i<jsonArray.length() ; i++){
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);

                String sellerEmail = jsonObject1.getString("sellerEmail");
                String productNo = jsonObject1.getString("productNo");
                String productName = jsonObject1.getString("productName");
                String productPrice = jsonObject1.getString("productPrice");
                String productContent = jsonObject1.getString("productContent");
                String productFilename = jsonObject1.getString("productFilename");
                String productDfilename = jsonObject1.getString("productDFilename");
                String productAFilename = jsonObject1.getString("productAFilename");
                String sellerImage = jsonObject1.getString("sellerImage");

                Product product = new Product(sellerEmail, productNo, productName, productPrice, productContent, productFilename, productDfilename, productAFilename, sellerImage);
                products.add(product);
            }

        } catch (Exception e){
            e.printStackTrace();
        }

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
