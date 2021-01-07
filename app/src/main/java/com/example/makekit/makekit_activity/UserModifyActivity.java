package com.example.makekit.makekit_activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.scroll.pickertest.DatePickerFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.makekit.R;
import com.example.makekit.makekit_adapter.UserAdapter;
import com.example.makekit.makekit_asynctask.CUDNetworkTask;
import com.example.makekit.makekit_asynctask.UserNetworkTask;
import com.example.makekit.makekit_bean.User;

import java.util.ArrayList;

public class UserModifyActivity extends AppCompatActivity {

    final static String TAG = "First";

    String urlAddrBase = null;
    String urlAddr1 = null;
    String urlAddr2 = null;
//    String urlAddr3 = null;

    ArrayList<User> members;   // 빈, 어댑터
    UserAdapter adapter;



    String macIP;
    String email;

    TextView user_email, user_name, user_pw, user_address, user_addressdetail, user_tel, user_birth;
    String useremail, username, userpw, useraddress, useraddressdetail, usertel, userbirth;
    Button update_btn;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_modify);


        macIP = "192.168.35.251";
        email = "con@naver.com";

        urlAddrBase = "http://" + macIP + ":8080/makeKit/jsp";  // 폴더까지만 지정

        urlAddr1 = urlAddrBase + "user_info_all.jsp?userEmail=" + email;
//        urlAddr3 = urlAddrBase + "user_update.jsp?userEmail=" + user_email;

        connectSelectGetData(urlAddr1);

        user_email = findViewById(R.id.user_email);
        user_name = findViewById(R.id.user_name);
        user_pw = findViewById(R.id.user_pw);
        user_address = findViewById(R.id.user_address);
        user_addressdetail = findViewById(R.id.user_addressdetail);
        user_tel = findViewById(R.id.user_tel);
        user_birth = findViewById(R.id.user_birth);

        //  --------------------------------------------- Select DB에서 받아오기
        String useremail = members.get(0).getEmail();
        user_email.setText(useremail);

        String username = members.get(0).getName();
        user_name.setText(username);

        String userpw = members.get(0).getPw();
        user_pw.setText(userpw);

        String useraddress = members.get(0).getAddress();
        user_address.setText(useraddress);

        String useraddressdetail = members.get(0).getAddressdetail();
        user_addressdetail.setText(useraddressdetail);

        String usertel = members.get(0).getTel();
        user_tel.setText(usertel);

        String userbirth = members.get(0).getBirth();
        user_birth.setText(userbirth);
        //  ---------------------------------------------

        //  ---------------------------------------------  Update



        update_btn = findViewById(R.id.user_update_btn);
        update_btn.setOnClickListener(onClickListener);

        findViewById(R.id.user_birth).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(v);
            }
        });
    }



    @Override
    public void onResume() {
        super.onResume();

        urlAddr1 = urlAddrBase + "user_info_all.jsp?userEmail=" + email;
        connectSelectGetData(urlAddr1);
        Log.v(TAG, "onResume()");


    }

    // NetworkTask에서 값을 가져오는 메소드
    private ArrayList<User> connectSelectGetData(String urlAddr2) {

        try {
            UserNetworkTask userNetworkTask = new UserNetworkTask(UserModifyActivity.this, urlAddr2);
            Object obj = userNetworkTask.execute().get();
            members = (ArrayList<User>) obj;
        } catch (Exception e){
            e.printStackTrace();
        }
        return members;
    }


    // Update
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            useremail = user_email.getText().toString();
            userpw = user_pw.getText().toString();
            username = user_name.getText().toString();
            useraddress = user_address.getText().toString();
            useraddressdetail = user_addressdetail.getText().toString();
            usertel = user_tel.getText().toString();
            userbirth = user_birth.getText().toString();

            updatePeople();


        }
    };

    // people Update data 송부
    private void updatePeople(){
        String urlAddr3 = "";
        urlAddr3 = urlAddrBase + "user_update.jsp?userEmail=" + useremail +"&userPw="+userpw+"&userName="+username+"&userAddress="+useraddress+"&userAddressDetail="+useraddressdetail+"&userTel="+usertel+"&userBirth="+userbirth;
        Log.v(TAG, urlAddr3);

        connectUpdateData(urlAddr3);

    }

    private void connectUpdateData(String urlAddr){
        try{
            CUDNetworkTask updatenetworkTask = new CUDNetworkTask(UserModifyActivity.this, urlAddr);
            updatenetworkTask.execute().get();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void showDatePicker(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(),"datePicker");
    }

    public void processDatePickerResult(int year, int month, int day){
        String month_string = Integer.toString(month+1);
        String day_string = Integer.toString(day);
        String year_string = Integer.toString(year);
        String dateMessage = (month_string + "/" + day_string + "/" + year_string);

        Toast.makeText(this,"Date: "+dateMessage,Toast.LENGTH_SHORT).show();
    }
}
