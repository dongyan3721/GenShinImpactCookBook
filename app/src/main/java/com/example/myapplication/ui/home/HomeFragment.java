package com.example.myapplication.ui.home;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.myapplication.App;
import com.example.myapplication.DetailActivity;
import com.example.myapplication.R;
import com.example.myapplication.adapter.ConditionAdapter;
import com.example.myapplication.adapter.DataAdapter;
import com.example.myapplication.databinding.ConditionBinding;
import com.example.myapplication.databinding.FragmentHomeBinding;
import com.example.myapplication.databinding.ItemConditionBinding;
import com.example.myapplication.databinding.ItemDataBinding;
import com.example.myapplication.entity.Condition;
import com.example.myapplication.entity.Role;
import com.blankj.utilcode.util.ToastUtils;
import com.example.myapplication.model.RoleViewModel;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements View.OnClickListener{

    private FragmentHomeBinding binding;

    DataAdapter adapter;
    RoleViewModel roleViewModel;
    Handler handler=new Handler();

    int conditionNumber=0;
    String conditionValue;

    List<Role> synthesisList=new ArrayList<>();

    FragmentManager fm;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        HomeViewModel homeViewModel =
//                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home,container,false);
        View root = binding.getRoot();

        return root;
    }
    @SuppressLint({"NotifyDataSetChanged", "Range"})
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btAttribute.setOnClickListener(this);
        binding.btStar.setOnClickListener(this);
        binding.btSearch.setOnClickListener(this);
        binding.btUp.setOnClickListener(this);

        fm = getFragmentManager();


        init();
        roleViewModel = App.getAppViewModel(getActivity().getApplication());
        roleViewModel.getMutableLiveData(requireContext()).observe(getViewLifecycleOwner(), roles -> {
            adapter.setItems(roles);
            adapter.notifyDataSetChanged();
        });

        //接入SQLite获取数据
        String database_path = getActivity().getDatabasePath("gen_shin.db").toString();
        SQLiteDatabase myDatabase = SQLiteDatabase.openDatabase(database_path, null, SQLiteDatabase.ENABLE_WRITE_AHEAD_LOGGING);
        try {
            List<Role> data = roleViewModel.getData(myDatabase);
            InputStream is = null;
            for (Role item:data) {
                is=getActivity().getAssets().open(item.getImgStr().substring(2));
                Bitmap bm= BitmapFactory.decodeStream(is);
                Drawable da=new BitmapDrawable(getResources(),bm);
                item.setImg(da);
                Log.e("aa","aa"+item.getName()+item.getStar());
                synthesisList.add(item);
            }
            assert is != null;
            is.close();
            roleViewModel.setMutableLiveData(synthesisList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void init(){
        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 3);
        gridLayoutManager.scrollToPosition(0);
        binding.recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new DataAdapter();
        binding.recyclerView.setAdapter(adapter);
        adapter.setOnItemOnclickLisenter(new DataAdapter.OnItemOnclickLisenter() {
            @Override
            public void OnItemOnclick(ItemDataBinding binding, int position, Role roleData) {
                Intent intent=new Intent(requireActivity(), DetailActivity.class);
                intent.putExtra("roleData",roleData.getName());
                startActivity(intent);
//                FragmentTransaction ft = fm.beginTransaction();//注意。一个transaction 只能commit一次，所以不要定义成全局变量
//                DetailFragment df = new DetailFragment();
//                Bundle bundle = new Bundle();
//                bundle.putString("name", roleData.getName());
//                df.setArguments(bundle);
//                ft.replace(R.id.detail, df);
//                ft.addToBackStack(null);
//                ft.commit();
//                getActivity().getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.nav_host_fragment_activity_main,new DetailFragment())
//                        .addToBackStack(null)
//                        .commit();



            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_search:
                if(binding.editView.getVisibility()==View.VISIBLE){
                    binding.editView.setVisibility(View.GONE);
                    String s=binding.editView.getText().toString();//编辑框输入的值必须跟角色名称、属性、性别、武器字体一致才能搜索到
//                    boolean b = roleViewModel.loadSearchData(s);
                    List<Role> searchList =new ArrayList<>();
                    String database_path = getActivity().getDatabasePath("gen_shin.db").toString();
                    SQLiteDatabase myDatabase = SQLiteDatabase.openDatabase(database_path, null, SQLiteDatabase.ENABLE_WRITE_AHEAD_LOGGING);
                    try {
                        searchList = roleViewModel.loadSearchData(myDatabase,s);
                        InputStream is = null;
                        for (Role item:searchList) {
                            is=getActivity().getAssets().open(item.getImgStr().substring(2));
                            Bitmap bm= BitmapFactory.decodeStream(is);
                            Drawable da=new BitmapDrawable(getResources(),bm);
                            item.setImg(da);
                            Log.e("aa","aa"+item.getName()+item.getStar());
                            synthesisList.add(item);
                        }
                        assert is != null;
                        is.close();
                        roleViewModel.setMutableLiveData(searchList);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if(searchList==null){
                        ToastUtils.showShort("搜索不存在");
                    }
                }else {
                    binding.editView.setText(null);
                    binding.editView.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.bt_up:
                binding.recyclerView.scrollToPosition(0);
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
        x.rc.setLayoutManager(new GridLayoutManager(requireContext(), 3));//
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
        AlertDialog builder = new AlertDialog.Builder(requireContext()).setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(!conditionValue.isEmpty() && conditionValue!=null){
                    if(conditionValue.equals("全部")){
//                        roleViewModel.loadData();
                        roleViewModel.setMutableLiveData(synthesisList);
                    }else {
                        if(conditionNumber==1){
                            roleViewModel.loadAttributeData(synthesisList,conditionValue);
                        }
                        if(conditionNumber==2){
                            roleViewModel.loadStarData(synthesisList,conditionValue);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}