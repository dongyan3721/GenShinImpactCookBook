package com.example.myapplication.utils;

import com.alibaba.fastjson2.JSON;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;

public class HttpManager {

    private static final String TEST_REQUEST_URL = "http://127.0.0.1:8080/user/register";

    // 声明一个信任所有签名的访问客户端
    private static final OkHttpClient client = SkipCertificateValidation.getUnsafeOkHttpClient();



    /**
     * 发送异步post请求
     * @param url 请求地址
     * @param params 请求参数实体类
     * @param callback 响应处理
     */
    public static void sendAsyncPostRequest(String url, Object params, Callback callback){
        String paramsString = JSON.toJSONString(params);
        MediaType formatter = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(paramsString, formatter);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(callback);
    }

    /**
     * 发送带token的异步post请求
     * @param url 请求地址
     * @param params 请求参数
     * @param callback 响应处理
     * @param token 令牌
     */
    public static void sendAsyncPostRequest(String url, Object params, Callback callback, String token){
        String paramsString = JSON.toJSONString(params);
        MediaType formatter = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(paramsString, formatter);
        Request request = new Request.Builder()
                .url(url)
                .header("token", token)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(callback);
    }

    /**
     * 发送同步post请求
     * @param url 请求地址
     * @param params 请求参数
     * @return 请求响应
     */
    public static Response sendSyncPostRequest(String url, Object params) throws IOException {
        String paramsString = JSON.toJSONString(params);
        System.out.println("json:"+paramsString);
        MediaType formatter = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(paramsString, formatter);
        System.out.println(requestBody);
        Request request = new Request.Builder()
                .url(url)
                .header("Accept-Encoding", "identity")
                .post(requestBody)
                .build();
        return client.newCall(request).execute();
    }

    /**
     * 发送带token的同步post请求
     * @param url 请求地址
     * @param params 请求参数
     * @return 请求响应
     */
    public static Response sendSyncPostRequest(String url, Object params, String token) throws IOException {
        String paramsString = JSON.toJSONString(params);
        MediaType formatter = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(paramsString, formatter);
        Request request = new Request.Builder()
                .url(url)
                .header("token", token)
                .post(requestBody)
                .build();
        return client.newCall(request).execute();
    }

}

