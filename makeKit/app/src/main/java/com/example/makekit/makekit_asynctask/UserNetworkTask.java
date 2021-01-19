package com.example.makekit.makekit_asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.makekit.makekit_bean.Order;
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
    final static String TAG = "UserNetWorkTask";
    Context context = null;
    String mAddr = null;
    String where = null;
    ProgressDialog progressDialog = null;
    ArrayList<User> members;
    ArrayList<User> user = null;
    ArrayList<Order> order = null;
    String loginCheck = null;


    public UserNetworkTask(Context context, String mAddr) {
        this.context = context;
        this.mAddr = mAddr;
        this.members = new ArrayList<User>();
        Log.v(TAG, "Start : "+ mAddr);
    }

    public UserNetworkTask(Context context, String mAddr, String where) {
        this.context = context;
        this.mAddr = mAddr;
        this.where = where;
        this.user = new ArrayList<User>();
        Log.v(TAG, "Start : " + mAddr);
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
                }
                if (where.equals("selectUser")) {
                    peopleParser(stringBuffer.toString());
                }
                if (where.equals("loginCount")) {
                    parserLoginCheck(stringBuffer.toString());
                }
                if (where.equals("insert")) {
                    result = parserAction(stringBuffer.toString());
                }
                if (where.equals("selectOrder")) {
                    parserOrderSelect(stringBuffer.toString());
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
            return user;

        }
        if (where.equals("loginCount")) {
            return loginCheck;

        }
        if (where.equals("selectUser")) {
            return user;

        }
        if (where.equals("selectOrder")) {
            return order;

        }
        if (where.equals("insert")) {
            return result;
        }
        return result;
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
            user.clear();
            for(int i = 0 ; i<jsonArray.length() ; i++){
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);

                String useremail = jsonObject1.getString("userEmail");
                String userpw = jsonObject1.getString("userPw");
                String username = jsonObject1.getString("userName");
                String useraddress = jsonObject1.getString("userAddress");
                String useraddressdetail = jsonObject1.getString("userAddressDetail");
                String usertel = jsonObject1.getString("userTel");
                String userbirth = jsonObject1.getString("userBirth");
                String userimage = jsonObject1.getString("userImage");


                User user2 = new User(useremail, userpw, username, useraddress, useraddressdetail, usertel, userbirth, userimage);
                user.add(user2);
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
            JSONArray jsonArray = new JSONArray(jsonObject.getString("user_info"));
            user.clear();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                String email = jsonObject1.getString("userEmail");
                String name = jsonObject1.getString("userName");
                String pw = jsonObject1.getString("userPw");
                String address = jsonObject1.getString("userAddress");
                String addressDetail = jsonObject1.getString("userAddressDetail");
                String tel = jsonObject1.getString("userTel");
                String birth = jsonObject1.getString("userBirth");

                User users = new User(email, name, pw, address, addressDetail, tel, birth);
                user.add(users);
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

    private String parserLoginCheck(String s) {

        String count = null;

        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("user_info"));

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);

                count = jsonObject1.getString("count");

                loginCheck = count;
                Log.v("여기", "parserLoginCheck : " + count);

                Log.v(TAG, "----------------------------------");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
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

}


