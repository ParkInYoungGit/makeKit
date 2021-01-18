package com.example.makekit.makekit_activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import com.example.makekit.makekit_fragment.DatePickerFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import android.widget.TextView;
import android.widget.Toast;

import com.example.makekit.R;
import com.example.makekit.makekit_adapter.UserAdapter;
import com.example.makekit.makekit_asynctask.CUDNetworkTask;
import com.example.makekit.makekit_asynctask.UserNetworkTask;
import com.example.makekit.makekit_bean.User;
import com.example.makekit.makekit_sharVar.SharVar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UserModifyActivity extends AppCompatActivity {

    final static String TAG = "First";

    private static final int SEARCH_ADDRESS_ACTIVITY = 10000;
    public static final String pattern1 = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,20}$"; // 영문, 숫자, 특수문자
    public static final String pattern2 = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$";

    private int _beforeLenght = 0;
    private int _afterLenght = 0;
    int imageCheck = 0;

    // 사진 올리고 내리기 위한 변수들
    private final int REQ_CODE_SELECT_IMAGE = 100;
    private String img_path = new String();
    private Bitmap image_bitmap_copy = null;
    private Bitmap image_bitmap = null;
    String imageName = null;
    private String f_ext = null;
    File tempSelectFile;

    int pwcheck;
    String url;

    String urlAddrBase = null;
    String urlAddr1 = null;
    String urlJsp = null;
//    String urlAddr3 = null;
//    String urlAddr3 = null;

    ArrayList<User> members;   // 빈, 어댑터
    UserAdapter adapter;


    String macIP;
    String email;
    String urlImage;
    String urlImage1;
    Matcher match;

    EditText user_pwcheck, user_tel, user_addressdetail;
    TextView user_email, user_pw, user_address, user_birth, tv_pwCheckMsg_user, currentPW, user_name;
    String useremail, username, userpw, useraddress, useraddressdetail, usertel, userbirth, userimage;
    Button update_btn;
    TextView fieldCheck;
    WebView user_image;
    ImageView user_image1;
    TextView tv_editPeopleImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_modify);

        // Thread 사용
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .permitDiskReads()
                .permitDiskWrites()
                .permitNetwork().build());

        // 아이피와 이메일 받기
        Intent intent = getIntent();
        macIP = SharVar.macIP;
        email = SharVar.userEmail;
        urlAddrBase = SharVar.urlAddrBase;


//        macIP = "192.168.2.2";
//        email = "son@naver.com";

        // jsp 사용할 폴더 지정
        urlAddr1 = urlAddrBase + "jsp/user_info_all.jsp?email=" + email;
        urlJsp = urlAddrBase + "jsp/";
        url =   urlJsp +"multipartRequest.jsp";

        // ========================================== 이메일 + 아이디값 셀렉트에 보내주기
        connectSelectGetData(urlAddr1);   // urlAddr1을  connectSelectGetData의 urlAddr2로 보내준다
        urlImage = urlAddrBase + "image/" + members.get(0).getImage();


        // ========================================== 텍스트뷰 가져오기
        user_image = findViewById(R.id.user_image);
        user_image1 = findViewById(R.id.user_image1);
        user_email = findViewById(R.id.user_email);
        user_name = findViewById(R.id.user_name);
        user_pw = findViewById(R.id.user_pw);
        user_address = findViewById(R.id.user_address);
        user_addressdetail = findViewById(R.id.user_addressdetail);
        user_tel = findViewById(R.id.user_tel);
        user_birth = findViewById(R.id.user_birth);
        tv_editPeopleImage = findViewById(R.id.tv_editPeopleImage);
        fieldCheck = findViewById(R.id.tv_fieldCheck_findId);
//

        user_pwcheck = findViewById(R.id.user_pwcheck);
        tv_pwCheckMsg_user = findViewById(R.id.tv_pwCheckMsg_user);

        //  --------------------------------------------- Select DB에서 받아오기

        String userimage = members.get(0).getImage();
        if (members.get(0).getImage().equals("null")) {
            urlImage = urlAddrBase + "image/ic_defaultpeople.jpg";
            user_image.loadUrl(urlImage);
        } else {

            // Initial webview
            user_image.setWebViewClient(new WebViewClient());

            // Enable JavaScript
            user_image.getSettings().setJavaScriptEnabled(true);
            user_image.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

            // WebView 세팅
            WebSettings webSettings = user_image.getSettings();
            webSettings.setUseWideViewPort(true);       // wide viewport를 사용하도록 설정
            webSettings.setLoadWithOverviewMode(true);  // 컨텐츠가 웹뷰보다 클 경우 스크린 크기에 맞게 조정
            //iv_viewPeople.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

            user_image.setBackgroundColor(0); //배경색
            user_image.setBackgroundResource(R.drawable.layout_outline);
            user_image.setHorizontalScrollBarEnabled(false); //가로 스크롤
            user_image.setVerticalScrollBarEnabled(false);   //세로 스크롤
            user_image.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY); // 스크롤 노출 타입
            user_image.setScrollbarFadingEnabled(false);
            user_image.setInitialScale(10);

            // 웹뷰 멀티 터치 가능하게 (줌기능)
            webSettings.setBuiltInZoomControls(false);   // 줌 아이콘 사용
            webSettings.setSupportZoom(false);

            user_image.loadUrl(urlImage); // 접속 URL


        }

//        if (members.get(0).getImage() == null) {
////            urlAddr1 = urlAddr + "people_query_all.jsp?peopleimage=" + peopleimage;
////            String result = connectCheckData(urlAddr1);
//            urlImage = urlImage+"ic_defaultpeople.jpg";
//            user_image.loadUrl(urlImage);
//            user_image.setWebChromeClient(new WebChromeClient());//웹뷰에 크롬 사용 허용//이 부분이 없으면 크롬에서 alert가 뜨지 않음
//            user_image.setWebViewClient(new ViewPeopleActivity.WebViewClientClass());//새창열기 없이 웹뷰 내에서 다시 열기//페이지 이동 원활히 하기위해 사용
//
////        } else if(peopleimage.length() != 0) {
//            // } else if(peopleimage.equals("!=null")) {
//        } else if(members.get(0).getImage() != null) {
////            urlAddr1 = urlAddr + "people_query_all.jsp?peopleimage=" + peopleimage;
////            String result = connectCheckData(urlAddr1);
//            urlImage = urlImage + members.get(0).getImage();
//            user_image.loadUrl(urlImage);
//            user_image.setWebChromeClient(new WebChromeClient());//웹뷰에 크롬 사용 허용//이 부분이 없으면 크롬에서 alert가 뜨지 않음
//            user_image.setWebViewClient(new ViewPeopleActivity.WebViewClientClass());//새창열기 없이 웹뷰 내에서 다시 열기//페이지 이동 원활히 하기위해 사용
//        }
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
        currentPW = user_pw;
        user_pw.addTextChangedListener(changeListener_pw);
        user_pwcheck.addTextChangedListener(changeListener_pwcheck);
        user_tel.addTextChangedListener(changeListener_tel);
        user_name.addTextChangedListener(changeListener_name);

        //  ---------------------------------------------  Update


        update_btn = findViewById(R.id.user_update_btn);
        update_btn.setOnClickListener(onClickListener);
        tv_editPeopleImage.setOnClickListener(onClickListener);

        findViewById(R.id.user_birth_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(v);
            }
        });

        user_address = findViewById(R.id.user_address);

        Button btn_update_user = (Button) findViewById(R.id.userModiAddress_button);

        if (btn_update_user != null) {
            btn_update_user.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(UserModifyActivity.this, WebViewActivity.class);
                    i.putExtra("macIP", macIP);
                    startActivityForResult(i, SEARCH_ADDRESS_ACTIVITY);
                }
            });
        }

//        TextInputLayout inputLayoutPW = findViewById(R.id.InputLayoutPw_join);
//        TextInputLayout inputLayoutPWCheck = findViewById(R.id.InputLayoutPwCheck_join);
//
//        inputLayoutPW.setPasswordVisibilityToggleEnabled(true);
//        inputLayoutPWCheck.setPasswordVisibilityToggleEnabled(true);

    }//============================


    @Override
    public void onResume() {
        super.onResume();
        // Select
        urlAddr1 = urlAddrBase + "jsp/user_info_all.jsp?email=" + email;
        connectSelectGetData(urlAddr1);
        Log.v(TAG, "onResume()");
    }

    // NetworkTask에서 값을 가져오는 메소드 (Select)  String urlAddr2는 urlAddr1을 받아서 UserNetworkTask로 보내준다
    private ArrayList<User> connectSelectGetData(String urlAddr2) {

        try {
            UserNetworkTask userNetworkTask = new UserNetworkTask(UserModifyActivity.this, urlAddr2, "selectUser");
            Object obj = userNetworkTask.execute().get();
            members = (ArrayList<User>) obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return members;
    }


    // Update 수정버튼
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent;
            switch (v.getId()) {
                case R.id.user_update_btn:
                    if (imageCheck == 1) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                doMultiPartRequest();
                            }
                        }).start();
                    }
                    Log.v(TAG, "image Name : " + imageName);
                    useremail = user_email.getText().toString();
                    userpw = user_pw.getText().toString();
                    username = user_name.getText().toString();
                    useraddress = user_address.getText().toString();
                    useraddressdetail = user_addressdetail.getText().toString();
                    usertel = user_tel.getText().toString();
                    userbirth = user_birth.getText().toString();
                    if (imageCheck == 1) {
                        userimage = imageName;
                    }

                    updatePeople();
                    checkField();

//                    userInfoCheck();

                    break;

                case R.id.tv_editPeopleImage:


                    intent = new Intent(Intent.ACTION_PICK);
                    intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                    intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, REQ_CODE_SELECT_IMAGE);
                    user_image.setVisibility(View.GONE);
                    user_image1.setVisibility(View.VISIBLE);

                    break;


            }
        }
    };

    // people Update data 송부
    private void updatePeople() {
        String urlAddr3 = "";
        urlAddr3 = urlAddrBase + "jsp/user_update.jsp?userEmail=" + useremail + "&userPw=" + userpw + "&userName=" + username + "&userAddress=" + useraddress + "&userAddressDetail=" + useraddressdetail + "&userTel=" + usertel + "&userBirth=" + userbirth + "&userImage=" + userimage;
        Log.v(TAG, urlAddr3);

        connectUpdateData(urlAddr3);

    }

    private void connectUpdateData(String urlAddr) {
        try {
            CUDNetworkTask updatenetworkTask = new CUDNetworkTask(UserModifyActivity.this, urlAddr);
            updatenetworkTask.execute().get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void showDatePicker(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void processDatePickerResult(int year, int month, int day) {
        String month_string = Integer.toString(month + 1);
        String day_string = Integer.toString(day);
        String year_string = Integer.toString(year);
        String dateMessage = (month_string + "/" + day_string + "/" + year_string);

        TextView birth = findViewById(R.id.user_birth);

        birth.setText(dateMessage);

    }


    // 이메일 주소 찾기

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 10000:

                    String data = intent.getExtras().getString("data");
                    if (data != null) {
                        user_address.setText(data);
                    }
                    break;

                case 100:

                    try {
//                        urlImage = urlAddrBase + "image/";
//
//                        img_path = getImagePathToUri(intent.getData()); //이미지의 URI를 얻어 경로값으로 반환.
//                        user_image.loadUrl(urlImage);
//                        Toast.makeText(getBaseContext(), "urlImage1 : " + urlImage, Toast.LENGTH_SHORT).show();
//                        Log.v("test", String.valueOf(intent.getData()));
//                        // Initial webview
//
//                        // Initial webview
//                        user_image.setWebViewClient(new WebViewClient());
//
//                        // Enable JavaScript
//                        user_image.getSettings().setJavaScriptEnabled(true);
//                        user_image.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//
//                        // WebView 세팅
//                        WebSettings webSettings = user_image.getSettings();
//                        webSettings.setUseWideViewPort(true);       // wide viewport를 사용하도록 설정
//                        webSettings.setLoadWithOverviewMode(true);  // 컨텐츠가 웹뷰보다 클 경우 스크린 크기에 맞게 조정
//                        //iv_viewPeople.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//
//                        user_image.setBackgroundColor(0); //배경색
//                        user_image.setBackgroundResource(R.drawable.layout_outline);
//                        user_image.setHorizontalScrollBarEnabled(false); //가로 스크롤
//                        user_image.setVerticalScrollBarEnabled(false);   //세로 스크롤
//                        user_image.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY); // 스크롤 노출 타입
//                        user_image.setScrollbarFadingEnabled(false);
//                        user_image.setInitialScale(10);
//
//                        // 웹뷰 멀티 터치 가능하게 (줌기능)
//                        webSettings.setBuiltInZoomControls(false);   // 줌 아이콘 사용
//                        webSettings.setSupportZoom(false);
//
//                        user_image.loadUrl(urlImage);
//
//                        Log.v("here", "urlImage1 : " + urlImage);// 접속 URL
////                    imageCheck=1;
////                    img_path = getImagePathToUri(intent.getData()); //이미지의 URI를 얻어 경로값으로 반환.
////                    Toast.makeText(getBaseContext(), "img_path : " + img_path, Toast.LENGTH_SHORT).show();
////                    Log.v("test", String.valueOf(intent.getData()));
////                    //이미지를 비트맵형식으로 반환
////                    image_bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), intent.getData());
////
////                    //image_bitmap 으로 받아온 이미지의 사이즈를 임의적으로 조절함. width: 400 , height: 300
////                    image_bitmap_copy = Bitmap.createScaledBitmap(image_bitmap, 400, 300, true);
//                        //editImage.setImageBitmap(image_bitmap_copy);
//
//                        // 파일 이름 및 경로 바꾸기(임시 저장)
//                        String date = new SimpleDateFormat("yyyyMMddHmsS").format(new Date());
//                        imageName = date + "." + f_ext;
//                        tempSelectFile = new File("/data/data/com.example.makekit/", imageName);
//                        OutputStream out = new FileOutputStream(tempSelectFile);
//                        image_bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
//
//                        // 임시 파일 경로로 위의 img_path 재정의
//                        img_path = "/data/data/com.example.makekit/" + imageName;
//
                        imageCheck=1;
                        img_path = getImagePathToUri(intent.getData()); //이미지의 URI를 얻어 경로값으로 반환.
                        Toast.makeText(getBaseContext(), "img_path : " + img_path, Toast.LENGTH_SHORT).show();
                        Log.v("test", String.valueOf(intent.getData()));
                        //이미지를 비트맵형식으로 반환
                        image_bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), intent.getData());

                        //image_bitmap 으로 받아온 이미지의 사이즈를 임의적으로 조절함. width: 400 , height: 300
                        image_bitmap_copy = Bitmap.createScaledBitmap(image_bitmap, 400, 300, true);
                        //editImage.setImageBitmap(image_bitmap_copy);

                        // 파일 이름 및 경로 바꾸기(임시 저장)
                        String date = new SimpleDateFormat("yyyyMMddHmsS").format(new Date());


                            user_image1.setImageBitmap(image_bitmap_copy);
                            imageName = date+"."+f_ext;
                            tempSelectFile = new File("/data/data/com.example.makekit/", imageName);    // 경로는 자기가 원하는 곳으로 바꿀 수 있음
                            img_path = "/data/data/com.example.makekit/"+imageName;



                        OutputStream out = new FileOutputStream(tempSelectFile);
                        image_bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);                        // 지정 경로로 임시 파일 보내기

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
        }
        super.onActivityResult(requestCode, resultCode, intent);
    }

    // 화면 touch 시 키보드 숨기기
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View focusView = getCurrentFocus();
        if (focusView != null) {
            Rect rect = new Rect();
            focusView.getGlobalVisibleRect(rect);
            int x = (int) ev.getX(), y = (int) ev.getY();
            if (!rect.contains(x, y)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                if (imm != null)
                    imm.hideSoftInputFromWindow(focusView.getWindowToken(), 0);
                focusView.clearFocus();
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    // pw 입력란 text 변경 시 listener
    TextWatcher changeListener_pw = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            // pw 입력 시
            String pwCheck = user_pw.getText().toString().trim();
            Boolean check = pwdRegularExpressionChk(pwCheck);

            if (pwCheck.length() == 0) {
                user_pw.setError(null);

            } else {
                if (check == false) {
                    user_pw.setError("비밀번호는 영문, 특수문자 포함하여 최소 8자 이상 입력해주세요.");
                }
            }
        }
    };

    // pw 입력란 text 변경 시 listener
    TextWatcher changeListener_pwcheck = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            // pwcheck 입력 시 일치 여부 message
            if (user_pwcheck.getText().toString().trim().length() != 0) {
                if ((user_pwcheck.getText().toString().trim()).equals(user_pw.getText().toString().trim())) {
                    tv_pwCheckMsg_user.setTextColor(getResources().getColor(R.color.black));
                    tv_pwCheckMsg_user.setText("비밀번호 일치");

                } else {
                    tv_pwCheckMsg_user.setTextColor(getResources().getColor(R.color.red));
                    tv_pwCheckMsg_user.setText("비밀번호 불일치");
                }
            }
        }
    };

    // 비밀번호 영/숫/특 포함 설정
    public boolean pwdRegularExpressionChk(String newPwd) {
        boolean chk = false;  // 특수문자, 영문, 숫자 조합 (8~10 자리)
        match = Pattern.compile(pattern1).matcher(newPwd);
        if (match.find()) {
            chk = true;
        }
        return chk;
    }

    // 입력란 field check
    private void checkField() {
        String userPW = user_pw.getText().toString().trim();
        String userPWCheck = user_pwcheck.getText().toString().trim();
        tv_pwCheckMsg_user.setText("");

        if (userPW.length() == 0) {
            tv_pwCheckMsg_user.setText("새로운 비밀번호를 입력해주세요.");

        } else if (userPW.length() != 0) {
            if (user_pwcheck.equals(userPW)) {
                tv_pwCheckMsg_user.setText("기존 비밀번호와 동일합니다.");

            } else {
                Boolean check = pwdRegularExpressionChk(userPW);

                if (check == false) {
                    tv_pwCheckMsg_user.setText("비밀번호를 영문, 특수문자 포함하여 \n최소 8자 이상 입력해주세요.");

                } else {
                    if (userPWCheck.length() == 0) {
                        tv_pwCheckMsg_user.setText("새로운 비밀번호 확인을 입력해주세요.");

                    } else if ((user_pwcheck.getText().toString().trim()).equals(user_pw.getText().toString().trim())) {
//                        updateUser(userPW);
                        Intent intent1 = new Intent(UserModifyActivity.this, MainActivity.class);
                        startActivity(intent1);

                    } else {
                        tv_pwCheckMsg_user.setText("비밀번호가 일치하지 않습니다. \n다시 확인해주세요.");
                        user_pwcheck.setText("");
                        Toast.makeText(UserModifyActivity.this, "비밀번호가 일치하지 않습니다. \n다시 확인해주세요.", Toast.LENGTH_SHORT).show();

                    }
                }
            }

        }


    }

    // name text
    TextWatcher changeListener_name = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    // phone text
    TextWatcher changeListener_tel = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            _beforeLenght = s.length();
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //fieldCheck.setText("");
            _afterLenght = s.length();
            // 삭제 중
            if (_beforeLenght > _afterLenght) {
                // 삭제 중에 마지막에 -는 자동으로 지우기
                if (s.toString().endsWith("-")) {
                    user_tel.setText(s.toString().substring(0, s.length() - 1));
                }
            }
            // 입력 중
            else if (_beforeLenght < _afterLenght) {
                if (_afterLenght == 4 && s.toString().indexOf("-") < 0) {
                    user_tel.setText(s.toString().subSequence(0, 3) + "-" + s.toString().substring(3, s.length()));
                } else if (_afterLenght == 9) {
                    user_tel.setText(s.toString().subSequence(0, 8) + "-" + s.toString().substring(8, s.length()));
                } else if (_afterLenght == 14) {
                    user_tel.setText(s.toString().subSequence(0, 13) + "-" + s.toString().substring(13, s.length()));
                }
            }
            user_tel.setSelection(user_tel.length());

        }

        @Override
        public void afterTextChanged(Editable s) {
            String phoneCheck = user_tel.getText().toString().trim();
            boolean flag = Pattern.matches(pattern2, phoneCheck);

            if (phoneCheck.length() == 0) {
                user_tel.setError(null);
            } else {
                if (flag == false) {
                    user_tel.setError("휴대폰 번호를 다시 입력해주세요.");
                }
            }
        }
    };


    public String getImagePathToUri(Uri data) {
        //사용자가 선택한 이미지의 정보를 받아옴
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(data, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();

        //이미지의 경로 값
        String imgPath = cursor.getString(column_index);
        Log.d("test", imgPath);

        //이미지의 이름 값
        String imgName = imgPath.substring(imgPath.lastIndexOf("/") + 1);

        // 확장자 명 저장
        f_ext = imgPath.substring(imgPath.length()-3, imgPath.length());
//        this.imageName = imgName;

        return imgPath;
    }//end of getImagePathToUri()
    //파일 변환
    private void doMultiPartRequest() {

        File f = new File(img_path);

        DoActualRequest(f);
    }

    //서버 보내기
    private void DoActualRequest(File file) {
        OkHttpClient client = new OkHttpClient();

        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", file.getName(),
                        RequestBody.create(MediaType.parse("image/*"), file))
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // user 정보 확인
//    private void userInfoCheck() {
//
//        String userName = user_name.getText().toString().trim();
//        String userPhone = user_tel.getText().toString().trim();
//
//        if (userName.length() == 0) {
//            fieldCheck.setText("이름을 입력해주세요");
//            user_name.setFocusableInTouchMode(true);
//            user_name.requestFocus();
//
//        } else if (userPhone.length() == 0) {
//            fieldCheck.setText("휴대폰 번호을 입력해주세요");
//            user_tel.setFocusableInTouchMode(true);
//            user_tel.requestFocus();
//
//        }
//
//    }
//    // user pw 수 data 송부
//    private void updateUser(String userPW) {
//        String urlAddr1 = "";
//        urlAddr1 = urlAddr + "&pw=" + userPW;
//
//        Log.v(TAG, urlAddr1);
//        String result = connectUpdateData(urlAddr1);
//
//        if (result.equals("1")) {
//            Toast.makeText(MypagePWActivity02.this, "비밀번호 변경이 완료되었습니다.", Toast.LENGTH_SHORT).show();
//            finish();
//
//        } else {
//            Toast.makeText(MypagePWActivity02.this, "비밀번호 변경에 실패하였습니다.", Toast.LENGTH_SHORT).show();
//
//        }
//    }
}

