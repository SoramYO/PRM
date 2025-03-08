package com.example.myorderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class FoodActivity extends AppCompatActivity {

    private ListView lvFood;
    private Button btnDatMon;
    private ArrayList<Food> foodList;
    private FoodAdapter adapter;

    // Lưu món ăn được chọn
    private Food selectedFood = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        lvFood = findViewById(R.id.lvFood);
        btnDatMon = findViewById(R.id.btnDatMonFood);

        // Tạo danh sách món ăn
        foodList = new ArrayList<>();
        foodList.add(new Food("Phở Hà Nội", "Phở bò, nước dùng đậm đà", "50.000đ", R.drawable.ic_pho));
        foodList.add(new Food("Bún Bò Huế", "Bún bò cay nhẹ, mắm ruốc", "45.000đ", R.drawable.ic_bun_bo));
        foodList.add(new Food("Mì Quảng", "Mì sợi to, nước lèo đậm vị", "40.000đ", R.drawable.ic_mi_quang));
        foodList.add(new Food("Hủ Tíu Sài Gòn", "Hủ tíu xương, tôm, thịt", "35.000đ", R.drawable.ic_hu_tiu));

        // Tạo adapter
        adapter = new FoodAdapter(this, foodList);
        lvFood.setAdapter(adapter);

        // Bắt sự kiện click vào từng item
        lvFood.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedFood = foodList.get(position);
            }
        });

        // Nút "Đặt món"
        btnDatMon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedFood != null) {
                    // Gửi dữ liệu về MainActivity
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("foodName", selectedFood.getName());
                    resultIntent.putExtra("foodPrice", selectedFood.getPrice());
                    setResult(RESULT_OK, resultIntent);
                }
                finish(); // Đóng FoodActivity
            }
        });
    }
}
