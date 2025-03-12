package com.example.lab5;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private List<ItemModel> itemList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(ItemModel item, int position);
    }

    public ItemAdapter(List<ItemModel> itemList, OnItemClickListener listener) {
        this.itemList = itemList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout, parent, false);
        return new ItemViewHolder(view);
    }

@Override
public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
    ItemModel item = itemList.get(position);
    holder.titleTextView.setText(item.getTitle());
    holder.descriptionTextView.setText(item.getDescription());
    
    // Check radio button based on category
    if ("Android".equals(item.getCategory())) {
        holder.categoryTextView.check(R.id.rb_android);
    } else if ("iOS".equals(item.getCategory())) {
        holder.categoryTextView.check(R.id.rb_ios);
    }
    
    holder.imageView.setText(item.getImageUrl()); // Set image URL to EditText

    holder.itemView.setOnClickListener(v -> {
        if (listener != null) listener.onItemClick(item, position);
    });
}

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void addItem(ItemModel item) {
        itemList.add(item);
        notifyItemInserted(itemList.size() - 1);
    }

    public void updateItem(int position, ItemModel item) {
        itemList.set(position, item);
        notifyItemChanged(position);
    }

    public void deleteItem(int position) {
        itemList.remove(position);
        notifyItemRemoved(position);
    }

public static class ItemViewHolder extends RecyclerView.ViewHolder {
    EditText titleTextView;
    EditText descriptionTextView;
    RadioGroup categoryTextView;
    EditText imageView;

    public ItemViewHolder(@NonNull View itemView) {
        super(itemView);
        titleTextView = itemView.findViewById(R.id.title);
        descriptionTextView = itemView.findViewById(R.id.description);
        categoryTextView = itemView.findViewById(R.id.category);
        imageView = itemView.findViewById(R.id.image);
    }
}
}