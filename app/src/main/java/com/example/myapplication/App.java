package com.example.myapplication;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.myapplication.model.RoleViewModel;

import java.io.IOException;


public class App extends Application implements ViewModelStoreOwner {

    private final ViewModelStore viewModelStore = new ViewModelStore();
    private ViewModelProvider.Factory factory;
    @Override
    public void onCreate() {
        super.onCreate();
        MySqliteOpenHelper myHelper = new MySqliteOpenHelper(this);
        try {
            myHelper.CopyDBFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @NonNull
    @Override
    public ViewModelStore getViewModelStore() {
        return viewModelStore;
    }

    /**
     * 获取一个全局的ViewModel
     */
    public ViewModelProvider getAppViewModelProvider(){
        return new ViewModelProvider(this, this.getAppFactory());
    }

    private ViewModelProvider.Factory getAppFactory(){
        if (factory == null) {
            factory = (ViewModelProvider.Factory) ViewModelProvider.AndroidViewModelFactory.getInstance(this);
        }
        return factory;
    }

    //定义扩展方法
    public static RoleViewModel getAppViewModel(Application application) {
        return ((App)application).getAppViewModelProvider().get(RoleViewModel.class);
    }

}
