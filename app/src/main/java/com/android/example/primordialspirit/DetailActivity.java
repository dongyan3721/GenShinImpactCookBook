package com.android.example.primordialspirit;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.android.example.primordialspirit.adapter.MembersAdapter;
import com.android.example.primordialspirit.adapter.dataAction;
import com.android.example.primordialspirit.entity.Members;
import com.android.example.primordialspirit.entity.characterData;
import com.android.example.primordialspirit.entity.detailData;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {
    private String head;
    private Drawable headUrl;
    private ArrayList<Members> arrayOfMembers = new ArrayList<Members>();
    private ArrayList<characterData> arrayOfCharacter = new ArrayList<characterData>();
    TextView textView;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        String role =  getIntent().getStringExtra("roleData");

        // 跳转到评论区
        ((Button)findViewById(R.id.goto_comment)).setOnClickListener(e->{
            Intent intent = new Intent(this, CommentsActivity.class);
            intent.putExtra("queryRoleName", role);
            startActivity(intent);
        });

        String database_path = getDatabasePath("gen_shin.db").toString();
        SQLiteDatabase myDatabase = SQLiteDatabase.openDatabase(database_path, null, SQLiteDatabase.ENABLE_WRITE_AHEAD_LOGGING);
        List<detailData> detailDatas = dataAction.findData(myDatabase,role);
        characterData characterData = dataAction.findAttribute(myDatabase,role);
        head = dataAction.findPic(myDatabase,role);

        //注册链接Adapter
        MembersAdapter adapter = new MembersAdapter(this, arrayOfMembers);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);

        InputStream is = null;
        try {
            is = getAssets().open(head.substring(2));
            Bitmap bm = BitmapFactory.decodeStream(is);
            Drawable da = new BitmapDrawable(getResources(), bm);
            ImageView imageView=findViewById(R.id.imageView);
            imageView.setImageDrawable(da);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



        //属性页信息
        addAttribute(characterData);

        //添加listview成员
        addMembers(detailDatas);
        ImageButton btn = findViewById(R.id.imageButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // 调用返回方法
            }
        });


        Button button = findViewById(R.id.check);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 切换布局的可见性
                LinearLayout layout1 = findViewById(R.id.layout1);
                LinearLayout layout2 = findViewById(R.id.layout2);
                Button button1 = findViewById(R.id.check);
                if (layout1.getVisibility() == View.VISIBLE) {
                    layout1.setVisibility(View.GONE);
                    layout2.setVisibility(View.VISIBLE);
                    button1.setText("技能信息");
                } else {
                    layout1.setVisibility(View.VISIBLE);
                    layout2.setVisibility(View.GONE);
                    button1.setText("属性信息");
                }
            }
        });

    }
    public void addMembers(List<detailData> detailDatas)
    {
        for (detailData detailData:detailDatas
        ) {
           try {
                InputStream is = getAssets().open(detailData.getPicUrl().substring(1).replace("png", "")+".png");
                Bitmap bm = BitmapFactory.decodeStream(is);
                Drawable da = new BitmapDrawable(getResources(), bm);
                Members members = new Members(detailData.getPicUrl(), detailData.getTalentName(), detailData.getTalentDetail(),da);
                arrayOfMembers.add(members);
                is.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        }
    }
    public void addAttribute(characterData characterData)
    {
        TextView title = findViewById(R.id.title);
        TextView country = findViewById(R.id.country);
        TextView star = findViewById(R.id.star);
        TextView level = findViewById(R.id.level);
        TextView attribute = findViewById(R.id.attribute);
        TextView weapen = findViewById(R.id.weapen);
        TextView god = findViewById(R.id.god);
        TextView race = findViewById(R.id.race);
        TextView food = findViewById(R.id.food);
        TextView time = findViewById(R.id.time);
        TextView introuduce = findViewById(R.id.intruduce);

        title.setText(characterData.getTitle() +"-"+ characterData.getFullName());
        country.setText("地区:"+ characterData.getCountry());
        star.setText(characterData.getStar() +"星");
        level.setText(characterData.getLevel());
        attribute.setText("属性:"+ characterData.getAttribute());
        weapen.setText("武器:"+ characterData.getWeapen());
        god.setText("命座:"+ characterData.getGod());
        race.setText("种族:"+ characterData.getRace());
        food.setText("特殊食材:"+ characterData.getFood());
        time.setText("上线日期:"+ characterData.getTime());
        introuduce.setText("\n  简介:"+"\n        "+ characterData.getIntroduce());

    }
}