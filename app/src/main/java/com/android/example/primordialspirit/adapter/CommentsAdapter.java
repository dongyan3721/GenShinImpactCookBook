package com.android.example.primordialspirit.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.android.example.primordialspirit.R;
import com.android.example.primordialspirit.entity.extend_for_listview.CommentsListViewItem;

import java.util.List;

public class CommentsAdapter extends ArrayAdapter<CommentsListViewItem> {

    // resource表示list中各个item的布局
    public CommentsAdapter(Context context, int resource, List<CommentsListViewItem> items){
        super(context, resource, items);
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        CommentsListViewItem item = getItem(position);
        @SuppressLint("ViewHolder") View view = LayoutInflater.from(getContext()).inflate(R.layout.item_comments_layout, parent, false);
        assert item != null;
        ((TextView)view.findViewById(R.id.comments_address)).setText("ip属地："+item.getAddress());
        ((TextView)view.findViewById(R.id.comments_adder)).setText(item.getAdder());
        ((TextView)view.findViewById(R.id.comments_content)).setText(item.getContent());
        ((ImageView)view.findViewById(R.id.comments_user_icon)).setImageResource(item.getImgId());
        return view;
    }
}
