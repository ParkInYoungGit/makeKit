package com.example.makekit.makekit_activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.makekit.R;
import com.example.makekit.makekit_asynctask.ReviewNetworkTask;
import com.example.makekit.makekit_fragment.ReviewListFragment;
import com.example.makekit.makekit_sharVar.SharVar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterReviewActivity extends AppCompatActivity {

    final static String TAG = "ReviewActivity";

    RatingBar ratingBar_star;
    EditText input_review;
    String macIP, email, urlJsp, url, urlRegister, urlAddrBase;
    String orderDetailNo, productNo;
    String strRating = null;
    ImageView camera;
    Button btn_register_review;

    // Check 변수
    int reviewInsertResult = 0;
    int addReviewCheck = 0;
    int imageCheck=0;

    // 입력 받는 변수
    String strReview=null;

    // DB에서 받아오는 변수
    String registerInsertResult;

    // 사진 올리고 내리기 위한 변수들
    private final int REQ_CODE_SELECT_IMAGE = 100;
    private String img_path = new String();
    private Bitmap image_bitmap_copy = null;
    private Bitmap image_bitmap = null;
    String imageName = null;
    private String f_ext = null;
    File tempSelectFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register_review);

        // 쉐어 변수 값 받아오기
//        SharedPreferences sf = getSharedPreferences("appData", MODE_PRIVATE);
//        macIP = sf.getString("macIP","");
//        email = sf.getString("useremail","");

        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        macIP = intent.getStringExtra("macIP");
        orderDetailNo = intent.getStringExtra("orderDetailNo");
        productNo = intent.getStringExtra("productNo");


        urlAddrBase = SharVar.urlAddrBase;
        // url
        urlJsp = urlAddrBase + "jsp/";
        url = urlAddrBase + "jsp/multipartRequest.jsp";
        urlRegister = urlAddrBase + "jsp/register_Review.jsp?";

        // 화면 구성
        ratingBar_star = findViewById(R.id.review_ratingBar);
        input_review = findViewById(R.id.input_WriteReview);
        camera = findViewById(R.id.review_photo);
        btn_register_review = findViewById(R.id.btn_registerReview);

        // 클릭 리스너
        camera.setOnClickListener(mClickListener);
        btn_register_review.setOnClickListener(mClickListener);

        // rating 점수
        ratingBar_star.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                float ratingStar = ratingBar_star.getRating();
                strRating = Float.toString(ratingStar);
            }
        });


    } // onCreate End=========================================================================

    View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // 화면의 값 받아오기
            strReview = input_review.getText().toString();

            switch (v.getId()) {
                case R.id.review_photo: // 사진 등록
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                    intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, REQ_CODE_SELECT_IMAGE);
                    break;

                case R.id.btn_registerReview: // 리뷰 등록

                    Log.v(TAG, "registerButton");
                    // 순서 1. 네트워크 연결 및 이미지 서버에 전송, 이미지 이름 저장
                    if(imageCheck==1){          // 겔러리 버튼 클릭했는지 확인
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                doMultiPartRequest();
                            }       // 서버에 올림
                        }).start();
                    }
                    Log.v(TAG, "image Name : "+ imageName);

                    // 순서 2. DB와 연결(NetworkTask)해서 정보 insert
                    urlRegister = urlRegister + "orderStar="+ strRating +"&orderReview="+ strReview+ "&reviewImg=" + imageName+ "&useremail="+ email + "&orderDetailNo=" + orderDetailNo + "&productNo=" + productNo;
                    connectInsertData(urlRegister);

                    // 입력이 제대로 됐는지 확인
                    if(registerInsertResult.equals("1")){
                        Toast.makeText(RegisterReviewActivity.this, "리뷰 등록이 완료 되었습니다.", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(RegisterReviewActivity.this, "리뷰 등록이 실패하였습니다. \n관리자에게 문의하세요.", Toast.LENGTH_SHORT).show();
                    }

                    // 리스트로 돌아가기
                    intent = new Intent(RegisterReviewActivity.this, ReviewListActivity.class);
                    intent.putExtra("macIP",macIP);
                    intent.putExtra("email",email);
                    startActivity(intent);
                    finish();
                    break;
            }
        }
    };



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // 겔러리 가기
        if (requestCode == REQ_CODE_SELECT_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {                     // 겔러리에 들어왔는지 확인
                try {
                    imageCheck=1;                                       // 겔러리에 들어왔는지 자바에서 확인하기 위한 변수
                    img_path = getImagePathToUri(data.getData());       //이미지의 URI를 얻어 경로값으로 반환.(method 확인 필요)

                    //이미지를 비트맵형식으로 반환
                    image_bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());

                    //image_bitmap 으로 받아온 이미지의 사이즈를 임의적으로 조절함. width: 400 , height: 300
                    image_bitmap_copy = Bitmap.createScaledBitmap(image_bitmap, 400, 300, true);
//                    registerImage.setImageBitmap(image_bitmap_copy);

                    // 파일 이름 및 경로 바꾸기(임시 저장)
                    String date = new SimpleDateFormat("yyyyMMddHmsS").format(new Date());
                    imageName = date+"."+f_ext;
                    tempSelectFile = new File("/data/data/com.example.makekit/", imageName);       // 경로는 자기가 원하는 곳으로 바꿀 수 있음
                    OutputStream out = new FileOutputStream(tempSelectFile);
                    image_bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);                        // 지정 경로로 임시 파일 보내기

                    // 임시 파일 경로로 위의 img_path 재정의
                    img_path = "/data/data/com.example.makekit/"+imageName;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public String getImagePathToUri(Uri data) {
        //사용자가 선택한 이미지의 정보를 받아옴
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(data, proj, null, null, null);             // 무엇을 선택했는지
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();

        //이미지의 경로 값
        String imgPath = cursor.getString(column_index);

        // 확장자 명 저장
        f_ext = imgPath.substring(imgPath.length()-3, imgPath.length());

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
        // body 구성
        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", file.getName(),
                        RequestBody.create(MediaType.parse("image/*"), file))
                .build();
        // 서버에 요청
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


    // DB와 연결하기 위한 method들
    private String connectInsertData(String urlAddr) {
        try {
            ReviewNetworkTask insnetworkTask = new ReviewNetworkTask(RegisterReviewActivity.this, urlAddr, "registerReview");
            Object object = insnetworkTask.execute().get();
            registerInsertResult = (String) object;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return registerInsertResult;
    }

//    private void connectInsertData(String urlAddr) {
//        try {
//            ReviewNetworkTask insnetworkTask = new ReviewNetworkTask(RegisterReviewActivity.this, urlAddr, "registerReview");
//            Object object = insnetworkTask.execute().get();
//            // registerInsertResult = (String) object;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

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



} // END =========================================================================