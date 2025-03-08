package com.example.lab4;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private TextView selectedFoodText, selectedDrinkText;
    private static final int REQUEST_CODE_FOOD = 1;
    private static final int REQUEST_CODE_DRINK = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        selectedFoodText = findViewById(R.id.selectedFoodText);
        selectedDrinkText = findViewById(R.id.selectedDrinkText);

        Button btnFood = findViewById(R.id.btnFood);
        Button btnDrink = findViewById(R.id.btnDrink);
        Button btnExit = findViewById(R.id.btnExit);

        btnFood.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FoodActivity.class);
            startActivityForResult(intent, REQUEST_CODE_FOOD);
        });

        btnDrink.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DrinkActivity.class);
            startActivityForResult(intent, REQUEST_CODE_DRINK);
        });

        btnExit.setOnClickListener(v -> finish());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_FOOD) {
                String foodName = data.getStringExtra("foodName");
                double foodPrice = data.getDoubleExtra("foodPrice", 0.0);
                selectedFoodText.setText("Món ăn đã chọn: " + foodName + " - $" + String.format("%.2f", foodPrice));
            } else if (requestCode == REQUEST_CODE_DRINK) {
                String drinkName = data.getStringExtra("drinkName");
                double drinkPrice = data.getDoubleExtra("drinkPrice", 0.0);
                selectedDrinkText.setText("Đồ uống đã chọn: " + drinkName + " - $" + String.format("%.2f", drinkPrice));
            }
        }
    }
}