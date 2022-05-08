package com.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.R;

public class SpinnerImageAdapter extends BaseAdapter {

    private int[] imgs;

    public SpinnerImageAdapter(int[] imgs) {
        this.imgs = imgs;
    }

    @Override
    public int getCount() {
        return imgs.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_view_holder, parent, false);
        ImageView imageView =  view.findViewById(R.id.image_view_holder_img);
        imageView.setImageResource(imgs[position]);
        return view;
    }
}
