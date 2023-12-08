package com.example.myapplication.adapter;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ItemConditionBinding;
import com.example.myapplication.entity.Condition;

import java.util.List;


public class ConditionAdapter extends RecyclerView.Adapter<ConditionAdapter.ViewHolder> {
    private List<Condition> items ;

    //点击增加事件
    public interface OnItemOnclickLisenter {
        void OnItemOnclick(ItemConditionBinding binding, int position,Condition conditionData);
    }
    private OnItemOnclickLisenter clickLisenter;
    public void setOnItemOnclickLisenter(OnItemOnclickLisenter lisenter) {
        this.clickLisenter = lisenter;
    }
    public ConditionAdapter(List<Condition> items) {
        this.items = items;
    }

    public List<Condition> getItems() {
        return items;
    }

    public void setItems(List<Condition> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemConditionBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_condition, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.setCondition(items.get(position));
        holder.binding.getRoot().setOnClickListener(v ->  clickLisenter.OnItemOnclick(holder.binding, position,items.get(position)));
        holder.binding.executePendingBindings();
    }


    @Override
    public int getItemCount() {
        return items.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemConditionBinding binding;
        /**
         * @param binding
         * */
        public ViewHolder(ItemConditionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
