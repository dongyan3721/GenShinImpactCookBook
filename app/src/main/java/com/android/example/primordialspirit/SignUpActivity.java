package com.android.example.primordialspirit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.android.example.primordialspirit.entity.User;
import com.android.example.primordialspirit.utils.HttpManager;
import com.android.example.primordialspirit.utils.RSAUtil;
import com.blankj.utilcode.util.ToastUtils;


import java.io.IOException;
import java.nio.charset.StandardCharsets;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SignUpActivity extends Activity {
    // 调用Actvity

    private static final String TAG = "SignUpActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //关联注册界面
        setContentView(R.layout.activity_sign_up);



        // 关联邮箱、密码、确认密码和注册、返回登录按钮
        EditText email = (EditText) this.findViewById(R.id.EmailEdit);
        EditText passWord = (EditText) this.findViewById(R.id.PassWordEdit);
        EditText passWordAgain = (EditText) this.findViewById(R.id.PassWordAgainEdit);
        Button signUpButton = (Button) this.findViewById(R.id.SignUpButton);
        Button backLoginButton = (Button) this.findViewById(R.id.BackLoginButton);

        // 立即注册按钮监听器
        signUpButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //trim() 方法用于删除字符串的头尾空白符，空白符包括：空格、制表符 tab、换行符等其他空白符
                        String strEmail = email.getText().toString().trim();
                        String strPassWord = passWord.getText().toString().trim();
                        String strPassWordAgain = passWordAgain.getText().toString().trim();
                        //注册格式粗检
                        if (!strEmail.contains("@")) {
                            Toast.makeText(SignUpActivity.this, "邮箱格式不正确！", Toast.LENGTH_SHORT).show();
                        } else if (strPassWord.length() > 16) {
                            Toast.makeText(SignUpActivity.this, "密码长度必须小于16！", Toast.LENGTH_SHORT).show();
                        } else if (strPassWord.length() < 6) {
                            Toast.makeText(SignUpActivity.this, "密码长度必须大于6！", Toast.LENGTH_SHORT).show();
                        } else if (!strPassWord.equals(strPassWordAgain)) {
                            Toast.makeText(SignUpActivity.this, "两次密码输入不一致！", Toast.LENGTH_SHORT).show();
                        } else {
                            // Toast.makeText(SignUpActivity.this, "注册成功！", Toast.LENGTH_SHORT).show();

                            // 密码符合要求，进行加密并发送数据到服务器
                            try {
                                // 使用RSA加密密码
                                String encryptedPassword = RSAUtil.encrypt(strPassWord.getBytes(StandardCharsets.UTF_8), getResources().getString(R.string.public_key));

                                // 创建user对象用以传输数据
                                User user = new User();
                                user.setEncryptedPassword(encryptedPassword);
                                user.setRegisterEmail(strEmail);

                                HttpManager.sendAsyncPostRequest("https://192.168.124.254:8080/user/register", user, new Callback() {
                                    @Override
                                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                                        ToastUtils.showShort("网络错误！");
                                    }

                                    @Override
                                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                                        String s = response.body().string();
                                        Log.d(TAG, "onResponse: "+s);
                                        JSONObject jsonObject = JSON.parseObject(s);
                                        if(jsonObject.getInteger("code")==200){
                                            ToastUtils.showShort("注册成功！即将返回登录界面");
                                            try {
                                                Thread.sleep(2500);
                                            } catch (InterruptedException e) {
                                                throw new RuntimeException(e);
                                            }
                                            Intent intent = new Intent(SignUpActivity.this, LogInActivity.class);
                                            startActivity(intent);
                                        }else{
                                            ToastUtils.showShort("响应失败！");
                                        }
                                    }
                                });

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

        // 返回登录按钮监听器
        backLoginButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 跳转到登录界面
                        Intent intent = new Intent(SignUpActivity.this, LogInActivity.class);
                        startActivity(intent);
                    }
                }
        );
    }

}