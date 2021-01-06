package com.example.makekit.makekit_asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.makekit.makekit_bean.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class UserNetworkTask extends AsyncTask<Integer, String, Object> {
    final static String TAG = "PeopleNetworkTask";
    Context context = null;
    String mAddr = null;
    String where = null;
    ProgressDialog progressDialog = null;
    ArrayList<User> members;

    public UserNetworkTask(Context context, String mAddr) {
        this.context = context;
        this.mAddr = mAddr;
        this.members = new ArrayList<User>();
        Log.v(TAG, "Start : "+ mAddr);
    }

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


                    peopleParser(stringBuffer.toString());


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

            return members;

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
            JSONArray jsonArray = new JSONArray(jsonObject.getString("user_info"));
            Log.v(TAG, "parser() in");
            members.clear();
            for(int i = 0 ; i<jsonArray.length() ; i++){
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);



                String useremail = jsonObject1.getString("userEmail");
                String userpw = jsonObject1.getString("userPw");
                String username = jsonObject1.getString("userName");
                String useraddress = jsonObject1.getString("userAddress");
                String useraddressdetail = jsonObject1.getString("userAddressDetail");
                String usertel = jsonObject1.getString("userTel");
                String userbirth = jsonObject1.getString("userBirth");




                User user = new User(useremail, userpw, username, useraddress, useraddressdetail, usertel, userbirth);
                members.add(user);
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
