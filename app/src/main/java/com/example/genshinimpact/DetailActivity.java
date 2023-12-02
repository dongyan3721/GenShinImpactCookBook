package com.example.genshinimpact;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private ArrayList<Members> arrayOfMembers = new ArrayList<Members>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //接入SQLite获取数据
        String database_path = getDatabasePath("gen_shin.db").toString();
        SQLiteDatabase myDatabase = SQLiteDatabase.openDatabase(database_path, null, SQLiteDatabase.ENABLE_WRITE_AHEAD_LOGGING);
        // TODO: 2023/12/2 添加id查找
        List<detailData> detailDatas = findData.findData(myDatabase);

        //注册链接Adapter
        MembersAdapter adapter = new MembersAdapter(this, arrayOfMembers);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);

        //添加listview成员
        addMembers(detailDatas);
    }
    public void addMembers(List<detailData> detailDatas)
    {
        for (detailData detailData:detailDatas
             ) {
            Members members = new Members(detailData.picUrl,detailData.talentName, detailData.talentDetail);
            arrayOfMembers.add(members);
        }
    }
}