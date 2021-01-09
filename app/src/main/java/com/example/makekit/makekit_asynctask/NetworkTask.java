package com.example.makekit.makekit_asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.makekit.makekit_activity.ProductData;
import com.example.makekit.makekit_bean.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class NetworkTask extends AsyncTask<Integer, String, Object> {
        final static String TAG = "PeopleNetworkTask";
        Context context = null;
        String mAddr = null;
        String where = null;
        ProgressDialog progressDialog = null;
        ArrayList<ProductData> productData = null;

        int loginCheck = 0;


        public NetworkTask(Context context, String mAddr) {
            this.context = context;
            this.mAddr = mAddr;
            this.productData = new ArrayList<ProductData>();
            Log.v(TAG, "Start : "+ mAddr);
        }

//        public UserNetworkTask(Context context, String mAddr, String where) {
//            this.context = context;
//            this.mAddr = mAddr;
//            this.where = where;
//            this.user = new ArrayList<User>();
//            Log.v(TAG, "Start : " + mAddr);
//        }

//    public PeopleNetworkTask(FirstFragment firstFragment, String mAddr) {
//        this.firstFragment = firstFragment;
//        this.mAddr = mAddr;
//        this.members = new ArrayList<People>();
//        Log.v(TAG, "Start : "+ mAddr);
//    }

        @Override
        protected void onPreExecute() {
            Log.v(TAG, "onPreExecute()");
//        progressDialog = new ProgressDialog(context);
//        progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);
//        progressDialog.setTitle("Data Fetch");
//        progressDialog.setMessage("Get...");
//        progressDialog.show();
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

                    if (where.equals("select")) {
                        parserSelect(stringBuffer.toString());
                    } else if (where.equals("selectUser")) {
                        peopleParser(stringBuffer.toString());
                    } else if (where.equals("loginCount")) {
                        parserLoginCheck(stringBuffer.toString());
                    } else {
                        result = parserAction(stringBuffer.toString());
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
                return productData;

            } else if (where.equals("loginCount")) {
                return loginCheck;

            } else if (where.equals("selectUser")) {
                return productData;

            }else{
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

        private void peopleParser(String s){
            Log.v(TAG, "parser()");
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = new JSONArray(jsonObject.getString("product_info"));
                Log.v(TAG, "parser() in");
                productData.clear();
                for(int i = 0 ; i<jsonArray.length() ; i++){
                    JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);

                    String productName = jsonObject1.getString("productName");
                    String productPrice = jsonObject1.getString("productPrice");
                    String productSubTitle = jsonObject1.getString("productSubTitle");
                    String productFilename = jsonObject1.getString("productFilename");

                    ProductData productData2 = new ProductData(productName, productPrice, productSubTitle, productFilename);
                    productData.add(productData2);
                }

            } catch (Exception e){
                e.printStackTrace();
            }
        }

        // select action
        private void parserSelect(String s) {
            Log.v(TAG, "Parser()");

            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = new JSONArray(jsonObject.getString("product_info"));
                productData.clear();

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                    String productName = jsonObject1.getString("productName");
                    String productPrice = jsonObject1.getString("productPrice");
                    String productSubTitle = jsonObject1.getString("productSubTitle");
                    String productFilename = jsonObject1.getString("productFilename");

                    ProductData productData2 = new ProductData(productName, productPrice, productSubTitle, productFilename);
                    productData.add(productData2);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        // insert/update action
        private String parserAction(String s) {
            Log.v(TAG, "parserAction()");
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

        private void parserLoginCheck(String s) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = new JSONArray(jsonObject.getString("user_info"));

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);

                    int count = jsonObject1.getInt("count");

                    loginCheck = count;
                    Log.v("여기", "parserLoginCheck : " + count);

                    Log.v(TAG, "----------------------------------");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
}
