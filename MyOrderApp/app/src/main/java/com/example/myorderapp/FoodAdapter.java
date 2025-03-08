package com.example.myorderapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class FoodAdapter extends BaseAdapter {

    private Context context;
    private List<Food> foodList;

    public FoodAdapter(Context context, List<Food> foodList) {
        this.context = context;
        this.foodList = foodList;
    }

    @Override
    public int getCount() {
        return foodList.size();
    }

    @Override
    public Object getItem(int position) {
        return foodList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // Tạo View cho từng item trong ListView
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_food, parent, false);
            holder = new ViewHolder();
            holder.imgFood = convertView.findViewById(R.id.imgFood);
            holder.tvName = convertView.findViewById(R.id.tvFoodName);
            holder.tvDescription = convertView.findViewById(R.id.tvFoodDescription);
            holder.tvPrice = convertView.findViewById(R.id.tvFoodPrice);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Food food = foodList.get(position);
        holder.imgFood.setImageResource(food.getImageResId());
        holder.tvName.setText(food.getName());
        holder.tvDescription.setText(food.getDescription());
        holder.tvPrice.setText(food.getPrice());

        return convertView;
    }

    static class ViewHolder {
        ImageView imgFood;
        TextView tvName;
        TextView tvDescription;
        TextView tvPrice;
    }
}
