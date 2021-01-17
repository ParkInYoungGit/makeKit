package com.example.makekit.makekit_activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.makekit.R;
import com.example.makekit.makekit_asynctask.ProductNetworkTask;
import com.example.makekit.makekit_sharVar.SharVar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ProductSalesWriteActivity extends AppCompatActivity {

    // 제품 설명
    EditText product_name, product_price, product_stock, product_content;
    Spinner product_type = null;

    // 제품 이미지 업로드 버튼
    TextView product_thumbnail_btn, product_detail_btn, product_detailsecond_btn;

    //제품 등록 버튼
    Button product_insert_btn;

    // 제품 이미지뷰
    ImageView product_thumbnail, product_detail, product_detailsecond;

    // 아이피, url 설정
    String macIP, urlJsp, urlImage, url, urlAddr, email, urlAddrBase;

    // DB에서 받아오는 변수
    String peopleNo, productInsertResult, phoneInsert, statusInsert, registerInsert;

    String productname, productprice, producttype, productstock, productcontent;

    // 제품 카테고리 목록
    String[] productCategory = {
            "한식", "양식", "일식", "중식", "기타"
    };

    // 사진 올리고 내리기 위한 변수들
    private final int REQ_CODE_SELECT_IMAGE = 100;

    private String img_path = new String();
    private String img_path1 = new String();
    private String img_path2 = new String();
    private String img_path3 = new String();

    private Bitmap image_bitmap_copy = null;
    private Bitmap image_bitmap = null;
    String imageName = null;
    String imageName2 = null;
    String imageName3 = null;
    private String f_ext = null;
    File tempSelectFile;
    int imageCheck=0;
    int check_image=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_product_sales_write);
        ActivityCompat.requestPermissions(ProductSalesWriteActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MODE_PRIVATE);
        macIP = SharVar.macIP;
        email = SharVar.userEmail;
        urlAddrBase = SharVar.urlAddrBase;
        urlJsp = urlAddrBase + "jsp/";  // jsp 폴더
        urlImage = urlAddrBase + "image/";  // image 폴더
        urlAddr = urlJsp+"ProductInsert.jsp?";
        // 사진 연결
        url =   urlJsp +"multipartRequest.jsp";

        // Thread 사용
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .permitDiskReads()
                .permitDiskWrites()
                .permitNetwork().build());

        // ==========================================================   findViewById

        // textView
        product_name = findViewById(R.id.product_name);
        product_price = findViewById(R.id.product_price);
        product_type = findViewById(R.id.product_type);
        product_stock = findViewById(R.id.product_stock);
        product_content = findViewById(R.id.product_content);
        // button
        product_thumbnail_btn = findViewById(R.id.product_thumbnail_btn);
        product_detail_btn = findViewById(R.id.product_detail_btn);
        product_detailsecond_btn = findViewById(R.id.product_detailsecond_btn);
        product_insert_btn = findViewById(R.id.product_insert_btn);

        // image
        product_thumbnail = findViewById(R.id.product_thumbnail);
        product_detail = findViewById(R.id.product_detail);
        product_detailsecond = findViewById(R.id.product_detailsecond);


        // ========================================================== onClickListener

        product_thumbnail_btn.setOnClickListener(onClickListener);
        product_detail_btn.setOnClickListener(onClickListener);
        product_detailsecond_btn.setOnClickListener(onClickListener);
        product_insert_btn.setOnClickListener(onClickListener);

        // 제품 카테고리 스피너 목록 설정
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_dropdown_item, productCategory);
        product_type.setAdapter(adapter);
        product_type.setSelection(0);

    } //------------ End onCreat

    // 이미지 등록 버튼
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            productname = product_name.getText().toString().trim();
            productprice = product_price.getText().toString().trim();
            producttype = product_type.getSelectedItem().toString().trim();
            productstock = product_stock.getText().toString().trim();
            productcontent = product_content.getText().toString().trim();

            Intent intent;

            switch (v.getId()) {
                case R.id.product_thumbnail_btn:       // 썸네일
                    check_image = 1;
                    intent = new Intent(Intent.ACTION_PICK);
                    intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                    intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, REQ_CODE_SELECT_IMAGE);
                    break;

                case R.id.product_detail_btn:          // 제품상세이미지
                    check_image = 2;
                    Intent intent2 = new Intent(Intent.ACTION_PICK);
                    intent2.setType(MediaStore.Images.Media.CONTENT_TYPE);
                    intent2.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent2, REQ_CODE_SELECT_IMAGE);
                    break;
//
                case R.id.product_detailsecond_btn:    // 제품 상세정보
                    check_image = 3;
                    Intent intent3 = new Intent(Intent.ACTION_PICK);
                    intent3.setType(MediaStore.Images.Media.CONTENT_TYPE);
                    intent3.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent3, REQ_CODE_SELECT_IMAGE);
                    break;

                case R.id.product_insert_btn:    // 제품 등록 버튼

                    if (imageCheck == 1) {
                        Log.v("imageCheck", "image :: :: :"+imageCheck);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                doMultiPartRequest();
                            }
                        }).start();
                    }
//                    insertUser(productname, productprice, producttype, productstock, productcontent, productthumbnail, productdetail, productdetailsecond);
                    urlAddr = urlAddr + "name=" + productname + "&price=" + productprice + "&type=" + producttype + "&stock=" + productstock + "&content=" + productcontent + "&thumnail=" + imageName + "&detail=" + imageName2 + "&detailsecond=" + imageName3;
                    connectInsertData();

                    Intent intentMain = new Intent(ProductSalesWriteActivity.this, MainActivity.class);
                    startActivity(intentMain);
                    finish();
                    break;

                default:
            }

        }
    };

    // 제품 등록 데이터 전달
    private String connectInsertData() {
        try {
            ProductNetworkTask insnetworkTask = new ProductNetworkTask(ProductSalesWriteActivity.this, urlAddr, "");
            Object object = insnetworkTask.execute().get();
            productInsertResult = (String) object;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productInsertResult;
    }

    // 이미지 처리 해주는 이벤트
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


                    // 파일 이름 및 경로 바꾸기(임시 저장)
                    String date = new SimpleDateFormat("yyyyMMddHmsS").format(new Date());

                    if (check_image == 1) {
                        product_thumbnail.setImageBitmap(image_bitmap_copy);
                        imageName = date+"."+f_ext;
                        tempSelectFile = new File("/data/data/com.example.makekit/", imageName);    // 경로는 자기가 원하는 곳으로 바꿀 수 있음
                        img_path1 = "/data/data/com.example.makekit/"+imageName;
                    } else if (check_image == 2) {
                        product_detail.setImageBitmap(image_bitmap_copy);
                        imageName2 = date+"detail."+f_ext;
                        tempSelectFile = new File("/data/data/com.example.makekit/", imageName2);       // 경로는 자기가 원하는 곳으로 바꿀 수 있음
                        img_path2 = "/data/data/com.example.makekit/"+imageName2;
                    } else if (check_image == 3) {
                        product_detailsecond.setImageBitmap(image_bitmap_copy);
                        imageName3 = date+"detailsecond."+f_ext;
                        tempSelectFile = new File("/data/data/com.example.makekit/", imageName3);       // 경로는 자기가 원하는 곳으로 바꿀 수 있음
                        img_path3 = "/data/data/com.example.makekit/"+imageName3;
                    }


                     OutputStream out = new FileOutputStream(tempSelectFile);
                    image_bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);                        // 지정 경로로 임시 파일 보내기


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    // 이미지 경로 저장
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

    //  이미지 파일 변환
    private void doMultiPartRequest() {

        ArrayList<String> file = new ArrayList<String>();
        file.add(img_path1);
        file.add(img_path2);
        file.add(img_path3);
        for (int i =0; i<3; i++){
            File f = new File(file.get(i).toString());
            Log.v("doMultiPartRequest", "file "+f);
            DoActualRequest(f);
        }
    }

    //  이미지 서버 보내기
    private void DoActualRequest(File file) {
        Log.v("DoActualRequest", "DoActualRequest() in ");
        Log.v("DoActualRequest", "file1()" + file);


        OkHttpClient client = new OkHttpClient();
        Log.v("DoActualRequest", "client() in ");


        // body 구성
        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", file.getName(),
                        RequestBody.create(MediaType.parse("image/*"), file))
                .build();
                Log.v("DoActualRequest", "body1() in ");


        // 서버에 요청
        Request request = new Request.Builder()
                .url(url)
                .post(body)

                .build();
                Log.v("urlllll","urslllll" + url);

        try {
            Response response1 = client.newCall(request).execute();

        } catch (IOException e) {
            e.printStackTrace();
        }
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

} // ==============================