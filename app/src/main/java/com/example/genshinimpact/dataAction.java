package com.example.genshinimpact;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class dataAction {
    @SuppressLint("Range")
    // TODO: 2023/12/2 添加名字查询
    public static List findData(SQLiteDatabase db,String id){
        String query1 = "SELECT * FROM 天赋表 WHERE 天赋持有人 = ?";

    //修改
        String[] selectionArgs = {id};

        // 执行查询操作
        Cursor cursor1 = db.rawQuery(query1, selectionArgs); //技能查询

        List<detailData> detailDatas = new ArrayList<>();
        List<characterData> characterDatas = new ArrayList<>();

        // 遍历结果
        if (cursor1.moveToFirst()) {
            do {
                detailData detailData = new detailData();
                detailData.talentName = cursor1.getString(cursor1.getColumnIndex("天赋名"));
                detailData.talentDetail = cursor1.getString(cursor1.getColumnIndex("天赋描述"));
                detailData.picUrl = cursor1.getString(cursor1.getColumnIndex("天赋图"));
                detailDatas.add(detailData);
            } while (cursor1.moveToNext());
        }

        // 关闭游标和数据库连接
        cursor1.close();

        return detailDatas;
    }

    @SuppressLint("Range")
    public static characterData findAttribute(SQLiteDatabase db,String id){
        String query2 = "SELECT * FROM 角色基础信息表 WHERE 姓名 = ?";
        //修改
        String[] selectionArgs = {id};

        // 执行查询操作
        Cursor cursor2 = db.rawQuery(query2,selectionArgs); //详细查询
        characterData characterData = new characterData();

        if (cursor2.moveToFirst()) {
            do {

                characterData.attribute = cursor2.getString(cursor2.getColumnIndex("属性"));
                characterData.country = cursor2.getString(cursor2.getColumnIndex("所属国家"));
                characterData.fullName = cursor2.getString(cursor2.getColumnIndex("全名"));
                characterData.star = cursor2.getString(cursor2.getColumnIndex("星级"));
                characterData.god = cursor2.getString(cursor2.getColumnIndex("命座"));
                characterData.level = cursor2.getString(cursor2.getColumnIndex("常驻/限定"));
                characterData.weapen = cursor2.getString(cursor2.getColumnIndex("武器类型"));
                characterData.race = cursor2.getString(cursor2.getColumnIndex("种族"));
                characterData.food = cursor2.getString(cursor2.getColumnIndex("特殊食材"));
                characterData.title = cursor2.getString(cursor2.getColumnIndex("称号"));
                characterData.time = cursor2.getString(cursor2.getColumnIndex("上线日期"));
                characterData.introduce = cursor2.getString(cursor2.getColumnIndex("简介"));
                // 进行相应的操作
            } while (cursor2.moveToNext());
        }


        // 关闭游标和数据库连接
        cursor2.close();


        return characterData;
    }
}
