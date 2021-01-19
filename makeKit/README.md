# makeKit

## 안드로이드 스튜디오를 이용한 쇼핑 어플

제작자 : 유민규, 송예진, 박인영, 박경미, 김대환

---

## G-Email 인증 시 설정

1.  libs 폴더에 아래 3개 jar 파일 추가

    - activation.jar
    - additionnal.jar
    - mail.jar
      -> Gmail 라이브러리 폴더에서 다운로드

2.  FindPWActivity에서 사용할 Gmail 및 비밀번호 기입
3.  사용할 Gmail 내 보안 > 보안 수준이 낮은 앱의 액세스 허용 설정

---

## build.gradle에 필요한 라이브러리

dependencies {

    implementation files('lib\\slider.jar')

    implementation 'androidx.recyclerview:recyclerview:1.0.0'
    implementation 'androidx.appcompat:appcompat:1.0.0'
    implementation 'androidx.coordinatorlayout:coordinatorlayout:1.1.0'
    implementation 'com.android.support:design:30.0.0'

    testImplementation 'junit:junit:4.+'
    androidTestImplementation 'androidx.test.ext:junit:1.1.2'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.3.0'
    implementation 'me.relex:circleindicator:2.1.4'
    implementation 'androidx.recyclerview:recyclerview:1.1.0'
    implementation 'com.google.android.material:material:1.0.0'
    implementation 'androidx.cardview:cardview:1.0.0'

    //사진을 서버에 올리기 위한 라이브러리
    implementation 'com.squareup.okhttp3:okhttp:4.10.0-RC1'


    // 구글 API
    implementation 'com.google.android.gms:play-services-maps:17.0.0'
    implementation 'com.google.android.gms:play-services-location:17.1.0'

    // 현 위치 가져오기
    implementation 'com.google.android.material:material:1.3.0-alpha02'

    // 주변
    implementation 'noman.placesapi:placesAPI:1.1.3'
    implementation 'com.google.maps.android:android-maps-utils:0.5'

    // Ria _ 카카오
    implementation "com.kakao.sdk:v2-user:2.2.0" // 카카오 로그인
    implementation "com.kakao.sdk:v2-talk:2.2.0" // 친구, 메시지(카카오톡)
    implementation "com.kakao.sdk:v2-link:2.2.0" // 메시지(카카오링크)
    implementation 'com.github.bumptech.glide:glide:4.11.0' // 프로필 이미지 핸들링

    // 구글 로그인
    implementation 'com.google.android.gms:play-services-auth:19.0.0'
    implementation platform('com.google.firebase:firebase-bom:26.2.0')
    implementation 'com.google.firebase:firebase-analytics'
    implementation 'androidx.multidex:multidex:2.0.1'
    implementation 'com.google.firebase:firebase-auth:19.0.0'

    // 이미지 압축
    implementation 'id.zelory:compressor:2.1.0'

    // 옵셥 팝업 띄우기
    implementation 'com.sothree.slidinguppanel:library:3.4.0'

}

---

## Manifest에 필요한 권한들

    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.RECEIVE_SMS" />
    <uses-permission android:name="android.permission.SEND_SMS" />
    <uses-permission android:name="android.permission.READ_PHONE_STATE" />
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
    <uses-permission android:name="android.permission.GET_ACCOUNTS" />

    <!-- 현 위치 가져오기 -->
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAG" />
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />

## Ip 변경

안드로이드 스튜디오를 통해 makeKit을 실행시키면 app/java/com.example/makekit_sharVar/SharVar

## Tomcat을 연결하기 위한 XML

안드로이드 스튜디오를 통해 makekit을 실행시키면 app/res/xml/network_security_config.jsp 를 통해 Tomcat과 연결을 할 수 있다.

## DB(MYSQL) 연결

MySQL Connector Download Link: [MySQL Connector][connector]

[connector]: https://dev.mysql.com/downloads/connector/j/8.0.html

# Tomcat 내 폴더 생성

톰켓 서버 경로(/webapps/ROOT/) 안에 'makekit' 폴더 생성한다.

## JSP 폴더

JSP 폴더 내의 자료는 톰켓 서버 경로(/webapps/ROOT/makeKit/jsp) 안에 'jsp파일'들을 넣어준다.

## IMAGE 폴더

JSP 폴더 내의 자료는 톰켓 서버 경로(/webapps/ROOT/makeKit/image) 안에 업로드된 이미지를 확인할 수 있다.

## JSP와 DB를 연결하기 위한 JSP내 사용자 환경에 맞는 소스 변경 요소들

String url\*mysql = "jdbc:mysql://localhost/\*\*\*데이터베이스 스키마 이름\_\*\*?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";

String id_mysql = "**아이디**";

String pw_mysql = "**암호**";

## 구현 기능들

- 로그인 정보 저장 기능
- 원하는 그룹별 묶어 관리
- 핸드폰 내의 사진을 핸드폰 내에 임시파일로 저장하고 tomcat 서버에 올림
- 긴급 연락처, 즐겨찾는 연락처 분리 사용 가능
- Email로 인증번호 발송하여 본인 인증 가능
- 입력한 휴대폰 번호로 인증번호를 발송하여 본인 인증
