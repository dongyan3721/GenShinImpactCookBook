package com.example.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.blankj.utilcode.util.ToastUtils;
import com.example.myapplication.config.LoginConfig;
import com.example.myapplication.config.TokenConfig;
import com.example.myapplication.entity.User;
import com.example.myapplication.utils.HttpManager;
import com.example.myapplication.utils.RSAUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import okhttp3.Response;


/*volley是Android 提供的网络请求库（类似的还有 OkHttp 或 Retrofit等）*/
public class LogInActivity extends AppCompatActivity {

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] != -1) {
                    Toast.makeText(this, "000000", Toast.LENGTH_SHORT).show();
                } else {
                    //T.showShort(mContext,"拒绝权限");
                    // 权限被拒绝，弹出dialog 提示去开启权限
                    break;
                }
            }
        }
    }

    private static final String TAG = "LoginActivity";

    private static final int MY_PERMISSION_REQUEST_CODE = 10000;

    private boolean checkPermissionAllGranted(String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                // 只要有一个权限没有被授予, 则直接返回 false
                return false;
            }
        }
        return true;
    }

    // 调用Actvity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String[] requiredPermission = {
                Manifest.permission.INTERNET,
        };

        boolean isAllGranted = checkPermissionAllGranted(requiredPermission);

        Log.d(TAG, "onCreate: " + isAllGranted);


        if (!isAllGranted) {
            ActivityCompat.requestPermissions(LogInActivity.this, requiredPermission, MY_PERMISSION_REQUEST_CODE);
            isAllGranted = checkPermissionAllGranted(requiredPermission);
        }

        Log.d(TAG, "onCreate: "+isAllGranted);

//        while (true) {
//            if (!isAllGranted) {
//                ActivityCompat.requestPermissions(LogInActivity.this, requiredPermission, MY_PERMISSION_REQUEST_CODE);
//                isAllGranted = checkPermissionAllGranted(requiredPermission);
//            } else {
//                break;
//            }
//        }

        // 关联登录界面
        setContentView(R.layout.activity_log_in);

        //使用this.getIntent()获取当前Activity的Intent
        Intent intent = this.getIntent();
        // 关联邮箱、密码和登录、注册按钮
        EditText email = (EditText) this.findViewById(R.id.EmailEdit);
        EditText passWord = (EditText) this.findViewById(R.id.PassWordEdit);
        Button loginButton = (Button) this.findViewById(R.id.LoginButton);
        Button signUpButton = (Button) this.findViewById(R.id.SignUpButton);

        // 登录按钮监听器
        loginButton.setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 获取用户名和密码
                        String strEmail = email.getText().toString().trim();
                        String strPassWord = passWord.getText().toString().trim();

                        String encryptedPassword;
                        try {
                            encryptedPassword = RSAUtil.encrypt(strPassWord.getBytes(StandardCharsets.UTF_8), getResources().getString(R.string.public_key));

                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        User user = new User();
                        user.setRegisterEmail(strEmail);
                        user.setEncryptedPassword(encryptedPassword);
                        System.out.println(encryptedPassword);
                        new ThreadHandler(user).start();
                    }
                });
        // 注册按钮监听器
        signUpButton.setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 跳转到注册界面
                        Intent intent = new Intent(LogInActivity.this, SignUpActivity.class);
                        startActivity(intent);
                    }
                }
        );

    }

    private class ThreadHandler extends Thread{
        private final User user;

        public ThreadHandler(User user){
            this.user = user;
        }
        @Override
        public void run() {
            Response response;
            try {
                response = HttpManager.sendSyncPostRequest("https://192.168.124.254:8080/user/email", user);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            try {
                String resp = response.body().string();
                JSONObject jsonObject = JSON.parseObject(resp);
                System.out.println(jsonObject);
                if(jsonObject.getInteger("code")==200){
                    // 把这次登录拿到的token放进到配置中
                    TokenConfig.token = jsonObject.getString("token");
                    LoginConfig.loginUser = (User)jsonObject.get("user");
                    Intent intent = new Intent(LogInActivity.this, SecondActivity.class);
                    ToastUtils.showShort("登录成功，即将跳转");
                    try {
                        Thread.sleep(2500);
                        startActivity(intent);
                        finish();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }else{
                    ToastUtils.showShort("用户名或密码错误！");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}