package com.example.myorderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class DrinkActivity extends AppCompatActivity {

    private ListView lvDrink;
    private Button btnDatMonDrink;
    private ArrayList<Drink> drinkList;
    private DrinkAdapter drinkAdapter;

    private Drink selectedDrink = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        lvDrink = findViewById(R.id.lvDrink);
        btnDatMonDrink = findViewById(R.id.btnDatMonDrink);

        // Tạo danh sách đồ uống
        drinkList = new ArrayList<>();
        drinkList.add(new Drink("Pepsi", "Nước ngọt có ga", "10.000đ", R.drawable.ic_pepsi));
        drinkList.add(new Drink("Heineken", "Bia Hà Lan", "20.000đ", R.drawable.ic_heineken));
        drinkList.add(new Drink("Tiger", "Bia Tiger", "18.000đ", R.drawable.ic_tiger));
        drinkList.add(new Drink("Sài Gòn Đỏ", "Bia Sài Gòn", "15.000đ", R.drawable.ic_saigon));

        drinkAdapter = new DrinkAdapter(this, drinkList);
        lvDrink.setAdapter(drinkAdapter);

        // Bắt sự kiện chọn item
        lvDrink.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedDrink = drinkList.get(position);
            }
        });

        // Nút "Đặt món"
        btnDatMonDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedDrink != null) {
                    // Gửi dữ liệu về MainActivity
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("drinkName", selectedDrink.getName());
                    resultIntent.putExtra("drinkPrice", selectedDrink.getPrice());
                    setResult(RESULT_OK, resultIntent);
                }
                finish();
            }
        });
    }
}
