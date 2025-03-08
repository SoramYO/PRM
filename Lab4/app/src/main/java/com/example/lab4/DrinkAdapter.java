package com.example.lab4;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class DrinkAdapter extends ArrayAdapter<Drink> {
    public DrinkAdapter(Context context, List<Drink> drinks) {
        super(context, 0, drinks);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_drink, parent, false);
        }

        Drink currentDrink = getItem(position);

        ImageView imageView = listItemView.findViewById(R.id.drink_image);
        TextView nameTextView = listItemView.findViewById(R.id.drink_name);
        TextView descriptionTextView = listItemView.findViewById(R.id.drink_description);
        TextView priceTextView = listItemView.findViewById(R.id.drink_price);

        imageView.setImageResource(currentDrink.getImageResource());
        nameTextView.setText(currentDrink.getName());
        descriptionTextView.setText(currentDrink.getDescription());
        priceTextView.setText(String.format("$%.2f", currentDrink.getPrice()));

        return listItemView;
    }
}