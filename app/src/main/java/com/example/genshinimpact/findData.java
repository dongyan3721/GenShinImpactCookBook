package com.example.genshinimpact;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class findData {
    @SuppressLint("Range")
    public static List findData(SQLiteDatabase db){
        String tablename = "天赋表";
        String query = "SELECT * FROM 天赋表 WHERE 天赋持有人 = ?";

// 定义查询参数
        String[] selectionArgs = {"芙宁娜"};

// 执行查询操作
        Cursor cursor = db.rawQuery(query, selectionArgs);
        List<detailData> detailDatas = new ArrayList<>();

// 遍历结果
        if (cursor.moveToFirst()) {
            do {
                detailData detailData = new detailData();
                detailData.talentName = cursor.getString(cursor.getColumnIndex("天赋名"));
                detailData.talentDetail = cursor.getString(cursor.getColumnIndex("天赋描述"));
                detailData.picUrl = cursor.getString(cursor.getColumnIndex("天赋图"));
                detailDatas.add(detailData);
                // 进行相应的操作
            } while (cursor.moveToNext());
        }


// 关闭游标和数据库连接

        return detailDatas;
    }
}
