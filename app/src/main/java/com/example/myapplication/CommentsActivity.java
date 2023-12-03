package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.example.myapplication.adapter.CommentsAdapter;
import com.example.myapplication.entity.Comments;
import com.example.myapplication.entity.CommentsQueryEntity;
import com.example.myapplication.entity.extend_for_listview.CommentsListViewItem;
import com.example.myapplication.utils.HttpManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

public class CommentsActivity extends AppCompatActivity {
    // 自定义的关闭遮罩层的数字
    private static final int CLOSE_DIALOG = 0X4656;
    private static final int START_LOADING = 0X4602;

    private static final String REQUEST_URL = "http://127.0.0.1:8080/comments/get-commnets";

    private static final String ADD_URL = "http://127.0.0.1:8080/comments/add-commnet";

    // 当前查的第几页评论，一致写为每页50条
    private int pageNum;
    // 记录当前页有多少条信息
    private int total;
    // 当前查询的角色，从前一个Activity传递的快递里面获取
    private String queryRoleName;

    // 渲染本页评论适配器的列表
    private List<CommentsListViewItem> adapterContent;

    @SuppressLint("HandlerLeak")
    private final Handler closeLoadingDialogHandler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what==CLOSE_DIALOG){
                post(closeLoadingDialog);
            }else if(msg.what==START_LOADING){
                post(startLoadingDialog);
            }
        }
    };

    // 关闭遮罩层的异步线程
    private final Runnable closeLoadingDialog = new Runnable() {
        @Override
        public void run() {
            if(loadingDialog.isShowing()){
                loadingDialog.dismiss();
            }
        }
    };

    // 开启转圈的异步线程
    private final Runnable startLoadingDialog = new Runnable() {
        @Override
        public void run() {
            loadingDialog = ProgressDialog.show(CommentsActivity.this, "请稍候", "正在拼命加载中o.O");
        }
    };

    // 遮罩层
    private ProgressDialog loadingDialog;

    // 本页列表视图控件
    private ListView commentsListView;

    @SuppressLint({"MissingInflatedId", "DefaultLocale"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        commentsListView = (ListView) findViewById(R.id.comments_list_view);
        // Intent intent = getIntent();
        // 所有页面组合的时候把他打开
        // this.queryRoleName = intent.getStringExtra("queryRoleName");
        new AdapterContentAccess(1).start();

        EditText pageNumSelector = (EditText)findViewById(R.id.comments_page_num);


        ((Button)findViewById(R.id.goto_determined)).setOnClickListener((e)->{
            String targetPageNum = pageNumSelector.getText().toString();
            int newPageNum;
            try {
                newPageNum = Integer.parseInt(targetPageNum);
            }catch (NumberFormatException error){
                Toast.makeText(this, "输入页码无效！", Toast.LENGTH_SHORT).show();
                return;
            }
            if (newPageNum==pageNum){
                Toast.makeText(this, String.format("当前已经在第%d页！", pageNum), Toast.LENGTH_SHORT).show();
                return;
            }else if(newPageNum<=0){
                Toast.makeText(this, "输入页码无效！", Toast.LENGTH_SHORT).show();
                return;
            }
            new AdapterContentAccess(newPageNum).start();
        });

        ((Button)findViewById(R.id.goto_previous)).setOnClickListener(e->{
            if (pageNum==1){
                Toast.makeText(this, "不能再往前了O.o", Toast.LENGTH_SHORT).show();
                return;
            }
            new AdapterContentAccess(pageNum-1).start();
        });

        ((Button)findViewById(R.id.goto_next)).setClickable(this.total==50);

        ((Button)findViewById(R.id.goto_next)).setOnClickListener(e->{
            new AdapterContentAccess(pageNum+1).start();
        });

        EditText addCommentInput = (EditText)findViewById(R.id.comments_add_text);

        ((Button)findViewById(R.id.add_comment_button)).setOnClickListener(e->{
            String exact = addCommentInput.getText().toString().replace(" ", "");
            if(exact.equals("")){
                Toast.makeText(this, "评论不能为空O.o", Toast.LENGTH_SHORT).show();
            }else{
                Comments comments = new Comments();
                comments.setAdder("是是是");
                comments.setAddress("后台设置");
                comments.setRoleName(queryRoleName);
                comments.setContent(exact);
                new SubmitNewComment(comments, pageNum).start();
            }
        });
    }

    private void addComment(Comments comments) throws IOException {
        String token = "";
        HttpManager.sendSyncPostRequest(ADD_URL, comments, token);
    }

    private void requestNewPage(int newPageNum) throws IOException {
        CommentsQueryEntity queryEntity = new CommentsQueryEntity();
        queryEntity.setEndNum(newPageNum*50);
        queryEntity.setStartNum((pageNum-1)*50);
        queryEntity.setRoleName(queryRoleName);
        queryEntity.setPageNum(newPageNum);
        pageNum = newPageNum;
        // 写一个类往里面的静态成员写token
        String token = "";
        Response response;
        try {
            response = HttpManager.sendSyncPostRequest(REQUEST_URL, queryEntity, token);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assert response.body() != null;
        String jsonString = response.body().string();
        JSONObject obj = JSON.parseObject(jsonString);
        total = obj.getInteger("total");
        List<Comments> rows = obj.getJSONArray("rows").toJavaList(Comments.class);
        adapterContent = new ArrayList<>();
        rows.forEach(item->{
            CommentsListViewItem viewItem = new CommentsListViewItem();
            viewItem.setImgId(R.drawable.person);
            viewItem.setAdder(item.getAdder());
            viewItem.setAddress(item.getAddress());
            viewItem.setContent(item.getContent());
            viewItem.setComId(item.getComId());
            viewItem.setRoleName(item.getRoleName());
            adapterContent.add(viewItem);
        });
        ((EditText)findViewById(R.id.comments_page_num)).setText(String.valueOf(pageNum));
    }

    private class AdapterContentAccess extends Thread{

        private final int newPageNum;

        public AdapterContentAccess(int newPageNum) {
            this.newPageNum = newPageNum;
        }

        @Override
        public void run() {
            try {
                closeLoadingDialogHandler.sendEmptyMessage(START_LOADING);
                requestNewPage(newPageNum);
                commentsListView.setAdapter(new CommentsAdapter(CommentsActivity.this, R.layout.item_comments_layout, adapterContent));
                closeLoadingDialogHandler.sendEmptyMessage(CLOSE_DIALOG);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private class SubmitNewComment extends Thread{

        private final Comments comments;

        private final int newPageNum;

        private SubmitNewComment(Comments comments, int newPageNum) {
            this.comments = comments;
            this.newPageNum = newPageNum;
        }

        @Override
        public void run() {
            try {
                closeLoadingDialogHandler.sendEmptyMessage(START_LOADING);
                addComment(comments);
                requestNewPage(newPageNum);
                commentsListView.setAdapter(new CommentsAdapter(CommentsActivity.this, R.layout.item_comments_layout, adapterContent));
                closeLoadingDialogHandler.sendEmptyMessage(CLOSE_DIALOG);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}