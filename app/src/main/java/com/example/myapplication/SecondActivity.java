package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.example.myapplication.adapter.ConditionAdapter;
import com.example.myapplication.adapter.DataAdapter;
import com.example.myapplication.databinding.ActivitySecondBinding;
import com.example.myapplication.databinding.ConditionBinding;
import com.example.myapplication.databinding.ItemConditionBinding;
import com.example.myapplication.databinding.ItemDataBinding;
import com.example.myapplication.entity.Condition;
import com.example.myapplication.entity.Role;
import com.example.myapplication.model.RoleViewModel;

import java.util.ArrayList;
import java.util.List;

public class   SecondActivity extends AppCompatActivity implements View.OnClickListener{
    ActivitySecondBinding secondBinding;
    DataAdapter adapter;
    RoleViewModel roleViewModel;
    Handler handler=new Handler();

    int conditionNumber=0;
    String conditionValue;
    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        secondBinding = DataBindingUtil.setContentView(this, R.layout.activity_second);
        secondBinding.btAttribute.setOnClickListener(this);
        secondBinding.btStar.setOnClickListener(this);
        secondBinding.btSearch.setOnClickListener(this);
        secondBinding.btUp.setOnClickListener(this);
        init();
        roleViewModel = App.getAppViewModel(getApplication());
        roleViewModel.getMutableLiveData(getApplicationContext()).observe(this, weathers -> {
            adapter.setItems(weathers);
            adapter.notifyDataSetChanged();
        });
        roleViewModel.loadData();
    }
    public void init(){
        GridLayoutManager gridLayoutManager = new GridLayoutManager(SecondActivity.this, 3);
        gridLayoutManager.scrollToPosition(0);
        secondBinding.recyclerView.setLayoutManager(gridLayoutManager);

        adapter = new DataAdapter();
        secondBinding.recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_search:
                if(secondBinding.editView.getVisibility()==View.VISIBLE){
                    secondBinding.editView.setVisibility(View.GONE);
                    String s=secondBinding.editView.getText().toString();//编辑框输入的值必须跟角色名称、属性、性别、武器字体一致才能搜索到
                    boolean b = roleViewModel.loadSearchData(s);
                    if(!b){
                        ToastUtils.showShort("搜索不存在");
                    }
                }else {
                    secondBinding.editView.setText(null);
                    secondBinding.editView.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.bt_up:
                secondBinding.recyclerView.scrollToPosition(0);
                break;
            case R.id.bt_attribute:
                conditionNumber=1;
                List<Condition> attributeCondition=new ArrayList<>();
                attributeCondition.add(new Condition("全部"));
                attributeCondition.add(new Condition("风"));
                attributeCondition.add(new Condition("火"));
                attributeCondition.add(new Condition("雷"));
                attributeCondition.add(new Condition("岩"));
                attributeCondition.add(new Condition("水"));
                attributeCondition.add(new Condition("冰"));
                attributeCondition.add(new Condition("草"));
                getCondition(attributeCondition,"属性筛选");
                break;
            case R.id.bt_star:
                conditionNumber=2;
                List<Condition> starCondition=new ArrayList<>();
                starCondition.add(new Condition("全部"));
                starCondition.add(new Condition("一星"));
                starCondition.add(new Condition("二星"));
                starCondition.add(new Condition("三星"));
                starCondition.add(new Condition("四星"));
                starCondition.add(new Condition("五星"));
                getCondition(starCondition,"星级筛选");
                break;

        }
    }
    public void getCondition(List<Condition> listData,String name){
        ConditionBinding x = DataBindingUtil.inflate(getLayoutInflater(), R.layout.condition, null, false);
        ConditionAdapter conditionAdapter = new ConditionAdapter(listData);
        x.rc.setLayoutManager(new GridLayoutManager(SecondActivity.this, 3));//
//        x.stockRecyclerview.addItemDecoration(new SpacesItemDecoration(15));//添加分割线
        x.rc.setHasFixedSize(true);
        x.rc.setAdapter(conditionAdapter);
        conditionAdapter.setOnItemOnclickLisenter(new ConditionAdapter.OnItemOnclickLisenter() {
            @Override
            public void OnItemOnclick(ItemConditionBinding binding, int position, Condition conditionData) {
                conditionValue=listData.get(position).getValue();
                ToastUtils.showShort(listData.get(position).getValue().toString());
            }
        });
        AlertDialog builder = new AlertDialog.Builder(this).setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(!conditionValue.isEmpty() && conditionValue!=null){
                    if(conditionValue.equals("全部")){
                        roleViewModel.loadData();
                    }else {
                        if(conditionNumber==1){
                            roleViewModel.loadAttributeData(conditionValue);
                        }
                        if(conditionNumber==2){
                            roleViewModel.loadStarData(conditionValue);
                        }
                    }
                    ToastUtils.showShort("确认");
                }else {
                    ToastUtils.showShort("没选择任何值");
                }
                conditionNumber=0;
                conditionValue=null;
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               ToastUtils.showShort("取消");
            }
        }).create();
        builder.setTitle(name);
        builder.setView(x.getRoot());
        builder.setCancelable(false);

        builder.show();//设置弹出框的取消按钮
        builder.getWindow().setLayout(850,1000);//设置弹出框宽高
        builder.getWindow().setDimAmount(0);//遮罩层
    }
}
