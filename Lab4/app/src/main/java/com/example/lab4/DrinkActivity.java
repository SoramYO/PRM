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

public class DrinkActivity extends AppCompatActivity {
    private ListView drinkListView;
    private DrinkAdapter adapter;
    private List<Drink> drinkList;
    private Drink selectedDrink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        drinkListView = findViewById(R.id.drinkList);
        Button btnOrderDrink = findViewById(R.id.btnOrderDrink);

        // Initialize drink list
        drinkList = new ArrayList<>();
        drinkList.add(new Drink("Number 1", "Enegy drink", 2.0, R.drawable.number1));
        drinkList.add(new Drink("Rồng đỏ", "Enegy drink", 3.5, R.drawable.rong_do));
        drinkList.add(new Drink("Tài lộc", "energy-drink-bottle", 3.0, R.drawable.sting));
        drinkList.add(new Drink("Tài lộc vàng", "energy-drink-bottle", 2.5, R.drawable.sting_vang));

        adapter = new DrinkAdapter(this, drinkList);
        drinkListView.setAdapter(adapter);

        drinkListView.setOnItemClickListener((parent, view, position, id) -> {
            for (int i = 0; i < parent.getChildCount(); i++) {
                parent.getChildAt(i).setSelected(false);
            }
            // Set new selection
            view.setSelected(true);
            selectedDrink = drinkList.get(position);
        });

        btnOrderDrink.setOnClickListener(v -> {
            if (selectedDrink != null) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("drinkName", selectedDrink.getName());
                resultIntent.putExtra("drinkPrice", selectedDrink.getPrice());
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}