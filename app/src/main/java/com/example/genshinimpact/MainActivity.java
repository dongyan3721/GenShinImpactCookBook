package com.example.genshinimpact;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private EditText editTextMessage;
    private Button buttonSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // TODO: 2023/12/2 提醒一下访问时前一个页面要提前做好数据库访问
        MyDatabaseHelper myHelper = new MyDatabaseHelper(MainActivity.this);
        try {
            myHelper.CopyDBFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        editTextMessage = findViewById(R.id.editTextMessage);
        buttonSend = findViewById(R.id.buttonSend);

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 读取EditText中的文本
                String message = editTextMessage.getText().toString();
                // 创建Intent以启动新的Activity
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                // 将数据放入Intent
                intent.putExtra("id", message);
                // 启动SecondActivity
                startActivity(intent);
            }
        });
    }


}