package com.example.lab4;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class FoodAdapter extends ArrayAdapter<Food> {
    public FoodAdapter(Context context, List<Food> foods) {
        super(context, 0, foods);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_food, parent, false);
        }

        Food currentFood = getItem(position);

        ImageView imageView = listItemView.findViewById(R.id.food_image);
        TextView nameTextView = listItemView.findViewById(R.id.food_name);
        TextView descriptionTextView = listItemView.findViewById(R.id.food_description);
        TextView priceTextView = listItemView.findViewById(R.id.food_price);

        imageView.setImageResource(currentFood.getImageResource());
        nameTextView.setText(currentFood.getName());
        descriptionTextView.setText(currentFood.getDescription());
        priceTextView.setText(String.format("$%.2f", currentFood.getPrice()));

        return listItemView;
    }
}