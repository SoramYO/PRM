package com.example.myorderapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class DrinkAdapter extends BaseAdapter {

    private Context context;
    private List<Drink> drinkList;

    public DrinkAdapter(Context context, List<Drink> drinkList) {
        this.context = context;
        this.drinkList = drinkList;
    }

    @Override
    public int getCount() {
        return drinkList.size();
    }

    @Override
    public Object getItem(int position) {
        return drinkList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_drink, parent, false);
            holder = new ViewHolder();
            holder.imgDrink = convertView.findViewById(R.id.imgDrink);
            holder.tvDrinkName = convertView.findViewById(R.id.tvDrinkName);
            holder.tvDrinkDesc = convertView.findViewById(R.id.tvDrinkDescription);
            holder.tvDrinkPrice = convertView.findViewById(R.id.tvDrinkPrice);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Drink drink = drinkList.get(position);
        holder.imgDrink.setImageResource(drink.getImageResId());
        holder.tvDrinkName.setText(drink.getName());
        holder.tvDrinkDesc.setText(drink.getDescription());
        holder.tvDrinkPrice.setText(drink.getPrice());

        return convertView;
    }

    static class ViewHolder {
        ImageView imgDrink;
        TextView tvDrinkName;
        TextView tvDrinkDesc;
        TextView tvDrinkPrice;
    }
}
