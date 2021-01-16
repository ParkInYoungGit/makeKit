package com.example.makekit.makekit_asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.makekit.makekit_bean.Cart;
import com.example.makekit.makekit_bean.Review;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class CartNetworkTask extends AsyncTask<Integer, String, Object> {

    final static String TAG = "CartNetworkTask";
    Context context = null;
    String mAddr = null;
    String where = null;
    ProgressDialog progressDialog = null;
    ArrayList<Cart> carts;
    ArrayList<String> cartNumber;
    String cartNum = null;

    public CartNetworkTask(Context context, String mAddr, String where) {
        this.context = context;
        this.mAddr = mAddr;
        this.where = where;
        this.carts = new ArrayList<Cart>();
        this.cartNumber = new ArrayList<String>();
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

        String result = null;

        StringBuffer stringBuffer = new StringBuffer();

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

                if(where.equals("selectCartNo")){
                    cartNum = cartNoParser(stringBuffer.toString());

                } else if(where.equals("select")){
                    cartParser(stringBuffer.toString());

                } else if (where.equals("selectCartCheck")) {
                    result = cartCheckParser(stringBuffer.toString());

                } else if(where.equals("selectCartQnt")) {
                    result = cartQntParser(stringBuffer.toString());

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

        if(where.equals("selectCartNo")) {
            return cartNum;

        } else if(where.equals("select")){
            return carts;

        } else if (where.equals("selectCartCheck")) {
            return result;

        } else if(where.equals("selectCartQnt")) {
            return result;

        } else {
            return result;
        }

    }

    private String cartNoParser(String s){
        String cartNo = null;
        Log.v(TAG, "parser()");
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("cart_info"));
            Log.v(TAG, "parser() in");
            //cartNumber.clear();
            for(int i = 0 ; i<jsonArray.length() ; i++){
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);

               cartNo = jsonObject1.getString("cartNo");

               //cartNumber.add(cartNo);
            }

        } catch (Exception e){
            e.printStackTrace();
        }
        return cartNo;
    }

    private void cartParser(String s){
        Log.v(TAG, "parser()");
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("cart_info"));
            Log.v(TAG, "parser() in");
            carts.clear();
            for(int i = 0 ; i<jsonArray.length() ; i++){
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);

                String productNo = jsonObject1.getString("productNo");
                String productName = jsonObject1.getString("productName");
                String prouductImage = jsonObject1.getString("prouductImage");
                String productPrice = jsonObject1.getString("productPrice");
                String productQuantity = jsonObject1.getString("productQuantity");
                String cartNo = jsonObject1.getString("cartNo");
                //String totalPrice = jsonObject1.getString("totalPrice");


                Cart cart = new Cart(cartNo, productNo, productQuantity, productName, prouductImage, productPrice);
                Log.v(TAG, String.valueOf(cart));
                carts.add(cart);
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

    // cart 존재 체크
    private String cartCheckParser(String s){
        String result="";
        Log.v(TAG, "parser()");
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("cart_info"));
            Log.v(TAG, "parser() in");
            for(int i = 0 ; i<jsonArray.length() ; i++){
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);

               result = jsonObject1.getString("result");

            }

        } catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }


    // cart 수량
    private String cartQntParser(String s){
        String result="";
        Log.v(TAG, "parser()");
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("cartqnt_info"));
            Log.v(TAG, "parser() in");
            for(int i = 0 ; i<jsonArray.length() ; i++){
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);

                String productNo = jsonObject1.getString("productNo");
                result = jsonObject1.getString("cartQuantity");

            }

        } catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

}
