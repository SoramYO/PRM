package com.example.lab5;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ItemActivity extends AppCompatActivity implements ItemAdapter.OnItemClickListener {
    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter;
    private List<ItemModel> itemList;
    private int editPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize item list and adapter
        itemList = new ArrayList<>();
        itemAdapter = new ItemAdapter(itemList, this);
        recyclerView.setAdapter(itemAdapter);

        // Add sample data
        itemList.add(new ItemModel("Android Development", "Learn to build Android apps", "Android", ""));
        itemList.add(new ItemModel("iOS Development", "Learn Swift and build iOS apps", "iOS", ""));
        itemList.add(new ItemModel("React Native", "Build cross-platform mobile apps", "Android",""));
        itemAdapter.notifyDataSetChanged();

        // Set up FAB to add new items
        FloatingActionButton fab = findViewById(R.id.fab_add);
        fab.setOnClickListener(v -> showAddEditDialog(null, -1));

        // Add back button to return to MainBasic
        Button btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> finish());
    }

    @Override
    public void onItemClick(ItemModel item, int position) {
        // Show edit dialog when an item is clicked
        showAddEditDialog(item, position);
    }

    private void showAddEditDialog(ItemModel item, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_edit_item, null);
        builder.setView(dialogView);

        EditText etTitle = dialogView.findViewById(R.id.et_title);
        EditText etDescription = dialogView.findViewById(R.id.et_description);
        RadioGroup rgCategory = dialogView.findViewById(R.id.rg_category);
        RadioButton rbAndroid = dialogView.findViewById(R.id.rb_android);
        RadioButton rbIOS = dialogView.findViewById(R.id.rb_ios);

        // Set the title based on whether we're adding or editing
        builder.setTitle(item == null ? "Add New Item" : "Edit Item");

        // If editing, populate fields with current data
        if (item != null) {
            etTitle.setText(item.getTitle());
            etDescription.setText(item.getDescription());
            if ("Android".equals(item.getCategory())) {
                rbAndroid.setChecked(true);
            } else if ("iOS".equals(item.getCategory())) {
                rbIOS.setChecked(true);
            }
        }

        builder.setPositiveButton(item == null ? "Add" : "Update", (dialog, which) -> {
            String title = etTitle.getText().toString();
            String description = etDescription.getText().toString();
            String category = rbAndroid.isChecked() ? "Android" : "iOS";
            String imageUrl = "";

            if (!title.isEmpty() && !description.isEmpty()) {
                if (item == null) {
                    // Add new item
                    itemAdapter.addItem(new ItemModel(title, description, category,imageUrl));
                } else {
                    // Update existing item
                    itemAdapter.updateItem(position, new ItemModel(title, description, category,imageUrl));
                }
            }
        });

        builder.setNegativeButton("Cancel", null);

        // If editing, add a delete button
        if (item != null) {
            builder.setNeutralButton("Delete", (dialog, which) -> {
                itemAdapter.deleteItem(position);
            });
        }

        builder.create().show();
    }
}