package com.feng.demo.mydemos.http;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.feng.demo.mydemos.R;
import com.feng.demo.utils.MyURLs;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.feng.demo.utils.MyLogUtils.JLog;

public class VolleyActivity extends AppCompatActivity {
    RequestQueue mQueue;
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley);
        mTextView = (TextView) findViewById(R.id.textId);
        mQueue = Volley.newRequestQueue(this);
    }

    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.get_btn:
                get();
                break;
            case R.id.put_btn:
                put();
                break;
            case R.id.clear_btn:
                mTextView.setText("");
                break;
            case R.id.getjson_btn:
                getJsonObjectRequest();
                break;
        }
    }

    public class MyResponseListener<T> implements Response.Listener<T> {

        @Override
        public void onResponse(T t) {
            JLog("onResponse...");
            if (t instanceof String) {
                mTextView.setText(t.toString());
            } else if (t instanceof JSONObject) {
                mTextView.setText(t.toString());
            }

        }
    }

    public class MyResponseErrorListener implements Response.ErrorListener {

        @Override
        public void onErrorResponse(VolleyError volleyError) {
            mTextView.setText(volleyError.getMessage());
        }
    }

    public void get() {
        StringRequest stringRequest = new StringRequest(MyURLs.TOMCATURL,
                new MyResponseListener<String>(), new MyResponseErrorListener());
        mQueue.add(stringRequest);
    }

    public void put() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, MyURLs.SOUGOU,
                new MyResponseListener<String>(), new MyResponseErrorListener()) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("params1", "value1");
                map.put("params2", "value2");
                return map;
            }
        };

        mQueue.add(stringRequest);
    }

    public void getJsonObjectRequest() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(MyURLs.WEATHER,
                new MyResponseListener<JSONArray>(), new MyResponseErrorListener());
        mQueue.add(jsonArrayRequest);
    }


}
