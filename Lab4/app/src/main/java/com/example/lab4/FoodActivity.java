package com.example.lab4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class FoodActivity extends AppCompatActivity {
    private ListView foodListView;
    private FoodAdapter adapter;
    private List<Food> foodList;
    private Food selectedFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        foodListView = findViewById(R.id.foodList);
        Button btnOrderFood = findViewById(R.id.btnOrderFood);

        // Initialize food list
        foodList = new ArrayList<>();
        foodList.add(new Food("Bánh canh", "Noodle soup with beef", 5.0, R.drawable.banh_canh));
        foodList.add(new Food("Bún Bò Huế", "Spicy beef noodle soup", 6.0, R.drawable.bun_bo_hue));
        foodList.add(new Food("Cơm tấm", "Rice with pork", 4.5, R.drawable.com_tam));
        foodList.add(new Food("Bánh xèo", "Rice cake", 5.5, R.drawable.banh_xeo));

        adapter = new FoodAdapter(this, foodList);
        foodListView.setAdapter(adapter);

foodListView.setOnItemClickListener((parent, view, position, id) -> {
    // Clear previous selection
    for (int i = 0; i < parent.getChildCount(); i++) {
        parent.getChildAt(i).setSelected(false);
    }
    // Set new selection
    view.setSelected(true);
    selectedFood = foodList.get(position);
});

        btnOrderFood.setOnClickListener(v -> {
            if (selectedFood != null) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("foodName", selectedFood.getName());
                resultIntent.putExtra("foodPrice", selectedFood.getPrice());
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}