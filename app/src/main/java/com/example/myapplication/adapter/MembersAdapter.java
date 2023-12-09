package com.example.myapplication.adapter;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.entity.Members;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class MembersAdapter extends ArrayAdapter<Members> {
    private Context context;
    public MembersAdapter(Context context, ArrayList<Members> members)
    {
        super(context,0, members);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        // Get the data item for this position
        Members members = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.Name);
        TextView tvBirthday = (TextView) convertView.findViewById(R.id.detail);
        ImageView imageView=convertView.findViewById(R.id.image);

       
        // Populate the data into the template view suing the data object
        tvName.setText(members.name);
        tvBirthday.setText(members.detail);
        imageView.setImageDrawable(members.imageURL);


        // return the complete view to render on screen
        return  convertView;
    }
}
