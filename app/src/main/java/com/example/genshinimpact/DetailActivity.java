package com.example.genshinimpact;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private ArrayList<Members> arrayOfMembers = new ArrayList<Members>();
    private ArrayList<characterData> arrayOfCharacter = new ArrayList<characterData>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //接入SQLite获取数据
        String database_path = getDatabasePath("gen_shin.db").toString();
        SQLiteDatabase myDatabase = SQLiteDatabase.openDatabase(database_path, null, SQLiteDatabase.ENABLE_WRITE_AHEAD_LOGGING);
        // TODO: 2023/12/2 添加id查找
        List<detailData> detailDatas = dataAction.findData(myDatabase);
        characterData characterData = dataAction.findAttribute(myDatabase);

        //注册链接Adapter
        MembersAdapter adapter = new MembersAdapter(this, arrayOfMembers);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);


        //属性页信息
        addAttribute(characterData);

        //添加listview成员
        addMembers(detailDatas);

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
            Members members = new Members(detailData.picUrl,detailData.talentName, detailData.talentDetail);
            arrayOfMembers.add(members);
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

            title.setText(characterData.title+"-"+characterData.fullName);
            country.setText("地区:"+characterData.country);
            star.setText(characterData.star+"星");
            level.setText(characterData.level);
            attribute.setText("属性:"+characterData.attribute);
            weapen.setText("武器:"+characterData.weapen);
            god.setText("命座:"+characterData.god);
            race.setText("种族:"+characterData.race);
            food.setText("特殊食材:"+characterData.food);
            time.setText("上线日期:"+characterData.time);
            introuduce.setText("\n  简介:"+"\n        "+characterData.introduce);


    }

}