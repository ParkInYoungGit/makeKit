package com.example.makekit.makekit_asynctask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.makekit.makekit_activity.ProdutctViewActivity;
import com.example.makekit.makekit_bean.Favorite;
import com.example.makekit.makekit_bean.Product;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import okhttp3.logging.LoggingEventListener;

public class WishlistNetworkTask extends AsyncTask<Integer, String, Object> {

    final static String TAG = "WishlistNetworkTask";
            Context context = null;
            String mAddr = null;
            String insertDate;
            String where = null;
            ArrayList<Favorite> sellerInfo = null;
            int count = 0;

    public WishlistNetworkTask(Context context, String mAddr, String where) {
        this.context = context;
        this.mAddr = mAddr;
        this.where = where;
        this.insertDate = insertDate;
        this.sellerInfo = new ArrayList<Favorite>();
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
                   // productParser(stringBuffer.toString());
                } else if (where.equals("selectdate")){
                    result = wishCheckParser(stringBuffer.toString());

                } else if (where.equals("selectseller")){
                    result = sellerFavoriteCheckParser(stringBuffer.toString());
                    Log.v(TAG, "result : "+result);


                } else{
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
            return insertDate;

        }  else if (where.equals("selectdate")) {
            return result;

        } else if(where.equals("selectseller")) {
          //return sellerInfo;
            return result;

        } else {
            return result;
        }

    }

    private String wishCheckParser(String s){
        int count = 0;
        String wishlistInsertDate = null;
        Log.v(TAG, "parser()");
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("wishlist_info"));
            Log.v(TAG, "parser() in");
            //insertDate.clear();
            for(int i = 0 ; i<jsonArray.length() ; i++){
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);

                wishlistInsertDate = jsonObject1.getString("wishlistInsertDate");
                count++;
                //insertDate.add(wishlistInsertDate);
            }

        } catch (Exception e){
            e.printStackTrace();
        }
        return Integer.toString(count);
    }

    // insert/update action
    private String parserAction(String s) {
        Log.v(TAG, "parserAction()");
        String returnResult = null;

        try {
            JSONObject jsonObject = new JSONObject(s);
            returnResult = jsonObject.getString("result");
            Log.v(TAG, "insert" +  returnResult);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return returnResult;
    }

    // seller 찜 검색
    private String sellerFavoriteCheckParser(String s){
        int count = 0;
        String sellerEmail = null;
        Log.v(TAG, "parser()");
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("sellerFavorite_info"));
            Log.v(TAG, "parser() in");
            sellerInfo.clear();
            for(int i = 0 ; i<jsonArray.length() ; i++){
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);

                String userEmail = jsonObject1.getString("userEmail");
                sellerEmail = jsonObject1.getString("sellerEmail");
                String productNo = jsonObject1.getString("productNo");
                String sellerName = jsonObject1.getString("sellerName");
                String sellerImage = jsonObject1.getString("sellerImage");
                String favoriteSellerEmail = jsonObject1.getString("favoriteSellerEmail");
                count++;
                Log.v(TAG, String.valueOf(count));
                Favorite favorite = new Favorite(userEmail, favoriteSellerEmail, sellerEmail, productNo, sellerName, sellerImage);
                sellerInfo.add(favorite);
            }

        } catch (Exception e){
            e.printStackTrace();
        }
        //return sellerInfo;
        return Integer.toString(count);
    }


}
