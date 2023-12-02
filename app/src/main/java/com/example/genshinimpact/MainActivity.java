package com.example.genshinimpact;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

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

    }
    public void turntopic(View view){
        Intent intentt = new Intent();
        intentt.setClass(MainActivity.this,DetailActivity.class);
        startActivity(intentt);
    }

}