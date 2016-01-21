package com.feng.demo.data;

import android.content.Context;
import android.text.TextUtils;
import android.widget.EditText;

import com.feng.demo.utils.SharedPreferenceHelper;

import static com.feng.demo.utils.MyLogUtils.JLog;

/**
 * Created by lenovo on 2016/1/4.
 */
public class MyURLs {
    public static final String TOMCATURL = "http://192.168.2.100:8080/MyTomcatServlet/login";
    public static final String BAIDU = "https://www.baidu.com";
    public static final String SOUGOU = "http://www.sougou.com";
    public static final String WEATHER = "http://m.weather.com.cn/data/101020100.html";
    public static final String[] ImageURLs = new String[]{
            "http://192.168.2.100:8080/resource/image/test_01.png",
            "http://192.168.2.100:8080/resource/image/test_02.jpg",
            "http://192.168.2.100:8080/resource/image/test_03.jpg"
    };
    public static final String HTTP = "http://";
    public static final String DEFAULT_IP = "192.168.2.100";
    public static final String PORT = ":8080";
    public static final String LOCAL_IMAGES_ADDRESS = "/resource/image/";
    public static final String LOCAL_FILE_ADDRESS = "/resource/file/";
    public static final String LOCAL_RESOURCE_ADDRESS = "/resource";
    public static final String LOCAL_UPLOAD_ADDRESS = "/MyTomcatServlet/upload";
    public static final String LOCAL_LOGIN_ADDRESS = "/MyTomcatServlet/login";
    public static final String[] IMAGE_NAMES = new String[]{
            "test_01.jpg",
            "test_02.jpg",
            "test_03.jpg",
            "test_04.jpg"
    };

    private MyURLs() {
    }

    public static String BuildURL(String ip, String... addresses) {
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(MyURLs.HTTP);
        urlBuilder.append(ip);
        urlBuilder.append(MyURLs.PORT);
        if (addresses != null && addresses.length > 0) {
            for (String address : addresses) {
                JLog("address=" + address);
                urlBuilder.append(address);
            }
        }
        String result = urlBuilder.toString();
        JLog("result uri=" + result);
        return result;
    }

    public static String getIPAddress(Context context,EditText editText){
        String ip = "";
        String text = editText.getText().toString();
        if (TextUtils.isEmpty(text)) {
            ip = SharedPreferenceHelper.getFromSharedPreferences(context);
            editText.setText(ip);
        } else {
            ip = text;
            SharedPreferenceHelper.putIntoSharedPreferences(context, ip);
        }
        JLog("initURLs: ip=" + ip);
        return ip;
    }
}
