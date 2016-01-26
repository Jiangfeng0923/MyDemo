package com.feng.demo.mydemos.auction;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.feng.demo.mydemos.R;
import com.feng.demo.mydemos.auction.util.DialogUtil;
import com.feng.demo.mydemos.auction.util.HttpUtil;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    EditText etName, etPass;
    Button bnLogin, bnCancel;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etName = (EditText) findViewById(R.id.userEditText);
        etPass = (EditText) findViewById(R.id.pwdEditText);
        bnLogin = (Button) findViewById(R.id.bnLogin);
        bnCancel = (Button) findViewById(R.id.bnCancel);
        bnCancel.setOnClickListener(new HomeListener(this));
        bnLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (validate())
                {
                    if (loginPro())
                    {
                        Intent intent = new Intent(LoginActivity.this
                                , AuctionClientActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        DialogUtil.showDialog(LoginActivity.this
                                , "login erro", false);
                    }
                }
            }
        });
    }

    private boolean loginPro()
    {
        String username = etName.getText().toString();
        String pwd = etPass.getText().toString();
        JSONObject jsonObj;
        try
        {
            jsonObj = query(username, pwd);
            if (jsonObj.getInt("userId") > 0)
            {
                return true;
            }
        }
        catch (Exception e)
        {
            DialogUtil.showDialog(this
                    , "login erro", false);
            e.printStackTrace();
        }

        return false;
    }

    private boolean validate()
    {
        String username = etName.getText().toString().trim();
        if (username.equals(""))
        {
            DialogUtil.showDialog(this, "username err", false);
            return false;
        }
        String pwd = etPass.getText().toString().trim();
        if (pwd.equals(""))
        {
            DialogUtil.showDialog(this, "pwd err", false);
            return false;
        }
        return true;
    }

    private JSONObject query(String username, String password)
            throws Exception
    {
        Map<String, String> map = new HashMap<String, String>();
        map.put("user", username);
        map.put("pass", password);
        String url = HttpUtil.BASE_URL + "login.jsp";
        return new JSONObject(HttpUtil.postRequest(url, map));
    }

}
