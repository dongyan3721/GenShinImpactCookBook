package com.example.genshinimpact;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private ArrayList<Members> arrayOfMembers = new ArrayList<Members>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        String database_path = getDatabasePath("gen_shin.db").toString();
        String name = null;
        MembersAdapter adapter = new MembersAdapter(this, arrayOfMembers);
        SQLiteDatabase myDatabase = SQLiteDatabase.openDatabase(database_path, null, SQLiteDatabase.ENABLE_WRITE_AHEAD_LOGGING);
        List<detailData> detailDatas = findData.findData(myDatabase);
        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        // add members
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