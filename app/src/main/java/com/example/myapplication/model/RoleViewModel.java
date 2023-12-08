package com.example.myapplication.model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.blankj.utilcode.util.ThreadUtils;
import com.example.myapplication.entity.Role;

import java.util.ArrayList;
import java.util.List;

public class RoleViewModel extends ViewModel {

    private MutableLiveData<List<Role>> mutableLiveData;

    private Context context;
    public MutableLiveData<List<Role>> getMutableLiveData(Context context) {
        if(mutableLiveData == null) {
            this.context=context;
            mutableLiveData = new MutableLiveData<>();
        }
        return mutableLiveData;
    }

    public void setMutableLiveData(List<Role> mutableLiveData) {
        if(ThreadUtils.isMainThread()) {
            this.mutableLiveData.setValue(mutableLiveData);
        } else {
            this.mutableLiveData.postValue(mutableLiveData);
        }
    }


    @SuppressLint("Range")
    public List<Role> loadSearchData(SQLiteDatabase db,String search){
        Cursor cursor = db.rawQuery("select 姓名,星级,头像地址,属性 from 角色基础信息表 WHERE 星级  || ' ' || 属性 || ' ' || 武器类型 || ' ' || 性别 || ' ' || 姓名  LIKE '%"+search+"%'" , null);

        List<Role> roleList=new ArrayList<>();
        // 遍历结果

        while (cursor.moveToNext()) {//遍历所有用户数据
            String imgStr= cursor.getString(cursor.getColumnIndex("头像地址"));
            String name= cursor.getString(cursor.getColumnIndex("姓名"));
            String star= cursor.getString(cursor.getColumnIndex("星级"));
            String attribute= cursor.getString(cursor.getColumnIndex("属性"));
            roleList.add(new Role(imgStr,null,name,star,attribute));
        }
        // 关闭游标和数据库连接
        cursor.close();
        db.close();

        return roleList;
    }
    public void loadAttributeData(List<Role> listData,String attribute){
        if(!TextUtils.isEmpty(attribute)){
            List<Role> list=new ArrayList<>();
            for (Role item:listData) {
                if(TextUtils.equals(item.getAttribute(),attribute)){
                    Log.e("dd","dd"+1);
                    list.add(item);
                }
            }
            setMutableLiveData(list);
        }
    }

    public void loadStarData(List<Role> listData,String star){
        if(!TextUtils.isEmpty(star)){
            String i = null;
            if(star.equals("一星")){
                i="1";
            }
            else if (star.equals("二星")) {
                i="2";
            }
            else if (star.equals("三星")) {
                i="3";
            }
            else if (star.equals("四星")) {
                i="4";
            }
            else if (star.equals("五星")) {
                i="5";
            }
            List<Role> list=new ArrayList<>();
            for (Role item:listData) {
                if(TextUtils.equals(item.getStar(),i)){
                    list.add(item);
                }
            }
            setMutableLiveData(list);
        }
    }



        @SuppressLint("Range")
        // TODO: 2023/12/2 添加名字查询
        public  List<Role> getData(SQLiteDatabase db){
            String query = "SELECT 姓名,星级,头像地址,属性 FROM 角色基础信息表";

            //修改
            String[] selectionArgs = {""};
            // 执行查询操作
            Cursor cursor = db.rawQuery(query, null);

            List<Role> roleList=new ArrayList<>();
            // 遍历结果
            while (cursor.moveToNext()) {//遍历所有用户数据
                String imgStr= cursor.getString(cursor.getColumnIndex("头像地址"));
                String name= cursor.getString(cursor.getColumnIndex("姓名"));
                String star= cursor.getString(cursor.getColumnIndex("星级"));
                String attribute= cursor.getString(cursor.getColumnIndex("属性"));
                roleList.add(new Role(imgStr,null,name,star,attribute));
                Log.e("bb","bb"+imgStr+name+star+attribute);
            }
            // 关闭游标和数据库连接
            cursor.close();
            db.close();

            return roleList;
        }

}
