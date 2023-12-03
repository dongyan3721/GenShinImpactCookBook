package com.example.myapplication.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.example.myapplication.entity.Comments;
import com.example.myapplication.entity.CommentsQueryEntity;

import junit.framework.TestCase;

import java.io.IOException;
import java.util.List;

import okhttp3.Response;

public class HttpManagerTest extends TestCase {

    public void testTestServerConnection() {
        try {
            HttpManager.testServerConnection();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void testSendSyncPostRequest() throws IOException {
        CommentsQueryEntity query = new CommentsQueryEntity();
        query.setPageNum(1);
        query.setRoleName("可是");
        query.setStartNum(0);
        query.setEndNum(10);
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6IjI5MTQ1NjY4OTNAcXEuY29tIiwicGFzc3dvcmQiOiJzMnhRckdWVk9wdkRtWmtvSjBaUFFjRHo1Q0pOMm1uVWtkeUExbk90VW04WVc1Q2tvZnhsWUFWeW1kbmk1U3N4SUJFYTdwSUxtUnRYWUVOcWl1VEZqQzQ0YW1DbkwvcEQ4aFp4djJkdXZEQm9QcjhabUkyZUZvbGRlSFI5ZkkrQmI4WXExQVA1dHg5aURYZGN5QjJOWVk4ZHVvTnZzN0V3WXZYNGVPdnQycEF6NXh3RHFiOHp0WHlxN0JCL3I0OHFxc2o5aWd6M1dQaVpPcDg5Y282bDJhWWJXUVZIdUVqUy9ubjB6a01CUU1MdTUwRisrL3pHL2Q4TnZLWk1BczRtN3Rnc3Y4L09GWjBEV3NmRzBneHZoVzQyWDUvRk1PamxVcXROc0Q4TTM2ZnJ0UlYyTitaTW40Lyt0eGJaYVlTSnR6d045Smt4UDdkWTJ1ZHpoQUx5c1E9PSIsImV4cCI6MTcwMTYxNTEyNn0.umUdgqy50FK_s9-FktiVWWhywYDnBxLH0URxGr20lgw";
        Response response = HttpManager.sendSyncPostRequest("http://127.0.0.1:8080/comments/get-comments", query, token);
        String jsonString = response.body().string();
        response.close();
        JSONObject obj = JSON.parseObject(jsonString);
        System.out.println(obj);
        JSONArray rows = obj.getJSONArray("rows");
        System.out.println(rows);
        List<Comments> comments = rows.toJavaList(Comments.class);
        int total = (int)obj.get("total");
        System.out.println(comments);
        System.out.println(total);
    }
}