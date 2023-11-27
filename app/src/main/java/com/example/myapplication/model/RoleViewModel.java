package com.example.myapplication.model;

import android.content.Context;
import android.text.TextUtils;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.blankj.utilcode.util.ThreadUtils;
import com.example.myapplication.R;
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
//        this.mutableLiveData.setValue(mutableLiveData);
    }

    public  List<Role> getRoleListData(){
        List<Role> roleList=new ArrayList<>();
        roleList.add(new Role(ContextCompat.getDrawable(context, R.drawable.p1),"魈",5,ContextCompat.getDrawable(context, R.drawable.star5),"风","男","长柄武器"));
        roleList.add(new Role(ContextCompat.getDrawable(context, R.drawable.p2),"巴巴托斯",5,ContextCompat.getDrawable(context, R.drawable.star5),"风","男","弓"));
        roleList.add(new Role(ContextCompat.getDrawable(context, R.drawable.p3),"荒泷一斗",5,ContextCompat.getDrawable(context, R.drawable.star5),"岩","男","双手剑"));
        roleList.add(new Role(ContextCompat.getDrawable(context, R.drawable.p4),"摩拉克斯",5,ContextCompat.getDrawable(context, R.drawable.star5),"岩","男","长柄武器"));
        roleList.add(new Role(ContextCompat.getDrawable(context, R.drawable.p5),"瑶瑶",4,ContextCompat.getDrawable(context, R.drawable.star4),"草","女","长柄武器"));
        roleList.add(new Role(ContextCompat.getDrawable(context, R.drawable.p6),"卯香菱",4,ContextCompat.getDrawable(context, R.drawable.star4),"火","女","长柄武器"));
        roleList.add(new Role(ContextCompat.getDrawable(context, R.drawable.p7),"琴·古恩希尔德",5,ContextCompat.getDrawable(context, R.drawable.star5),"风","女","单手剑"));
        roleList.add(new Role(ContextCompat.getDrawable(context, R.drawable.p8),"诺艾尔",4,ContextCompat.getDrawable(context, R.drawable.star4),"岩","女","双手剑"));
        roleList.add(new Role(ContextCompat.getDrawable(context, R.drawable.p9),"九条裟罗",4,ContextCompat.getDrawable(context, R.drawable.star4),"雷","女","弓"));
        return roleList;
    }
    public void loadData() {
        setMutableLiveData(getRoleListData());
    }

    public boolean loadSearchData(String search){
        if(!TextUtils.isEmpty(search)){
            List<Role> list=new ArrayList<>();
            for (Role item:getRoleListData()) {
                if(item.getName().equals(search) || item.getAttribute().equals(search) || item.getGender().equals(search) || item.getWeapon().equals(search)){
                    list.add(item);
                }
            }
            if(list.size()!=0){
                setMutableLiveData(list);
                return true;
            }else {
                return false;
            }
        }
        return false;
    }
    public void loadAttributeData(String attribute){
        if(!TextUtils.isEmpty(attribute)){
            List<Role> list=new ArrayList<>();
            for (Role item:getRoleListData()) {
                if(item.getAttribute().equals(attribute)){
                    list.add(item);
                }
            }
            setMutableLiveData(list);
        }
    }

    public void loadStarData(String star){
        if(!TextUtils.isEmpty(star)){
            int i=0;
            if(star.equals("一星")){
                i=1;
            }
            else if (star.equals("二星")) {
                i=2;
            }
            else if (star.equals("三星")) {
                i=3;
            }
            else if (star.equals("四星")) {
                i=4;
            }
            else if (star.equals("五星")) {
                i=5;
            }
            List<Role> list=new ArrayList<>();
            for (Role item:getRoleListData()) {
                if(item.getStar()==i){
                    list.add(item);
                }
            }
            setMutableLiveData(list);
        }
    }
}
