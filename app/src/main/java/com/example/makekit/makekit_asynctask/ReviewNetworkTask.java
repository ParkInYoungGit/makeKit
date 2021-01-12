package com.example.makekit.makekit_asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.example.makekit.makekit_bean.Review;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ReviewNetworkTask extends AsyncTask<Integer, String, Object> {

    final static String TAG = "ReviewNetworkTask";
    Context context = null;
    String mAddr = null;
    String where = null;
    ProgressDialog progressDialog = null;
    ArrayList<Review> reviews;

    public ReviewNetworkTask(Context context, String mAddr, String where) {
        this.context = context;
        this.mAddr = mAddr;
        this.where = where;
        this.reviews = new ArrayList<Review>();
        Log.v(TAG, "Start : "+ mAddr);
    }

    public ReviewNetworkTask(FragmentActivity activity, String urlAddr) {
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
        Log.v(TAG, "before try !!!!!!!!!!!!!!! ");
        try{
            Log.v(TAG, "after try !!!!!!!!!!!!!!! ");
            URL url = new URL(mAddr);
            Log.v(TAG, "mAddr >>>>>>>>>>>>>>>" + mAddr);

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

                if(where.equals("selectProduct")){
                    sellerParser(stringBuffer.toString());
                 }

                if(where.equals("selectReview")){
                    sellerParser(stringBuffer.toString());
                }

                if(where.equals("registerReview")){
                    result = registerReviewParser(stringBuffer.toString());
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


        if (where.equals("selectProduct")) {
            return reviews;
        }
        if (where.equals("selectReview")) {
            return reviews;
        }
        if (where.equals("registerReview")) {
            return result;
        }

        return reviews;

    }

    private void sellerParser(String s){
        Log.v(TAG, "parser()");
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("review_info"));
            Log.v(TAG, "parser() in");
            reviews.clear();
            for(int i = 0 ; i<jsonArray.length() ; i++){
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);

                String orderDetailNo = jsonObject1.getString("orderDetailNo");
                String reviewWriterName = jsonObject1.getString("reviewWriterName");
                String reviewContent = jsonObject1.getString("reviewContent");
                String reviewImage = jsonObject1.getString("reviewImage");
                String reviewDate = jsonObject1.getString("reviewDate");
                String reviewStar = jsonObject1.getString("reviewStar");

                Review review = new Review(orderDetailNo, reviewWriterName, reviewContent, reviewImage, reviewDate, reviewStar);
                reviews.add(review);
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }



    private String registerReviewParser(String s){
        Log.v(TAG,"parserRegisterReview()");
        String returnResult = null;

        try{
            JSONObject jsonObject = new JSONObject(s);
            returnResult = jsonObject.getString("result");
            Log.v(TAG, returnResult);

        } catch (Exception e){
            e.printStackTrace();
        }

        return returnResult;
    }






} // END
