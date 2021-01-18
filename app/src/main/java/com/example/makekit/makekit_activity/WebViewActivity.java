package com.example.makekit.makekit_activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.makekit.R;

public class WebViewActivity extends AppCompatActivity {
    private String macIP;
    private WebView browser;
    private Handler handler;
    TextView textView;

    class MyJavaScriptInterface
    {
        @JavascriptInterface
        @SuppressWarnings("unused")
        public void processDATA(String data) {
            Bundle extra = new Bundle();
            Intent intent = new Intent();
            extra.putString("data", data);
            intent.putExtras(extra);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_web_view);

        // WebView 초기화
        init_webView();


        Intent intent = getIntent();
        macIP = intent.getStringExtra("macIP");


        browser = (WebView) findViewById(R.id.webView);
        browser.getSettings().setJavaScriptEnabled(true);
        browser.addJavascriptInterface(new MyJavaScriptInterface(), "Android");

        browser.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                browser.loadUrl("javascript:sample2_execDaumPostcode();");
            }
        });

//        macIP = "192.168.35.133";
        browser.loadUrl("http://"+macIP+":8080/makeKit/jsp/daum.html");


        // 핸들러를 통한 JavaScript 이벤트 반응
        handler = new Handler();

    } //onCreate End


    public void init_webView() {

        // WebView 설정
        browser = (WebView) findViewById(R.id.webView);

        // JavaScript 허용
        browser.getSettings().setJavaScriptEnabled(true);

        // JavaScript의 window.open 허용
        browser.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        // JavaScript이벤트에 대응할 함수를 정의 한 클래스를 붙여줌
        browser.addJavascriptInterface(new AndroidBridge(), "Android");


        // web client 를 chrome 으로 설정
        browser.setWebChromeClient(new WebChromeClient());


    }


    private class AndroidBridge {

        @JavascriptInterface

        public void setAddress(final String arg1, final String arg2, final String arg3) {

            handler.post(new Runnable() {

                @Override

                public void run() {

                    textView.setText(String.format("(%s) %s %s", arg1, arg2, arg3));

                    // WebView를 초기화 하지않으면 재사용할 수 없음

                    init_webView();

                }

            });

        }

    }
}