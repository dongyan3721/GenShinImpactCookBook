package com.android.example.primordialspirit.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.android.example.primordialspirit.R;
import com.android.example.primordialspirit.databinding.ItemDataBinding;
import com.android.example.primordialspirit.entity.Role;


import java.util.ArrayList;
import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private List<Role> items ;

    //点击增加事件
    public interface OnItemOnclickLisenter {
        void OnItemOnclick(ItemDataBinding binding, int position, Role roleData);
    }
    private OnItemOnclickLisenter clickLisenter;
    public void setOnItemOnclickLisenter(OnItemOnclickLisenter lisenter) {
        this.clickLisenter = lisenter;
    }



    public DataAdapter() {
        this.items = new ArrayList<>();
    }

    public List<Role> getItems() {
        return items;
    }

    public void setItems(List<Role> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_data, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.binding.setRole(items.get(position));
        holder.binding.executePendingBindings();
        holder.binding.getRoot().setOnClickListener(v ->  clickLisenter.OnItemOnclick(holder.binding, position,items.get(position)));
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemDataBinding binding;
        /**
         * @param binding
         * */
        public ViewHolder(ItemDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }
}
