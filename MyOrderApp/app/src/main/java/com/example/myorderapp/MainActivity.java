package com.example.myorderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_FOOD = 100;
    private static final int REQUEST_CODE_DRINK = 200;

    private Button btnChooseFood, btnChooseDrink, btnExit;
    private TextView tvResult;

    private String chosenFood = "";
    private String chosenDrink = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnChooseFood = findViewById(R.id.btnChooseFood);
        btnChooseDrink = findViewById(R.id.btnChooseDrink);
        btnExit       = findViewById(R.id.btnExit);
        tvResult      = findViewById(R.id.tvResult);

        // Nút chọn thức ăn
        btnChooseFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FoodActivity.class);
                startActivityForResult(intent, REQUEST_CODE_FOOD);
            }
        });

        // Nút chọn đồ uống
        btnChooseDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DrinkActivity.class);
                startActivityForResult(intent, REQUEST_CODE_DRINK);
            }
        });

        // Nút thoát
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == REQUEST_CODE_FOOD) {
                chosenFood = data.getStringExtra("foodName");
                String foodPrice = data.getStringExtra("foodPrice");
                chosenFood = chosenFood + " (" + foodPrice + ")";
            } else if (requestCode == REQUEST_CODE_DRINK) {
                chosenDrink = data.getStringExtra("drinkName");
                String drinkPrice = data.getStringExtra("drinkPrice");
                chosenDrink = chosenDrink + " (" + drinkPrice + ")";
            }

            tvResult.setText("Bạn đã chọn: " + chosenFood + " - " + chosenDrink);
        }
    }
}
