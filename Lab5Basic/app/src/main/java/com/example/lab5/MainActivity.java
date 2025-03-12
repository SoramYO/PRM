package com.example.lab5;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        userList = new ArrayList<>();

        // Thêm dữ liệu người dùng từ hình ảnh
        userList.add(new User("NguyenTT", "Tran Thanh Nguyen", "nguyentt@fpt.edu.vn"));
        userList.add(new User("Antv", "Tran Van An", "antv@gmail.com"));
        userList.add(new User("BangTT", "Tran Thanh Bang", "bangtt@gmail.com"));
        userList.add(new User("KhangTT", "Tran Thanh Khang", "khangtt@gmail.com"));
        userList.add(new User("BaoNT", "Nguyen Thanh Bao", "baont@gmail.com"));
        userList.add(new User("HungVH", "Vu Huy Hung", "hungvh@gmail.com"));

        userAdapter = new UserAdapter(userList);
        recyclerView.setAdapter(userAdapter);
    }
}