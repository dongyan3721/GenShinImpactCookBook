package com.android.example.primordialspirit.adapter;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.android.example.primordialspirit.entity.characterData;
import com.android.example.primordialspirit.entity.detailData;

import java.util.ArrayList;
import java.util.List;

public class dataAction {
    @SuppressLint("Range")
    public static List findData(SQLiteDatabase db,String id){
        String query1 = "SELECT * FROM 天赋表 WHERE 天赋持有者 = ?";

    //修改
        String[] selectionArgs = {id};

        // 执行查询操作
        Cursor cursor1 = db.rawQuery(query1, selectionArgs); //技能查询

        List<detailData> detailDatas = new ArrayList<>();

        // 遍历结果
        if (cursor1.moveToFirst()) {
            do {
                detailData detailData = new detailData();
                detailData.setTalentName(cursor1.getString(cursor1.getColumnIndex("天赋名")));
                detailData.setTalentDetail(cursor1.getString(cursor1.getColumnIndex("天赋描述")));
                detailData.setPicUrl(cursor1.getString(cursor1.getColumnIndex("天赋图")));
                detailDatas.add(detailData);
            } while (cursor1.moveToNext());
        }

        // 关闭游标和数据库连接
        cursor1.close();

        return detailDatas;
    }

    @SuppressLint("Range")
    public static characterData findAttribute(SQLiteDatabase db, String id){
        String query2 = "SELECT * FROM 角色基础信息表 WHERE 姓名 = ?";
        //修改
        String[] selectionArgs = {id};

        // 执行查询操作
        Cursor cursor2 = db.rawQuery(query2,selectionArgs); //详细查询
        characterData characterData = new characterData();

        if (cursor2.moveToFirst()) {
            do {

                characterData.setAttribute(cursor2.getString(cursor2.getColumnIndex("属性")));
                characterData.setCountry(cursor2.getString(cursor2.getColumnIndex("所属国家")));
                characterData.setFullName(cursor2.getString(cursor2.getColumnIndex("全名")));
                characterData.setStar(cursor2.getString(cursor2.getColumnIndex("星级")));
                characterData.setGod(cursor2.getString(cursor2.getColumnIndex("命座")));
                characterData.setLevel(cursor2.getString(cursor2.getColumnIndex("常驻/限定")));
                characterData.setWeapen(cursor2.getString(cursor2.getColumnIndex("武器类型")));
                characterData.setRace(cursor2.getString(cursor2.getColumnIndex("种族")));
                characterData.setFood(cursor2.getString(cursor2.getColumnIndex("特殊食材")));
                characterData.setTitle(cursor2.getString(cursor2.getColumnIndex("称号")));
                characterData.setTime(cursor2.getString(cursor2.getColumnIndex("上线日期")));
                characterData.setIntroduce(cursor2.getString(cursor2.getColumnIndex("简介")));
                // 进行相应的操作
            } while (cursor2.moveToNext());
        }


        // 关闭游标和数据库连接
        cursor2.close();


        return characterData;
    }

    @SuppressLint("Range")
    public static String findPic(SQLiteDatabase db, String id){
        String query1 = "SELECT * FROM 角色基础信息表 WHERE 姓名 = ?";

        //修改
        String[] selectionArgs = {id};

        // 执行查询操作
        Cursor cursor3 = db.rawQuery(query1, selectionArgs); //技能查询

        String ad = null;

        // 遍历结果
        if (cursor3.moveToFirst()) {
            do {
                ad=cursor3.getString(cursor3.getColumnIndex("立绘地址"));
            } while (cursor3.moveToNext());
        }

        // 关闭游标和数据库连接
        cursor3.close();

       return ad;
    }
}
