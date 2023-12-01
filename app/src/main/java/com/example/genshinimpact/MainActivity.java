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