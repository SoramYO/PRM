package com.example.listviewbasic;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileInputStream;
import java.util.ArrayList;

public class FruitAdapter extends ArrayAdapter<Fruit> {
    private Context context;
    private ArrayList<Fruit> fruits;

    public FruitAdapter(Context context, ArrayList<Fruit> fruits) {
        super(context, R.layout.list_item, fruits);
        this.context = context;
        this.fruits = fruits;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
            holder = new ViewHolder();
            holder.imageView = convertView.findViewById(R.id.imageView);
            holder.titleTextView = convertView.findViewById(R.id.titleTextView);
            holder.descriptionTextView = convertView.findViewById(R.id.descriptionTextView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Fruit fruit = fruits.get(position);
        
        // Load ảnh từ internal storage
        try {
            FileInputStream fis = context.openFileInput(fruit.getImagePath());
            Bitmap bitmap = BitmapFactory.decodeStream(fis);
            holder.imageView.setImageBitmap(bitmap);
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
            holder.imageView.setImageResource(android.R.drawable.ic_menu_gallery);
        }
        
        holder.titleTextView.setText(fruit.getTitle());
        holder.descriptionTextView.setText(fruit.getDescription());

        return convertView;
    }

    static class ViewHolder {
        ImageView imageView;
        TextView titleTextView;
        TextView descriptionTextView;
    }
} 