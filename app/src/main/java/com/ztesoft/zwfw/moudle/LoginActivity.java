package com.ztesoft.zwfw.moudle;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.ztesoft.zwfw.Config;
import com.ztesoft.zwfw.R;
import com.ztesoft.zwfw.base.BaseActivity;
import com.ztesoft.zwfw.domain.resp.LoginResp;
import com.ztesoft.zwfw.utils.APPPreferenceManager;
import com.ztesoft.zwfw.utils.http.RequestManager;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends BaseActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private Context mContext;

    private EditText edtUserName;
    private EditText edtPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = this;

        edtUserName = (EditText) findViewById(R.id.edt_username);
        edtPwd = (EditText) findViewById(R.id.edt_pwd);

    }



    public void onLogin(View view){
        String userName = edtUserName.getText().toString();
        String userPwd = edtPwd.getText().toString();
        if(TextUtils.isEmpty(userName)){
            Toast.makeText(mContext,"请输入工号",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(userPwd)){
            Toast.makeText(mContext,"请输入密码",Toast.LENGTH_SHORT).show();
            return;
        }

        final Map<String,Object>  map = new HashMap<String,Object>();
        map.put("userName",userName);
        map.put("userPwd",Base64.encodeToString(userPwd.getBytes(),Base64.DEFAULT));
        map.put("pwdLength",userPwd.length());
        RequestManager.getInstance().postHeader(Config.BASE_URL + Config.URL_LOGIN,JSON.toJSONString(map), new RequestManager.RequestListener() {
            @Override
            public void onRequest(String url, int actionId) {
                showProgressDialog(getString(R.string.logging));
            }

            @Override
            public void onSuccess(String response, String url, int actionId) {
                Log.d(TAG,"onSuccess"+response);
                hideProgressDialog();
                LoginResp loginResp = JSON.parseObject(response,LoginResp.class);
                if(loginResp.getErrorCode().equals("SUCCESS")){
                    APPPreferenceManager.getInstance().saveObject(mContext,Config.IS_LOGIN,true);
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    finish();
                }else{
                    Toast.makeText(mContext,loginResp.getErrorMessage(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(String errorMsg, String url, int actionId) {
                hideProgressDialog();
                Toast.makeText(mContext,errorMsg,Toast.LENGTH_SHORT).show();
            }
        },0);
    }
}
